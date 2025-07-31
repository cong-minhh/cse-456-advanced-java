package vn.edu.eiu.lab5.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> items = new ArrayList<>();
    
    @Transient
    private BigDecimal totalAmount;

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
    public void addItem(Product product, int quantity) {
        InvoiceItem item = new InvoiceItem(product, quantity);
        items.add(item);
        item.setInvoice(this);
    }
    
    public BigDecimal getTotalAmount() {
        if (totalAmount == null) {
            totalAmount = items.stream()
                    .map(InvoiceItem::getTotalPrice)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        }
        return totalAmount;
    }
    
    private String generateInvoiceNumber() {
        return "INV-" + System.currentTimeMillis();
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

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", issueDate=" + issueDate +
                ", customer=" + customer.getName() +
                ", totalAmount=" + getTotalAmount() +
                ", itemsCount=" + items.size() +
                '}';
    }
}
