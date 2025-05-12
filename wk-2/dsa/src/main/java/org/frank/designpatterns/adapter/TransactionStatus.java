package org.frank.designpatterns.adapter;

/**
 * Enum representing the possible states of a payment transaction.
 */
public enum TransactionStatus {
    /**
     * The transaction was completed successfully.
     */
    COMPLETED,
    
    /**
     * The transaction is still being processed.
     */
    PENDING,
    
    /**
     * The transaction failed.
     */
    FAILED,
    
    /**
     * The transaction was refunded.
     */
    REFUNDED,
    
    /**
     * The transaction was declined by the payment processor.
     */
    DECLINED,
    
    /**
     * The transaction was flagged for review due to potential fraud.
     */
    FLAGGED_FOR_REVIEW,
    
    /**
     * The status of the transaction is unknown.
     */
    UNKNOWN;
    
    /**
     * Convert a legacy status string to a TransactionStatus enum value.
     * 
     * @param legacyStatus The legacy status string
     * @return The corresponding TransactionStatus enum value
     */
    public static TransactionStatus fromLegacyStatus(String legacyStatus) {
        if (legacyStatus == null) {
            return UNKNOWN;
        }
        
        switch (legacyStatus.toUpperCase()) {
            case "COMPLETED":
            case "SUCCESS":
            case "SUCCESSFUL":
                return COMPLETED;
            case "PENDING":
            case "PROCESSING":
            case "IN_PROGRESS":
                return PENDING;
            case "FAILED":
            case "FAILURE":
            case "ERROR":
                return FAILED;
            case "REFUNDED":
            case "REFUND":
            case "RETURNED":
                return REFUNDED;
            case "DECLINED":
            case "REJECTED":
                return DECLINED;
            case "REVIEW":
            case "FLAGGED":
            case "SUSPICIOUS":
                return FLAGGED_FOR_REVIEW;
            default:
                return UNKNOWN;
        }
    }
}