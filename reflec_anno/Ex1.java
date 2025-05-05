package reflec_anno;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Ex1 {

    public static void main(String[] args) {
        // Create an instance of TestClass
        TestClass testObj = new TestClass();

        // Create a proxy for the TestClass
        TestInterface proxy = createProxy(testObj);

        // Call methods through the proxy
        proxy.methodWithAnnotation();
        proxy.methodWithoutAnnotation();

        // Direct method calls (without proxy)
        System.out.println("\nDirect method calls (without proxy):");
        testObj.methodWithAnnotation();
        testObj.methodWithoutAnnotation();
    }

    // Create a dynamic proxy that handles the LogExecutionTime annotation
    private static TestInterface createProxy(TestClass target) {
        return (TestInterface) Proxy.newProxyInstance(
            Ex1.class.getClassLoader(),
            new Class<?>[] { TestInterface.class },
            new LogExecutionTimeHandler(target)
        );
    }
}

// Interface for our test class
interface TestInterface {
    void methodWithAnnotation();
    void methodWithoutAnnotation();
}

// Implementation class
class TestClass implements TestInterface {

    @LogExecutionTime
    public void methodWithAnnotation() {
        System.out.println("Executing method with @LogExecutionTime annotation");
        // Simulate some work
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void methodWithoutAnnotation() {
        System.out.println("Executing method without annotation");
        // Simulate some work (shorter)
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// InvocationHandler that processes the LogExecutionTime annotation
class LogExecutionTimeHandler implements InvocationHandler {
    private final Object target;

    public LogExecutionTimeHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // Get the method from the original class (not the proxy)
        Method originalMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

        // Check if the method has the LogExecutionTime annotation
        if (originalMethod.isAnnotationPresent(LogExecutionTime.class)) {
            long startTime = System.currentTimeMillis();

            // Invoke the method
            Object result = originalMethod.invoke(target, args);

            long endTime = System.currentTimeMillis();
            System.out.println("[LogExecutionTime] Method '" + originalMethod.getName() + 
                               "' executed in " + (endTime - startTime) + " ms");

            return result;
        } else {
            // If the method doesn't have the annotation, just invoke it normally
            return originalMethod.invoke(target, args);
        }
    }
}
