package vn.edu.eiu.lab5;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import vn.edu.eiu.lab5.config.AppConfig;
import vn.edu.eiu.lab5.model.Customer;
import vn.edu.eiu.lab5.model.Invoice;
import vn.edu.eiu.lab5.model.Product;
import vn.edu.eiu.lab5.service.CustomerService;
import vn.edu.eiu.lab5.service.InvoiceService;
import vn.edu.eiu.lab5.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static CustomerService customerService;
    private static ProductService productService;
    private static InvoiceService invoiceService;

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)) {
            // Get service beans
            customerService = context.getBean(CustomerService.class);
            productService = context.getBean(ProductService.class);
            invoiceService = context.getBean(InvoiceService.class);

            System.out.println("=== Electronic Sales System ===");
            boolean running = true;

            while (running) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Create New Customer");
                System.out.println("2. List All Products");
                System.out.println("3. Create New Invoice");
                System.out.println("4. View Customer Invoices");
                System.out.println("5. Exit");
                System.out.print("Select an option: ");

                int choice = readIntInput();

                switch (choice) {
                    case 1 -> createNewCustomer();
                    case 2 -> listAllProducts();
                    case 3 -> createNewInvoice();
                    case 4 -> viewCustomerInvoices();
                    case 5 -> running = false;
                    default -> System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void createNewCustomer() {
        System.out.println("\n=== Create New Customer ===");
        
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();
        
        try {
            Customer customer = new Customer(name, email, phone);
            customerService.save(customer);
            System.out.println("Customer created successfully! ID: " + customer.getId());
        } catch (Exception e) {
            System.out.println("Error creating customer: " + e.getMessage());
        }
    }

    private static void listAllProducts() {
        System.out.println("\n=== Available Products ===");
        List<Product> products = productService.findAll();
        
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }
        
        System.out.printf("%-5s %-30s %-50s %10s%n", "ID", "Name", "Description", "Price");
        System.out.println("-".repeat(100));
        
        for (Product product : products) {
            String description = product.getDescription();
            if (description.length() > 45) {
                description = description.substring(0, 42) + "...";
            }
            System.out.printf("%-5d %-30s %-50s $%9.2f%n",
                    product.getId(),
                    product.getName(),
                    description,
                    product.getPrice());
        }
    }

    private static void createNewInvoice() {
        System.out.println("\n=== Create New Invoice ===");
        
        // List all customers
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            System.out.println("No customers found. Please create a customer first.");
            return;
        }
        
        System.out.println("\nSelect a customer:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, 
                    customers.get(i).getName(), 
                    customers.get(i).getEmail());
        }
        
        System.out.print("Enter customer number: ");
        int customerIndex = readIntInput() - 1;
        
        if (customerIndex < 0 || customerIndex >= customers.size()) {
            System.out.println("Invalid customer selection.");
            return;
        }
        
        Customer customer = customers.get(customerIndex);
        Invoice invoice = invoiceService.createInvoice(customer.getId());
        
        boolean addingItems = true;
        while (addingItems) {
            System.out.println("\n=== Add Products to Invoice ===");
            listAllProducts();
            
            System.out.print("\nEnter product ID to add (or 0 to finish): ");
            long productId = readLongInput();
            
            if (productId == 0) {
                addingItems = false;
                continue;
            }
            
            System.out.print("Enter quantity: ");
            int quantity = readIntInput();
            
            try {
                invoiceService.addProductToInvoice(invoice.getId(), productId, quantity);
                System.out.println("Product added to invoice.");
                
                // Refresh invoice to get updated items
                invoice = invoiceService.findById(invoice.getId());
                System.out.println(invoiceService.generateInvoiceText(invoice));
                
            } catch (Exception e) {
                System.out.println("Error adding product: " + e.getMessage());
            }
        }
        
        System.out.println("\n=== Final Invoice ===");
        System.out.println(invoiceService.generateInvoiceText(invoice));
    }

    private static void viewCustomerInvoices() {
        System.out.println("\n=== View Customer Invoices ===");
        
        // List all customers
        List<Customer> customers = customerService.findAll();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        
        System.out.println("\nSelect a customer to view invoices:");
        for (int i = 0; i < customers.size(); i++) {
            System.out.printf("%d. %s (%s)%n", i + 1, 
                    customers.get(i).getName(), 
                    customers.get(i).getEmail());
        }
        
        System.out.print("Enter customer number: ");
        int customerIndex = readIntInput() - 1;
        
        if (customerIndex < 0 || customerIndex >= customers.size()) {
            System.out.println("Invalid customer selection.");
            return;
        }
        
        Customer customer = customers.get(customerIndex);
        List<Invoice> invoices = invoiceService.findByCustomerId(customer.getId());
        
        if (invoices.isEmpty()) {
            System.out.println("No invoices found for this customer.");
            return;
        }
        
        System.out.println("\n=== Invoices for " + customer.getName() + " ===");
        for (Invoice invoice : invoices) {
            System.out.println(invoiceService.generateInvoiceText(invoice));
            System.out.println("-".repeat(50));
        }
    }

    private static int readIntInput() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private static long readLongInput() {
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }
}