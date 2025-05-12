package org.frank.designpatterns.observer.stock;

/**
 * StockObserver interface that defines the methods that all stock observers must implement.
 * This is the "Observer" in the Observer pattern for stock price monitoring.
 */
public interface StockObserver {
    
    /**
     * Update method called by the StockSubject when a stock price changes.
     * 
     * @param symbol The stock symbol
     * @param price The new price of the stock
     */
    void update(String symbol, double price);
}