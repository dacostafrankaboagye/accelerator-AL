package org.frank.designpatterns.factory;

/**
 * PostgreSQL implementation of the DatabaseConnection interface.
 */
public class PostgreSQLConnection implements DatabaseConnection {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private boolean connected;
    
    /**
     * Constructor for PostgreSQLConnection.
     * 
     * @param host The database host
     * @param port The database port
     * @param database The database name
     * @param username The database username
     * @param password The database password
     */
    public PostgreSQLConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connected = false;
    }
    
    @Override
    public boolean connect() {
        // In a real implementation, this would establish a connection to the PostgreSQL database
        System.out.println("Connecting to PostgreSQL database at " + host + ":" + port + "/" + database);
        System.out.println("Using credentials: " + username + "/********");
        
        // Simulate connection success
        connected = true;
        System.out.println("Connected to PostgreSQL database successfully");
        
        return connected;
    }
    
    @Override
    public void disconnect() {
        if (connected) {
            // In a real implementation, this would close the connection to the PostgreSQL database
            System.out.println("Disconnecting from PostgreSQL database");
            connected = false;
            System.out.println("Disconnected from PostgreSQL database successfully");
        } else {
            System.out.println("Not connected to PostgreSQL database");
        }
    }
    
    @Override
    public String executeQuery(String query) {
        if (!connected) {
            throw new IllegalStateException("Not connected to PostgreSQL database");
        }
        
        // In a real implementation, this would execute the query on the PostgreSQL database
        System.out.println("Executing query on PostgreSQL database: " + query);
        
        // Simulate query result
        return "PostgreSQL query result for: " + query;
    }
    
    @Override
    public String getDatabaseType() {
        return "PostgreSQL";
    }
}