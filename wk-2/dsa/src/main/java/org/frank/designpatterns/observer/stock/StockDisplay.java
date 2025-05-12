package org.frank.designpatterns.observer.stock;

import java.util.ArrayList;
import java.util.List;

/**
 * StockDisplay class that displays stock price updates.
 * This is a concrete observer in the Observer pattern for stock price monitoring.
 */
public class StockDisplay implements StockObserver {
    
    private List<String> subscribedStocks;
    private StockSubject stockSubject;
    
    /**
     * Constructor for StockDisplay.
     * 
     * @param stockSubject The stock subject to observe
     */
    public StockDisplay(StockSubject stockSubject) {
        this.stockSubject = stockSubject;
        this.subscribedStocks = new ArrayList<>();
        stockSubject.registerObserver(this);
    }
    
    /**
     * Subscribe to updates for a specific stock.
     * 
     * @param symbol The stock symbol to subscribe to
     */
    public void subscribeToStock(String symbol) {
        if (!subscribedStocks.contains(symbol)) {
            subscribedStocks.add(symbol);
            System.out.println("Subscribed to stock: " + symbol);
            
            // Display current price if available
            Double currentPrice = stockSubject.getStockPrice(symbol);
            if (currentPrice != null) {
                System.out.println("Current price of " + symbol + ": $" + currentPrice);
            }
        }
    }
    
    /**
     * Unsubscribe from updates for a specific stock.
     * 
     * @param symbol The stock symbol to unsubscribe from
     * @return true if unsubscribed successfully, false if not subscribed
     */
    public boolean unsubscribeFromStock(String symbol) {
        boolean removed = subscribedStocks.remove(symbol);
        if (removed) {
            System.out.println("Unsubscribed from stock: " + symbol);
        }
        return removed;
    }
    
    @Override
    public void update(String symbol, double price) {
        // Only display updates for stocks that this observer is subscribed to
        if (subscribedStocks.contains(symbol)) {
            display(symbol, price);
        }
    }
    
    /**
     * Display the updated stock price.
     * 
     * @param symbol The stock symbol
     * @param price The new price of the stock
     */
    private void display(String symbol, double price) {
        System.out.println("\nStock Display Update:");
        System.out.println("Stock: " + symbol);
        System.out.println("New Price: $" + price);
    }
    
    /**
     * Get the list of stocks this observer is subscribed to.
     * 
     * @return The list of subscribed stock symbols
     */
    public List<String> getSubscribedStocks() {
        return new ArrayList<>(subscribedStocks);
    }
    
    /**
     * Unregister from the stock subject.
     */
    public void unregister() {
        stockSubject.removeObserver(this);
        subscribedStocks.clear();
    }
}