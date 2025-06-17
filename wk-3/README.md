### Unleashing the Power of Java 17+
-  Scope: Design a system for managing product information in an online store.
    - Exercise 1.1: Modeling Products with Records
        - Create a Record named Product(String name, double price, String category)
        - Explore using accessors (getName(), getPrice(), etc.) to access product data within the Record.
    - Exercise 1.2: Sealing the Product Hierarchy
        - Design a Sealed Class hierarchy for Product with subclasses for different product types (e.g., ElectronicsProduct, ClothingProduct).
        - Utilize sealed subclasses to restrict the creation of unauthorized product types.
    - Exercise 1.3: Exploring New Java Features 
        - Experiment with switch expressions for product category handling.
        - Investigate text blocks for cleaner multi-line string literals within product descriptions (if applicable).
- solution
    - [./unleashingthePowerofJava17/README.md](./unleashingthePowerofJava17/README.md)

### Mastering Reactive Programming with 
- Scope
1. Grasp the core concepts of reactive programming and its advantages for handling 
asynchronous and event-driven data streams.
2. Implement reactive patterns effectively using RxJava operators for manipulating 
and transforming data streams.
3. Apply RxJava to handle real-world scenarios involving asynchronous operations 
(e.g., API calls, user interactions).
- Exercises: Scenario 2: Stock Price Monitoring
    - Scope: Develop a real-time stock price monitoring application that retrieves data from a financial API (e.g., Alpha Vantage) and displays it dynamically.
- Exercise 2.1: Understanding Reactive Programming
    - Research the core principles of reactive programming (Observables, Subscribers,Schedulers).
    - Compare reactive programming to traditional imperative approaches.
- Exercise 2.2: Setting Up RxJava
    - Integrate RxJava library into your project.
    - Explore basic operators like map, filter, and subscribe using sample data streams.
- Exercise 2.3: Building a Reactive Stock Price Dashboard
    - Connect to a financial API and retrieve real-time stock price data (consider using an RxJava library for API calls).
    - Utilize RxJava operators to filter and process the data stream for relevant information (e.g., specific stock symbols).
    - Update a UI (consider JavaFX or a web framework) dynamically based on the processed data stream.

- Solution
    - [./therxjava/README.md](./therxjava/README.md)

### Exercises:
-  E-commerce Order Processing
    - Scope: Design a system for processing customer orders in an e-commerce application.
        - Exercise 3.1: Lambda Power
            - Refactor a code snippet for calculating order total using a lambda expression instead of an anonymous inner class.
            - Explore using lambdas for filtering and transforming order items based on specific criteria (e.g., price, category).
        - Exercise 3.2: Streamlining with Streams
            - Utilize Java Streams API (e.g., filter, map, reduce) to process a list of customer orders.
            - Calculate statistics like total order amount or number of orders per customer using Streams.
            - Exercise 3.3: Functional Refactoring
            - Identify a section of your existing Java code that could benefit from functional programming techniques.
            - Refactor the code using lambdas and Streams to improve readability and maintainability.
- Solution
    - [./fnxprogramming/README.md](./fnxprogramming/README.md)

