package org.frank.designpatterns.singleton;

/**
 * Example class demonstrating the usage of the Logger singleton.
 */
public class LoggerExample {
    
    public static void main(String[] args) {
        // Get the Logger instance
        Logger logger = Logger.getInstance();
        
        // Log some messages
        logger.info("Application started");
        
        // Simulate some application logic
        try {
            // Simulate some operation
            logger.info("Performing operation");
            
            // Simulate an error
            if (Math.random() < 0.5) {
                throw new RuntimeException("Something went wrong!");
            }
            
            logger.info("Operation completed successfully");
        } catch (Exception e) {
            logger.error("Error occurred: " + e.getMessage());
        }
        
        // Get the Logger instance again (should be the same instance)
        Logger anotherLogger = Logger.getInstance();
        
        // Verify it's the same instance
        logger.info("Is the same instance? " + (logger == anotherLogger));
        
        // Log a warning
        logger.warning("Application is about to close");
        
        logger.info("Application ended");
    }
}