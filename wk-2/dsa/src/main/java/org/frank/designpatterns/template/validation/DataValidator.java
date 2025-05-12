package org.frank.designpatterns.template.validation;

/**
 * Abstract class that defines the template method for data validation.
 * This class demonstrates the Template Method pattern by defining the skeleton
 * of the validation algorithm, deferring some steps to subclasses.
 */
public abstract class DataValidator {
    
    /**
     * Template method that defines the skeleton of the validation algorithm.
     * This method is final to prevent subclasses from changing the algorithm structure.
     * 
     * @param data The data to validate
     * @return true if the data is valid, false otherwise
     */
    public final boolean validate(String data) {
        // Step 1: Check if data is null or empty
        if (data == null || data.trim().isEmpty()) {
            System.out.println("Validation failed: Data is null or empty");
            return false;
        }
        
        // Step 2: Preprocess the data (e.g., trim, normalize)
        String preprocessedData = preprocess(data);
        System.out.println("Data preprocessed: " + preprocessedData);
        
        // Step 3: Check format using regular expression
        if (!checkFormat(preprocessedData)) {
            System.out.println("Validation failed: Data format is invalid");
            return false;
        }
        
        // Step 4: Perform additional validation specific to the data type
        if (!performAdditionalValidation(preprocessedData)) {
            System.out.println("Validation failed: Additional validation failed");
            return false;
        }
        
        // Step 5: Log the validation result
        logValidationResult(preprocessedData, true);
        
        return true;
    }
    
    /**
     * Preprocess the data before validation.
     * This is a hook method that subclasses can override.
     * The default implementation simply trims the data.
     * 
     * @param data The data to preprocess
     * @return The preprocessed data
     */
    protected String preprocess(String data) {
        // Default implementation: trim the data
        return data.trim();
    }
    
    /**
     * Check if the data format is valid using a regular expression.
     * This is a required step that all subclasses must implement.
     * 
     * @param data The data to check
     * @return true if the format is valid, false otherwise
     */
    protected abstract boolean checkFormat(String data);
    
    /**
     * Perform additional validation specific to the data type.
     * This is a hook method that subclasses can override.
     * The default implementation returns true (no additional validation).
     * 
     * @param data The data to validate
     * @return true if the additional validation passes, false otherwise
     */
    protected boolean performAdditionalValidation(String data) {
        // Default implementation: no additional validation
        return true;
    }
    
    /**
     * Log the validation result.
     * This is a hook method that subclasses can override.
     * The default implementation prints a message to the console.
     * 
     * @param data The data that was validated
     * @param isValid Whether the data is valid
     */
    protected void logValidationResult(String data, boolean isValid) {
        System.out.println("Validation " + (isValid ? "passed" : "failed") + " for data: " + data);
    }
    
    /**
     * Get the name of the validator.
     * This is a required step that all subclasses must implement.
     * 
     * @return The name of the validator
     */
    public abstract String getName();
}