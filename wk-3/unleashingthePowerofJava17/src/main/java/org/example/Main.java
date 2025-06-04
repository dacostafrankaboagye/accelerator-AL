package org.example;


public class Main {

    public String getCategoryMessage(String category){
        return switch (category) {
            case "Electronics" -> "This is an electronics product.";
            case "Clothing" -> "This is a clothing product.";
            default -> "This is a product.";
        };
    }

    public static void main(String[] args) {

        Product laptop = new Product("Laptop", 1200.0, "Electronics");

        System.out.println(laptop.name()); // Laptop
        System.out.println(laptop.price()); // 1200.0
        System.out.println(laptop.category()); // Electronics

        String productDescription = """
                                    Lenovo ThinkPad
                                    - Intel i7
                                    - 16GB RAM
                                    - 512GB SSD
                                    - Great for developers!
                                    """;

        System.out.println(productDescription);

    }
}