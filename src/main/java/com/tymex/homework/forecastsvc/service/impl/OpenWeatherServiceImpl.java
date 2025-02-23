package com.tymex.homework.forecastsvc.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.tymex.homework.forecastsvc.service.WeatherService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherServiceImpl implements WeatherService {

    private final RestTemplate restTemplate;

    @Value("${weather-map.api.url}")
    private String openWeatherUrl;

    @Value("${weather-map.api.apiKey}")
    private String apiKey;

    @Override
    public OpenWeatherResponse getForecastByCityName(String cityName) {

// //     Thay thế {cityName} và {apiKey}
//        String expandedUrl = UriComponentsBuilder.fromHttpUrl(openWeatherUrl)
//                .buildAndExpand(cityName, apiKey)
//                .toUriString();
        String url = openWeatherUrl.replace("{cityName}", cityName).replace("{apiKey}", apiKey);
        try {
            return restTemplate.getForObject(url, OpenWeatherResponse.class);
        } catch (HttpClientErrorException exception) {
//            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Error fetching data from OpenWeather");
            throw exception;
        } catch(Exception e) {
            log.error("New exception has occurred with msg: {} ",e.getMessage());
            throw e;
        }
    }
}
