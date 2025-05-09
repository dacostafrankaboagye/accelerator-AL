## Solution
![./dsa/README.md](./dsa/README.md)

# Design Patterns
- Creational Patterns:
    - Singleton Pattern:
        - Exercise 1: Implement a singleton class for a Logger that provides a single instance for 
        centralized logging throughout your application.
        - Exercise 2: Create a configuration manager singleton that reads configuration values from a file or environment 
        variables and provides access to those values throughout the application.
    - Factory Pattern:
        - Exercise 1: Design a factory class that can create different shapes (e.g., Circle, Square) based on a provided type string.
        - Exercise 2: Implement a factory for database connections. The factory can create connections for different database types (e.g., MySQL, PostgreSQL) based on configuration parameters.
    - Builder Pattern:
        - Exercise 1: Create a builder for a User object that allows for setting optional fields like email address or phone number.
        - Exercise 2: Design a builder for a complex search query object with various filter criteria. Users can build the query step-by-step using the builder.
    - Prototype
    - Abstract Factory

- Structural Patterns:
    - Adapter Pattern:
        - Exercise 1: Create an adapter that allows you to use a legacy payment processing library
         with a modern payment gateway API.
        - Exercise 2: Implement an adapter to make an incompatible datasource (e.g., CSV file) accessible through a common interface used by your application.
    - Decorator Pattern:
        - Exercise 1: Design a decorator for a Shape interface that adds functionality like colored borders or transparency effects.
        - Exercise 2: Implement a decorator for text messages that adds encryption or compression before sending them.
    - Facade Pattern:
        - Exercise 1: Create a facade for a shopping cart system that simplifies adding items, calculating discounts, and initiating checkout.
        - Exercise 2: Design a facade for a complex email sending system that hides the underlying details of composing, sending,and managing email messages.
    - Proxy Pattern:
        - Exercise 1: Implement a proxy for a remote service that handles caching responses and managing network connections.
        - Exercise 2: Create a proxy for a file download process that adds authorization checks and progress reporting before allowing the actual download
    - Bridge
    - Composite
    - flyweight

- Behavioral Patterns:
    - Observer Pattern:
        - Exercise 1: Design a weather station application using the observer 
        pattern. The weather station acts as a subject, and observers can be 
        displays or data loggers that update when weather data changes.
        - Exercise 2: Implement a stock price monitoring system with an observer 
        pattern. Users can subscribe to specific stocks, and the system notifies 
        them when prices change.
    - Strategy Pattern:
        - Exercise 1: Develop a sorting application that allows users to choose 
        between different sorting algorithms (e.g., bubble sort, selection sort) at 
        runtime using the strategy pattern.
        - Exercise 2: Design a compression library with different compression 
        strategies (e.g., zip, gzip) that can be plugged in based on user 
        preferences or file type.
    - Template Method Pattern:
        - Exercise 1: Implement an order processing system with a template 
        method. Subclasses can handle specific order types (e.g., domestic, 
        international) by overriding certain steps in the template.
        - Exercise 2: Design a data validation framework with a template method 
        pattern. Subclasses can implement specific validation rules for different 
        data types (e.g., email address, phone number) within the defined 
        template.
    - Chain of responsibility
    - Mediator
    - Memento
    - Iterator
    - Interpreter
    - Command
    - State
    - Visitor

