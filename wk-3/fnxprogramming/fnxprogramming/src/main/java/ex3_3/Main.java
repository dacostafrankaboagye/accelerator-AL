package ex3_3;
/*

🎯 Goal:
Refactor a verbose Java method to use lambdas and streams for clarity and maintainability.

*/

import domainclasses.Order;
import domainclasses.OrderItem;

import java.util.List;

public class Main {


    public static double traditionalCalculateBooksTotal(List<Order> orders) {
        double total = 0;
        for (Order order : orders) {
            for (OrderItem item : order.getItems()) {
                if ("Books".equalsIgnoreCase(item.getCategory())) {
                    total += item.getPrice() * item.getQuantity();
                }
            }
        }
        return total;
    }

    /*
    This is easier to read, test, and maintain.
    It avoids nested loops and make your intention clear with functional operations like filter, mapToDouble, and sum.

    */
    public static double functionalCalculateBooksTotal(List<Order> orders) {
        return orders.stream()
                .flatMap(order -> order.getItems().stream())
                .filter(item -> "Books".equalsIgnoreCase(item.getCategory()))
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
    }

    public static void main(String[] args) {

        List<Order> orders = List.of(
                new Order("ORD-001", "Alice", List.of(
                        new OrderItem("Laptop", "Electronics", 1200.00, 1),
                        new OrderItem("Notebook", "Books", 15.50, 3),
                        new OrderItem("Pen", "Stationery", 2.00, 5)
                )),
                new Order("ORD-002", "Bob", List.of(
                        new OrderItem("Textbook", "Books", 45.99, 2),
                        new OrderItem("Backpack", "Accessories", 59.99, 1)
                )),
                new Order("ORD-003", "Charlie", List.of(
                        new OrderItem("Novel", "Books", 19.99, 1),
                        new OrderItem("Charger", "Electronics", 29.99, 1)
                )),
                new Order("ORD-004", "Alice", List.of(
                        new OrderItem("Magazine", "Books", 5.99, 2),
                        new OrderItem("Tablet", "Electronics", 299.99, 1)
                ))
        );

        var traditionalresult = traditionalCalculateBooksTotal(orders);
        System.out.println("traditionalresult  $" +  traditionalresult);

        var functionalResult = functionalCalculateBooksTotal(orders);
        System.out.println("functionalResult  $" +  functionalResult);

    }
}
