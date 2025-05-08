package com.scalable.service;

import com.scalable.config.MainConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.scalable.dto.weather.WeatherResponse;

@Service
public class WeatherService {
    @Autowired
    private MainConfig mainConfig;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<WeatherResponse> getWeatherForCity(String city) {
        String endpointTemplate = mainConfig.getWeatherService().getEndpoint().get("getWeatherEndpoint");
        String formattedEndpoint = String.format(endpointTemplate, city);

        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getWeatherService().getScheme())
                .host(mainConfig.getWeatherService().getHost())
                .port(mainConfig.getWeatherService().getPort())
                .path(formattedEndpoint)
                .encode().build().toUriString();

        return restTemplate.getForEntity(url, WeatherResponse.class);
    }
}
