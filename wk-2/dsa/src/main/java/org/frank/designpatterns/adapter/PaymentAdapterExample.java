package org.frank.designpatterns.adapter;

/**
 * Example class demonstrating the usage of the PaymentProcessorAdapter.
 */
public class PaymentAdapterExample {
    
    public static void main(String[] args) {
        System.out.println("Payment Processor Adapter Example");
        System.out.println("--------------------------------");
        
        // Create the legacy payment processor
        LegacyPaymentProcessor legacyProcessor = new LegacyPaymentProcessor();
        
        // Create the adapter that wraps the legacy processor
        ModernPaymentGateway paymentGateway = new PaymentProcessorAdapter(legacyProcessor);
        
        try {
            System.out.println("\nExample 1: Process a payment");
            
            // Create payment details using the modern API
            PaymentDetails paymentDetails = new PaymentDetails(
                "John Doe",
                "4111111111111111",
                "12/25",
                "123",
                99.99,
                "USD",
                "Purchase of premium subscription"
            );
            
            // Process the payment using the modern API (which internally uses the legacy processor)
            String transactionReference = paymentGateway.processTransaction(paymentDetails);
            System.out.println("Payment processed successfully. Transaction reference: " + transactionReference);
            
            // Verify the transaction status
            System.out.println("\nExample 2: Verify a transaction");
            TransactionStatus status = paymentGateway.verifyTransaction(transactionReference);
            System.out.println("Transaction status: " + status);
            
            // Issue a refund
            System.out.println("\nExample 3: Issue a refund");
            boolean refundSuccess = paymentGateway.issueRefund(transactionReference, 99.99);
            System.out.println("Refund successful: " + refundSuccess);
            
            // Verify the transaction status again after refund
            System.out.println("\nExample 4: Verify transaction after refund");
            status = paymentGateway.verifyTransaction(transactionReference);
            System.out.println("Transaction status after refund: " + status);
            
            // Try to process a payment with invalid details
            System.out.println("\nExample 5: Process a payment with invalid details (will be handled gracefully)");
            try {
                PaymentDetails invalidDetails = new PaymentDetails(
                    "Jane Smith",
                    "1234", // Invalid card number
                    "00/00", // Invalid expiry date
                    "000",
                    50.00
                );
                
                paymentGateway.processTransaction(invalidDetails);
            } catch (PaymentException e) {
                System.out.println("Caught expected exception: " + e.getMessage());
                System.out.println("Error code: " + e.getErrorCode());
            }
            
        } catch (PaymentException e) {
            System.err.println("Payment error: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nDemonstration of direct usage of legacy processor (without adapter):");
        // Direct usage of legacy processor (for comparison)
        String transactionId = legacyProcessor.processPayment("Direct Customer", 49.99, "5555555555554444", "06/26");
        System.out.println("Legacy transaction ID: " + transactionId);
        
        System.out.println("\nAdapter Pattern Benefits:");
        System.out.println("1. Allows incompatible interfaces to work together");
        System.out.println("2. Enables using legacy code with modern interfaces");
        System.out.println("3. Provides a way to reuse existing code with new requirements");
        System.out.println("4. Helps in the transition from old systems to new ones");
    }
}