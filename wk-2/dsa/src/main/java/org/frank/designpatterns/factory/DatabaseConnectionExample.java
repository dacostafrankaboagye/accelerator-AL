package org.frank.designpatterns.factory;

/**
 * Example class demonstrating the usage of the DatabaseConnectionFactory.
 */
public class DatabaseConnectionExample {
    
    public static void main(String[] args) {
        // Create a database connection factory
        DatabaseConnectionFactory connectionFactory = new DatabaseConnectionFactory();
        
        System.out.println("Database Connection Factory Example");
        System.out.println("----------------------------------");
        
        // Example 1: Create connections using the enum type
        System.out.println("\nExample 1: Creating connections using enum type");
        
        try {
            // Create a MySQL connection
            DatabaseConnection mysqlConnection = connectionFactory.createConnection(
                DatabaseConnectionFactory.DatabaseType.MYSQL,
                "localhost", 3306, "mydb", "root", "password"
            );
            
            System.out.println("Created a " + mysqlConnection.getDatabaseType() + " connection");
            mysqlConnection.connect();
            String result1 = mysqlConnection.executeQuery("SELECT * FROM users");
            System.out.println(result1);
            mysqlConnection.disconnect();
            
            // Create a PostgreSQL connection
            DatabaseConnection postgresConnection = connectionFactory.createConnection(
                DatabaseConnectionFactory.DatabaseType.POSTGRESQL,
                "localhost", 5432, "mydb", "postgres", "password"
            );
            
            System.out.println("\nCreated a " + postgresConnection.getDatabaseType() + " connection");
            postgresConnection.connect();
            String result2 = postgresConnection.executeQuery("SELECT * FROM users");
            System.out.println(result2);
            postgresConnection.disconnect();
            
        } catch (Exception e) {
            System.err.println("Error creating or using connections: " + e.getMessage());
        }
        
        // Example 2: Create connections using string type
        System.out.println("\nExample 2: Creating connections using string type");
        
        try {
            // Create a MySQL connection
            DatabaseConnection mysqlConnection = connectionFactory.createConnection(
                "mysql", "db.example.com", 3306, "production", "admin", "secure_password"
            );
            
            System.out.println("Created a " + mysqlConnection.getDatabaseType() + " connection");
            mysqlConnection.connect();
            String result1 = mysqlConnection.executeQuery("SELECT COUNT(*) FROM orders");
            System.out.println(result1);
            mysqlConnection.disconnect();
            
            // Create a PostgreSQL connection (case-insensitive)
            DatabaseConnection postgresConnection = connectionFactory.createConnection(
                "PostgreSQL", "db.example.com", 5432, "production", "admin", "secure_password"
            );
            
            System.out.println("\nCreated a " + postgresConnection.getDatabaseType() + " connection");
            postgresConnection.connect();
            String result2 = postgresConnection.executeQuery("SELECT COUNT(*) FROM orders");
            System.out.println(result2);
            postgresConnection.disconnect();
            
            // Try to create an unsupported database type
            try {
                DatabaseConnection oracleConnection = connectionFactory.createConnection(
                    "oracle", "db.example.com", 1521, "production", "admin", "secure_password"
                );
                oracleConnection.connect();
            } catch (IllegalArgumentException e) {
                System.out.println("\nCaught exception: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("Error creating or using connections: " + e.getMessage());
        }
        
        // Example 3: Create connections using specific methods
        System.out.println("\nExample 3: Creating connections using specific methods");
        
        try {
            // Create a MySQL connection
            DatabaseConnection mysqlConnection = connectionFactory.createMySQLConnection(
                "mysql.example.com", 3306, "analytics", "reporter", "read_only"
            );
            
            System.out.println("Created a " + mysqlConnection.getDatabaseType() + " connection");
            mysqlConnection.connect();
            String result1 = mysqlConnection.executeQuery("SELECT * FROM statistics");
            System.out.println(result1);
            mysqlConnection.disconnect();
            
            // Create a PostgreSQL connection
            DatabaseConnection postgresConnection = connectionFactory.createPostgreSQLConnection(
                "postgres.example.com", 5432, "analytics", "reporter", "read_only"
            );
            
            System.out.println("\nCreated a " + postgresConnection.getDatabaseType() + " connection");
            postgresConnection.connect();
            String result2 = postgresConnection.executeQuery("SELECT * FROM statistics");
            System.out.println(result2);
            postgresConnection.disconnect();
            
        } catch (Exception e) {
            System.err.println("Error creating or using connections: " + e.getMessage());
        }
    }
}