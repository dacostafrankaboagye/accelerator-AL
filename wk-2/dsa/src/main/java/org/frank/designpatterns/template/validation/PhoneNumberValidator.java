package org.frank.designpatterns.template.validation;

import java.util.regex.Pattern;

/**
 * Concrete implementation of DataValidator for validating phone numbers.
 */
public class PhoneNumberValidator extends DataValidator {
    
    // Regular expression for validating phone numbers (international format)
    private static final String PHONE_REGEX = "^\\+?[1-9]\\d{1,14}$";
    
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    
    @Override
    protected String preprocess(String data) {
        // Preprocess the phone number: remove spaces, dashes, parentheses, etc.
        return data.replaceAll("[\\s\\-\\(\\)]", "");
    }
    
    @Override
    protected boolean checkFormat(String data) {
        // Check if the phone number matches the regular expression
        boolean matches = PHONE_PATTERN.matcher(data).matches();
        if (!matches) {
            System.out.println("Phone number format is invalid: " + data);
        }
        return matches;
    }
    
    @Override
    protected boolean performAdditionalValidation(String data) {
        // Additional validation for phone numbers
        
        // Check if the phone number has a reasonable length (between 7 and 15 digits)
        String digitsOnly = data.replaceAll("\\D", "");
        if (digitsOnly.length() < 7 || digitsOnly.length() > 15) {
            System.out.println("Phone number length is invalid: " + digitsOnly.length() + " digits");
            return false;
        }
        
        // Check if the phone number doesn't start with 0 (after the country code)
        if (data.startsWith("+") && data.length() > 3) {
            // If the number has a country code (starts with +), check the first digit after the country code
            // Assuming country codes are 1-3 digits
            for (int i = 1; i < 4 && i < data.length(); i++) {
                if (data.charAt(i) == '0' && Character.isDigit(data.charAt(i-1))) {
                    System.out.println("Phone number should not have leading zeros after country code");
                    return false;
                }
            }
        } else if (data.startsWith("0") && data.length() > 1 && Character.isDigit(data.charAt(1))) {
            // If the number starts with 0 and the next character is a digit, it might be invalid
            System.out.println("Phone number should not start with 0 followed by another digit");
            return false;
        }
        
        return true;
    }
    
    @Override
    protected void logValidationResult(String data, boolean isValid) {
        if (isValid) {
            System.out.println("Phone number validation passed: " + data);
        } else {
            System.out.println("Phone number validation failed: " + data);
        }
    }
    
    @Override
    public String getName() {
        return "Phone Number Validator";
    }
}