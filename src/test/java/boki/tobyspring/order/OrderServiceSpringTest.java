package boki.tobyspring.order;

import boki.tobyspring.OrderConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = OrderConfig.class)
public class OrderServiceSpringTest {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DataSource dataSource;

    @Test
    void createOrder() {
        var order = orderService.createOrder("0100", BigDecimal.ONE);

        Assertions.assertThat(order.getId()).isGreaterThan(0);
    }

    @Test
    void getOrder() {
        Order order = orderRepository.findOrderById(1L);

        Assertions.assertThat(order.getId()).isEqualTo(1);
    }

    @Test
    void createOrders() {
        List<OrderReq> orderReqs = List.of(
            new OrderReq("0200", BigDecimal.ONE),
            new OrderReq("0201", BigDecimal.TWO)
        );

        var orders = orderService.createOrders(orderReqs);

        Assertions.assertThat(orders).hasSize(2);
        orders.forEach(order -> Assertions.assertThat(order.getId()).isGreaterThan(0));
    }

    @Test
    void createDuplicatedOrders() {
        System.out.println(this.orderService.getClass()); // Proxy

        List<OrderReq> orderReqs = List.of(
            new OrderReq("0300", BigDecimal.ONE),
            new OrderReq("0300", BigDecimal.TWO)
        );

        Assertions.assertThatThrownBy(() -> orderService.createOrders(orderReqs))
            .isInstanceOf(DataIntegrityViolationException.class);

        JdbcClient client = JdbcClient.create(dataSource);
        var count = client.sql("SELECT COUNT(*) FROM orders WHERE NO = '0300'").query(Long.class).single();
        Assertions.assertThat(count).isEqualTo(0);
    }

}
