package com.scalable.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class MainConfig {
    private WeatherServiceConfig weatherService;
    private ReminderServiceConfig reminderService;
    private QuoteServiceConfig quoteService;
    private UserServiceConfig userService;
    private RestTemplateConfig restClient;
}