package org.frank.designpatterns.adapter;

/**
 * Legacy payment processing library that we need to adapt to work with our modern payment gateway.
 * This represents an old system that we can't modify but need to continue using.
 */
public class LegacyPaymentProcessor {
    
    /**
     * Process a payment using the legacy system.
     * 
     * @param customerName The name of the customer
     * @param amount The payment amount
     * @param cardNumber The credit card number
     * @param expiryDate The card expiry date in MM/YY format
     * @return A transaction ID if successful, or null if failed
     */
    public String processPayment(String customerName, double amount, String cardNumber, String expiryDate) {
        // Simulate legacy payment processing
        System.out.println("Legacy Payment Processor: Processing payment for " + customerName);
        System.out.println("Amount: $" + amount);
        System.out.println("Card: " + maskCardNumber(cardNumber) + ", Expiry: " + expiryDate);
        
        // Generate a simple transaction ID
        String transactionId = "LEGACY-" + System.currentTimeMillis();
        System.out.println("Legacy Payment Processor: Payment successful. Transaction ID: " + transactionId);
        
        return transactionId;
    }
    
    /**
     * Refund a payment using the legacy system.
     * 
     * @param transactionId The original transaction ID
     * @param amount The amount to refund (can be partial)
     * @return true if the refund was successful, false otherwise
     */
    public boolean refundPayment(String transactionId, double amount) {
        // Simulate legacy refund processing
        System.out.println("Legacy Payment Processor: Processing refund for transaction " + transactionId);
        System.out.println("Refund amount: $" + amount);
        
        // Simulate successful refund
        System.out.println("Legacy Payment Processor: Refund successful");
        
        return true;
    }
    
    /**
     * Check the status of a payment.
     * 
     * @param transactionId The transaction ID
     * @return The status of the payment ("Completed", "Pending", "Failed")
     */
    public String checkPaymentStatus(String transactionId) {
        // Simulate checking payment status
        System.out.println("Legacy Payment Processor: Checking status of transaction " + transactionId);
        
        // Always return "Completed" for simplicity
        return "Completed";
    }
    
    /**
     * Mask a credit card number for security.
     * 
     * @param cardNumber The full card number
     * @return The masked card number (only last 4 digits visible)
     */
    private String maskCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) {
            return "****";
        }
        
        int length = cardNumber.length();
        StringBuilder masked = new StringBuilder();
        
        for (int i = 0; i < length - 4; i++) {
            masked.append("*");
        }
        
        masked.append(cardNumber.substring(length - 4));
        
        return masked.toString();
    }
}