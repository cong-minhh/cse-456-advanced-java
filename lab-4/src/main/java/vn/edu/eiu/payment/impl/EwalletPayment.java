package vn.edu.eiu.payment.impl;

import vn.edu.eiu.payment.PaymentMethod;

public class EwalletPayment implements PaymentMethod {
    private String walletId;
    private String phoneNumber;

    public EwalletPayment(String walletId, String phoneNumber) {
        this.walletId = walletId;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean makePayment(double amount) {
        // Actual e-wallet payment processing logic would go here
        System.out.println("Processing e-wallet payment of $" + amount);
        System.out.println("Wallet ID: " + walletId);
        System.out.println("Phone number: " + maskPhoneNumber(phoneNumber));
        // Simulate processing
        try {
            System.out.println("Sending OTP to " + phoneNumber + "...");
            Thread.sleep(1000);
            System.out.println("OTP verified successfully");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
        return true;
    }
    
    private String maskPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return "****";
        }
        return "****" + phoneNumber.substring(phoneNumber.length() - 4);
    }
}
