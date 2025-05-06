package boki.tobyspring;

import boki.tobyspring.api.ApiTemplate;
import boki.tobyspring.api.ErApiExtractor;
import boki.tobyspring.api.SimpleApiExecutor;
import boki.tobyspring.payment.ExRateProvider;
import boki.tobyspring.exrate.WebApiExRateProvider;
import boki.tobyspring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ApiTemplate apiTemplate() {
        return new ApiTemplate(new SimpleApiExecutor(), new ErApiExtractor());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider(apiTemplate());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
