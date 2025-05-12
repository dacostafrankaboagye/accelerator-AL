package org.frank.designpatterns.facade.email;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Example class demonstrating the usage of the EmailFacade.
 */
public class EmailFacadeExample {
    
    public static void main(String[] args) {
        System.out.println("Email Facade Example");
        System.out.println("-------------------");
        
        // Create the email facade with default components
        EmailFacade emailFacade = new EmailFacade("noreply@example.com", "Example Company");
        
        System.out.println("\nExample 1: Sending a welcome email");
        // Send a welcome email
        String welcomeEmailId = emailFacade.sendWelcomeEmail(
            "john.doe@example.com",
            "John Doe",
            "Example Company"
        );
        
        if (welcomeEmailId != null) {
            System.out.println("Welcome email sent successfully with ID: " + welcomeEmailId);
        }
        
        System.out.println("\nExample 2: Sending a password reset email");
        // Send a password reset email
        String resetEmailId = emailFacade.sendPasswordResetEmail(
            "jane.smith@example.com",
            "Jane Smith",
            "https://example.com/reset-password?token=abc123",
            "Example Company"
        );
        
        if (resetEmailId != null) {
            System.out.println("Password reset email sent successfully with ID: " + resetEmailId);
        }
        
        System.out.println("\nExample 3: Sending an order confirmation email");
        // Send an order confirmation email
        String orderEmailId = emailFacade.sendOrderConfirmationEmail(
            "bob.johnson@example.com",
            "Bob Johnson",
            "ORD-12345",
            "1x Product A ($99.99)\n2x Product B ($49.99 each)",
            "$199.97",
            "123 Main St, Anytown, USA",
            "Example Company"
        );
        
        if (orderEmailId != null) {
            System.out.println("Order confirmation email sent successfully with ID: " + orderEmailId);
        }
        
        System.out.println("\nExample 4: Sending a custom email");
        // Send a custom email
        String customEmailId = emailFacade.sendCustomEmail(
            "alice.green@example.com",
            "Important Announcement",
            "Dear Alice,\n\nWe are pleased to announce our new product line.\n\nBest regards,\nExample Company"
        );
        
        if (customEmailId != null) {
            System.out.println("Custom email sent successfully with ID: " + customEmailId);
        }
        
        System.out.println("\nExample 5: Adding a custom template and sending an email with it");
        // Create a custom template
        EmailTemplate newsletterTemplate = new EmailTemplate(
            "newsletter",
            "{{company_name}} Newsletter - {{month}} {{year}}",
            "Dear {{recipient_name}},\n\n" +
            "Welcome to our {{month}} newsletter!\n\n" +
            "{{content}}\n\n" +
            "To unsubscribe, click here: {{unsubscribe_link}}\n\n" +
            "Best regards,\n" +
            "The {{company_name}} Team"
        );
        
        // Add the template to the facade
        emailFacade.addTemplate(newsletterTemplate);
        
        // Create values for the template
        Map<String, String> newsletterValues = new HashMap<>();
        newsletterValues.put("company_name", "Example Company");
        newsletterValues.put("month", "June");
        newsletterValues.put("year", "2023");
        newsletterValues.put("recipient_name", "David Wilson");
        newsletterValues.put("content", "We have some exciting news to share with you this month...");
        newsletterValues.put("unsubscribe_link", "https://example.com/unsubscribe?email=david.wilson@example.com");
        
        // Send an email using the custom template
        String newsletterEmailId = emailFacade.sendTemplateEmail(
            "newsletter",
            "david.wilson@example.com",
            newsletterValues
        );
        
        if (newsletterEmailId != null) {
            System.out.println("Newsletter email sent successfully with ID: " + newsletterEmailId);
        }
        
        System.out.println("\nExample 6: Sending bulk emails");
        // Send bulk emails
        List<String> recipients = Arrays.asList(
            "user1@example.com",
            "user2@example.com",
            "user3@example.com"
        );
        
        Map<String, String> bulkValues = new HashMap<>();
        bulkValues.put("company_name", "Example Company");
        bulkValues.put("user_name", "Valued Customer"); // Same for all recipients
        
        Map<String, String> bulkResults = emailFacade.sendBulkTemplateEmails(
            "welcome",
            recipients,
            bulkValues
        );
        
        System.out.println("Bulk emails sent: " + bulkResults.size() + " out of " + recipients.size());
        for (Map.Entry<String, String> entry : bulkResults.entrySet()) {
            System.out.println("  " + entry.getKey() + ": " + entry.getValue());
        }
        
        System.out.println("\nExample 7: Handling invalid content");
        // Add a blocked word
        emailFacade.addBlockedWord("spam");
        
        // Try to send an email with blocked content
        String invalidEmailId = emailFacade.sendCustomEmail(
            "test@example.com",
            "This is not spam",
            "This message contains the word spam which should be blocked."
        );
        
        if (invalidEmailId == null) {
            System.out.println("Email with blocked content was correctly rejected");
        }
        
        System.out.println("\nExample 8: Retrieving sent emails and logs");
        // Get all sent emails
        List<Map<String, Object>> sentEmails = emailFacade.getSentEmails();
        System.out.println("Total sent emails: " + sentEmails.size());
        
        // Get all logs
        List<Map<String, Object>> logs = emailFacade.getLogs();
        System.out.println("Total log entries: " + logs.size());
        
        System.out.println("\nFacade Pattern Benefits:");
        System.out.println("1. Simplifies a complex subsystem by providing a unified interface");
        System.out.println("2. Decouples the client from the subsystem components");
        System.out.println("3. Promotes loose coupling between subsystems and clients");
        System.out.println("4. Provides a context-specific interface to a more general facility");
        System.out.println("5. Hides the complexities of the subsystem from the client");
    }
}