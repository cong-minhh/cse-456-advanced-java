package vn.edu.eiu.payment.impl;

import vn.edu.eiu.payment.PaymentMethod;

public class CardPayment implements PaymentMethod {
    private String cardNumber;
    private String expiryDate;
    private String cvv;

    public CardPayment(String cardNumber, String expiryDate, String cvv) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
    }

    @Override
    public boolean makePayment(double amount) {
        // Actual card payment processing logic would go here
        System.out.println("Processing card payment of $" + amount);
        System.out.println("Card ending with: " + cardNumber.substring(cardNumber.length() - 4));
        System.out.println("Expiry Date: " + expiryDate);
        
        // Validate card details
        if (!isCardValid()) {
            System.out.println("Payment failed: Invalid card details");
            return false;
        }
        
        // Simulate processing
        try {
            System.out.println("Verifying CVV...");
            Thread.sleep(800);
            System.out.println("Checking expiration date...");
            Thread.sleep(800);
            System.out.println("Processing transaction...");
            Thread.sleep(400);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        
        System.out.println("Card payment authorized successfully");
        return true;
    }
    
    private boolean isCardValid() {
        // Simple validation - in a real application, this would be more comprehensive
        if (expiryDate == null || expiryDate.length() != 5 || !expiryDate.contains("/")) {
            System.out.println("Invalid expiry date format. Please use MM/YY format.");
            return false;
        }
        
        if (cvv == null || cvv.length() < 3 || cvv.length() > 4) {
            System.out.println("Invalid CVV. Must be 3-4 digits.");
            return false;
        }
        
        return true;
    }
}
