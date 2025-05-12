package org.frank.designpatterns.facade;

/**
 * Product class representing an item that can be purchased.
 * This is part of the subsystem used by the ShoppingCartFacade.
 */
public class Product {
    private String id;
    private String name;
    private double price;
    private String category;
    private boolean inStock;
    
    /**
     * Constructor for Product.
     * 
     * @param id The product ID
     * @param name The product name
     * @param price The product price
     * @param category The product category
     * @param inStock Whether the product is in stock
     */
    public Product(String id, String name, double price, String category, boolean inStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
        this.inStock = inStock;
    }
    
    /**
     * Get the product ID.
     * 
     * @return The product ID
     */
    public String getId() {
        return id;
    }
    
    /**
     * Get the product name.
     * 
     * @return The product name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the product price.
     * 
     * @return The product price
     */
    public double getPrice() {
        return price;
    }
    
    /**
     * Set the product price.
     * 
     * @param price The new product price
     */
    public void setPrice(double price) {
        this.price = price;
    }
    
    /**
     * Get the product category.
     * 
     * @return The product category
     */
    public String getCategory() {
        return category;
    }
    
    /**
     * Check if the product is in stock.
     * 
     * @return true if the product is in stock, false otherwise
     */
    public boolean isInStock() {
        return inStock;
    }
    
    /**
     * Set whether the product is in stock.
     * 
     * @param inStock Whether the product is in stock
     */
    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                ", inStock=" + inStock +
                '}';
    }
}