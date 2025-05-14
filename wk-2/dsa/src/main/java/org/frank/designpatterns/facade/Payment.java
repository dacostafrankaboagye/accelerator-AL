package org.frank.designpatterns.facade;

import java.util.UUID;

/**
 * Payment class that handles payment processing.
 * This is part of the subsystem used by the ShoppingCartFacade.
 */
public class Payment {
    
    /**
     * Enum for payment methods.
     */
    public enum PaymentMethod {
        CREDIT_CARD,
        PAYPAL,
        BANK_TRANSFER,
        CASH_ON_DELIVERY
    }
    
    /**
     * Enum for payment status.
     */
    public enum PaymentStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REFUNDED
    }
    
    /**
     * Process a payment.
     * 
     * @param amount The amount to charge
     * @param method The payment method
     * @param details Additional payment details (e.g., credit card number, PayPal email)
     * @return The payment transaction ID if successful, null otherwise
     */
    public String processPayment(double amount, PaymentMethod method, String details) {
        System.out.println("Processing payment of $" + amount + " using " + method);
        
        // Simulate payment processing
        boolean success = simulatePaymentProcessing(method, details);
        
        if (success) {
            String transactionId = generateTransactionId();
            System.out.println("Payment successful. Transaction ID: " + transactionId);
            return transactionId;
        } else {
            System.out.println("Payment failed.");
            return null;
        }
    }
    
    /**
     * Refund a payment.
     * 
     * @param transactionId The transaction ID of the payment to refund
     * @param amount The amount to refund (can be partial)
     * @return true if the refund was successful, false otherwise
     */
    public boolean refundPayment(String transactionId, double amount) {
        System.out.println("Processing refund of $" + amount + " for transaction " + transactionId);
        
        // Simulate refund processing
        boolean success = Math.random() > 0.1; // 90% success rate
        
        if (success) {
            System.out.println("Refund successful.");
            return true;
        } else {
            System.out.println("Refund failed.");
            return false;
        }
    }
    
    /**
     * Check the status of a payment.
     * 
     * @param transactionId The transaction ID
     * @return The payment status
     */
    public PaymentStatus checkPaymentStatus(String transactionId) {
        System.out.println("Checking status of transaction " + transactionId);
        
        // Simulate status check
        double random = Math.random();
        PaymentStatus status;
        
        if (random < 0.7) {
            status = PaymentStatus.COMPLETED;
        } else if (random < 0.8) {
            status = PaymentStatus.PENDING;
        } else if (random < 0.9) {
            status = PaymentStatus.FAILED;
        } else {
            status = PaymentStatus.REFUNDED;
        }
        
        System.out.println("Payment status: " + status);
        return status;
    }
    
    /**
     * Validate payment details.
     * 
     * @param method The payment method
     * @param details The payment details
     * @return true if the details are valid, false otherwise
     */
    public boolean validatePaymentDetails(PaymentMethod method, String details) {
        System.out.println("Validating payment details for " + method);
        
        if (details == null || details.isEmpty()) {
            System.out.println("Payment details cannot be empty");
            return false;
        }
        
        switch (method) {
            case CREDIT_CARD:
                return validateCreditCardDetails(details);
            case PAYPAL:
                return validatePayPalDetails(details);
            case BANK_TRANSFER:
                return validateBankTransferDetails(details);
            case CASH_ON_DELIVERY:
                return true; // No validation needed for cash on delivery
            default:
                System.out.println("Unsupported payment method");
                return false;
        }
    }
    
    /**
     * Simulate payment processing.
     * 
     * @param method The payment method
     * @param details The payment details
     * @return true if the payment was successful, false otherwise
     */
    private boolean simulatePaymentProcessing(PaymentMethod method, String details) {
        // Validate payment details first
        if (!validatePaymentDetails(method, details)) {
            return false;
        }
        
        // Simulate processing time - lol : maybe later I will try actual integration - but you get it
        try {
            Thread.sleep(500); // Simulate a delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulate success rate based on payment method
        double successRate;
        switch (method) {
            case CREDIT_CARD:
                successRate = 0.95; // 95% success rate
                break;
            case PAYPAL:
                successRate = 0.98; // 98% success rate
                break;
            case BANK_TRANSFER:
                successRate = 0.9; // 90% success rate
                break;
            case CASH_ON_DELIVERY:
                successRate = 0.8; // 80% success rate
                break;
            default:
                successRate = 0.5;
        }
        
        return Math.random() < successRate;
    }
    
    /**
     * Validate credit card details.
     * 
     * @param details The credit card details
     * @return true if the details are valid, false otherwise
     */
    private boolean validateCreditCardDetails(String details) {
        // Simple validation for demonstration purposes
        // In a real application, this would be much more comprehensive I tell you
        if (details.length() < 15) {
            System.out.println("Invalid credit card number");
            return false;
        }
        
        // Check if the details contain a valid format (simplified)
        if (!details.matches("\\d{13,19}")) {  // Ensures that the string has between 13 and 19 digits.
            System.out.println("Credit card number should contain only digits");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate PayPal details.
     * 
     * @param details The PayPal details (email)
     * @return true if the details are valid, false otherwise
     */
    private boolean validatePayPalDetails(String details) {
        // Simple validation for demonstration purposes
        if (!details.contains("@")) {
            System.out.println("Invalid PayPal email address");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validate bank transfer details.
     * 
     * @param details The bank transfer details
     * @return true if the details are valid, false otherwise
     */
    private boolean validateBankTransferDetails(String details) {
        // Simple validation for demonstration purposes
        if (details.length() < 10) {
            System.out.println("Invalid bank account details");
            return false;
        }
        
        return true;
    }
    
    /**
     * Generate a unique transaction ID.
     * 
     * @return A unique transaction ID
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(); // simple right??
    }
}