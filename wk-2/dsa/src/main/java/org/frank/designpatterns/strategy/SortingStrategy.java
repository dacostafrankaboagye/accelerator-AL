package org.frank.designpatterns.strategy;

/**
 * Strategy interface for different sorting algorithms.
 * This interface defines the contract for all sorting strategies.
 */
public interface SortingStrategy {
    
    /**
     * Sort the given array of integers.
     * 
     * @param array The array to be sorted
     * @return The sorted array
     */
    int[] sort(int[] array);
    
    /**
     * Get the name of the sorting algorithm.
     * 
     * @return The name of the sorting algorithm
     */
    String getName();
}