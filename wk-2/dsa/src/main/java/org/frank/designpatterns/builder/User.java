package org.frank.designpatterns.builder;

/**
 * User class with optional fields that can be set using a builder.
 */
public class User {
    // Required fields
    private final String id;
    private final String username;
    
    // Optional fields
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phoneNumber;
    private final String address;
    private final int age;
    private final boolean active;
    
    /**
     * Private constructor that takes a builder to initialize the fields.
     * This constructor is private to enforce the use of the builder.
     * 
     * @param builder The builder to use for initialization
     */
    private User(UserBuilder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
        this.age = builder.age;
        this.active = builder.active;
    }
    
    // Getters for all fields
    
    public String getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public int getAge() {
        return age;
    }
    
    public boolean isActive() {
        return active;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                ", active=" + active +
                '}';
    }
    
    /**
     * Builder class for User.
     */
    public static class UserBuilder {
        // Required fields
        private final String id;
        private final String username;
        
        // Optional fields with default values
        private String firstName = "";
        private String lastName = "";
        private String email = "";
        private String phoneNumber = "";
        private String address = "";
        private int age = 0;
        private boolean active = false;
        
        /**
         * Constructor for UserBuilder with required fields.
         * 
         * @param id The user ID
         * @param username The username
         */
        public UserBuilder(String id, String username) {
            this.id = id;
            this.username = username;
        }
        
        /**
         * Set the first name.
         * 
         * @param firstName The first name
         * @return This builder for method chaining
         */
        public UserBuilder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }
        
        /**
         * Set the last name.
         * 
         * @param lastName The last name
         * @return This builder for method chaining
         */
        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }
        
        /**
         * Set the email address.
         * 
         * @param email The email address
         * @return This builder for method chaining
         */
        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        /**
         * Set the phone number.
         * 
         * @param phoneNumber The phone number
         * @return This builder for method chaining
         */
        public UserBuilder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        
        /**
         * Set the address.
         * 
         * @param address The address
         * @return This builder for method chaining
         */
        public UserBuilder address(String address) {
            this.address = address;
            return this;
        }
        
        /**
         * Set the age.
         * 
         * @param age The age
         * @return This builder for method chaining
         */
        public UserBuilder age(int age) {
            this.age = age;
            return this;
        }
        
        /**
         * Set the active status.
         * 
         * @param active The active status
         * @return This builder for method chaining
         */
        public UserBuilder active(boolean active) {
            this.active = active;
            return this;
        }
        
        /**
         * Build the User object.
         * 
         * @return A new User object with the specified fields
         */
        public User build() {
            return new User(this);
        }
    }
}