package boki.tobyspring.data;

import boki.tobyspring.order.Order;
import boki.tobyspring.order.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import jakarta.annotation.PostConstruct;
import org.springframework.jdbc.core.simple.JdbcClient;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class JdbcOrderRepository implements OrderRepository {

    private final JdbcClient jdbcClient;

    private final ObjectMapper mapper;

    public JdbcOrderRepository(DataSource dataSource) {
        this.jdbcClient = JdbcClient.create(dataSource);
        mapper = new ObjectMapper();
        // H2 설정때문에.. ㅂㄷㅂㄷ
        this.mapper.setPropertyNamingStrategy(new PropertyNamingStrategies.UpperSnakeCaseStrategy());
    }

    @PostConstruct
    void initDB() {
        jdbcClient.sql("""
                CREATE TABLE orders
            (
                id    bigint NOT NULL,
                no    varchar(255),
                total numeric(38, 2),
                PRIMARY KEY (id)
            );
            ALTER TABLE if EXISTS orders DROP CONSTRAINT IF EXISTS UK_43egxxciqr9ncgmxbdx2avi8n;
            ALTER TABLE if EXISTS orders ADD CONSTRAINT UK_43egxxciqr9ncgmxbdx2avi8n UNIQUE (NO);
            CREATE SEQUENCE orders_SEQ START WITH 1 INCREMENT BY 50;
            """).update();
    }

    @Override
    public void save(Order order) {
        Long id = jdbcClient.sql("SELECT next value FOR orders_SEQ").query(Long.class).single();
        order.setId(id);

        jdbcClient.sql("INSERT INTO orders (no,total,id) VALUES (?,?,?)")
            .params(order.getNo(), order.getTotal(), order.getId())
            .update();
    }

    public Order findOrderById(Long id) {
        List<Map<String, Object>> rows = jdbcClient.sql("SELECT * FROM orders WHERE id = ?")
            .param(id).query().listOfRows();

        return mapper.convertValue(
            rows.getFirst(),
            Order.class
        );
    }

}
