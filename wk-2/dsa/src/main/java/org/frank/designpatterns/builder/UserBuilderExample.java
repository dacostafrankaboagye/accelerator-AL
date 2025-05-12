package org.frank.designpatterns.builder;

/**
 * Example class demonstrating the usage of the User builder.
 */
public class UserBuilderExample {
    
    public static void main(String[] args) {
        System.out.println("User Builder Example");
        System.out.println("-------------------");
        
        // Example 1: Create a user with only required fields
        User user1 = new User.UserBuilder("1", "johndoe")
                .build();
        
        System.out.println("User 1 (minimal):");
        System.out.println(user1);
        
        // Example 2: Create a user with some optional fields
        User user2 = new User.UserBuilder("2", "janedoe")
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .build();
        
        System.out.println("\nUser 2 (with some optional fields):");
        System.out.println(user2);
        
        // Example 3: Create a user with all fields
        User user3 = new User.UserBuilder("3", "bobsmith")
                .firstName("Bob")
                .lastName("Smith")
                .email("bob.smith@example.com")
                .phoneNumber("123-456-7890")
                .address("123 Main St, Anytown, USA")
                .age(30)
                .active(true)
                .build();
        
        System.out.println("\nUser 3 (with all fields):");
        System.out.println(user3);
        
        // Example 4: Demonstrate the flexibility of the builder pattern
        // We can set the fields in any order
        User user4 = new User.UserBuilder("4", "alicegreen")
                .active(true)
                .age(25)
                .email("alice.green@example.com")
                .firstName("Alice")
                .lastName("Green")
                .build();
        
        System.out.println("\nUser 4 (fields set in different order):");
        System.out.println(user4);
        
        // Example 5: Create multiple users with similar properties
        User.UserBuilder employeeBuilder = new User.UserBuilder("", "")
                .address("456 Corporate Ave, Business City, USA")
                .active(true);
        
        User employee1 = employeeBuilder
                .firstName("Employee")
                .lastName("One")
                .email("employee1@company.com")
                .build();
        
        User employee2 = employeeBuilder
                .firstName("Employee")
                .lastName("Two")
                .email("employee2@company.com")
                .build();
        
        System.out.println("\nNote: Example 5 doesn't work as expected because each builder method returns a new builder.");
        System.out.println("To create multiple users with similar properties, you would need to create a separate builder for each user.");
    }
}