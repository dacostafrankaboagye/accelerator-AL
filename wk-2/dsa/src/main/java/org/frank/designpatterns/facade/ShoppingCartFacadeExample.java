package org.frank.designpatterns.facade;

/**
 * Example class demonstrating the usage of the ShoppingCartFacade.
 */
public class ShoppingCartFacadeExample {
    
    public static void main(String[] args) {
        System.out.println("Shopping Cart Facade Example");
        System.out.println("----------------------------");
        
        // Create and initialize the inventory
        Inventory inventory = new Inventory();
        initializeInventory(inventory);
        
        // Create the shopping cart facade
        ShoppingCartFacade facade = new ShoppingCartFacade(inventory);
        
        System.out.println("\nExample 1: Adding products to cart");
        // Add products to the cart
        facade.addProductToCart("P001", 2); // 2 laptops
        facade.addProductToCart("P003", 1); // 1 headphones
        facade.addProductToCart("P004", 3); // 3 books
        
        // Print the cart status
        facade.printCartStatus();
        
        System.out.println("\nExample 2: Applying discounts");
        // Apply discounts
        facade.applyPercentageDiscount("P001", 10); // 10% off laptops
        facade.applyBuyOneGetOneDiscount("P004"); // Buy one get one free for books
        facade.applyCategoryDiscount("Electronics", 5); // 5% off all electronics
        
        // Print the active discounts
        facade.printDiscounts();
        
        // Calculate and print the totals with 8% tax
        double[] totals = facade.calculateTotals(8.0);
        System.out.println("\nOrder Summary:");
        System.out.println("Subtotal: $" + String.format("%.2f", totals[0]));
        System.out.println("Discount: $" + String.format("%.2f", totals[1]));
        System.out.println("Tax (8%): $" + String.format("%.2f", totals[2]));
        System.out.println("Total: $" + String.format("%.2f", totals[3]));
        
        System.out.println("\nExample 3: Checkout process");
        // Process checkout
        Order order = facade.checkout(
            "John Doe",
            "john.doe@example.com",
            "123 Main St, Anytown, USA",
            Payment.PaymentMethod.CREDIT_CARD,
            "4111111111111111", // Credit card number
            8.0 // Tax rate
        );
        
        if (order != null) {
            // Print the order details
            order.printOrderDetails();
            
            System.out.println("\nExample 4: Shipping the order");
            // Ship the order
            facade.shipOrder(order, "TRACK-12345");
            
            // Print the updated order details
            System.out.println("\nUpdated Order Details:");
            order.printOrderDetails();
        }
        
        System.out.println("\nExample 5: Creating and cancelling an order");
        // Add more products to the cart
        facade.addProductToCart("P002", 1); // 1 smartphone
        
        // Print the cart status
        facade.printCartStatus();
        
        // Process another checkout
        Order anotherOrder = facade.checkout(
            "Jane Smith",
            "jane.smith@example.com",
            "456 Oak St, Othertown, USA",
            Payment.PaymentMethod.PAYPAL,
            "jane.smith@example.com", // PayPal email
            8.0 // Tax rate
        );
        
        if (anotherOrder != null) {
            // Print the order details
            anotherOrder.printOrderDetails();
            
            System.out.println("\nCancelling the order:");
            // Cancel the order
            facade.cancelOrder(anotherOrder);
            
            // Print the updated order details
            System.out.println("\nUpdated Order Details:");
            anotherOrder.printOrderDetails();
        }
        
        System.out.println("\nExample 6: Checking inventory after all operations");
        // Print the inventory status
        facade.printInventoryStatus();

        System.out.println("\nAwesome right?? ");
        System.out.println("\nFacade Pattern Benefits:");
        System.out.println("1. Simplifies a complex subsystem by providing a unified interface");
        System.out.println("2. Decouples the client from the subsystem components");
        System.out.println("3. Promotes loose coupling between subsystems and clients");
        System.out.println("4. Provides a context-specific interface to a more general facility");
    }
    
    /**
     * Initialize the inventory with some products.
     * 
     * @param inventory The inventory to initialize
     */
    private static void initializeInventory(Inventory inventory) {
        Product laptop = new Product("P001", "Laptop", 999.99, "Electronics", true);
        Product smartphone = new Product("P002", "Smartphone", 699.99, "Electronics", true);
        Product headphones = new Product("P003", "Headphones", 149.99, "Electronics", true);
        Product book = new Product("P004", "Book", 19.99, "Books", true);
        Product tShirt = new Product("P005", "T-Shirt", 24.99, "Clothing", true);
        
        inventory.addProduct(laptop, 10);
        inventory.addProduct(smartphone, 15);
        inventory.addProduct(headphones, 20);
        inventory.addProduct(book, 50);
        inventory.addProduct(tShirt, 30);
        
        System.out.println("Inventory initialized with products");
    }
}