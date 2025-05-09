package org.frank.designpatterns.factory;

/**
 * Factory for creating database connections based on configuration parameters.
 */
public class DatabaseConnectionFactory {
    
    /**
     * Database types supported by the factory.
     */
    public enum DatabaseType {
        MYSQL,
        POSTGRESQL
    }
    
    /**
     * Create a database connection based on the provided type and configuration.
     * 
     * @param type The type of database (MySQL, PostgreSQL)
     * @param host The database host
     * @param port The database port
     * @param database The database name
     * @param username The database username
     * @param password The database password
     * @return The created database connection
     * @throws IllegalArgumentException if the database type is not supported
     */
    public DatabaseConnection createConnection(DatabaseType type, String host, int port, 
                                              String database, String username, String password) {
        switch (type) {
            case MYSQL:
                return new MySQLConnection(host, port, database, username, password);
            case POSTGRESQL:
                return new PostgreSQLConnection(host, port, database, username, password);
            default:
                throw new IllegalArgumentException("Unsupported database type: " + type);
        }
    }
    
    /**
     * Create a database connection based on the provided type string and configuration.
     * 
     * @param typeStr The type of database as a string ("mysql", "postgresql")
     * @param host The database host
     * @param port The database port
     * @param database The database name
     * @param username The database username
     * @param password The database password
     * @return The created database connection
     * @throws IllegalArgumentException if the database type is not supported
     */
    public DatabaseConnection createConnection(String typeStr, String host, int port, 
                                              String database, String username, String password) {
        if (typeStr == null) {
            throw new IllegalArgumentException("Database type cannot be null");
        }
        
        String type = typeStr.toLowerCase();
        
        switch (type) {
            case "mysql":
                return new MySQLConnection(host, port, database, username, password);
            case "postgresql":
                return new PostgreSQLConnection(host, port, database, username, password);
            default:
                throw new IllegalArgumentException("Unsupported database type: " + typeStr);
        }
    }
    
    /**
     * Create a MySQL database connection.
     * 
     * @param host The database host
     * @param port The database port
     * @param database The database name
     * @param username The database username
     * @param password The database password
     * @return A new MySQL database connection
     */
    public DatabaseConnection createMySQLConnection(String host, int port, String database, 
                                                  String username, String password) {
        return new MySQLConnection(host, port, database, username, password);
    }
    
    /**
     * Create a PostgreSQL database connection.
     * 
     * @param host The database host
     * @param port The database port
     * @param database The database name
     * @param username The database username
     * @param password The database password
     * @return A new PostgreSQL database connection
     */
    public DatabaseConnection createPostgreSQLConnection(String host, int port, String database, 
                                                       String username, String password) {
        return new PostgreSQLConnection(host, port, database, username, password);
    }
}