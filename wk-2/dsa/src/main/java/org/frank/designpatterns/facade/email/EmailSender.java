package org.frank.designpatterns.facade.email;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * EmailSender class that handles sending emails.
 * This is part of the subsystem used by the EmailFacade.
 */
public class EmailSender {
    
    /**
     * Enum for email sending protocols.
     */
    public enum Protocol {
        SMTP,
        API,
        LOCAL
    }
    
    private Protocol protocol;
    private String smtpServer;
    private int smtpPort;
    private String smtpUsername;
    private String smtpPassword;
    private String apiKey;
    private String apiEndpoint;
    private String fromEmail;
    private String fromName;
    
    // For simulation purposes, store sent emails
    private List<Map<String, Object>> sentEmails;
    
    /**
     * Constructor for EmailSender with SMTP configuration.
     * 
     * @param smtpServer The SMTP server address
     * @param smtpPort The SMTP server port
     * @param smtpUsername The SMTP username
     * @param smtpPassword The SMTP password
     * @param fromEmail The sender's email address
     * @param fromName The sender's name
     */
    public EmailSender(String smtpServer, int smtpPort, String smtpUsername, String smtpPassword, 
                      String fromEmail, String fromName) {
        this.protocol = Protocol.SMTP;
        this.smtpServer = smtpServer;
        this.smtpPort = smtpPort;
        this.smtpUsername = smtpUsername;
        this.smtpPassword = smtpPassword;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.sentEmails = new ArrayList<>();
    }
    
    /**
     * Constructor for EmailSender with API configuration.
     * 
     * @param apiEndpoint The API endpoint
     * @param apiKey The API key
     * @param fromEmail The sender's email address
     * @param fromName The sender's name
     */
    public EmailSender(String apiEndpoint, String apiKey, String fromEmail, String fromName) {
        this.protocol = Protocol.API;
        this.apiEndpoint = apiEndpoint;
        this.apiKey = apiKey;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.sentEmails = new ArrayList<>();
    }
    
    /**
     * Constructor for EmailSender with local (simulation) configuration.
     * 
     * @param fromEmail The sender's email address
     * @param fromName The sender's name
     */
    public EmailSender(String fromEmail, String fromName) {
        this.protocol = Protocol.LOCAL;
        this.fromEmail = fromEmail;
        this.fromName = fromName;
        this.sentEmails = new ArrayList<>();
    }
    
    /**
     * Send an email.
     * 
     * @param to The recipient's email address
     * @param subject The email subject
     * @param body The email body
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendEmail(String to, String subject, String body) {
        System.out.println("Sending email to " + to + " using " + protocol);
        
        // Simulate sending email
        boolean success = simulateSendEmail(to, subject, body);
        
        if (success) {
            String emailId = generateEmailId();
            
            // Store the sent email for simulation purposes
            Map<String, Object> sentEmail = new HashMap<>();
            sentEmail.put("id", emailId);
            sentEmail.put("to", to);
            sentEmail.put("from", fromEmail);
            sentEmail.put("fromName", fromName);
            sentEmail.put("subject", subject);
            sentEmail.put("body", body);
            sentEmail.put("timestamp", LocalDateTime.now());
            sentEmail.put("protocol", protocol);
            
            sentEmails.add(sentEmail);
            
            System.out.println("Email sent successfully. ID: " + emailId);
            return emailId;
        } else {
            System.out.println("Failed to send email to " + to);
            return null;
        }
    }
    
    /**
     * Send an email using a pre-composed email map.
     * 
     * @param email A map containing the email details (keys: "to", "subject", "body")
     * @return The email ID if sent successfully, null otherwise
     */
    public String sendEmail(Map<String, String> email) {
        if (email == null || !email.containsKey("to") || !email.containsKey("subject") || !email.containsKey("body")) {
            System.out.println("Invalid email map");
            return null;
        }
        
        return sendEmail(email.get("to"), email.get("subject"), email.get("body"));
    }
    
    /**
     * Send emails in bulk.
     * 
     * @param emails A list of maps containing the email details (keys: "to", "subject", "body")
     * @return A map of recipient email addresses to email IDs for successfully sent emails
     */
    public Map<String, String> sendEmails(List<Map<String, String>> emails) {
        Map<String, String> results = new HashMap<>();
        
        if (emails == null || emails.isEmpty()) {
            return results;
        }
        
        for (Map<String, String> email : emails) {
            String emailId = sendEmail(email);
            if (emailId != null) {
                results.put(email.get("to"), emailId);
            }
        }
        
        return results;
    }
    
    /**
     * Get all sent emails.
     * 
     * @return A list of maps containing the sent email details
     */
    public List<Map<String, Object>> getSentEmails() {
        return new ArrayList<>(sentEmails);
    }
    
    /**
     * Get a sent email by ID.
     * 
     * @param emailId The email ID
     * @return A map containing the sent email details, or null if not found
     */
    public Map<String, Object> getSentEmail(String emailId) {
        for (Map<String, Object> email : sentEmails) {
            if (email.get("id").equals(emailId)) {
                return new HashMap<>(email);
            }
        }
        return null;
    }
    
    /**
     * Clear the sent emails list.
     */
    public void clearSentEmails() {
        sentEmails.clear();
    }
    
    /**
     * Simulate sending an email.
     * 
     * @param to The recipient's email address
     * @param subject The email subject
     * @param body The email body
     * @return true if the email was sent successfully, false otherwise
     */
    private boolean simulateSendEmail(String to, String subject, String body) {
        // Simulate processing time
        try {
            Thread.sleep(100); // Simulate a delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Simulate success rate based on protocol
        double successRate;
        switch (protocol) {
            case SMTP:
                successRate = 0.95; // 95% success rate
                break;
            case API:
                successRate = 0.98; // 98% success rate
                break;
            case LOCAL:
                successRate = 1.0; // 100% success rate (simulation)
                break;
            default:
                successRate = 0.9;
        }
        
        // Print email details for simulation
        System.out.println("Email Details:");
        System.out.println("From: " + fromName + " <" + fromEmail + ">");
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + (body.length() > 100 ? body.substring(0, 100) + "..." : body));
        
        return Math.random() < successRate;
    }
    
    /**
     * Generate a unique email ID.
     * 
     * @return A unique email ID
     */
    private String generateEmailId() {
        return "EMAIL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}