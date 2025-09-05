package vn.edu.eiu.service;

import vn.edu.eiu.payment.PaymentMethod;

/**
 * The PaymentService class is responsible for processing payments.
 * Single Responsibility: This class has a single responsibility of processing payments
 * by delegating the actual payment processing to the injected PaymentMethod implementation.
 */
public class PaymentService {
    private final PaymentMethod paymentMethod;

    // Constructor Dependency Injection
    public PaymentService(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Processes a payment with the given amount
     * @param amount the amount to be paid
     * @return true if payment was successful, false otherwise
     */
    public boolean processPayment(double amount) {
        System.out.println("Processing payment of $" + amount);
        return paymentMethod.makePayment(amount);
    }
}
