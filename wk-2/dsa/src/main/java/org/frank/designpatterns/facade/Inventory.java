package org.frank.designpatterns.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * Inventory class that manages the products and their quantities.
 * This is part of the subsystem used by the ShoppingCartFacade.
 */
public class Inventory {
    private Map<String, Product> products;
    private Map<String, Integer> quantities;
    
    /**
     * Constructor for Inventory.
     */
    public Inventory() {
        this.products = new HashMap<>();
        this.quantities = new HashMap<>();
    }
    
    /**
     * Add a product to the inventory.
     * 
     * @param product The product to add
     * @param quantity The quantity of the product
     */
    public void addProduct(Product product, int quantity) {
        products.put(product.getId(), product);
        quantities.put(product.getId(), quantity);
        product.setInStock(quantity > 0);
        
        System.out.println("Added " + quantity + " units of " + product.getName() + " to inventory");
    }
    
    /**
     * Update the quantity of a product in the inventory.
     * 
     * @param productId The ID of the product
     * @param quantity The new quantity
     * @return true if the product was found and updated, false otherwise
     */
    public boolean updateQuantity(String productId, int quantity) {
        if (!products.containsKey(productId)) {
            System.out.println("Product with ID " + productId + " not found in inventory");
            return false;
        }
        
        quantities.put(productId, quantity);
        Product product = products.get(productId);
        product.setInStock(quantity > 0);
        
        System.out.println("Updated quantity of " + product.getName() + " to " + quantity);
        return true;
    }
    
    /**
     * Get a product from the inventory.
     * 
     * @param productId The ID of the product
     * @return The product, or null if not found
     */
    public Product getProduct(String productId) {
        return products.get(productId);
    }
    
    /**
     * Get the quantity of a product in the inventory.
     * 
     * @param productId The ID of the product
     * @return The quantity, or 0 if the product is not found
     */
    public int getQuantity(String productId) {
        return quantities.getOrDefault(productId, 0);
    }
    
    /**
     * Check if a product is available in the requested quantity.
     * 
     * @param productId The ID of the product
     * @param requestedQuantity The requested quantity
     * @return true if the product is available in the requested quantity, false otherwise
     */
    public boolean isAvailable(String productId, int requestedQuantity) {
        if (!products.containsKey(productId)) {
            return false;
        }
        
        int availableQuantity = quantities.getOrDefault(productId, 0);
        return availableQuantity >= requestedQuantity;
    }
    
    /**
     * Remove a quantity of a product from the inventory.
     * 
     * @param productId The ID of the product
     * @param quantity The quantity to remove
     * @return true if the product was found and the quantity was removed, false otherwise
     */
    public boolean removeFromInventory(String productId, int quantity) {
        if (!isAvailable(productId, quantity)) {
            System.out.println("Product with ID " + productId + " is not available in the requested quantity");
            return false;
        }
        
        int currentQuantity = quantities.get(productId);
        int newQuantity = currentQuantity - quantity;
        quantities.put(productId, newQuantity);
        
        Product product = products.get(productId);
        product.setInStock(newQuantity > 0);
        
        System.out.println("Removed " + quantity + " units of " + product.getName() + " from inventory");
        return true;
    }
    
    /**
     * Get all products in the inventory.
     * 
     * @return A map of product IDs to products
     */
    public Map<String, Product> getAllProducts() {
        return new HashMap<>(products);
    }
    
    /**
     * Print the current inventory.
     */
    public void printInventory() {
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Product> entry : products.entrySet()) {
            String productId = entry.getKey();
            Product product = entry.getValue();
            int quantity = quantities.getOrDefault(productId, 0);
            
            System.out.println(product.getName() + " (ID: " + productId + "): " + 
                    quantity + " units, $" + product.getPrice() + " each");
        }
    }
}