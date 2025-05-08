package com.scalable.config;

import lombok.Data;
import java.util.Map;

@Data
public class WeatherServiceConfig {
    private String scheme;
    private String host;
    private int port;
    private Map<String, String> endpoint;
}
