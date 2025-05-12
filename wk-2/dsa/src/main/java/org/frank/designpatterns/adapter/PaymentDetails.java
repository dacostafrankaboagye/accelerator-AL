package org.frank.designpatterns.adapter;

/**
 * Class to encapsulate payment details for the modern payment gateway.
 */
public class PaymentDetails {
    private String customerName;
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;
    private String currency;
    private String description;
    
    /**
     * Constructor for PaymentDetails.
     * 
     * @param customerName The name of the customer
     * @param cardNumber The credit card number
     * @param expiryDate The card expiry date in MM/YY format
     * @param cvv The card verification value
     * @param amount The payment amount
     * @param currency The payment currency (e.g., "USD", "EUR")
     * @param description A description of the payment
     */
    public PaymentDetails(String customerName, String cardNumber, String expiryDate, 
                         String cvv, double amount, String currency, String description) {
        this.customerName = customerName;
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.amount = amount;
        this.currency = currency;
        this.description = description;
    }
    
    /**
     * Constructor for PaymentDetails with default currency (USD) and empty description.
     * 
     * @param customerName The name of the customer
     * @param cardNumber The credit card number
     * @param expiryDate The card expiry date in MM/YY format
     * @param cvv The card verification value
     * @param amount The payment amount
     */
    public PaymentDetails(String customerName, String cardNumber, String expiryDate, 
                         String cvv, double amount) {
        this(customerName, cardNumber, expiryDate, cvv, amount, "USD", "");
    }
    
    // Getters
    
    public String getCustomerName() {
        return customerName;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public String getCvv() {
        return cvv;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return "PaymentDetails{" +
                "customerName='" + customerName + '\'' +
                ", cardNumber='****" + cardNumber.substring(Math.max(0, cardNumber.length() - 4)) + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}