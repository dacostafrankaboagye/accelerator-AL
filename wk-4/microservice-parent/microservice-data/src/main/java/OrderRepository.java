import java.util.List;

public class OrderRepository {

    public List<Order> findAllOrders() {
        return List.of(
                new Order(1L, "Laptop", 1200.0),
                new Order(2L, "Headphones", 150.0),
                new Order(3L, "Monitor", 300.0)
        );

    }
}
