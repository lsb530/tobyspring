package boki.tobyspring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

class PaymentTest {

    @Test
    void createPrepared() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        Payment payment = Payment.createPrepared(
            1L, "USD", BigDecimal.TEN, BigDecimal.valueOf(1000), LocalDateTime.now(clock)
        );

        Assertions.assertThat(payment.getConvertedAmount()).isEqualByComparingTo(BigDecimal.valueOf(10_000));
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(LocalDateTime.now(clock).plusMinutes(30));
    }

    @Test
    void isValid() {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        Clock after30Minutes = Clock.offset(clock, Duration.of(30, ChronoUnit.MINUTES));

        Payment payment = Payment.createPrepared(
            1L, "USD", BigDecimal.TEN, BigDecimal.valueOf(1000), LocalDateTime.now(clock)
        );

        Assertions.assertThat(payment.isValid(clock)).isTrue();
        Assertions.assertThat(payment.isValid(after30Minutes)).isFalse();
    }

}