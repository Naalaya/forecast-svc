package com.tymex.homework.forecastsvc.service.impl;

import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import org.springframework.stereotype.Component;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class ForecastConverterServiceImpl implements ForecastConverterService {

    private Timestamp timestampConverter(Long value){
        return Timestamp.from(Instant.ofEpochSecond(value));
    }
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
                .cloudPercentage(response.getClouds().getAll())
                .sunrise(timestampConverter(response.getSys().getSunrise()))
                .sunset(timestampConverter(response.getSys().getSunset()))
                .build();
    }

    @Override
    public WeatherResponse convertApiResponse(OpenWeatherResponse response){
        return WeatherResponse.builder()
                .cityName(response.getName())
                .msg(response.getMessage())
                .weather(WeatherResponse.Weather.builder()
                        .weather_main(response.getWeather().getFirst().getMain())
                        .weather_description(response.getWeather().getFirst().getDescription())
                        .build())
                .temperature(WeatherResponse.Temperature.builder()
                        .temp_main(response.getMain().getTemp())
                        .temp_min(response.getMain().getTemp_min())
                        .temp_max(response.getMain().getTemp_max())
                        .humidity(response.getMain().getHumidity())
                        .pressure(response.getMain().getPressure())
                        .build())
                .wind(WeatherResponse.Wind.builder()
                        .speed(response.getWind().getSpeed())
                        .direction(response.getWind().getDeg())
                        .build())
                .Cloud_percentage(response.getClouds().getAll())
                .sun_sys(WeatherResponse.Sun.builder()
                                .dawn(timestampConverter(response.getSys().getSunrise()))
                                .gust(timestampConverter(response.getSys().getSunset()))
                        .build())
                .build();
    }
}
