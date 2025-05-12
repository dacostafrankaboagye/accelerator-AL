package org.frank.designpatterns.strategy;

/**
 * Context class that uses a SortingStrategy to perform sorting operations.
 * This class demonstrates the Strategy pattern by allowing the sorting algorithm
 * to be changed at runtime.
 */
public class SortingContext {
    
    private SortingStrategy strategy;
    
    /**
     * Constructor that accepts a sorting strategy.
     * 
     * @param strategy The sorting strategy to use
     */
    public SortingContext(SortingStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Set a new sorting strategy.
     * 
     * @param strategy The new sorting strategy to use
     */
    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Execute the sorting operation using the current strategy.
     * 
     * @param array The array to be sorted
     * @return The sorted array
     */
    public int[] executeSort(int[] array) {
        return strategy.sort(array);
    }
    
    /**
     * Get the name of the current sorting strategy.
     * 
     * @return The name of the current sorting strategy
     */
    public String getStrategyName() {
        return strategy.getName();
    }
}