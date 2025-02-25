package com.tymex.homework.forecastsvc.service;

import org.springframework.http.ResponseEntity;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;


public interface ForecastService {
    ResponseEntity<WeatherResponse> getForecastByCityName(String cityName);
}


