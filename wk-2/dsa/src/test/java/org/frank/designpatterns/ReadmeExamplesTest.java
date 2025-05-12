package org.frank.designpatterns;

import org.frank.designpatterns.observer.CurrentConditionsDisplay;
import org.frank.designpatterns.observer.WeatherStation;
import org.frank.designpatterns.strategy.BubbleSortStrategy;
import org.frank.designpatterns.strategy.SelectionSortStrategy;
import org.frank.designpatterns.strategy.SortingContext;
import org.frank.designpatterns.strategy.SortingStrategy;
import org.frank.designpatterns.template.DomesticOrderProcessor;
import org.frank.designpatterns.template.InternationalOrderProcessor;
import org.frank.designpatterns.template.OrderProcessor;

import java.util.Arrays;

/**
 * This class tests the examples provided in the README.md file
 * to ensure they work as expected.
 */
public class ReadmeExamplesTest {

    public static void main(String[] args) {
        System.out.println("Testing README examples...");

        try {
            // Test Observer Pattern example
            testObserverPattern();

            // Test Strategy Pattern example
            testStrategyPattern();

            // Test Template Method Pattern example
            testTemplateMethodPattern();

            System.out.println("\nAll examples tested successfully!");
        } catch (AssertionError e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }

    private static void testObserverPattern() {
        System.out.println("\n=== Testing Observer Pattern Example ===");

        // Create the weather station (subject)
        WeatherStation weatherStation = new WeatherStation();

        // Create and register the display (observer)
        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherStation);

        // Change the weather data
        weatherStation.setMeasurements(80, 65, 30.4f);

        // Verify the observer received the update
        assert currentDisplay.getTemperature() == 80 : "Temperature not updated correctly";
        assert currentDisplay.getHumidity() == 65 : "Humidity not updated correctly";
        assert currentDisplay.getPressure() == 30.4f : "Pressure not updated correctly";

        System.out.println("Observer pattern example verified successfully");
    }

    private static void testStrategyPattern() {
        System.out.println("\n=== Testing Strategy Pattern Example ===");

        // Create sorting strategies
        SortingStrategy bubbleSort = new BubbleSortStrategy();
        SortingStrategy selectionSort = new SelectionSortStrategy();

        // Create context with initial strategy
        SortingContext context = new SortingContext(bubbleSort);

        // Sample array to sort
        int[] array = {64, 34, 25, 12, 22, 11, 90};
        System.out.println("Original array: " + Arrays.toString(array));

        // Sort using bubble sort
        int[] sortedArray = context.executeSort(array);
        System.out.println("Sorted with " + context.getStrategyName() + ": " + Arrays.toString(sortedArray));

        // Verify the array is sorted
        int[] expectedSortedArray = {11, 12, 22, 25, 34, 64, 90};
        assert Arrays.equals(expectedSortedArray, sortedArray) : "Array not sorted correctly with bubble sort";

        // Change strategy and sort again
        context.setStrategy(selectionSort);
        sortedArray = context.executeSort(array);
        System.out.println("Sorted with " + context.getStrategyName() + ": " + Arrays.toString(sortedArray));

        // Verify the array is sorted with the new strategy
        assert Arrays.equals(expectedSortedArray, sortedArray) : "Array not sorted correctly with selection sort";

        System.out.println("Strategy pattern example verified successfully");
    }

    private static void testTemplateMethodPattern() {
        System.out.println("\n=== Testing Template Method Pattern Example ===");

        // Create order processors
        OrderProcessor domesticProcessor = new DomesticOrderProcessor();
        OrderProcessor internationalProcessor = new InternationalOrderProcessor();

        // Process a domestic order
        System.out.println("\nProcessing domestic order:");
        boolean domesticResult = domesticProcessor.processOrder("DOM12345");
        System.out.println("Domestic order processing " + 
                (domesticResult ? "completed successfully" : "failed"));

        // Verify the domestic order was processed successfully
        assert domesticResult : "Domestic order processing failed";

        // Process an international order
        System.out.println("\nProcessing international order:");
        boolean internationalResult = internationalProcessor.processOrder("INT67890");
        System.out.println("International order processing " + 
                (internationalResult ? "completed successfully" : "failed"));

        // Verify the international order was processed successfully
        assert internationalResult : "International order processing failed";

        System.out.println("Template method pattern example verified successfully");
    }
}
