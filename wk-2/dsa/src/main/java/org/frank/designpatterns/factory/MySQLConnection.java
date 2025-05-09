package org.frank.designpatterns.factory;

/**
 * MySQL implementation of the DatabaseConnection interface.
 */
public class MySQLConnection implements DatabaseConnection {
    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private boolean connected;
    
    /**
     * Constructor for MySQLConnection.
     * 
     * @param host The database host
     * @param port The database port
     * @param database The database name
     * @param username The database username
     * @param password The database password
     */
    public MySQLConnection(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.connected = false;
    }
    
    @Override
    public boolean connect() {
        // In a real implementation, this would establish a connection to the MySQL database
        System.out.println("Connecting to MySQL database at " + host + ":" + port + "/" + database);
        System.out.println("Using credentials: " + username + "/********");
        
        // Simulate connection success
        connected = true;
        System.out.println("Connected to MySQL database successfully");
        
        return connected;
    }
    
    @Override
    public void disconnect() {
        if (connected) {
            // In a real implementation, this would close the connection to the MySQL database
            System.out.println("Disconnecting from MySQL database");
            connected = false;
            System.out.println("Disconnected from MySQL database successfully");
        } else {
            System.out.println("Not connected to MySQL database");
        }
    }
    
    @Override
    public String executeQuery(String query) {
        if (!connected) {
            throw new IllegalStateException("Not connected to MySQL database");
        }
        
        // In a real implementation, this would execute the query on the MySQL database
        System.out.println("Executing query on MySQL database: " + query);
        
        // Simulate query result
        return "MySQL query result for: " + query;
    }
    
    @Override
    public String getDatabaseType() {
        return "MySQL";
    }
}