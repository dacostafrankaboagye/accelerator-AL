package org.frank.designpatterns.template.validation;

import java.util.regex.Pattern;

/**
 * Concrete implementation of DataValidator for validating email addresses.
 */
public class EmailValidator extends DataValidator {
    
    // Regular expression for validating email addresses
    private static final String EMAIL_REGEX = 
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
    
    @Override
    protected String preprocess(String data) {
        // Preprocess the email: trim and convert to lowercase
        return data.trim().toLowerCase();
    }
    
    @Override
    protected boolean checkFormat(String data) {
        // Check if the email matches the regular expression
        boolean matches = EMAIL_PATTERN.matcher(data).matches();
        if (!matches) {
            System.out.println("Email format is invalid: " + data);
        }
        return matches;
    }
    
    @Override
    protected boolean performAdditionalValidation(String data) {
        // Additional validation for email addresses
        
        // Check if the domain part has at least one dot
        int atIndex = data.indexOf('@');
        if (atIndex != -1) {
            String domainPart = data.substring(atIndex + 1);
            if (!domainPart.contains(".")) {
                System.out.println("Email domain is invalid: " + domainPart);
                return false;
            }
            
            // Check if the domain is not a common disposable email domain
            String[] disposableDomains = {"tempmail.com", "throwaway.com", "fakeinbox.com"};
            for (String disposableDomain : disposableDomains) {
                if (domainPart.equals(disposableDomain)) {
                    System.out.println("Disposable email domains are not allowed: " + domainPart);
                    return false;
                }
            }
        }
        
        return true;
    }
    
    @Override
    protected void logValidationResult(String data, boolean isValid) {
        if (isValid) {
            System.out.println("Email validation passed: " + data);
        } else {
            System.out.println("Email validation failed: " + data);
        }
    }
    
    @Override
    public String getName() {
        return "Email Validator";
    }
}