package boki.tobyspring.payment;

import boki.tobyspring.TestObjectFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestObjectFactory.class})
class PaymentServiceSpringTest {

    @Autowired BeanFactory beanFactory;
    @Autowired PaymentService paymentService;
    @Autowired ExRateProviderStub exRateProviderStub;

    @Test
    void convertedAmount() throws IOException {
        // BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
        // PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
    }

    @Test
    void convertedAmountWithStub() throws IOException {
        // exRate: 1000
        Payment payment = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(1_000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));

        // exRate: 500
        exRateProviderStub.setExRate(BigDecimal.valueOf(500));
        Payment payment2 = paymentService.prepare(1L, "USD", BigDecimal.TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(BigDecimal.valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(5_000));
    }

}