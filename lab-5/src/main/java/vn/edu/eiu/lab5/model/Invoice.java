package vn.edu.eiu.lab5.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "invoice_number", nullable = false, unique = true)
    private String invoiceNumber;
    
    @Column(name = "issue_date", nullable = false)
    private LocalDateTime issueDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;
    
    @Column(name = "total_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;
    
    public void calculateTotal() {
        if (product != null && quantity > 0) {
            this.unitPrice = product.getPrice();
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity));
        } else {
            this.totalPrice = BigDecimal.ZERO;
        }
    }

    // Constructors
    public Invoice() {
        this.issueDate = LocalDateTime.now();
    }

    public Invoice(Customer customer) {
        this();
        this.customer = customer;
        this.invoiceNumber = generateInvoiceNumber();
    }

    // Business methods
    public BigDecimal getTotalAmount() {
        if (totalPrice == null) {
            calculateTotal();
        }
        return totalPrice != null ? totalPrice : BigDecimal.ZERO;
    }
    
    private String generateInvoiceNumber() {
        if (invoiceNumber == null) {
            this.invoiceNumber = "INV-" + System.currentTimeMillis();
        }
        return invoiceNumber;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", issueDate=" + issueDate +
                ", customer=" + (customer != null ? customer.getName() : "null") +
                ", product=" + (product != null ? product.getName() : "null") +
                ", quantity=" + quantity +
                ", totalAmount=" + getTotalAmount() +
                '}';
    }
}
