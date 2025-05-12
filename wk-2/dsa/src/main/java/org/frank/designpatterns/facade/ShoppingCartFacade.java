package org.frank.designpatterns.facade;

import java.util.Map;

/**
 * ShoppingCartFacade provides a simplified interface to the complex subsystem of shopping cart operations.
 * This is an example of the Facade pattern.
 */
public class ShoppingCartFacade {
    
    private final Inventory inventory;
    private final ShoppingCart cart;
    private final Discount discount;
    private final Payment payment;
    
    /**
     * Constructor for ShoppingCartFacade.
     * 
     * @param inventory The inventory to use
     */
    public ShoppingCartFacade(Inventory inventory) {
        this.inventory = inventory;
        this.cart = new ShoppingCart(inventory);
        this.discount = new Discount();
        this.payment = new Payment();
    }
    
    /**
     * Add a product to the shopping cart.
     * 
     * @param productId The ID of the product to add
     * @param quantity The quantity to add
     * @return true if the product was added successfully, false otherwise
     */
    public boolean addProductToCart(String productId, int quantity) {
        return cart.addItem(productId, quantity);
    }
    
    /**
     * Remove a product from the shopping cart.
     * 
     * @param productId The ID of the product to remove
     * @return true if the product was removed successfully, false otherwise
     */
    public boolean removeProductFromCart(String productId) {
        return cart.removeItem(productId);
    }
    
    /**
     * Update the quantity of a product in the shopping cart.
     * 
     * @param productId The ID of the product
     * @param quantity The new quantity
     * @return true if the product was updated successfully, false otherwise
     */
    public boolean updateProductQuantity(String productId, int quantity) {
        return cart.updateItemQuantity(productId, quantity);
    }
    
    /**
     * Get the current shopping cart.
     * 
     * @return The shopping cart
     */
    public ShoppingCart getCart() {
        return cart;
    }
    
    /**
     * Apply a percentage discount to a product.
     * 
     * @param productId The ID of the product
     * @param percentage The discount percentage (0-100)
     */
    public void applyPercentageDiscount(String productId, double percentage) {
        discount.addPercentageDiscount(productId, percentage);
    }
    
    /**
     * Apply a fixed amount discount to a product.
     * 
     * @param productId The ID of the product
     * @param amount The discount amount
     */
    public void applyFixedAmountDiscount(String productId, double amount) {
        discount.addFixedAmountDiscount(productId, amount);
    }
    
    /**
     * Apply a buy-one-get-one discount to a product.
     * 
     * @param productId The ID of the product
     */
    public void applyBuyOneGetOneDiscount(String productId) {
        discount.addBuyOneGetOneDiscount(productId);
    }
    
    /**
     * Apply a category discount.
     * 
     * @param category The product category
     * @param percentage The discount percentage (0-100)
     */
    public void applyCategoryDiscount(String category, double percentage) {
        discount.addCategoryDiscount(category, percentage);
    }
    
    /**
     * Calculate the total price of the items in the shopping cart, including discounts and tax.
     * 
     * @param taxRate The tax rate (0-100)
     * @return An array containing [subtotal, discountAmount, taxAmount, total]
     */
    public double[] calculateTotals(double taxRate) {
        double subtotal = cart.calculateTotal();
        double discountAmount = discount.calculateDiscount(cart, inventory);
        double taxAmount = (subtotal - discountAmount) * (taxRate / 100);
        double total = subtotal - discountAmount + taxAmount;
        
        return new double[] {subtotal, discountAmount, taxAmount, total};
    }
    
    /**
     * Process the checkout and create an order.
     * 
     * @param customerName The customer's name
     * @param customerEmail The customer's email
     * @param shippingAddress The shipping address
     * @param paymentMethod The payment method
     * @param paymentDetails The payment details
     * @param taxRate The tax rate (0-100)
     * @return The created order, or null if checkout failed
     */
    public Order checkout(String customerName, String customerEmail, String shippingAddress,
                         Payment.PaymentMethod paymentMethod, String paymentDetails, double taxRate) {
        
        // Check if the cart is empty
        if (cart.isEmpty()) {
            System.out.println("Cannot checkout with an empty cart");
            return null;
        }
        
        // Calculate totals
        double[] totals = calculateTotals(taxRate);
        double subtotal = totals[0];
        double discountAmount = totals[1];
        double taxAmount = totals[2];
        double total = totals[3];
        
        // Create the order
        Order order = new Order(
            cart.getItems(),
            subtotal,
            discountAmount,
            taxAmount,
            customerName,
            customerEmail,
            shippingAddress
        );
        
        // Set payment method
        order.setPaymentMethod(paymentMethod.toString());
        
        // Process payment
        String transactionId = payment.processPayment(total, paymentMethod, paymentDetails);
        
        if (transactionId == null) {
            System.out.println("Payment failed. Order cancelled.");
            order.setStatus(Order.OrderStatus.CANCELLED);
            return order;
        }
        
        // Set transaction ID and update order status
        order.setTransactionId(transactionId);
        order.setStatus(Order.OrderStatus.PROCESSING);
        
        // Update inventory
        updateInventory(order);
        
        // Clear the cart
        cart.clear();
        
        System.out.println("Order created successfully: " + order.getOrderId());
        return order;
    }
    
    /**
     * Update the inventory based on the order.
     * 
     * @param order The order
     */
    private void updateInventory(Order order) {
        Map<String, Integer> items = order.getItems();
        
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            // Remove the ordered quantity from inventory
            inventory.removeFromInventory(productId, quantity);
        }
    }
    
    /**
     * Ship an order.
     * 
     * @param order The order to ship
     * @param trackingNumber The tracking number
     * @return true if the order was shipped successfully, false otherwise
     */
    public boolean shipOrder(Order order, String trackingNumber) {
        if (order.getStatus() != Order.OrderStatus.PROCESSING) {
            System.out.println("Cannot ship order " + order.getOrderId() + " because it is not in PROCESSING state");
            return false;
        }
        
        order.setTrackingNumber(trackingNumber);
        System.out.println("Order " + order.getOrderId() + " has been shipped with tracking number " + trackingNumber);
        return true;
    }
    
    /**
     * Cancel an order and refund the payment.
     * 
     * @param order The order to cancel
     * @return true if the order was cancelled successfully, false otherwise
     */
    public boolean cancelOrder(Order order) {
        if (!order.cancelOrder()) {
            return false;
        }
        
        // Refund the payment if there's a transaction ID
        String transactionId = order.getTransactionId();
        if (transactionId != null && !transactionId.isEmpty()) {
            boolean refundSuccess = payment.refundPayment(transactionId, order.getTotal());
            
            if (refundSuccess) {
                order.setStatus(Order.OrderStatus.REFUNDED);
                System.out.println("Payment refunded for order " + order.getOrderId());
            } else {
                System.out.println("Failed to refund payment for order " + order.getOrderId());
            }
        }
        
        // Return the items to inventory
        Map<String, Integer> items = order.getItems();
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            Product product = inventory.getProduct(productId);
            if (product != null) {
                int currentQuantity = inventory.getQuantity(productId);
                inventory.updateQuantity(productId, currentQuantity + quantity);
            }
        }
        
        return true;
    }
    
    /**
     * Print the current state of the shopping cart.
     */
    public void printCartStatus() {
        cart.printCart();
    }
    
    /**
     * Print the current inventory.
     */
    public void printInventoryStatus() {
        inventory.printInventory();
    }
    
    /**
     * Print the active discounts.
     */
    public void printDiscounts() {
        discount.printDiscounts();
    }
}