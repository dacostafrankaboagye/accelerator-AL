package org.frank.designpatterns.adapter;

/**
 * Exception thrown when a payment operation fails.
 */
public class PaymentException extends Exception {
    
    /**
     * Error code for the exception.
     */
    private final String errorCode;
    
    /**
     * Constructor with error message.
     * 
     * @param message The error message
     */
    public PaymentException(String message) {
        super(message);
        this.errorCode = "UNKNOWN";
    }
    
    /**
     * Constructor with error message and cause.
     * 
     * @param message The error message
     * @param cause The cause of the exception
     */
    public PaymentException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNKNOWN";
    }
    
    /**
     * Constructor with error message and error code.
     * 
     * @param message The error message
     * @param errorCode The error code
     */
    public PaymentException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    /**
     * Constructor with error message, cause, and error code.
     * 
     * @param message The error message
     * @param cause The cause of the exception
     * @param errorCode The error code
     */
    public PaymentException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    /**
     * Get the error code.
     * 
     * @return The error code
     */
    public String getErrorCode() {
        return errorCode;
    }
    
    @Override
    public String toString() {
        return "PaymentException [errorCode=" + errorCode + ", message=" + getMessage() + "]";
    }
}