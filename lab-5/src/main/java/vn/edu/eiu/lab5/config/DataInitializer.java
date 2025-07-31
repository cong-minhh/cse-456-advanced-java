package vn.edu.eiu.lab5.config;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.edu.eiu.lab5.model.Product;
import vn.edu.eiu.lab5.service.ProductService;

import java.math.BigDecimal;

@Component
public class DataInitializer {

    private final ProductService productService;

    @Autowired
    public DataInitializer(ProductService productService) {
        this.productService = productService;
    }

    @PostConstruct
    public void init() {
        // Check if products already exist
        if (productService.findAll().isEmpty()) {
            // Add sample products
            productService.save(new Product("Laptop X1", "High-performance laptop with 16GB RAM, 1TB SSD, Intel i7", new BigDecimal("1299.99")));
            productService.save(new Product("Smartphone Pro", "Latest smartphone with 6.7\" OLED display, 128GB storage", new BigDecimal("899.99")));
            productService.save(new Product("Wireless Earbuds", "Noise-cancelling wireless earbuds with 30h battery", new BigDecimal("199.99")));
            productService.save(new Product("4K Smart TV", "55-inch 4K UHD Smart TV with HDR", new BigDecimal("599.99")));
            productService.save(new Product("Gaming Console", "Next-gen gaming console with 1TB storage", new BigDecimal("499.99")));
            productService.save(new Product("Smart Watch", "Fitness tracker with heart rate monitor and GPS", new BigDecimal("249.99")));
            productService.save(new Product("Bluetooth Speaker", "Portable waterproof speaker with 20h playtime", new BigDecimal("129.99")));
            productService.save(new Product("Tablet Pro", "10.5\" tablet with 256GB storage and stylus", new BigDecimal("449.99")));
        }
    }
}
