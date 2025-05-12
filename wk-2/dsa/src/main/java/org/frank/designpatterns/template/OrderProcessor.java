package org.frank.designpatterns.template;

/**
 * Abstract class that defines the template method for processing orders.
 * This class demonstrates the Template Method pattern by defining the skeleton
 * of the order processing algorithm, deferring some steps to subclasses.
 */
public abstract class OrderProcessor {
    
    /**
     * Template method that defines the skeleton of the order processing algorithm.
     * This method is final to prevent subclasses from changing the algorithm structure.
     * 
     * @param orderId The ID of the order to process
     * @return true if the order was processed successfully, false otherwise
     */
    public final boolean processOrder(String orderId) {
        System.out.println("Starting to process order: " + orderId);
        
        // Step 1: Validate the order
        if (!validateOrder(orderId)) {
            System.out.println("Order validation failed for order: " + orderId);
            return false;
        }
        
        // Step 2: Calculate shipping cost
        double shippingCost = calculateShippingCost(orderId);
        System.out.println("Shipping cost calculated: $" + shippingCost);
        
        // Step 3: Apply any special handling
        applySpecialHandling(orderId);
        
        // Step 4: Process payment
        if (!processPayment(orderId)) {
            System.out.println("Payment processing failed for order: " + orderId);
            return false;
        }
        
        // Step 5: Prepare shipping label
        String shippingLabel = prepareShippingLabel(orderId);
        System.out.println("Shipping label prepared: " + shippingLabel);
        
        // Step 6: Send confirmation
        sendConfirmation(orderId);
        
        System.out.println("Order processing completed for order: " + orderId);
        return true;
    }
    
    /**
     * Validate the order. This is a required step that all subclasses must implement.
     * 
     * @param orderId The ID of the order to validate
     * @return true if the order is valid, false otherwise
     */
    protected abstract boolean validateOrder(String orderId);
    
    /**
     * Calculate the shipping cost for the order. This is a required step that all subclasses must implement.
     * 
     * @param orderId The ID of the order
     * @return The shipping cost
     */
    protected abstract double calculateShippingCost(String orderId);
    
    /**
     * Apply any special handling for the order. This is a hook method that subclasses can override.
     * The default implementation does nothing.
     * 
     * @param orderId The ID of the order
     */
    protected void applySpecialHandling(String orderId) {
        // Default implementation does nothing
        // This is a hook method that subclasses can override if needed
    }
    
    /**
     * Process payment for the order. This is a required step that all subclasses must implement.
     * 
     * @param orderId The ID of the order
     * @return true if payment was processed successfully, false otherwise
     */
    protected abstract boolean processPayment(String orderId);
    
    /**
     * Prepare the shipping label for the order. This is a required step that all subclasses must implement.
     * 
     * @param orderId The ID of the order
     * @return The shipping label
     */
    protected abstract String prepareShippingLabel(String orderId);
    
    /**
     * Send confirmation for the order. This is a required step that all subclasses must implement.
     * 
     * @param orderId The ID of the order
     */
    protected abstract void sendConfirmation(String orderId);
}