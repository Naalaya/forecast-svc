package com.tymex.homework.forecastsvc.service;

import org.springframework.http.ResponseEntity;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;


public interface ForecastService {
    ResponseEntity<OpenWeatherResponse> getForecastByCityName(String cityName);
}


