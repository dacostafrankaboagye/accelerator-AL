package reflec_anno;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Exercise 2: Implement a class loader that can load java classes from a 
 * custom directory at runtime using reflection.
 */
public class Ex2 {

    public static void main(String[] args) {
        try {
            // Directory where custom classes are stored
            String customClassDir = "custom_classes";

            // Create the directory if it doesn't exist
            File dir = new File(customClassDir);
            if (!dir.exists()) {
                dir.mkdir();
                System.out.println("Created directory: " + customClassDir);
                System.out.println("Please compile TestCustomClass.java and move the .class file to this directory.");
                System.out.println("Then run this program again.");
                return;
            }

            // Initialize our custom class loader
            CustomClassLoader loader = new CustomClassLoader(customClassDir);

            // Load the TestCustomClass using our custom class loader
            Class<?> customClass = loader.loadClass("reflec_anno.TestCustomClass");
            System.out.println("Successfully loaded class: " + customClass.getName());

            // Create an instance of the loaded class using reflection
            Object instance = customClass.getDeclaredConstructor().newInstance();
            System.out.println("Created instance: " + instance);

            // Get and invoke the getMessage method
            Method getMessageMethod = customClass.getMethod("getMessage");
            String message = (String) getMessageMethod.invoke(instance);
            System.out.println("Original message: " + message);

            // Get and invoke the setMessage method
            Method setMessageMethod = customClass.getMethod("setMessage", String.class);
            setMessageMethod.invoke(instance, "Message changed through reflection!");

            // Get the updated message
            message = (String) getMessageMethod.invoke(instance);
            System.out.println("Updated message: " + message);

            // Invoke the printMessage method
            Method printMessageMethod = customClass.getMethod("printMessage");
            System.out.print("Calling printMessage(): ");
            printMessageMethod.invoke(instance);

            // Create another instance using a different constructor
            Constructor<?> constructor = customClass.getConstructor(String.class);
            Object instance2 = constructor.newInstance("Custom message via constructor");
            System.out.println("Created second instance with custom message: " + instance2);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
                 NoSuchMethodException | InvocationTargetException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

/**
 * A custom class loader that loads classes from a specified directory.
 */
class CustomClassLoader extends ClassLoader {
    private String classDir;

    public CustomClassLoader(String classDir) {
        this.classDir = classDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // Convert class name to file path
            String classPath = name.replace('.', File.separatorChar) + ".class";
            Path path = Paths.get(classDir, classPath);

            // Read the class file bytes
            byte[] classBytes = Files.readAllBytes(path);

            // Define the class
            return defineClass(name, classBytes, 0, classBytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class " + name, e);
        }
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        // First, check if the class has already been loaded
        Class<?> loadedClass = findLoadedClass(name);
        if (loadedClass != null) {
            return loadedClass;
        }

        try {
            // Try to load the class from our custom directory
            return findClass(name);
        } catch (ClassNotFoundException e) {
            // If not found in our directory, delegate to parent class loader
            return super.loadClass(name);
        }
    }
}
