package reflec_anno;

/**
 * A simple test class that can be compiled and placed in the custom_classes directory
 * to test the custom class loader implementation.
 * 
 * To use this class:
 * 1. Compile it: javac reflec_anno/TestCustomClass.java
 * 2. Move the .class file to the custom_classes directory
 * 3. Run Ex2 to load this class using the custom class loader
 */
public class TestCustomClass {
    private String message;
    
    public TestCustomClass() {
        this.message = "Hello from custom loaded class!";
    }
    
    public TestCustomClass(String message) {
        this.message = message;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void printMessage() {
        System.out.println(message);
    }
    
    @Override
    public String toString() {
        return "TestCustomClass [message=" + message + "]";
    }
}