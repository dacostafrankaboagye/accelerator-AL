package org.frank.designpatterns.strategy;

/**
 * Concrete implementation of the SortingStrategy interface using Selection Sort algorithm.
 */
public class SelectionSortStrategy implements SortingStrategy {

    @Override
    public int[] sort(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        
        int[] result = array.clone(); // Create a copy to avoid modifying the original array
        int n = result.length;
        
        // Selection sort algorithm
        for (int i = 0; i < n - 1; i++) {
            // Find the minimum element in the unsorted part of the array
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (result[j] < result[minIndex]) {
                    minIndex = j;
                }
            }
            
            // Swap the found minimum element with the element at index i
            int temp = result[minIndex];
            result[minIndex] = result[i];
            result[i] = temp;
        }
        
        return result;
    }

    @Override
    public String getName() {
        return "Selection Sort";
    }
}