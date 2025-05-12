package org.frank.designpatterns.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * ShoppingCart class that manages the items a user wants to purchase.
 * This is part of the subsystem used by the ShoppingCartFacade.
 */
public class ShoppingCart {
    private Map<String, Integer> items; // Product ID -> Quantity
    private Inventory inventory;
    
    /**
     * Constructor for ShoppingCart.
     * 
     * @param inventory The inventory to check product availability
     */
    public ShoppingCart(Inventory inventory) {
        this.items = new HashMap<>();
        this.inventory = inventory;
    }
    
    /**
     * Add a product to the shopping cart.
     * 
     * @param productId The ID of the product to add
     * @param quantity The quantity to add
     * @return true if the product was added successfully, false otherwise
     */
    public boolean addItem(String productId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0");
            return false;
        }
        
        Product product = inventory.getProduct(productId);
        if (product == null) {
            System.out.println("Product with ID " + productId + " not found");
            return false;
        }
        
        if (!inventory.isAvailable(productId, quantity)) {
            System.out.println("Product " + product.getName() + " is not available in the requested quantity");
            return false;
        }
        
        // Add to existing quantity if the product is already in the cart
        int currentQuantity = items.getOrDefault(productId, 0);
        items.put(productId, currentQuantity + quantity);
        
        System.out.println("Added " + quantity + " units of " + product.getName() + " to cart");
        return true;
    }
    
    /**
     * Update the quantity of a product in the shopping cart.
     * 
     * @param productId The ID of the product
     * @param quantity The new quantity
     * @return true if the product was updated successfully, false otherwise
     */
    public boolean updateItemQuantity(String productId, int quantity) {
        if (!items.containsKey(productId)) {
            System.out.println("Product with ID " + productId + " not in cart");
            return false;
        }
        
        if (quantity <= 0) {
            // Remove the item if quantity is 0 or negative
            return removeItem(productId);
        }
        
        Product product = inventory.getProduct(productId);
        if (!inventory.isAvailable(productId, quantity)) {
            System.out.println("Product " + product.getName() + " is not available in the requested quantity");
            return false;
        }
        
        items.put(productId, quantity);
        System.out.println("Updated quantity of " + product.getName() + " to " + quantity);
        return true;
    }
    
    /**
     * Remove a product from the shopping cart.
     * 
     * @param productId The ID of the product to remove
     * @return true if the product was removed successfully, false otherwise
     */
    public boolean removeItem(String productId) {
        if (!items.containsKey(productId)) {
            System.out.println("Product with ID " + productId + " not in cart");
            return false;
        }
        
        Product product = inventory.getProduct(productId);
        items.remove(productId);
        
        System.out.println("Removed " + product.getName() + " from cart");
        return true;
    }
    
    /**
     * Get the items in the shopping cart.
     * 
     * @return A map of product IDs to quantities
     */
    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }
    
    /**
     * Calculate the total price of the items in the shopping cart.
     * 
     * @return The total price
     */
    public double calculateTotal() {
        double total = 0.0;
        
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            Product product = inventory.getProduct(productId);
            if (product != null) {
                total += product.getPrice() * quantity;
            }
        }
        
        return total;
    }
    
    /**
     * Clear the shopping cart.
     */
    public void clear() {
        items.clear();
        System.out.println("Shopping cart cleared");
    }
    
    /**
     * Check if the shopping cart is empty.
     * 
     * @return true if the shopping cart is empty, false otherwise
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }
    
    /**
     * Get the number of different products in the shopping cart.
     * 
     * @return The number of different products
     */
    public int getItemCount() {
        return items.size();
    }
    
    /**
     * Get the total quantity of all products in the shopping cart.
     * 
     * @return The total quantity
     */
    public int getTotalQuantity() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }
    
    /**
     * Print the contents of the shopping cart.
     */
    public void printCart() {
        if (items.isEmpty()) {
            System.out.println("Shopping cart is empty");
            return;
        }
        
        System.out.println("Shopping Cart Contents:");
        double total = 0.0;
        
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            
            Product product = inventory.getProduct(productId);
            if (product != null) {
                double itemTotal = product.getPrice() * quantity;
                total += itemTotal;
                
                System.out.println(product.getName() + " (ID: " + productId + "): " + 
                        quantity + " x $" + product.getPrice() + " = $" + itemTotal);
            }
        }
        
        System.out.println("Total: $" + total);
    }
}