# Design Patterns in Java

This repository contains implementations of various design patterns in Java. Each pattern is explained with examples to demonstrate its usage and benefits.

## Table of Contents

- [Creational Patterns](#creational-patterns)
  - [Singleton Pattern](#singleton-pattern)
  - [Factory Pattern](#factory-pattern)
  - [Builder Pattern](#builder-pattern)

## Creational Patterns

Creational patterns deal with object creation mechanisms, trying to create objects in a manner suitable to the situation.

### Singleton Pattern

The Singleton pattern ensures that a class has only one instance and provides a global point of access to that instance. This is useful when exactly one object is needed to coordinate actions across the system.

#### Basic Concept

- Ensures a class has only one instance
- Provides a global point of access to that instance
- Private constructor to prevent instantiation from outside
- Static method to get the instance

#### Example 1: Logger

A singleton Logger class that provides a single instance for centralized logging throughout the application.

**Implementation Details:**

- The `Logger` class has a private static instance variable
- A private constructor prevents external instantiation
- The `getInstance()` method provides access to the singleton instance
- Thread-safety is ensured using the `synchronized` keyword
- Various logging methods (info, error, warning) are provided

**Usage Example:**

```java
// Get the Logger instance
Logger logger = Logger.getInstance();

// Log some messages
logger.info("Application started");
logger.error("An error occurred");
logger.warning("This is a warning");

// Get the Logger instance again (will be the same instance)
Logger anotherLogger = Logger.getInstance();
```

#### Example 2: Configuration Manager

A configuration manager singleton that reads configuration values from a file or environment variables and provides access to those values throughout the application.

**Implementation Details:**

- The `ConfigurationManager` class has a private static instance variable
- A private constructor initializes a map to store configuration values and loads environment variables
- The `getInstance()` method provides access to the singleton instance
- Methods to load configuration from a properties file
- Methods to get, set, and check configuration values

**Usage Example:**

```java
// Get the ConfigurationManager instance
ConfigurationManager config = ConfigurationManager.getInstance();

// Access environment variables
String javaHome = config.getValue("JAVA_HOME", "Not set");

// Set custom configuration values
config.setValue("app.name", "MyApp");
config.setValue("app.version", "1.0.0");

// Load configuration from a file
config.loadConfigFromFile("config.properties");

// Get configuration values
String dbUrl = config.getValue("database.url");
```

### Factory Pattern

The Factory pattern provides an interface for creating objects in a superclass, but allows subclasses to alter the type of objects that will be created. It's useful when a system should be independent of how its products are created, composed, and represented.

#### Basic Concept

- Creates objects without exposing the instantiation logic
- Refers to the newly created object through a common interface
- Allows for flexibility in creating different objects based on conditions

#### Example 1: Shape Factory

A factory class that can create different shapes (e.g., Circle, Square) based on a provided type string.

**Implementation Details:**

- The `Shape` interface defines common methods for all shapes
- Concrete classes (`Circle`, `Square`) implement the `Shape` interface
- The `ShapeFactory` class provides methods to create shapes based on type
- Factory methods return objects through the common `Shape` interface

**Usage Example:**

```java
// Create a shape factory
ShapeFactory shapeFactory = new ShapeFactory();

// Create shapes using the generic method
Shape circle = shapeFactory.createShape("circle", 5.0);
Shape square = shapeFactory.createShape("square", 4.0);

// Create shapes using specific methods
Shape anotherCircle = shapeFactory.createCircle(7.5);
Shape anotherSquare = shapeFactory.createSquare(6.0);

// Use the shapes polymorphically
circle.draw();
System.out.println("Area: " + circle.calculateArea());
```

#### Example 2: Database Connection Factory

A factory for database connections that can create connections for different database types (e.g., MySQL, PostgreSQL) based on configuration parameters.

**Implementation Details:**

- The `DatabaseConnection` interface defines common methods for all database connections
- Concrete classes (`MySQLConnection`, `PostgreSQLConnection`) implement the interface
- The `DatabaseConnectionFactory` class provides methods to create connections based on type
- Factory methods return objects through the common `DatabaseConnection` interface

**Usage Example:**

```java
// Create a database connection factory
DatabaseConnectionFactory connectionFactory = new DatabaseConnectionFactory();

// Create connections using the enum type
DatabaseConnection mysqlConnection = connectionFactory.createConnection(
    DatabaseConnectionFactory.DatabaseType.MYSQL,
    "localhost", 3306, "mydb", "root", "password"
);

// Create connections using string type
DatabaseConnection postgresConnection = connectionFactory.createConnection(
    "postgresql", "db.example.com", 5432, "production", "admin", "secure_password"
);

// Use the connections
mysqlConnection.connect();
String result = mysqlConnection.executeQuery("SELECT * FROM users");
mysqlConnection.disconnect();
```

### Builder Pattern

The Builder pattern separates the construction of a complex object from its representation, allowing the same construction process to create different representations. It's useful when an object needs to be created with many optional parameters or configurations.

#### Basic Concept

- Separates the construction of a complex object from its representation
- Allows the same construction process to create different representations
- Provides a clear step-by-step construction process
- Often uses method chaining for a fluent interface

#### Example 1: User Builder

A builder for a User object that allows for setting optional fields like email address or phone number.

**Implementation Details:**

- The `User` class has required and optional fields
- A private constructor takes a builder to initialize the fields
- The nested `UserBuilder` class provides methods to set optional fields
- The builder returns itself from each setter method for method chaining
- The `build()` method creates and returns the final `User` object

**Usage Example:**

```java
// Create a user with only required fields
User user1 = new User.UserBuilder("1", "johndoe")
        .build();

// Create a user with some optional fields
User user2 = new User.UserBuilder("2", "janedoe")
        .firstName("Jane")
        .lastName("Doe")
        .email("jane.doe@example.com")
        .build();

// Create a user with all fields
User user3 = new User.UserBuilder("3", "bobsmith")
        .firstName("Bob")
        .lastName("Smith")
        .email("bob.smith@example.com")
        .phoneNumber("123-456-7890")
        .address("123 Main St, Anytown, USA")
        .age(30)
        .active(true)
        .build();
```

#### Example 2: Search Query Builder

A builder for a complex search query object with various filter criteria. Users can build the query step-by-step using the builder.

**Implementation Details:**

- The `SearchQuery` class has many fields for different search criteria
- A private constructor takes a builder to initialize the fields
- The nested `SearchQueryBuilder` class provides methods to set search criteria
- The builder returns itself from each setter method for method chaining
- The `build()` method creates and returns the final `SearchQuery` object
- The `toSqlString()` method generates a SQL-like representation of the query

**Usage Example:**

```java
// Basic search with just a keyword
SearchQuery query1 = new SearchQuery.SearchQueryBuilder()
        .keyword("laptop")
        .build();

// Search with categories and sorting
SearchQuery query2 = new SearchQuery.SearchQueryBuilder()
        .keyword("phone")
        .addCategory("Electronics")
        .addCategory("Mobile Devices")
        .sortBy("price", false) // Sort by price descending
        .build();

// Advanced search with multiple filters
SearchQuery query3 = new SearchQuery.SearchQueryBuilder()
        .keyword("shoes")
        .categories(Arrays.asList("Footwear", "Sports", "Fashion"))
        .addFilter("brand", "Nike")
        .addFilter("color", "black")
        .priceRange(50.0, 200.0)
        .dateRange("2023-01-01", "2023-12-31")
        .page(2)
        .pageSize(25)
        .build();
```

## Running the Examples

Each pattern has example classes that demonstrate its usage. You can run these examples to see the patterns in action.

For example, to run the Logger singleton example:

```
java org.frank.designpatterns.singleton.LoggerExample
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.