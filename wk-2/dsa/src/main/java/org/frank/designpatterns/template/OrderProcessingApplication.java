package org.frank.designpatterns.template;

import java.util.Scanner;

/**
 * Demo application that demonstrates the Template Method pattern with different order processors.
 */
public class OrderProcessingApplication {

    public static void main(String[] args) {
        // Create the order processors
        OrderProcessor domesticProcessor = new DomesticOrderProcessor();
        OrderProcessor internationalProcessor = new InternationalOrderProcessor();
        
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== Order Processing System ===");
            System.out.println("Choose an option:");
            System.out.println("1. Process a domestic order");
            System.out.println("2. Process an international order");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            
            switch (choice) {
                case 1:
                    System.out.print("Enter domestic order ID (should start with 'DOM'): ");
                    String domesticOrderId = scanner.nextLine();
                    System.out.println("\nProcessing domestic order...");
                    boolean domesticResult = domesticProcessor.processOrder(domesticOrderId);
                    System.out.println("Domestic order processing " + 
                            (domesticResult ? "completed successfully" : "failed"));
                    break;
                case 2:
                    System.out.print("Enter international order ID (should start with 'INT'): ");
                    String internationalOrderId = scanner.nextLine();
                    System.out.println("\nProcessing international order...");
                    boolean internationalResult = internationalProcessor.processOrder(internationalOrderId);
                    System.out.println("International order processing " + 
                            (internationalResult ? "completed successfully" : "failed"));
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }
}