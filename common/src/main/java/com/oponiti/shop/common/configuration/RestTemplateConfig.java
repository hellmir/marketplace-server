package com.oponiti.shop.common.configuration;

import com.oponiti.shop.common.utility.http.client.resttemplate.RestTemplateErrorHandler;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    @ConditionalOnMissingBean(RestTemplate.class)
    public RestTemplate defaultRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestTemplateErrorHandler());

        return restTemplate;
    }
}
