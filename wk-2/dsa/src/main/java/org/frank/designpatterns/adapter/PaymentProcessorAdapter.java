package org.frank.designpatterns.adapter;

/**
 * Adapter that allows a LegacyPaymentProcessor to be used through the ModernPaymentGateway interface.
 * This is an example of the Adapter pattern.
 */
public class PaymentProcessorAdapter implements ModernPaymentGateway {
    
    /**
     * The legacy payment processor that we're adapting.
     */
    private final LegacyPaymentProcessor legacyProcessor;
    
    /**
     * Constructor for PaymentProcessorAdapter.
     * 
     * @param legacyProcessor The legacy payment processor to adapt
     */
    public PaymentProcessorAdapter(LegacyPaymentProcessor legacyProcessor) {
        this.legacyProcessor = legacyProcessor;
    }
    
    @Override
    public String processTransaction(PaymentDetails paymentDetails) throws PaymentException {
        try {
            // Extract the necessary information from the PaymentDetails object
            String customerName = paymentDetails.getCustomerName();
            String cardNumber = paymentDetails.getCardNumber();
            String expiryDate = paymentDetails.getExpiryDate();
            double amount = paymentDetails.getAmount();
            
            // Call the legacy processor with the extracted information
            String transactionId = legacyProcessor.processPayment(customerName, amount, cardNumber, expiryDate);
            
            // Check if the transaction was successful
            if (transactionId == null) {
                throw new PaymentException("Payment processing failed", "PAYMENT_FAILED");
            }
            
            return transactionId;
        } catch (Exception e) {
            // Wrap any exceptions in a PaymentException
            if (e instanceof PaymentException) {
                throw (PaymentException) e;
            } else {
                throw new PaymentException("Error processing payment: " + e.getMessage(), e, "SYSTEM_ERROR");
            }
        }
    }
    
    @Override
    public boolean issueRefund(String transactionReference, double amount) throws PaymentException {
        try {
            // Call the legacy processor to refund the payment
            boolean refundSuccess = legacyProcessor.refundPayment(transactionReference, amount);
            
            // Check if the refund was successful
            if (!refundSuccess) {
                throw new PaymentException("Refund processing failed", "REFUND_FAILED");
            }
            
            return true;
        } catch (Exception e) {
            // Wrap any exceptions in a PaymentException
            if (e instanceof PaymentException) {
                throw (PaymentException) e;
            } else {
                throw new PaymentException("Error processing refund: " + e.getMessage(), e, "SYSTEM_ERROR");
            }
        }
    }
    
    @Override
    public TransactionStatus verifyTransaction(String transactionReference) throws PaymentException {
        try {
            // Call the legacy processor to check the payment status
            String legacyStatus = legacyProcessor.checkPaymentStatus(transactionReference);
            
            // Convert the legacy status to a TransactionStatus enum value
            return TransactionStatus.fromLegacyStatus(legacyStatus);
        } catch (Exception e) {
            // Wrap any exceptions in a PaymentException
            throw new PaymentException("Error verifying transaction: " + e.getMessage(), e, "VERIFICATION_ERROR");
        }
    }
}