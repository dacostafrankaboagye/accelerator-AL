package org.frank.designpatterns.observer.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * StockSubject class that acts as the subject in the Observer pattern for stock price monitoring.
 * It maintains a list of observers and notifies them when stock prices change.
 */
public class StockSubject {
    
    private Map<String, Double> stockPrices;
    private List<StockObserver> observers;
    
    /**
     * Constructor for StockSubject.
     */
    public StockSubject() {
        this.stockPrices = new HashMap<>();
        this.observers = new ArrayList<>();
    }
    
    /**
     * Register an observer to receive stock price updates.
     * 
     * @param observer The observer to register
     */
    public void registerObserver(StockObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer registered: " + observer.getClass().getSimpleName());
        }
    }
    
    /**
     * Remove an observer from the list of registered observers.
     * 
     * @param observer The observer to remove
     * @return true if the observer was removed, false if it wasn't registered
     */
    public boolean removeObserver(StockObserver observer) {
        boolean removed = observers.remove(observer);
        if (removed) {
            System.out.println("Observer removed: " + observer.getClass().getSimpleName());
        }
        return removed;
    }
    
    /**
     * Notify all registered observers of stock price changes for a specific stock.
     * 
     * @param symbol The stock symbol
     */
    private void notifyObservers(String symbol) {
        for (StockObserver observer : observers) {
            observer.update(symbol, stockPrices.get(symbol));
        }
    }
    
    /**
     * Add a new stock to monitor.
     * 
     * @param symbol The stock symbol
     * @param initialPrice The initial price of the stock
     */
    public void addStock(String symbol, double initialPrice) {
        stockPrices.put(symbol, initialPrice);
        System.out.println("Added stock: " + symbol + " with initial price: $" + initialPrice);
    }
    
    /**
     * Update the price of a stock and notify observers.
     * 
     * @param symbol The stock symbol
     * @param newPrice The new price of the stock
     */
    public void updateStockPrice(String symbol, double newPrice) {
        if (stockPrices.containsKey(symbol)) {
            double oldPrice = stockPrices.get(symbol);
            stockPrices.put(symbol, newPrice);
            
            System.out.println("\nStock price updated: " + symbol);
            System.out.println("Old price: $" + oldPrice);
            System.out.println("New price: $" + newPrice);
            
            // Notify all observers of the price change
            notifyObservers(symbol);
        } else {
            System.out.println("Stock " + symbol + " is not being monitored.");
        }
    }
    
    /**
     * Get the current price of a stock.
     * 
     * @param symbol The stock symbol
     * @return The current price of the stock, or null if the stock is not being monitored
     */
    public Double getStockPrice(String symbol) {
        return stockPrices.get(symbol);
    }
    
    /**
     * Get all monitored stocks and their prices.
     * 
     * @return A map of stock symbols to their current prices
     */
    public Map<String, Double> getAllStockPrices() {
        return new HashMap<>(stockPrices);
    }
    
    /**
     * Get the number of registered observers.
     * 
     * @return The number of registered observers
     */
    public int getObserverCount() {
        return observers.size();
    }
}