package org.frank.designpatterns.facade;

import java.util.HashMap;
import java.util.Map;

/**
 * Discount class that handles various types of discounts.
 * This is part of the subsystem used by the ShoppingCartFacade.
 */
public class Discount {
    // Discount types
    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT,
        BUY_ONE_GET_ONE,
        CATEGORY_DISCOUNT
    }
    
    private Map<String, Double> percentageDiscounts; // Product ID -> Discount percentage (0-100)
    private Map<String, Double> fixedAmountDiscounts; // Product ID -> Discount amount
    private Map<String, Boolean> buyOneGetOneDiscounts; // Product ID -> Is eligible for BOGO
    private Map<String, Double> categoryDiscounts; // Category -> Discount percentage (0-100)
    
    /**
     * Constructor for Discount.
     */
    public Discount() {
        this.percentageDiscounts = new HashMap<>();
        this.fixedAmountDiscounts = new HashMap<>();
        this.buyOneGetOneDiscounts = new HashMap<>();
        this.categoryDiscounts = new HashMap<>();
    }
    
    /**
     * Add a percentage discount for a product.
     * 
     * @param productId The ID of the product
     * @param percentage The discount percentage (0-100)
     */
    public void addPercentageDiscount(String productId, double percentage) {
        if (percentage < 0 || percentage > 100) {
            System.out.println("Percentage must be between 0 and 100");
            return;
        }
        
        percentageDiscounts.put(productId, percentage);
        System.out.println("Added " + percentage + "% discount for product ID " + productId);
    }
    
    /**
     * Add a fixed amount discount for a product.
     * 
     * @param productId The ID of the product
     * @param amount The discount amount
     */
    public void addFixedAmountDiscount(String productId, double amount) {
        if (amount < 0) {
            System.out.println("Discount amount must be positive");
            return;
        }
        
        fixedAmountDiscounts.put(productId, amount);
        System.out.println("Added $" + amount + " discount for product ID " + productId);
    }
    
    /**
     * Add a buy-one-get-one discount for a product.
     * 
     * @param productId The ID of the product
     */
    public void addBuyOneGetOneDiscount(String productId) {
        buyOneGetOneDiscounts.put(productId, true);
        System.out.println("Added buy-one-get-one discount for product ID " + productId);
    }
    
    /**
     * Add a category discount.
     * 
     * @param category The product category
     * @param percentage The discount percentage (0-100)
     */
    public void addCategoryDiscount(String category, double percentage) {
        if (percentage < 0 || percentage > 100) {
            System.out.println("Percentage must be between 0 and 100");
            return;
        }
        
        categoryDiscounts.put(category, percentage);
        System.out.println("Added " + percentage + "% discount for category " + category);
    }
    
    /**
     * Remove a discount for a product.
     * 
     * @param productId The ID of the product
     * @param discountType The type of discount to remove
     */
    public void removeDiscount(String productId, DiscountType discountType) {
        switch (discountType) {
            case PERCENTAGE:
                percentageDiscounts.remove(productId);
                break;
            case FIXED_AMOUNT:
                fixedAmountDiscounts.remove(productId);
                break;
            case BUY_ONE_GET_ONE:
                buyOneGetOneDiscounts.remove(productId);
                break;
            default:
                System.out.println("Invalid discount type for product-specific discount");
        }
        
        System.out.println("Removed " + discountType + " discount for product ID " + productId);
    }
    
    /**
     * Remove a category discount.
     * 
     * @param category The product category
     */
    public void removeCategoryDiscount(String category) {
        categoryDiscounts.remove(category);
        System.out.println("Removed discount for category " + category);
    }
    
    /**
     * Calculate the discount for a shopping cart.
     * 
     * @param cart The shopping cart
     * @param inventory The inventory to get product information
     * @return The total discount amount
     */
    public double calculateDiscount(ShoppingCart cart, Inventory inventory) {
        double totalDiscount = 0.0;
        Map<String, Integer> items = cart.getItems();
        
        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = inventory.getProduct(productId);
            
            if (product == null) {
                continue;
            }
            
            double productPrice = product.getPrice();
            double productTotal = productPrice * quantity;
            double productDiscount = 0.0;
            
            // Apply percentage discount if available
            if (percentageDiscounts.containsKey(productId)) {
                double percentage = percentageDiscounts.get(productId);
                productDiscount += (productTotal * percentage / 100);
            }
            
            // Apply fixed amount discount if available
            if (fixedAmountDiscounts.containsKey(productId)) {
                double amount = fixedAmountDiscounts.get(productId);
                productDiscount += Math.min(amount * quantity, productTotal);
            }
            
            // Apply buy-one-get-one discount if available
            if (buyOneGetOneDiscounts.containsKey(productId) && quantity >= 2) {
                int freeItems = quantity / 2;
                productDiscount += freeItems * productPrice;
            }
            
            // Apply category discount if available
            String category = product.getCategory();
            if (categoryDiscounts.containsKey(category)) {
                double percentage = categoryDiscounts.get(category);
                // Only apply category discount if it's better than other discounts
                double categoryDiscount = productTotal * percentage / 100;
                if (categoryDiscount > productDiscount) {
                    productDiscount = categoryDiscount;
                }
            }
            
            totalDiscount += productDiscount;
        }
        
        return totalDiscount;
    }
    
    /**
     * Print all active discounts.
     */
    public void printDiscounts() {
        System.out.println("Active Discounts:");
        
        if (!percentageDiscounts.isEmpty()) {
            System.out.println("Percentage Discounts:");
            for (Map.Entry<String, Double> entry : percentageDiscounts.entrySet()) {
                System.out.println("  Product ID " + entry.getKey() + ": " + entry.getValue() + "%");
            }
        }
        
        if (!fixedAmountDiscounts.isEmpty()) {
            System.out.println("Fixed Amount Discounts:");
            for (Map.Entry<String, Double> entry : fixedAmountDiscounts.entrySet()) {
                System.out.println("  Product ID " + entry.getKey() + ": $" + entry.getValue());
            }
        }
        
        if (!buyOneGetOneDiscounts.isEmpty()) {
            System.out.println("Buy-One-Get-One Discounts:");
            for (String productId : buyOneGetOneDiscounts.keySet()) {
                System.out.println("  Product ID " + productId);
            }
        }
        
        if (!categoryDiscounts.isEmpty()) {
            System.out.println("Category Discounts:");
            for (Map.Entry<String, Double> entry : categoryDiscounts.entrySet()) {
                System.out.println("  Category " + entry.getKey() + ": " + entry.getValue() + "%");
            }
        }
    }
}