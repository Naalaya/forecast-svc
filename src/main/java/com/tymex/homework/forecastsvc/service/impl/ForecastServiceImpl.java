package com.tymex.homework.forecastsvc.service.impl;

import com.tymex.homework.forecastsvc.enums.Status;
import com.tymex.homework.forecastsvc.repository.ForecastHistoryRepository;
import com.tymex.homework.forecastsvc.service.AuditProducerService;
import com.tymex.homework.forecastsvc.service.OpenWeatherService;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.ForecastService;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
import org.springframework.web.client.HttpClientErrorException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForecastServiceImpl implements ForecastService {

    private final OpenWeatherService openWeatherService;
    private final ForecastConverterService forecastConverter;
    private final ForecastHistoryRepository forecastHistoryRepository;
    private final AuditProducerService producer;

    @SneakyThrows
    @Override
    public void getForecastByCityName(String cityName) {

        ForecastHistory forecastHistory = ForecastHistory.builder()
                .cityName(cityName)
                .status(Status.PROCESSING)
                .build();
        log.info("method: getForecastByCityName. ForecastHistory: {}", forecastHistory);
        forecastHistoryRepository.save(forecastHistory);

        fetchOpenWeatherFromOpenWeather(forecastHistory);
        log.info("[Forecast-svc] Weather data for {} successfully fetched from OpenWeather", cityName);

        WeatherResponse weatherResponse = forecastConverter.convertApiResponse(forecastHistory);

        producer.logAndPublish("method: getForecastByCityName", forecastHistory.getStatus().toString(), forecastHistory.getCityName(), weatherResponse);
    }


    private ForecastHistory fetchOpenWeatherFromOpenWeather(ForecastHistory forecastHistory) {
        try {
            OpenWeatherResponse openWeatherResponse = openWeatherService.getForecastFromOpenWeather(forecastHistory.getCityName());
            log.info("method: fetchOpenWeatherFromOpenWeather. CityName: {} - Response: {}", forecastHistory.getCityName(), openWeatherResponse);

            ForecastHistory forecastHistorySource = forecastConverter.convertToRecord(openWeatherResponse).setStatus(Status.SUCCESSFUL);
            extracted(forecastHistory, forecastHistorySource);
        } catch (HttpClientErrorException e) {
            forecastHistory.setCod(404);
            forecastHistory.setStatus(Status.FAILED);
            forecastHistory.setMessage(e.getResponseBodyAsString());
            log.error("Error when fetchOpenWeatherFromOpenWeather. CityName:{} - Error: {}",forecastHistory.getCityName(), forecastHistory.getMessage());
        }
            forecastHistoryRepository.save(forecastHistory);
            return forecastHistory;
    }


    private void extracted(ForecastHistory forecastHistoryTarget, ForecastHistory forecastHistorySource) {
        forecastHistoryTarget.setCod(forecastHistorySource.getCod());
        forecastHistoryTarget.setStatus(forecastHistorySource.getStatus());
        forecastHistoryTarget.setSunrise(forecastHistorySource.getSunrise());
        forecastHistoryTarget.setSunset(forecastHistorySource.getSunset());
        forecastHistoryTarget.setHumidity(forecastHistorySource.getHumidity());
        forecastHistoryTarget.setWindSpeed(forecastHistorySource.getWindSpeed());
        forecastHistoryTarget.setCloudPercentage(forecastHistorySource.getCloudPercentage());
        forecastHistoryTarget.setMessage(forecastHistorySource.getMessage());
        forecastHistoryTarget.setPressure(forecastHistorySource.getPressure());
        forecastHistoryTarget.setTemperature(forecastHistorySource.getTemperature());
        forecastHistoryTarget.setTemperatureMin(forecastHistorySource.getTemperatureMin());
        forecastHistoryTarget.setTemperatureMax(forecastHistorySource.getTemperatureMax());
        forecastHistoryTarget.setWeatherDescription(forecastHistorySource.getWeatherDescription());
        forecastHistoryTarget.setWeatherId(forecastHistorySource.getWeatherId());
        forecastHistoryTarget.setWeatherMain(forecastHistorySource.getWeatherMain());
        forecastHistoryTarget.setWeatherDescription(forecastHistorySource.getWeatherDescription());
        forecastHistoryTarget.setWindDeg(forecastHistorySource.getWindDeg());
    }
}
