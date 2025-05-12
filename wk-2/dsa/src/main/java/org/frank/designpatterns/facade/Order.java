package org.frank.designpatterns.facade;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Order class that represents a customer order.
 * This is part of the subsystem used by the ShoppingCartFacade.
 */
public class Order {
    
    /**
     * Enum for order status.
     */
    public enum OrderStatus {
        CREATED,
        PAYMENT_PENDING,
        PAID,
        PROCESSING,
        SHIPPED,
        DELIVERED,
        CANCELLED,
        REFUNDED
    }
    
    private final String orderId;
    private final Map<String, Integer> items; // Product ID -> Quantity
    private final double subtotal;
    private final double discount;
    private final double tax;
    private final double total;
    private final LocalDateTime orderDate;
    private final String customerName;
    private final String customerEmail;
    private final String shippingAddress;
    private String paymentMethod;
    private String transactionId;
    private OrderStatus status;
    private String trackingNumber;
    
    /**
     * Constructor for Order.
     * 
     * @param items The items in the order
     * @param subtotal The subtotal before discount and tax
     * @param discount The discount amount
     * @param tax The tax amount
     * @param customerName The customer's name
     * @param customerEmail The customer's email
     * @param shippingAddress The shipping address
     */
    public Order(Map<String, Integer> items, double subtotal, double discount, double tax,
                String customerName, String customerEmail, String shippingAddress) {
        this.orderId = generateOrderId();
        this.items = new HashMap<>(items);
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.total = subtotal - discount + tax;
        this.orderDate = LocalDateTime.now();
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.shippingAddress = shippingAddress;
        this.status = OrderStatus.CREATED;
    }
    
    /**
     * Get the order ID.
     * 
     * @return The order ID
     */
    public String getOrderId() {
        return orderId;
    }
    
    /**
     * Get the items in the order.
     * 
     * @return A map of product IDs to quantities
     */
    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }
    
    /**
     * Get the subtotal.
     * 
     * @return The subtotal
     */
    public double getSubtotal() {
        return subtotal;
    }
    
    /**
     * Get the discount amount.
     * 
     * @return The discount amount
     */
    public double getDiscount() {
        return discount;
    }
    
    /**
     * Get the tax amount.
     * 
     * @return The tax amount
     */
    public double getTax() {
        return tax;
    }
    
    /**
     * Get the total amount.
     * 
     * @return The total amount
     */
    public double getTotal() {
        return total;
    }
    
    /**
     * Get the order date.
     * 
     * @return The order date
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    /**
     * Get the customer's name.
     * 
     * @return The customer's name
     */
    public String getCustomerName() {
        return customerName;
    }
    
    /**
     * Get the customer's email.
     * 
     * @return The customer's email
     */
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    /**
     * Get the shipping address.
     * 
     * @return The shipping address
     */
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    /**
     * Get the payment method.
     * 
     * @return The payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    /**
     * Set the payment method.
     * 
     * @param paymentMethod The payment method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    /**
     * Get the transaction ID.
     * 
     * @return The transaction ID
     */
    public String getTransactionId() {
        return transactionId;
    }
    
    /**
     * Set the transaction ID.
     * 
     * @param transactionId The transaction ID
     */
    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
        if (transactionId != null && !transactionId.isEmpty()) {
            this.status = OrderStatus.PAID;
        }
    }
    
    /**
     * Get the order status.
     * 
     * @return The order status
     */
    public OrderStatus getStatus() {
        return status;
    }
    
    /**
     * Set the order status.
     * 
     * @param status The order status
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    
    /**
     * Get the tracking number.
     * 
     * @return The tracking number
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }
    
    /**
     * Set the tracking number.
     * 
     * @param trackingNumber The tracking number
     */
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        if (trackingNumber != null && !trackingNumber.isEmpty()) {
            this.status = OrderStatus.SHIPPED;
        }
    }
    
    /**
     * Cancel the order.
     * 
     * @return true if the order was cancelled successfully, false otherwise
     */
    public boolean cancelOrder() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED) {
            System.out.println("Cannot cancel order " + orderId + " because it has already been " + status);
            return false;
        }
        
        this.status = OrderStatus.CANCELLED;
        System.out.println("Order " + orderId + " has been cancelled");
        return true;
    }
    
    /**
     * Mark the order as delivered.
     */
    public void markAsDelivered() {
        if (status != OrderStatus.SHIPPED) {
            System.out.println("Cannot mark order " + orderId + " as delivered because it has not been shipped yet");
            return;
        }
        
        this.status = OrderStatus.DELIVERED;
        System.out.println("Order " + orderId + " has been marked as delivered");
    }
    
    /**
     * Print the order details.
     */
    public void printOrderDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        System.out.println("Order Details:");
        System.out.println("Order ID: " + orderId);
        System.out.println("Date: " + orderDate.format(formatter));
        System.out.println("Status: " + status);
        System.out.println("Customer: " + customerName + " (" + customerEmail + ")");
        System.out.println("Shipping Address: " + shippingAddress);
        
        if (paymentMethod != null) {
            System.out.println("Payment Method: " + paymentMethod);
        }
        
        if (transactionId != null) {
            System.out.println("Transaction ID: " + transactionId);
        }
        
        if (trackingNumber != null) {
            System.out.println("Tracking Number: " + trackingNumber);
        }
        
        System.out.println("\nItems:");
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            System.out.println("  Product ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
        
        System.out.println("\nSubtotal: $" + String.format("%.2f", subtotal));
        System.out.println("Discount: $" + String.format("%.2f", discount));
        System.out.println("Tax: $" + String.format("%.2f", tax));
        System.out.println("Total: $" + String.format("%.2f", total));
    }
    
    /**
     * Generate a unique order ID.
     * 
     * @return A unique order ID
     */
    private String generateOrderId() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", items=" + items.size() +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}