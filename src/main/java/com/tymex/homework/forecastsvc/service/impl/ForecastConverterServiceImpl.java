package com.tymex.homework.forecastsvc.service.impl;

import org.springframework.stereotype.Component;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

@Component
public class ForecastConverterServiceImpl implements ForecastConverterService {

    @Override
    public ForecastHistory convertToRecord(OpenWeatherResponse response) {
        return ForecastHistory.builder()
                .cityName(response.getName())
                .cod(response.getCod())
                .message(response.getMessage())
                .weatherId(response.getWeather().getFirst().getId())
                .weatherMain(response.getWeather().getFirst().getMain())
                .weatherDescription(response.getWeather().getFirst().getDescription())
                .temperature(response.getMain().getTemp())
                .temperatureMin(response.getMain().getTemp_min())
                .temperatureMax(response.getMain().getTemp_max())
                .humidity(response.getMain().getHumidity())
                .pressure(response.getMain().getPressure())
                .windSpeed(response.getWind().getSpeed())
                .windDeg(response.getWind().getDeg())
                .windGust(response.getWind().getGust())
                .cloudPercentage(response.getClouds().getAll())
                .sunrise(response.getSys().getSunrise())
                .sunset(response.getSys().getSunset())
                .build();
    }
}
