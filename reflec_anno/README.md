# Reflection and Annotations Exercises

This package contains implementations for two exercises related to Java reflection and annotations.

## Exercise 1: LogExecutionTime Annotation

### Description
A custom annotation `@LogExecutionTime` that logs the execution time of methods. When a method is annotated with `@LogExecutionTime`, the time it takes to execute will be measured and logged.

### Implementation
- `LogExecutionTime.java`: The annotation interface
- `Ex1.java`: Demonstration of the annotation usage with a dynamic proxy implementation

### How to Use
1. Annotate any method with `@LogExecutionTime`
2. Use a dynamic proxy to intercept method calls (as shown in `Ex1.java`)
3. The execution time will be logged when the method is called

### Example
```java
@LogExecutionTime
public void methodWithAnnotation() {
    // Method implementation
}
```

### Running the Example
```
java reflec_anno.Ex1
```

## Exercise 2: Custom Class Loader

### Description
A custom class loader that can load classes from a specified directory at runtime using reflection.

### Implementation
- `Ex2.java`: Contains the `CustomClassLoader` implementation and a demonstration
- `TestCustomClass.java`: A sample class that can be used to test the custom class loader

### How to Use
1. Compile the test class: `javac reflec_anno/TestCustomClass.java`
2. Create a directory named `custom_classes` (if it doesn't exist)
3. Copy the compiled `.class` file to the `custom_classes` directory
4. Run `Ex2` to load and interact with the class

### Running the Example
```
java reflec_anno.Ex2
```

### Expected Output
When running with `TestCustomClass.class` in the `custom_classes` directory:
```
Successfully loaded class: TestCustomClass
  - Class loader: reflec_anno.CustomClassLoader@...
  - Methods: 5
  - Created instance: TestCustomClass [message=Hello from custom loaded class!]
  - Invoked getMessage(): Hello from custom loaded class!
  - Invoked printMessage(): Hello from custom loaded class!
  - Changed message using setMessage()
  - Updated message: Message changed via reflection!
```

## Technical Details

### LogExecutionTime Implementation
The implementation uses Java's dynamic proxy mechanism to intercept method calls and measure execution time for methods annotated with `@LogExecutionTime`.

### CustomClassLoader Implementation
The custom class loader extends Java's `ClassLoader` class and overrides:
- `findClass()`: To locate and load class files from a custom directory
- `loadClass()`: To handle the class loading process with proper delegation

The loader reads `.class` files from the specified directory, converts them to byte arrays, and defines classes from those bytes using the `defineClass()` method.