package org.frank.designpatterns.template;

/**
 * Concrete implementation of OrderProcessor for international orders.
 * This class provides specific implementations for the abstract methods
 * defined in the OrderProcessor class and overrides the hook method.
 */
public class InternationalOrderProcessor extends OrderProcessor {

    // Simulated base rate for international shipping
    private static final double INTERNATIONAL_BASE_SHIPPING_RATE = 19.99;
    
    @Override
    protected boolean validateOrder(String orderId) {
        // Simulate order validation for international orders
        System.out.println("Validating international order: " + orderId);
        // In a real application, this would check the database, inventory, etc.
        // For this example, we'll assume all orders with IDs starting with "INT" are valid
        return orderId != null && orderId.startsWith("INT");
    }

    @Override
    protected double calculateShippingCost(String orderId) {
        // Simulate shipping cost calculation for international orders
        System.out.println("Calculating shipping cost for international order: " + orderId);
        // In a real application, this would calculate based on weight, distance, country, etc.
        // For this example, we'll use a base rate for international shipping
        return INTERNATIONAL_BASE_SHIPPING_RATE;
    }
    
    @Override
    protected void applySpecialHandling(String orderId) {
        // Override the hook method to add special handling for international orders
        System.out.println("Applying special handling for international order: " + orderId);
        System.out.println("- Adding customs declaration form");
        System.out.println("- Adding international tracking");
        System.out.println("- Adding insurance for international shipping");
    }

    @Override
    protected boolean processPayment(String orderId) {
        // Simulate payment processing for international orders
        System.out.println("Processing payment for international order: " + orderId);
        // In a real application, this would interact with a payment gateway
        // For this example, we'll assume all payments are successful
        return true;
    }

    @Override
    protected String prepareShippingLabel(String orderId) {
        // Simulate shipping label preparation for international orders
        System.out.println("Preparing shipping label for international order: " + orderId);
        // In a real application, this would generate a proper shipping label
        // For this example, we'll just return a simple string
        return "INTERNATIONAL - " + orderId + " - DHL Express International";
    }

    @Override
    protected void sendConfirmation(String orderId) {
        // Simulate sending confirmation for international orders
        System.out.println("Sending confirmation email for international order: " + orderId);
        // In a real application, this would send an actual email
        System.out.println("International order confirmation email sent for order: " + orderId);
    }
}