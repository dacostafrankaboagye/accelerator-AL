package org.frank.designpatterns.strategy;

/**
 * Concrete implementation of the SortingStrategy interface using Bubble Sort algorithm.
 */
public class BubbleSortStrategy implements SortingStrategy {

    @Override
    public int[] sort(int[] array) {
        if (array == null || array.length <= 1) {
            return array;
        }
        
        int[] result = array.clone(); // Create a copy to avoid modifying the original array
        int n = result.length;
        
        // Bubble sort algorithm
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (result[j] > result[j + 1]) {
                    // Swap elements
                    int temp = result[j];
                    result[j] = result[j + 1];
                    result[j + 1] = temp;
                }
            }
        }
        
        return result;
    }

    @Override
    public String getName() {
        return "Bubble Sort";
    }
}