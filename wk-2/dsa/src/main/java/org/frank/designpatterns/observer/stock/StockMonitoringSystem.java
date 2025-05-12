package org.frank.designpatterns.observer.stock;

import java.util.Scanner;

/**
 * Demo application that demonstrates the Observer pattern with stock price monitoring.
 * Users can subscribe to specific stocks and receive updates when prices change.
 */
public class StockMonitoringSystem {

    public static void main(String[] args) {
        // Create the stock subject
        StockSubject stockMarket = new StockSubject();
        
        // Add some initial stocks with prices
        stockMarket.addStock("AAPL", 150.25);
        stockMarket.addStock("GOOGL", 2750.80);
        stockMarket.addStock("MSFT", 305.45);
        stockMarket.addStock("AMZN", 3300.15);
        
        // Create a stock display (observer)
        StockDisplay userDisplay = new StockDisplay(stockMarket);
        
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== Stock Monitoring System ===");
            System.out.println("Choose an option:");
            System.out.println("1. Subscribe to a stock");
            System.out.println("2. Unsubscribe from a stock");
            System.out.println("3. Update a stock price");
            System.out.println("4. View subscribed stocks");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter stock symbol to subscribe to (e.g., AAPL): ");
                    String subscribeSymbol = scanner.nextLine().toUpperCase();
                    userDisplay.subscribeToStock(subscribeSymbol);
                    break;
                case 2:
                    System.out.print("Enter stock symbol to unsubscribe from: ");
                    String unsubscribeSymbol = scanner.nextLine().toUpperCase();
                    boolean unsubscribed = userDisplay.unsubscribeFromStock(unsubscribeSymbol);
                    if (!unsubscribed) {
                        System.out.println("You are not subscribed to " + unsubscribeSymbol);
                    }
                    break;
                case 3:
                    System.out.print("Enter stock symbol to update: ");
                    String updateSymbol = scanner.nextLine().toUpperCase();
                    System.out.print("Enter new price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine(); // Consume the newline
                    stockMarket.updateStockPrice(updateSymbol, newPrice);
                    break;
                case 4:
                    System.out.println("\nYour subscribed stocks:");
                    for (String symbol : userDisplay.getSubscribedStocks()) {
                        Double price = stockMarket.getStockPrice(symbol);
                        System.out.println(symbol + ": $" + (price != null ? price : "N/A"));
                    }
                    if (userDisplay.getSubscribedStocks().isEmpty()) {
                        System.out.println("You are not subscribed to any stocks.");
                    }
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
        userDisplay.unregister();
    }
}