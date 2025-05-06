package boki.tobyspring.exrate;

import boki.tobyspring.api.ApiClientApiExecutor;
import boki.tobyspring.api.ApiTemplate;
import boki.tobyspring.api.ExApiExRateExtractor;
import boki.tobyspring.payment.ExRateProvider;

import java.math.BigDecimal;

public class WebApiExRateProvider implements ExRateProvider {

    ApiTemplate apiTemplate = new ApiTemplate();

    @Override
    public BigDecimal getExRate(String currency) {
        String url = "https://open.er-api.com/v6/latest/" + currency;

        return apiTemplate.getExRate(url, new ApiClientApiExecutor(), new ExApiExRateExtractor());
    }

}
