package org.frank.designpatterns.adapter;

/**
 * Modern payment gateway interface that our application is designed to work with.
 * This represents the "Target" in the Adapter pattern.
 */
public interface ModernPaymentGateway {
    
    /**
     * Process a payment transaction.
     * 
     * @param paymentDetails A PaymentDetails object containing all necessary payment information
     * @return A transaction reference
     * @throws PaymentException If the payment processing fails
     */
    String processTransaction(PaymentDetails paymentDetails) throws PaymentException;
    
    /**
     * Issue a refund for a previous transaction.
     * 
     * @param transactionReference The reference of the transaction to refund
     * @param amount The amount to refund
     * @return true if the refund was successful, false otherwise
     * @throws PaymentException If the refund processing fails
     */
    boolean issueRefund(String transactionReference, double amount) throws PaymentException;
    
    /**
     * Verify the status of a transaction.
     * 
     * @param transactionReference The reference of the transaction to verify
     * @return The transaction status
     * @throws PaymentException If the verification fails
     */
    TransactionStatus verifyTransaction(String transactionReference) throws PaymentException;
}