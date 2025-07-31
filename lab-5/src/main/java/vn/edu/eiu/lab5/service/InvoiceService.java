package vn.edu.eiu.lab5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.edu.eiu.lab5.model.*;
import vn.edu.eiu.lab5.repository.InvoiceRepository;
import vn.edu.eiu.lab5.repository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, ProductRepository productRepository) {
        this.invoiceRepository = invoiceRepository;
        this.productRepository = productRepository;
    }

    public Invoice findById(Long id) {
        return invoiceRepository.findById(id);
    }

    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    public Invoice save(Invoice invoice) {
        if (invoice.getId() == null) {
            invoice.setIssueDate(LocalDate.now());
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
        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setIssueDate(LocalDate.now());
        return invoice;
    }

    public void addProduct(Invoice invoice, Long productId, int quantity) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        
        Product product = productRepository.findById(productId);
        if (product == null) {
            throw new IllegalStateException("Product not found with id: " + productId);
        }
        
        // Check if product already exists in the invoice
        for (InvoiceItem item : invoice.getItems()) {
            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(item.getQuantity() + quantity);
                item.calculateTotal();
                invoice.calculateTotal();
                return;
            }
        }
        
        // Add new item
        InvoiceItem item = new InvoiceItem();
        item.setInvoice(invoice);
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice());
        item.calculateTotal();
        
        invoice.getItems().add(item);
        invoice.calculateTotal();
    }
    
    public void removeProduct(Invoice invoice, Long productId) {
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice cannot be null");
        }
        
        boolean removed = invoice.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        if (removed) {
            invoice.calculateTotal();
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
        for (InvoiceItem item : invoice.getItems()) {
            Product product = item.getProduct();
            sb.append(String.format("%-30s %5d x $%8.2f = $%10.2f%n", 
                product.getName(), 
                item.getQuantity(), 
                item.getUnitPrice().doubleValue(),
                item.getTotalPrice().doubleValue()));
        }
        sb.append("--------------------------------------------------\n");
        sb.append(String.format("TOTAL: $%10.2f%n", invoice.getTotalAmount().doubleValue()));
        sb.append("==================================================\n");
        
        return sb.toString();
    }
    
    public List<Invoice> findByCustomerId(Long customerId) {
        return invoiceRepository.findByCustomerId(customerId);
    }
}
