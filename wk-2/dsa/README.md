# Design Patterns in Java

This repository contains implementations of various design patterns in Java. Each pattern is explained with examples to demonstrate its usage and benefits.

## Table of Contents

- [Creational Patterns](#creational-patterns)
  - [Singleton Pattern](#singleton-pattern)
  - [Factory Pattern](#factory-pattern)
  - [Builder Pattern](#builder-pattern)

- [Structural Patterns](#structural-patterns)
  - [Adapter Pattern](#adapter-pattern)
  - [Decorator Pattern](#decorator-pattern)
  - [Facade Pattern](#facade-pattern)
  - [Proxy Pattern](#proxy-pattern)

- [Behavioral Patterns](#behavioral-patterns)
  - [Observer Pattern](#observer-pattern)
  - [Strategy Pattern](#strategy-pattern)
  - [Template Method Pattern](#template-method-pattern)


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

## Structural Patterns

Structural patterns deal with the composition of classes and objects to form larger structures and provide new functionality.

### Adapter Pattern

The Adapter pattern allows incompatible interfaces to work together. It acts as a bridge between two incompatible interfaces by wrapping an instance of one class into an adapter class that presents the interface expected by clients.

#### Implementations:

1. **Payment Processing Adapter**
  - Adapts a legacy payment processing system to work with a modern payment gateway API
  - Classes: `LegacyPaymentProcessor`, `ModernPaymentGateway`, `PaymentProcessorAdapter`
  - Example: `PaymentAdapterExample`

2. **Data Source Adapter**
  - Makes an incompatible CSV data source accessible through a common interface
  - Classes: `CSVDataSource`, `DataSource`, `CSVDataSourceAdapter`
  - Example: `DataSourceAdapterExample`

### Decorator Pattern

The Decorator pattern attaches additional responsibilities to objects dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

#### Implementations:

1. **Shape Decorators**
  - Adds visual enhancements to shape objects
  - Base: `Shape` interface with `Circle` and `Rectangle` implementations
  - Decorators: `BorderDecorator`, `ColorDecorator`, `TransparencyDecorator`
  - Example: `ShapeDecoratorExample`

2. **Message Decorators**
  - Adds processing capabilities to messages before sending
  - Base: `Message` interface with `TextMessage` implementation
  - Decorators: `EncryptionDecorator`, `CompressionDecorator`
  - Example: `MessageDecoratorExample`

### Facade Pattern

The Facade pattern provides a simplified interface to a complex subsystem. It doesn't hide the subsystem but makes it easier to use by providing a higher-level interface.

#### Implementations:

1. **Shopping Cart Facade**
  - Simplifies the process of adding items, calculating discounts, and checkout
  - Facade: `ShoppingCartFacade`
  - Subsystem: `Product`, `Inventory`, `Discount`, `Order`, `Payment`, `ShoppingCart`
  - Example: `ShoppingCartFacadeExample`

2. **Email System Facade**
  - Hides the complexity of composing, sending, and managing email messages
  - Facade: `EmailFacade`
  - Subsystem: `EmailComposer`, `EmailSender`, `EmailLogger`, `EmailTemplate`, `EmailValidator`
  - Example: `EmailFacadeExample`

### Proxy Pattern

The Proxy pattern provides a surrogate or placeholder for another object to control access to it. It can be used for lazy loading, access control, logging, etc.

#### Implementations:

1. **Remote Service Proxy**
  - Handles caching responses and managing network connections
  - Interface: `RemoteService`
  - Implementation: `RemoteServiceImpl`
  - Proxy: `RemoteServiceProxy`
  - Example: `RemoteServiceProxyExample`

2. **File Download Proxy**
  - Adds authorization checks and progress reporting before allowing downloads
  - Interface: `FileDownloader`
  - Implementation: `FileDownloaderImpl`
  - Proxy: `FileDownloaderProxy`
  - Example: `FileDownloaderProxyExample`

## Behavioral Patterns

Behavioral patterns are concerned with algorithms and the assignment of responsibilities between objects.

### Observer Pattern

The Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

#### Example 1: Weather Station

In this example, a `WeatherStation` acts as the subject, and observers like `CurrentConditionsDisplay` update when weather data changes.

```java
// Create the weather station (subject)
WeatherStation weatherStation = new WeatherStation();

// Create and register the display (observer)
CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherStation);

// Change the weather data
weatherStation.setMeasurements(80, 65, 30.4f);
```

#### Example 2: Stock Price Monitoring

This example implements a stock price monitoring system where users can subscribe to specific stocks and receive updates when prices change.

```java
// Create the stock subject
StockSubject stockMarket = new StockSubject();

// Add some stocks with initial prices
stockMarket.addStock("AAPL", 150.25);
stockMarket.addStock("GOOGL", 2750.80);

// Create a stock display (observer)
StockDisplay userDisplay = new StockDisplay(stockMarket);

// Subscribe to specific stocks
userDisplay.subscribeToStock("AAPL");

// Update a stock price (will notify observers)
stockMarket.updateStockPrice("AAPL", 152.75);
```

### Strategy Pattern

The Strategy pattern defines a family of algorithms, encapsulates each one, and makes them interchangeable. It lets the algorithm vary independently from clients that use it.

#### Example 1: Sorting Application

This example demonstrates a sorting application that allows users to choose between different sorting algorithms (bubble sort, selection sort) at runtime.

```java
// Create sorting strategies
SortingStrategy bubbleSort = new BubbleSortStrategy();
SortingStrategy selectionSort = new SelectionSortStrategy();

// Create context with initial strategy
SortingContext context = new SortingContext(bubbleSort);

// Sample array to sort
int[] array = {64, 34, 25, 12, 22, 11, 90};

// Sort using bubble sort
int[] sortedArray = context.executeSort(array);
System.out.println("Sorted with " + context.getStrategyName());

// Change strategy and sort again
context.setStrategy(selectionSort);
sortedArray = context.executeSort(array);
System.out.println("Sorted with " + context.getStrategyName());
```

#### Example 2: Compression Library

This example implements a compression library with different compression strategies (ZIP, GZIP) that can be selected based on user preferences or file type.

```java
// Create compression strategies
CompressionStrategy zipStrategy = new ZipStrategy();
CompressionStrategy gzipStrategy = new GzipStrategy();

// Create context with initial strategy
CompressionContext context = new CompressionContext(zipStrategy);

// Compress a file using ZIP
File sourceFile = new File("example.txt");
File compressedFile = context.compress(sourceFile, "compressed");

// Change strategy to GZIP and compress again
context.setStrategy(gzipStrategy);
compressedFile = context.compress(sourceFile, "compressed");
```

### Template Method Pattern

The Template Method pattern defines the skeleton of an algorithm in a method, deferring some steps to subclasses. It lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure.

#### Example 1: Order Processing

This example implements an order processing system with a template method. Subclasses handle specific order types (domestic, international) by overriding certain steps in the template.

```java
// Create order processors
OrderProcessor domesticProcessor = new DomesticOrderProcessor();
OrderProcessor internationalProcessor = new InternationalOrderProcessor();

// Process a domestic order
boolean domesticResult = domesticProcessor.processOrder("DOM12345");

// Process an international order
boolean internationalResult = internationalProcessor.processOrder("INT67890");
```

#### Example 2: Data Validation Framework

This example implements a data validation framework with a template method pattern. Subclasses implement specific validation rules for different data types (email address, phone number) within the defined template.

```java
// Create validators
DataValidator emailValidator = new EmailValidator();
DataValidator phoneValidator = new PhoneNumberValidator();

// Validate an email address
boolean isValidEmail = emailValidator.validate("user@example.com");

// Validate a phone number
boolean isValidPhone = phoneValidator.validate("+1234567890");
```

## License

IT'S OPEN - that's it !!