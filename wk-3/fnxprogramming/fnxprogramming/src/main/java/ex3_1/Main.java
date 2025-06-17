package ex3_1;

/*
🔁 Goal:
Use a lambda to calculate the total of an order.
Use lambdas to filter or transform order items by criteria (e.g., price or category).
*/

import domainclasses.OrderItem;

import java.util.List;

public class Main {

    private static  final  List<OrderItem> MYITEMS = List.of(
            new OrderItem("Laptop", "Electronics", 1200, 1),
            new OrderItem("Shoes", "Fashion", 100, 2),
            new OrderItem("Book", "Books", 20, 5)
    );

    static void usingAnonymousInnerClass(){
        TotalCalculator cal = new TotalCalculator() {
            @Override
            public double calculate(List<OrderItem> items) {
                double total = 0;
                for(OrderItem item : items){
                    total += item.getQuantity() * item.getPrice();
                }
                return total;
            }
        };
        double result  = cal.calculate(MYITEMS);
        System.out.println("Result : " + result );
    }

    static void usingLambda(){
        TotalCalculator cal = items ->
                items
                     .stream()
                     .mapToDouble(item -> item.getQuantity() * item.getPrice())
                     .sum();
        double result  = cal.calculate(MYITEMS);
        System.out.println("Result : " + result );
    }

    static void filterAndTransformExample(){

        // Filter expensive items
        System.out.println("Expensive items:");
        MYITEMS.stream()
                .filter(item -> item.getPrice() > 100)
                .forEach(System.out::println);

        // Transform: get names of all fashion items
        System.out.println("\nFashion items:");
        List<String> fashionItems = MYITEMS.stream()
                .filter(item -> item.getCategory().equalsIgnoreCase("Fashion"))
                .map(OrderItem::getName)
                .toList();
        fashionItems.forEach(System.out::println);
    }

    public static void main(String[] args) {

        // usingAnonymousInnerClass();

        // usingLambda();

        filterAndTransformExample();



    }
}
