package org.frank.designpatterns.template.validation;

import java.util.Scanner;

/**
 * Demo application that demonstrates the Template Method pattern with different data validators.
 */
public class DataValidationApplication {

    public static void main(String[] args) {
        // Create the validators
        DataValidator emailValidator = new EmailValidator();
        DataValidator phoneValidator = new PhoneNumberValidator();
        
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        while (!exit) {
            System.out.println("\n=== Data Validation Framework ===");
            System.out.println("Choose an option:");
            System.out.println("1. Validate an email address");
            System.out.println("2. Validate a phone number");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline
            
            switch (choice) {
                case 1:
                    System.out.println("\n--- " + emailValidator.getName() + " ---");
                    System.out.print("Enter an email address to validate: ");
                    String email = scanner.nextLine();
                    
                    System.out.println("\nValidating email: " + email);
                    boolean emailValid = emailValidator.validate(email);
                    System.out.println("Email validation result: " + (emailValid ? "Valid" : "Invalid"));
                    
                    // Examples of valid and invalid emails
                    if (!emailValid) {
                        System.out.println("\nExamples of valid email addresses:");
                        System.out.println("- user@example.com");
                        System.out.println("- john.doe@company.co.uk");
                        System.out.println("- info@domain.org");
                    }
                    break;
                case 2:
                    System.out.println("\n--- " + phoneValidator.getName() + " ---");
                    System.out.print("Enter a phone number to validate: ");
                    String phoneNumber = scanner.nextLine();
                    
                    System.out.println("\nValidating phone number: " + phoneNumber);
                    boolean phoneValid = phoneValidator.validate(phoneNumber);
                    System.out.println("Phone number validation result: " + (phoneValid ? "Valid" : "Invalid"));
                    
                    // Examples of valid and invalid phone numbers
                    if (!phoneValid) {
                        System.out.println("\nExamples of valid phone numbers:");
                        System.out.println("- +1234567890");
                        System.out.println("- 123-456-7890");
                        System.out.println("- (123) 456 7890");
                    }
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