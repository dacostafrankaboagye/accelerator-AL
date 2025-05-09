package org.frank.designpatterns.singleton;

/**
 * Singleton Logger class that provides a single instance for centralized logging
 * throughout the application.
 */
public class Logger {
    // The single instance of Logger
    private static Logger instance;
    
    // Private constructor to prevent instantiation from outside
    private Logger() {
        // Initialization code
        System.out.println("Logger instance created");
    }
    
    /**
     * Get the singleton instance of Logger.
     * Uses lazy initialization (creates the instance only when needed).
     * 
     * @return The singleton Logger instance
     */
    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }
        return instance;
    }
    
    /**
     * Log an information message.
     * 
     * @param message The message to log
     */
    public void info(String message) {
        System.out.println("INFO: " + message);
    }
    
    /**
     * Log an error message.
     * 
     * @param message The error message to log
     */
    public void error(String message) {
        System.err.println("ERROR: " + message);
    }
    
    /**
     * Log a warning message.
     * 
     * @param message The warning message to log
     */
    public void warning(String message) {
        System.out.println("WARNING: " + message);
    }
}