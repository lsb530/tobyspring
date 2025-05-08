package boki.tobyspring.order;

public interface OrderRepository {

    void save(Order order);

    Order findOrderById(Long id);

}
