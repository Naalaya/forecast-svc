package com.tymex.homework.forecastsvc.service.impl;

import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.ForecastRepositoryService;
import com.tymex.homework.forecastsvc.service.ForecastService;
import com.tymex.homework.forecastsvc.service.WeatherService;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastServiceImpl implements ForecastService {

    private final WeatherService weatherService;
    private final ForecastConverterService forecastConverter;
    private final ForecastRepositoryService forecastRepositoryService;
//    private final KafkaTemplate<String ,Object> kafkaTemplate;
    private final ConsumerFactory<String, Object> consumerFactory;

    @Override
    @KafkaListener(topics = "forecastNotificationTopic")
    public ResponseEntity<WeatherResponse> getForecastByCityName(String cityName) {

            // ...
            log.info("#### -> Consumed message -> {}", consumerFactory.createConsumer());

        try {
            // Call the WeatherService to get forecast data from API
            OpenWeatherResponse response = weatherService.getForecastByCityName(cityName);

            assert response != null;

//            Optional.ofNullable(response)

            response.setMessage("Get weather by City Name: " + response.getName() + " successfully");
            WeatherResponse weatherResponse = forecastConverter.convertApiResponse(response);

            // Convert the response to RecordForecastHistory before saving it to the DB
            ForecastHistory forecastHistorySaved = forecastConverter.convertToRecord(response);
            log.info("function msg: {}", response.getMessage());

            // Save the forecast history record to the database
            forecastRepositoryService.save(forecastHistorySaved);

            // Return the response to the client
            return new ResponseEntity<>(weatherResponse, HttpStatus.OK);
        } catch (HttpClientErrorException exception) {
            log.error("Error: {}", exception.getResponseBodyAsString());
            // Handle exception and record the error in the database
            ForecastHistory record = ForecastHistory.builder()
                    .cityName(cityName)
                    .cod(exception.getStatusCode().value())
                    .message(Objects.requireNonNull(exception.getResponseBodyAs(Object.class)).toString())
                    .build();
            forecastRepositoryService.save(record);
            throw exception;
        }
    }
}
