package org.frank.designpatterns.singleton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Example class demonstrating the usage of the ConfigurationManager singleton.
 */
public class ConfigurationManagerExample {
    
    public static void main(String[] args) {
        // Get the ConfigurationManager instance
        ConfigurationManager config = ConfigurationManager.getInstance();
        
        System.out.println("Configuration Manager Example");
        System.out.println("-----------------------------");
        
        // Display some environment variables
        System.out.println("\nEnvironment Variables:");
        System.out.println("JAVA_HOME: " + config.getValue("JAVA_HOME", "Not set"));
        System.out.println("USER: " + config.getValue("USER", "Not set"));
        System.out.println("PATH: " + config.getValue("PATH", "Not set"));
        
        // Set some custom configuration values
        config.setValue("app.name", "ConfigurationManagerDemo");
        config.setValue("app.version", "1.0.0");
        config.setValue("app.debug", "true");
        
        // Display the custom configuration values
        System.out.println("\nCustom Configuration Values:");
        System.out.println("app.name: " + config.getValue("app.name"));
        System.out.println("app.version: " + config.getValue("app.version"));
        System.out.println("app.debug: " + config.getValue("app.debug"));
        
        // Create a temporary properties file
        try {
            // Create a temporary file
            File tempFile = File.createTempFile("config", ".properties");
            tempFile.deleteOnExit();
            
            // Write some properties to the file
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write("database.url=jdbc:mysql://localhost:3306/mydb\n");
                writer.write("database.username=admin\n");
                writer.write("database.password=secret\n");
                writer.write("server.port=8080\n");
            }
            
            // Load the configuration from the file
            config.loadConfigFromFile(tempFile.getAbsolutePath());
            
            // Display the loaded configuration values
            System.out.println("\nLoaded Configuration Values from File:");
            System.out.println("database.url: " + config.getValue("database.url"));
            System.out.println("database.username: " + config.getValue("database.username"));
            System.out.println("database.password: " + config.getValue("database.password"));
            System.out.println("server.port: " + config.getValue("server.port"));
            
        } catch (IOException e) {
            System.err.println("Error creating or reading configuration file: " + e.getMessage());
        }
        
        // Get the ConfigurationManager instance again (should be the same instance)
        ConfigurationManager anotherConfig = ConfigurationManager.getInstance();
        
        // Verify it's the same instance
        System.out.println("\nIs the same instance? " + (config == anotherConfig));
        
        // Verify that the second instance has the same values
        System.out.println("Value from second instance - app.name: " + anotherConfig.getValue("app.name"));
    }
}