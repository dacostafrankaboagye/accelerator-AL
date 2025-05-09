package org.frank.designpatterns.singleton;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Singleton Configuration Manager that reads configuration values from a file or environment
 * variables and provides access to those values throughout the application.
 */
public class ConfigurationManager {
    // The single instance of ConfigurationManager
    private static ConfigurationManager instance;
    
    // Store configuration values
    private Map<String, String> configValues;
    
    // Private constructor to prevent instantiation from outside
    private ConfigurationManager() {
        configValues = new HashMap<>();
        loadEnvironmentVariables();
    }
    
    /**
     * Get the singleton instance of ConfigurationManager.
     * Uses lazy initialization (creates the instance only when needed).
     * 
     * @return The singleton ConfigurationManager instance
     */
    public static synchronized ConfigurationManager getInstance() {
        if (instance == null) {
            instance = new ConfigurationManager();
        }
        return instance;
    }
    
    /**
     * Load configuration from a properties file.
     * 
     * @param filePath Path to the properties file
     * @throws IOException If the file cannot be read
     */
    public void loadConfigFromFile(String filePath) throws IOException {
        Properties properties = new Properties();
        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            
            // Add all properties to the configuration map
            for (String key : properties.stringPropertyNames()) {
                configValues.put(key, properties.getProperty(key));
            }
        }
    }
    
    /**
     * Load configuration from environment variables.
     */
    private void loadEnvironmentVariables() {
        // Get all environment variables
        Map<String, String> env = System.getenv();
        
        // Add all environment variables to the configuration map
        configValues.putAll(env);
    }
    
    /**
     * Get a configuration value.
     * 
     * @param key The configuration key
     * @return The configuration value, or null if the key doesn't exist
     */
    public String getValue(String key) {
        return configValues.get(key);
    }
    
    /**
     * Get a configuration value with a default value if the key doesn't exist.
     * 
     * @param key The configuration key
     * @param defaultValue The default value to return if the key doesn't exist
     * @return The configuration value, or the default value if the key doesn't exist
     */
    public String getValue(String key, String defaultValue) {
        return configValues.getOrDefault(key, defaultValue);
    }
    
    /**
     * Set a configuration value.
     * 
     * @param key The configuration key
     * @param value The configuration value
     */
    public void setValue(String key, String value) {
        configValues.put(key, value);
    }
    
    /**
     * Check if a configuration key exists.
     * 
     * @param key The configuration key
     * @return true if the key exists, false otherwise
     */
    public boolean hasKey(String key) {
        return configValues.containsKey(key);
    }
    
    /**
     * Get all configuration keys.
     * 
     * @return A set of all configuration keys
     */
    public Iterable<String> getAllKeys() {
        return configValues.keySet();
    }
}