package vn.edu.eiu;

import vn.edu.eiu.payment.PaymentMethod;
import vn.edu.eiu.payment.impl.BankTransferPayment;
import vn.edu.eiu.payment.impl.CardPayment;
import vn.edu.eiu.payment.impl.EwalletPayment;
import vn.edu.eiu.service.PaymentService;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Payment Processing System ===\n");
        
        // Process credit card payment
        System.out.println("1. Processing Credit Card Payment:");
        PaymentMethod cardPayment = new CardPayment("4111111111111111", "12/25", "123");
        PaymentService cardPaymentService = new PaymentService(cardPayment);
        processPayment(cardPaymentService, 150.75);
        
        // Process e-wallet payment
        System.out.println("\n2. Processing E-Wallet Payment:");
        PaymentMethod ewalletPayment = new EwalletPayment("EW123456789", "+84987654321");
        PaymentService ewalletPaymentService = new PaymentService(ewalletPayment);
        processPayment(ewalletPaymentService, 75.50);
        
        // Process bank transfer payment
        System.out.println("\n3. Processing Bank Transfer Payment:");
        PaymentMethod bankTransfer = new BankTransferPayment("1234567890", "Vietcombank", "NGUYEN VAN A");
        PaymentService bankTransferService = new PaymentService(bankTransfer);
        processPayment(bankTransferService, 300.25);
    }
    
    private static void processPayment(PaymentService paymentService, double amount) {
        boolean result = paymentService.processPayment(amount);
        System.out.println("Payment " + (result ? "successful!" : "failed!"));
    }
}