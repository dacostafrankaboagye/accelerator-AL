package org.frank.designpatterns.facade.email;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * EmailValidator class that validates email addresses, subjects, and content.
 * This is part of the subsystem used by the EmailFacade.
 */
public class EmailValidator {
    
    // Regular expression for validating email addresses
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    // Maximum lengths for email components
    private static final int MAX_SUBJECT_LENGTH = 255;
    private static final int MAX_BODY_LENGTH = 100000; // 100 KB
    
    // List of blocked words for spam detection
    private List<String> blockedWords;
    
    /**
     * Constructor for EmailValidator.
     */
    public EmailValidator() {
        this.blockedWords = new ArrayList<>();
        initializeBlockedWords();
    }
    
    /**
     * Initialize the list of blocked words.
     */
    private void initializeBlockedWords() {
        // Add some common spam words
        blockedWords.add("viagra");
        blockedWords.add("lottery");
        blockedWords.add("winner");
        blockedWords.add("million dollars");
        blockedWords.add("free money");
        blockedWords.add("casino");
        blockedWords.add("prize");
        blockedWords.add("jackpot");
        blockedWords.add("inheritance");
        blockedWords.add("nigerian prince");
    }
    
    /**
     * Add a word to the blocked words list.
     * 
     * @param word The word to block
     */
    public void addBlockedWord(String word) {
        if (word != null && !word.isEmpty()) {
            blockedWords.add(word.toLowerCase());
        }
    }
    
    /**
     * Remove a word from the blocked words list.
     * 
     * @param word The word to unblock
     * @return true if the word was removed, false if it wasn't in the list
     */
    public boolean removeBlockedWord(String word) {
        if (word != null) {
            return blockedWords.remove(word.toLowerCase());
        }
        return false;
    }
    
    /**
     * Get the list of blocked words.
     * 
     * @return The list of blocked words
     */
    public List<String> getBlockedWords() {
        return new ArrayList<>(blockedWords);
    }
    
    /**
     * Validate an email address.
     * 
     * @param email The email address to validate
     * @return true if the email address is valid, false otherwise
     */
    public boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate a list of email addresses.
     * 
     * @param emails The list of email addresses to validate
     * @return A list of invalid email addresses, or an empty list if all are valid
     */
    public List<String> validateEmails(List<String> emails) {
        List<String> invalidEmails = new ArrayList<>();
        
        if (emails == null) {
            return invalidEmails;
        }
        
        for (String email : emails) {
            if (!isValidEmail(email)) {
                invalidEmails.add(email);
            }
        }
        
        return invalidEmails;
    }
    
    /**
     * Validate an email subject.
     * 
     * @param subject The subject to validate
     * @return true if the subject is valid, false otherwise
     */
    public boolean isValidSubject(String subject) {
        if (subject == null) {
            return false;
        }
        
        // Check subject length
        if (subject.length() > MAX_SUBJECT_LENGTH) {
            return false;
        }
        
        // Check for blocked words
        return !containsBlockedWords(subject);
    }
    
    /**
     * Validate email content.
     * 
     * @param content The content to validate
     * @return true if the content is valid, false otherwise
     */
    public boolean isValidContent(String content) {
        if (content == null) {
            return false;
        }
        
        // Check content length
        if (content.length() > MAX_BODY_LENGTH) {
            return false;
        }
        
        // Check for blocked words
        return !containsBlockedWords(content);
    }
    
    /**
     * Check if a text contains any blocked words.
     * 
     * @param text The text to check
     * @return true if the text contains blocked words, false otherwise
     */
    public boolean containsBlockedWords(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        
        String lowerText = text.toLowerCase();
        
        for (String word : blockedWords) {
            if (lowerText.contains(word)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Get a list of blocked words found in a text.
     * 
     * @param text The text to check
     * @return A list of blocked words found in the text
     */
    public List<String> findBlockedWords(String text) {
        List<String> foundWords = new ArrayList<>();
        
        if (text == null || text.isEmpty()) {
            return foundWords;
        }
        
        String lowerText = text.toLowerCase();
        
        for (String word : blockedWords) {
            if (lowerText.contains(word)) {
                foundWords.add(word);
            }
        }
        
        return foundWords;
    }
}