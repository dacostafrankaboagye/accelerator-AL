package org.frank.designpatterns.template;

/**
 * Concrete implementation of OrderProcessor for domestic orders.
 * This class provides specific implementations for the abstract methods
 * defined in the OrderProcessor class.
 */
public class DomesticOrderProcessor extends OrderProcessor {

    // Simulated flat rate for domestic shipping
    private static final double DOMESTIC_SHIPPING_RATE = 5.99;
    
    @Override
    protected boolean validateOrder(String orderId) {
        // Simulate order validation for domestic orders
        System.out.println("Validating domestic order: " + orderId);
        // In a real application, this would check the database, inventory, etc.
        // For this example, we'll assume all orders with IDs starting with "DOM" are valid
        return orderId != null && orderId.startsWith("DOM");
    }

    @Override
    protected double calculateShippingCost(String orderId) {
        // Simulate shipping cost calculation for domestic orders
        System.out.println("Calculating shipping cost for domestic order: " + orderId);
        // In a real application, this would calculate based on weight, distance, etc.
        // For this example, we'll use a flat rate for domestic shipping
        return DOMESTIC_SHIPPING_RATE;
    }

    @Override
    protected boolean processPayment(String orderId) {
        // Simulate payment processing for domestic orders
        System.out.println("Processing payment for domestic order: " + orderId);
        // In a real application, this would interact with a payment gateway
        // For this example, we'll assume all payments are successful
        return true;
    }

    @Override
    protected String prepareShippingLabel(String orderId) {
        // Simulate shipping label preparation for domestic orders
        System.out.println("Preparing shipping label for domestic order: " + orderId);
        // In a real application, this would generate a proper shipping label
        // For this example, we'll just return a simple string
        return "DOMESTIC - " + orderId + " - USPS Standard Shipping";
    }

    @Override
    protected void sendConfirmation(String orderId) {
        // Simulate sending confirmation for domestic orders
        System.out.println("Sending confirmation email for domestic order: " + orderId);
        // In a real application, this would send an actual email
        System.out.println("Domestic order confirmation email sent for order: " + orderId);
    }
}