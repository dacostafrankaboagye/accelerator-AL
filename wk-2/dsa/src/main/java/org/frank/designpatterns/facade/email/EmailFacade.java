package org.frank.designpatterns.facade.email;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmailFacade provides a simplified interface to the complex subsystem of email operations.
 * This is an example of the Facade pattern.
 */
public class EmailFacade {
    
    private final EmailValidator validator;
    private final EmailComposer composer;
    private final EmailSender sender;
    private final EmailLogger logger;
    
    /**
     * Constructor for EmailFacade with all components.
     * 
     * @param validator The email validator
     * @param composer The email composer
     * @param sender The email sender
     * @param logger The email logger
     */
    public EmailFacade(EmailValidator validator, EmailComposer composer, EmailSender sender, EmailLogger logger) {
        this.validator = validator;
        this.composer = composer;
        this.sender = sender;
        this.logger = logger;
    }
    
    /**
     * Constructor for EmailFacade that creates default components.
     * 
     * @param fromEmail The sender's email address
     * @param fromName The sender's name
     */
    public EmailFacade(String fromEmail, String fromName) {
        this.validator = new EmailValidator();
        this.composer = new EmailComposer(validator);
        this.sender = new EmailSender(fromEmail, fromName);
        this.logger = new EmailLogger();
    }
    
    /**
     * Send a welcome email.
     * 
     * @param recipientEmail The recipient's email address
     * @param userName The user's name
     * @param companyName The company name
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendWelcomeEmail(String recipientEmail, String userName, String companyName) {
        logger.info(EmailLogger.EventType.COMPOSE, "Composing welcome email for " + userName);
        
        // Create values for template placeholders
        Map<String, String> values = new HashMap<>();
        values.put("user_name", userName);
        values.put("company_name", companyName);
        
        // Compose the email
        Map<String, String> email = composer.composeEmail("welcome", recipientEmail, values);
        
        if (email == null) {
            logger.error(EmailLogger.EventType.COMPOSE, "Failed to compose welcome email for " + userName);
            return null;
        }
        
        logger.info(EmailLogger.EventType.SEND, "Sending welcome email to " + recipientEmail);
        
        // Send the email
        String emailId = sender.sendEmail(email);
        
        if (emailId != null) {
            logger.info(EmailLogger.EventType.SEND, "Welcome email sent successfully to " + recipientEmail);
        } else {
            logger.error(EmailLogger.EventType.SEND, "Failed to send welcome email to " + recipientEmail);
        }
        
        return emailId;
    }
    
    /**
     * Send a password reset email.
     * 
     * @param recipientEmail The recipient's email address
     * @param userName The user's name
     * @param resetLink The password reset link
     * @param companyName The company name
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendPasswordResetEmail(String recipientEmail, String userName, String resetLink, String companyName) {
        logger.info(EmailLogger.EventType.COMPOSE, "Composing password reset email for " + userName);
        
        // Create values for template placeholders
        Map<String, String> values = new HashMap<>();
        values.put("user_name", userName);
        values.put("reset_link", resetLink);
        values.put("company_name", companyName);
        
        // Compose the email
        Map<String, String> email = composer.composeEmail("password_reset", recipientEmail, values);
        
        if (email == null) {
            logger.error(EmailLogger.EventType.COMPOSE, "Failed to compose password reset email for " + userName);
            return null;
        }
        
        logger.info(EmailLogger.EventType.SEND, "Sending password reset email to " + recipientEmail);
        
        // Send the email
        String emailId = sender.sendEmail(email);
        
        if (emailId != null) {
            logger.info(EmailLogger.EventType.SEND, "Password reset email sent successfully to " + recipientEmail);
        } else {
            logger.error(EmailLogger.EventType.SEND, "Failed to send password reset email to " + recipientEmail);
        }
        
        return emailId;
    }
    
    /**
     * Send an order confirmation email.
     * 
     * @param recipientEmail The recipient's email address
     * @param userName The user's name
     * @param orderId The order ID
     * @param orderDetails The order details
     * @param orderTotal The order total
     * @param shippingAddress The shipping address
     * @param companyName The company name
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendOrderConfirmationEmail(String recipientEmail, String userName, String orderId, 
                                           String orderDetails, String orderTotal, String shippingAddress, 
                                           String companyName) {
        logger.info(EmailLogger.EventType.COMPOSE, "Composing order confirmation email for " + userName);
        
        // Create values for template placeholders
        Map<String, String> values = new HashMap<>();
        values.put("user_name", userName);
        values.put("order_id", orderId);
        values.put("order_details", orderDetails);
        values.put("order_total", orderTotal);
        values.put("shipping_address", shippingAddress);
        values.put("company_name", companyName);
        
        // Compose the email
        Map<String, String> email = composer.composeEmail("order_confirmation", recipientEmail, values);
        
        if (email == null) {
            logger.error(EmailLogger.EventType.COMPOSE, "Failed to compose order confirmation email for " + userName);
            return null;
        }
        
        logger.info(EmailLogger.EventType.SEND, "Sending order confirmation email to " + recipientEmail);
        
        // Send the email
        String emailId = sender.sendEmail(email);
        
        if (emailId != null) {
            logger.info(EmailLogger.EventType.SEND, "Order confirmation email sent successfully to " + recipientEmail);
        } else {
            logger.error(EmailLogger.EventType.SEND, "Failed to send order confirmation email to " + recipientEmail);
        }
        
        return emailId;
    }
    
    /**
     * Send a custom email.
     * 
     * @param recipientEmail The recipient's email address
     * @param subject The email subject
     * @param body The email body
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendCustomEmail(String recipientEmail, String subject, String body) {
        logger.info(EmailLogger.EventType.COMPOSE, "Composing custom email for " + recipientEmail);
        
        // Validate the email address
        if (!validator.isValidEmail(recipientEmail)) {
            logger.error(EmailLogger.EventType.VALIDATE, "Invalid recipient email address: " + recipientEmail);
            return null;
        }
        
        // Validate the subject and body
        if (!validator.isValidSubject(subject)) {
            logger.error(EmailLogger.EventType.VALIDATE, "Invalid subject: " + subject);
            return null;
        }
        
        if (!validator.isValidContent(body)) {
            logger.error(EmailLogger.EventType.VALIDATE, "Invalid content. Content contains blocked words or is too long.");
            return null;
        }
        
        logger.info(EmailLogger.EventType.SEND, "Sending custom email to " + recipientEmail);
        
        // Send the email
        String emailId = sender.sendEmail(recipientEmail, subject, body);
        
        if (emailId != null) {
            logger.info(EmailLogger.EventType.SEND, "Custom email sent successfully to " + recipientEmail);
        } else {
            logger.error(EmailLogger.EventType.SEND, "Failed to send custom email to " + recipientEmail);
        }
        
        return emailId;
    }
    
    /**
     * Send an email using a template.
     * 
     * @param templateName The name of the template to use
     * @param recipientEmail The recipient's email address
     * @param values The values to use for placeholders
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendTemplateEmail(String templateName, String recipientEmail, Map<String, String> values) {
        logger.info(EmailLogger.EventType.COMPOSE, "Composing template email for " + recipientEmail + " using template " + templateName);
        
        // Compose the email
        Map<String, String> email = composer.composeEmail(templateName, recipientEmail, values);
        
        if (email == null) {
            logger.error(EmailLogger.EventType.COMPOSE, "Failed to compose template email for " + recipientEmail);
            return null;
        }
        
        logger.info(EmailLogger.EventType.SEND, "Sending template email to " + recipientEmail);
        
        // Send the email
        String emailId = sender.sendEmail(email);
        
        if (emailId != null) {
            logger.info(EmailLogger.EventType.SEND, "Template email sent successfully to " + recipientEmail);
        } else {
            logger.error(EmailLogger.EventType.SEND, "Failed to send template email to " + recipientEmail);
        }
        
        return emailId;
    }
    
    /**
     * Send emails in bulk using a template.
     * 
     * @param templateName The name of the template to use
     * @param recipients The list of recipient email addresses
     * @param values The values to use for placeholders
     * @return A map of recipient email addresses to email IDs for successfully sent emails
     */
    public Map<String, String> sendBulkTemplateEmails(String templateName, List<String> recipients, Map<String, String> values) {
        logger.info(EmailLogger.EventType.COMPOSE, "Composing bulk template emails using template " + templateName);
        
        // Compose the emails
        List<Map<String, String>> emails = composer.composeEmails(templateName, recipients, values);
        
        if (emails.isEmpty()) {
            logger.error(EmailLogger.EventType.COMPOSE, "Failed to compose bulk template emails");
            return new HashMap<>();
        }
        
        logger.info(EmailLogger.EventType.SEND, "Sending bulk template emails to " + recipients.size() + " recipients");
        
        // Send the emails
        Map<String, String> results = sender.sendEmails(emails);
        
        logger.info(EmailLogger.EventType.SEND, "Sent " + results.size() + " out of " + recipients.size() + " emails successfully");
        
        return results;
    }
    
