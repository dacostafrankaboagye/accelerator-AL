package ex3_2;

/*
🎯 Goal:
Use Streams API to process a list of orders.
Calculate statistics like total amount, order count, etc.

*/


import domainclasses.Order;
import domainclasses.OrderItem;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        List<Order> orders = List.of(
                Order.builder()
                        .orderId("01")
                        .customer("Alice")
                        .items(List.of(
                                new OrderItem("Laptop", "Electronics", 1000, 1),
                                new OrderItem("Mouse", "Electronics", 50, 2)
                        ))
                        .build(),
                Order.builder()
                        .orderId("02")
                        .customer("Bob")
                        .items(List.of(
                                new OrderItem("T-Shirt", "Fashion", 25, 4)
                        ))
                        .build(),
                Order.builder()
                        .orderId("03")
                        .customer("Alice")
                        .items(List.of(
                                new OrderItem("Book", "Books", 30, 2)
                        ))
                        .build()
        );

        // Total Order Amount
        double totalAmount = orders.stream()
                .flatMap(order -> order.getItems().stream())
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        System.out.println("\uD83D\uDCB0 Total Order Amount: " + totalAmount);

        // Number of Orders per Customer
        Map<String, Long> ordersPerCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.counting()));
        System.out.println("\uD83D\uDC64 Number of Orders per Customer: " + ordersPerCustomer);

        // Total Spent Per Customer
        Map<String, Double> amountPerCustomer = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.summingDouble(order ->
                                order.getItems().stream()
                                        .mapToDouble(item -> item.getPrice() * item.getQuantity())
                                        .sum()
                        )
                ));
        System.out.println("\uD83D\uDCCA Total Spent Per Customer: " + amountPerCustomer);
    }
}
