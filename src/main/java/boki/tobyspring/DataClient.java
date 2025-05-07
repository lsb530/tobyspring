package boki.tobyspring;

import boki.tobyspring.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        EntityManagerFactory entityManagerFactory = beanFactory.getBean(EntityManagerFactory.class);

        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();

        Order order = new Order("item1", BigDecimal.TEN);
        System.out.println(order);

        em.persist(order);

        System.out.println(order);

        em.getTransaction().commit();
        em.close();
    }

}
