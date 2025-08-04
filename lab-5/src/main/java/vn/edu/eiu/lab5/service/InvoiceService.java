package vn.edu.eiu.lab5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import vn.edu.eiu.lab5.model.Customer;
import vn.edu.eiu.lab5.model.Invoice;
import vn.edu.eiu.lab5.model.Product;
import vn.edu.eiu.lab5.repository.InvoiceRepository;
import vn.edu.eiu.lab5.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final CustomerService customerService;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, 
                         ProductRepository productRepository,
                         CustomerService customerService) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
        this.customerService = customerService;
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice save(Invoice invoice) {
        if (invoice.getId() == null) {
            invoice.setIssueDate(LocalDateTime.now());
            // Generate invoice number (simple timestamp-based for now)
            if (invoice.getInvoiceNumber() == null) {
                invoice.setInvoiceNumber("INV-" + System.currentTimeMillis());
            }
            invoice.calculateTotal();
            invoiceRepository.save(invoice);
            return invoice;
        } else {
            invoice.calculateTotal();
            return invoiceRepository.update(invoice);
        }
    }

    public Invoice update(Invoice invoice) {
        return save(invoice);
    }

    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

    public Invoice createInvoice(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        // Verify customer exists
        Customer existingCustomer = customerService.findById(customer.getId());
        if (existingCustomer == null) {
            throw new IllegalStateException("Customer not found with id: " + customer.getId());
        }
        
        Invoice invoice = new Invoice();
        invoice.setCustomer(existingCustomer);
        invoice.setIssueDate(LocalDateTime.now());
        return invoice;
    }

    public Invoice addProduct(Long invoiceId, Long productId, int quantity) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("Invoice ID cannot be null");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        // Find existing invoice or create a new one
        Invoice invoice;
        if (invoiceId == 0) {
            throw new IllegalArgumentException("Invoice ID cannot be zero");
        } else {
            invoice = invoiceRepository.findById(invoiceId);
            if (invoice == null) {
                throw new IllegalStateException("Invoice not found with id: " + invoiceId);
            }
        }
        
        // Find product
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalStateException("Product not found with id: " + productId);
        }
        
        // Update invoice with new product
        invoice.setProduct(product);
        invoice.setQuantity(quantity);
        invoice.setUnitPrice(product.getPrice());
        invoice.calculateTotal();
        
        return save(invoice);
    }
    
    public void removeProduct(Long invoiceId) {
        if (invoiceId == null) {
            throw new IllegalArgumentException("Invoice ID cannot be null");
        }
        
        Invoice invoice = invoiceRepository.findById(invoiceId);
        if (invoice != null) {
            // Since we're now using a single product per invoice, we can just clear the product
            invoice.setProduct(null);
            invoice.setQuantity(0);
            invoice.setUnitPrice(BigDecimal.ZERO);
            invoice.setTotalPrice(BigDecimal.ZERO);
            save(invoice);
        }
    }
    
    public String generateInvoiceText(Invoice invoice) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append("=== INVOICE ===\n");
        sb.append("Invoice #").append(invoice.getInvoiceNumber()).append("\n");
        sb.append("Date: ").append(invoice.getIssueDate()).append("\n\n");
        
        Customer customer = invoice.getCustomer();
        sb.append("Customer:\n");
        sb.append(customer.getName()).append("\n");
        sb.append(customer.getEmail()).append("\n");
        sb.append(customer.getPhone()).append("\n\n");
        
        sb.append("Items:\n");
        sb.append("--------------------------------------------------\n");
        
        Product product = invoice.getProduct();
        if (product != null) {
            sb.append(String.format("%-30s %5d x $%8.2f = $%10.2f%n", 
                product.getName(), 
                invoice.getQuantity(), 
                invoice.getUnitPrice().doubleValue(),
                invoice.getTotalPrice().doubleValue()));
        }
        
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("TOTAL: $%10.2f%n", 
            invoice.getTotalPrice() != null ? invoice.getTotalPrice().doubleValue() : 0.0));
        sb.append("==================================================\n");
        
        return sb.toString();
    }
    
    public List<Invoice> findByCustomerId(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }
}
