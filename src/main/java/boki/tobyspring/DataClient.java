package boki.tobyspring;

import boki.tobyspring.data.OrderRepository;
import boki.tobyspring.order.Order;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository repository = beanFactory.getBean(OrderRepository.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        new TransactionTemplate(transactionManager).execute(new TransactionCallback<Order>() {
            @Override
            public Order doInTransaction(TransactionStatus status) {
                return null;
            }
        });

        // transaction begin
        Order order = new Order("100", BigDecimal.TEN);
        repository.save(order);

        System.out.println(order);

        try {
            Order order2 = new Order("100", BigDecimal.ONE);
            repository.save(order2);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        // commit
    }

}
