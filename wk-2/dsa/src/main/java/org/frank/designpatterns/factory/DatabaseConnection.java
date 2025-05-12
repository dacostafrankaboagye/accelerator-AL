package org.frank.designpatterns.factory;

/**
 * Interface for database connections.
 */
public interface DatabaseConnection {
    /**
     * Connect to the database.
     * 
     * @return true if the connection is successful, false otherwise
     */
    boolean connect();
    
    /**
     * Disconnect from the database.
     */
    void disconnect();
    
    /**
     * Execute a query on the database.
     * 
     * @param query The SQL query to execute
     * @return The result of the query as a string
     */
    String executeQuery(String query);
    
    /**
     * Get the database type.
     * 
     * @return The database type (e.g., MySQL, PostgreSQL)
     */
    String getDatabaseType();
}