public class Main {

    public static void main(String[] args) {

        OrderRepository orderRepository = new OrderRepository();

        orderRepository.findAllOrders().forEach(System.out::println);


    }
}
