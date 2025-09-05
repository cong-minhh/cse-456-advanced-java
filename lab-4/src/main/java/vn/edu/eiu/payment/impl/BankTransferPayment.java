package vn.edu.eiu.payment.impl;

import vn.edu.eiu.payment.PaymentMethod;

public class BankTransferPayment implements PaymentMethod {
    private String accountNumber;
    private String bankName;
    private String accountHolderName;

    public BankTransferPayment(String accountNumber, String bankName, String accountHolderName) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.accountHolderName = accountHolderName;
    }

    @Override
    public boolean makePayment(double amount) {
        // Actual bank transfer processing logic would go here
        System.out.println("\n=== Initiating Bank Transfer ===");
        System.out.println("Amount: $" + amount);
        System.out.println("Beneficiary: " + accountHolderName);
        System.out.println("Bank: " + bankName);
        System.out.println("Account: " + maskAccountNumber(accountNumber));
        
        // Simulate processing steps
        try {
            System.out.println("\nVerifying account details...");
            Thread.sleep(800);
            
            System.out.println("Validating recipient information...");
            if (!isValidRecipient()) {
                System.out.println("Error: Invalid recipient information");
                return false;
            }
            
            System.out.println("Initiating transfer...");
            Thread.sleep(1000);
            
            System.out.println("\nTransfer successful!");
            System.out.println(amount + " has been transferred to " + accountHolderName);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Transfer was interrupted");
            return false;
        }
        
        // Print receipt
        printReceipt(amount);
        return true;
    }

    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }
    
    private boolean isValidRecipient() {
        // In a real application, this would validate the recipient's details
        // against the bank's records
        return accountNumber != null && !accountNumber.trim().isEmpty()
                && bankName != null && !bankName.trim().isEmpty()
                && accountHolderName != null && !accountHolderName.trim().isEmpty();
    }
    
    private void printReceipt(double amount) {
        System.out.println("\n=== TRANSFER RECEIPT ===");
        System.out.println("From: Your Account");
        System.out.println("To: " + accountHolderName);
        System.out.println("Bank: " + bankName);
        System.out.println("Account: " + maskAccountNumber(accountNumber));
        System.out.println("Amount: $" + amount);
        System.out.println("Date: " + java.time.LocalDateTime.now());
        System.out.println("Status: COMPLETED");
        System.out.println("======================");
    }
}
