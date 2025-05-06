package boki.tobyspring;

import boki.tobyspring.api.ApiTemplate;
import boki.tobyspring.api.ErApiExtractor;
import boki.tobyspring.api.SimpleApiExecutor;
import boki.tobyspring.exrate.RestTemplateExRateProvider;
import boki.tobyspring.payment.ExRateProvider;
import boki.tobyspring.exrate.WebApiExRateProvider;
import boki.tobyspring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new JdkClientHttpRequestFactory());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new RestTemplateExRateProvider(restTemplate());
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
