package com.tymex.homework.forecastsvc.service.impl;

import com.tymex.homework.forecastsvc.model.AuditLogs;
import com.tymex.homework.forecastsvc.repository.AuditLogsRepository;
import com.tymex.homework.forecastsvc.repository.ForecastHistoryRepository;
import com.tymex.homework.forecastsvc.service.OpenWeatherService;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    private final KafkaTemplate<String ,String> kafkaTemplate;
    private final AuditLogsRepository auditLogs;

    @Override
    public ResponseEntity<WeatherResponse> getForecastByCityName(String cityName) {
        try {
            OpenWeatherResponse response = openWeatherService.getForecastFromOpenWeather(cityName);
            response.setMessage("Get weather by City Name: " + response.getName() + " successfully");

//            Convert
            WeatherResponse weatherResponse = forecastConverter.convertApiResponse(response);
            ForecastHistory forecastHistorySaved = forecastConverter.convertToRecord(response);

            log.info("function msg: {}", response.getMessage());
            forecastHistoryRepository.save(forecastHistorySaved);
            sendMsgThenRecord("Get Weather By CityName","Success",cityName, weatherResponse.toString());
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
            sendMsgThenRecord("Get Weather By CityName","Failure", cityName, e.getResponseBodyAsString());
            throw e;
        }
    }
    private void sendMsgThenRecord(String event,String state, String cityName, String content) {
        kafkaTemplate.send("forecastNotificationTopic", cityName, content)
            .whenComplete((result, exception) -> {
                if (exception == null) {
                    AuditLogs log = AuditLogs.builder()
                            .event_type(event)
                            .state(state)
                            .content(content)
                            .build();
                    auditLogs.save(log);
                } else {
                    log.error("Error when send Msg: {}", exception.getMessage(), exception);
                }
            });
    }
}
