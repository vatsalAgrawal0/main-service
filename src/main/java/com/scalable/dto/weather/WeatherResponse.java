package com.scalable.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    private String message;
    private String cod;
    private int count;
    private List<WeatherData> list;
}