    /**
     * Add a blocked word to the validator.
     * 
     * @param word The word to block
     */
    public void addBlockedWord(String word) {
        validator.addBlockedWord(word);
        logger.info(EmailLogger.EventType.VALIDATE, "Added blocked word: " + word);
    }
    
    /**
     * Add a template to the composer.
     * 
     * @param template The template to add
     */
    public void addTemplate(EmailTemplate template) {
        composer.addTemplate(template);
        logger.info(EmailLogger.EventType.COMPOSE, "Added template: " + template.getName());
    }
    
    /**
     * Get all available template names.
     * 
     * @return A list of template names
     */
    public List<String> getAvailableTemplates() {
        return composer.getAvailableTemplates();
    }
    
    /**
     * Get all sent emails.
     * 
     * @return A list of maps containing the sent email details
     */
    public List<Map<String, Object>> getSentEmails() {
        return sender.getSentEmails();
    }
    
    /**
     * Get all logs.
     * 
     * @return A list of log entries
     */
    public List<Map<String, Object>> getLogs() {
        return logger.getLogs();
    }
    
    /**
     * Get the email validator.
     * 
     * @return The email validator
     */
    public EmailValidator getValidator() {
        return validator;
    }
    
    /**
     * Get the email composer.
     * 
     * @return The email composer
     */
    public EmailComposer getComposer() {
        return composer;
    }
    
    /**
     * Get the email sender.
     * 
     * @return The email sender
     */
    public EmailSender getSender() {
        return sender;
    }
    
    /**
     * Get the email logger.
     * 
     * @return The email logger
     */
    public EmailLogger getLogger() {
        return logger;
    }
}