package com.tymex.homework.forecastsvc.controller;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tymex.homework.forecastsvc.controller.model.WeatherRequest;
import com.tymex.homework.forecastsvc.service.ForecastService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/")
public class WeatherController {


    private final ForecastService forecastService;
    @GetMapping("/forecast/getByCityName")
    // convert to @RequestBody?
//    public ResponseEntity<?> getForecastByCityName(@RequestHeader("City-Name") String cityName) {
    public ResponseEntity<?> getForecastByCityName(@RequestParam String cityName) {
        ResponseEntity<OpenWeatherResponse> response = forecastService.getForecastByCityName(cityName);
        log.info("Function called: {}, cityName: {}","getWeatherByCityName", cityName);
        return response;
    }
//
//    @GetMapping("weather/byCityName")
//    public ResponseEntity<?> getForecastByCityName(
//            @RequestBody (required = false) WeatherRequest forecastRequest
//    ) {}
}
