package com.tymex.homework.forecastsvc.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tymex.homework.forecastsvc.repository.ForecastHistoryRepository;
import com.tymex.homework.forecastsvc.service.AuditProducerService;
import com.tymex.homework.forecastsvc.service.OpenWeatherService;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.ForecastService;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;


@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastServiceImpl implements ForecastService {

    private final OpenWeatherService openWeatherService;
    private final ForecastConverterService forecastConverter;
    private final ForecastHistoryRepository forecastHistoryRepository;
    private final AuditProducerService producer;

    @Override
    public ResponseEntity<WeatherResponse> getForecastByCityName(String cityName) {
        try {
            OpenWeatherResponse response = openWeatherService.getForecastFromOpenWeather(cityName);

            response.setMessage("Get Weather Successfully");
//            Convert
            WeatherResponse weatherResponse = forecastConverter.convertApiResponse(response);
            ForecastHistory forecastHistorySaved = forecastConverter.convertToRecord(response);

            log.info("function msg: {}", response.getMessage());
            forecastHistoryRepository.save(forecastHistorySaved);
            producer.logAndPublish("Get Weather By CityName","Success",cityName, weatherResponse);
            return new ResponseEntity<>(weatherResponse, HttpStatus.OK);

        } catch (HttpClientErrorException e) {
            ForecastHistory record = ForecastHistory.builder()
                    .cityName(cityName)
                    .cod(e.getStatusCode().value())
                    .message(e.getMessage())
                    .build();
            forecastHistoryRepository.save(record);

            // Send/ Record its Logs
            log.error("An error occurred: {}", e.getMessage());
            try {
                producer.logAndPublish("Get Weather By CityName","Failure", cityName, e.getResponseBodyAsString());
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
            throw e;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
