package com.devmohamed.historical_weather_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenMeteoRestTemplateConfig {

    @Value("${client.open-meteo.base-url}")
    private String openMeteoBaseUrl;

    @Bean(name = "OpenMeteoRestTemplate")
    public RestTemplate openMeteoRestTemplateConfig(RestTemplateBuilder builder) {
        return builder.rootUri(openMeteoBaseUrl).build();
    }
}
