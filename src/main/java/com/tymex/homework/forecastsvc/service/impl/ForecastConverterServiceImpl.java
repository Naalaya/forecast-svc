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
    public WeatherResponse convertApiResponse(ForecastHistory record){
        return WeatherResponse.builder()
                .cityName(record.getCityName())
                .msg(record.getMessage())
                .weather(WeatherResponse.Weather.builder()
                        .weather_main(record.getWeatherMain())
                        .weather_description(record.getWeatherDescription())
                        .build())
                .temperature(WeatherResponse.Temperature.builder()
                        .temp_main(record.getTemperature())
                        .temp_min(record.getTemperatureMin())
                        .temp_max(record.getTemperatureMax())
                        .humidity(record.getHumidity())
                        .pressure(record.getPressure())
                        .build())
                .wind(WeatherResponse.Wind.builder()
                        .speed(record.getWindSpeed())
                        .direction(record.getWindDeg())
                        .build())
                .Cloud_percentage(record.getCloudPercentage())
                .sun_sys(WeatherResponse.Sun.builder()
                                .dawn(record.getSunrise())
                                .gust(record.getSunset())
                        .build())
                .build();
    }
}
