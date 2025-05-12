package org.frank.designpatterns.facade.email;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * EmailComposer class that composes emails by combining templates with recipient information and content.
 * This is part of the subsystem used by the EmailFacade.
 */
public class EmailComposer {
    
    private final EmailValidator validator;
    private final Map<String, EmailTemplate> templates;
    
    /**
     * Constructor for EmailComposer.
     * 
     * @param validator The email validator to use
     */
    public EmailComposer(EmailValidator validator) {
        this.validator = validator;
        this.templates = new HashMap<>();
        initializeDefaultTemplates();
    }
    
    /**
     * Initialize default email templates.
     */
    private void initializeDefaultTemplates() {
        // Welcome email template
        EmailTemplate welcomeTemplate = new EmailTemplate(
            "welcome",
            "Welcome to {{company_name}}, {{user_name}}!",
            "Dear {{user_name}},\n\n" +
            "Welcome to {{company_name}}! We're excited to have you on board.\n\n" +
            "Your account has been successfully created. You can now start using our services.\n\n" +
            "If you have any questions, please don't hesitate to contact us at {{support_email}}.\n\n" +
            "Best regards,\n" +
            "The {{company_name}} Team"
        );
        welcomeTemplate.setDefaultValue("company_name", "Our Company");
        welcomeTemplate.setDefaultValue("support_email", "support@example.com");
        
        // Password reset email template
        EmailTemplate passwordResetTemplate = new EmailTemplate(
            "password_reset",
            "Password Reset Request for {{user_name}}",
            "Dear {{user_name}},\n\n" +
            "We received a request to reset your password. Please click on the link below to reset your password:\n\n" +
            "{{reset_link}}\n\n" +
            "If you didn't request a password reset, please ignore this email or contact us at {{support_email}}.\n\n" +
            "Best regards,\n" +
            "The {{company_name}} Team"
        );
        passwordResetTemplate.setDefaultValue("company_name", "Our Company");
        passwordResetTemplate.setDefaultValue("support_email", "support@example.com");
        
        // Order confirmation email template
        EmailTemplate orderConfirmationTemplate = new EmailTemplate(
            "order_confirmation",
            "Order Confirmation #{{order_id}}",
            "Dear {{user_name}},\n\n" +
            "Thank you for your order #{{order_id}}.\n\n" +
            "Order Details:\n" +
            "{{order_details}}\n\n" +
            "Total: {{order_total}}\n\n" +
            "Your order will be shipped to:\n" +
            "{{shipping_address}}\n\n" +
            "If you have any questions, please contact us at {{support_email}}.\n\n" +
            "Best regards,\n" +
            "The {{company_name}} Team"
        );
        orderConfirmationTemplate.setDefaultValue("company_name", "Our Company");
        orderConfirmationTemplate.setDefaultValue("support_email", "support@example.com");
        
        // Add templates to the map
        templates.put(welcomeTemplate.getName(), welcomeTemplate);
        templates.put(passwordResetTemplate.getName(), passwordResetTemplate);
        templates.put(orderConfirmationTemplate.getName(), orderConfirmationTemplate);
    }
    
    /**
     * Add a template to the composer.
     * 
     * @param template The template to add
     */
    public void addTemplate(EmailTemplate template) {
        if (template != null) {
            templates.put(template.getName(), template);
        }
    }
    
    /**
     * Get a template by name.
     * 
     * @param templateName The name of the template
     * @return The template, or null if not found
     */
    public EmailTemplate getTemplate(String templateName) {
        return templates.get(templateName);
    }
    
    /**
     * Get all available template names.
     * 
     * @return A list of template names
     */
    public List<String> getAvailableTemplates() {
        return new ArrayList<>(templates.keySet());
    }
    
    /**
     * Compose an email using a template.
     * 
     * @param templateName The name of the template to use
     * @param recipient The recipient's email address
     * @param values The values to use for placeholders
     * @return A map containing the composed email (keys: "to", "subject", "body"), or null if validation fails
     */
    public Map<String, String> composeEmail(String templateName, String recipient, Map<String, String> values) {
        // Validate recipient email
        if (!validator.isValidEmail(recipient)) {
            System.out.println("Invalid recipient email address: " + recipient);
            return null;
        }
        
        // Get the template
        EmailTemplate template = templates.get(templateName);
        if (template == null) {
            System.out.println("Template not found: " + templateName);
            return null;
        }
        
        // Fill the template
        String subject = template.fillSubject(values);
        String body = template.fillBody(values);
        
        // Validate the subject and body
        if (!validator.isValidSubject(subject)) {
            System.out.println("Invalid subject: " + subject);
            return null;
        }
        
        if (!validator.isValidContent(body)) {
            System.out.println("Invalid content. Content contains blocked words or is too long.");
            return null;
        }
        
        // Create the email
        Map<String, String> email = new HashMap<>();
        email.put("to", recipient);
        email.put("subject", subject);
        email.put("body", body);
        
        return email;
    }
    
    /**
     * Compose an email to multiple recipients using a template.
     * 
     * @param templateName The name of the template to use
     * @param recipients The list of recipient email addresses
     * @param values The values to use for placeholders
     * @return A list of maps containing the composed emails, or an empty list if validation fails
     */
    public List<Map<String, String>> composeEmails(String templateName, List<String> recipients, Map<String, String> values) {
        List<Map<String, String>> emails = new ArrayList<>();
        
        // Validate recipients
        List<String> invalidEmails = validator.validateEmails(recipients);
        if (!invalidEmails.isEmpty()) {
            System.out.println("Invalid recipient email addresses: " + invalidEmails);
            return emails;
        }
        
        // Get the template
        EmailTemplate template = templates.get(templateName);
        if (template == null) {
            System.out.println("Template not found: " + templateName);
            return emails;
        }
        
        // Fill the template
        String subject = template.fillSubject(values);
        String body = template.fillBody(values);
        
        // Validate the subject and body
        if (!validator.isValidSubject(subject)) {
            System.out.println("Invalid subject: " + subject);
            return emails;
        }
        
        if (!validator.isValidContent(body)) {
            System.out.println("Invalid content. Content contains blocked words or is too long.");
            return emails;
        }
        
        // Create the emails
        for (String recipient : recipients) {
            Map<String, String> email = new HashMap<>();
            email.put("to", recipient);
            email.put("subject", subject);
            email.put("body", body);
            emails.add(email);
        }
        
        return emails;
    }
    
    /**
     * Compose a custom email without using a template.
     * 
     * @param recipient The recipient's email address
     * @param subject The email subject
     * @param body The email body
     * @return A map containing the composed email (keys: "to", "subject", "body"), or null if validation fails
     */
    public Map<String, String> composeCustomEmail(String recipient, String subject, String body) {
        // Validate recipient email
        if (!validator.isValidEmail(recipient)) {
            System.out.println("Invalid recipient email address: " + recipient);
            return null;
        }
        
        // Validate the subject and body
        if (!validator.isValidSubject(subject)) {
            System.out.println("Invalid subject: " + subject);
            return null;
        }
        
        if (!validator.isValidContent(body)) {
            System.out.println("Invalid content. Content contains blocked words or is too long.");
            return null;
        }
        
        // Create the email
        Map<String, String> email = new HashMap<>();
        email.put("to", recipient);
        email.put("subject", subject);
        email.put("body", body);
        
        return email;
    }
}