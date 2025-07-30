package com.ecommerce.inventory_service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestTemplate;


@Configuration
public class AppConfig {

    @Autowired
    private Environment env;

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
         String apiKey = env.getProperty("api.key");
         System.out.println(apiKey);
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("x-api-key", apiKey); 
            return execution.execute(request, body);
        });

        return restTemplate;
    }
}
