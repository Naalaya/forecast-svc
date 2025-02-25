package com.tymex.homework.forecastsvc.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherResponse {

    private String cityName;
    private String msg;
    private Weather weather;
    private Temperature temperature;
    private Wind wind;
    private int Cloud_percentage;
    private Sun sun_sys;

    @Data
    @Builder
    public static class Weather {
        private String weather_main;
        private String weather_description;
    }

    @Data
    @Builder
    public static class Temperature {
        private double temp_main;
        private double temp_min;
        private double temp_max;
        private int humidity;
        private int pressure;
    }

    @Data
    @Builder
    public static class Wind {
        private double speed;
        private int direction;
    }

    @Data
    @Builder
    public static class Sun {
        private Timestamp dawn;
        private Timestamp gust;
    }
}
