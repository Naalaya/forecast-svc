package com.tymex.homework.forecastsvc.controller;

import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tymex.homework.forecastsvc.service.ForecastService;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/")
public class WeatherController {

    private final ForecastService forecastService;
    @GetMapping("/forecast/cityName")
    public ResponseEntity<?> getForecastByCityName(@RequestHeader("City-Name") String cityName) {
            ResponseEntity<WeatherResponse> response = forecastService.getForecastByCityName(cityName);
            log.info("Function called: {}, cityName: {}", "getWeatherByCityName", cityName);
            return response;
    }
}
