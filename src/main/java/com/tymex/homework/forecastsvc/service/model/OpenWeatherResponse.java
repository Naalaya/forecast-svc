package com.tymex.homework.forecastsvc.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OpenWeatherResponse {

        private int cod;
        private String message;
        private String name;
        private List<Weather> weather;
        private Main main;
        private Wind wind;
        private Clouds clouds;
        private Sys sys;

        @Data
        @Builder
        public static class Weather {
            private int id;
            private String main;
            private String description;
        }

        @Data
        @Builder
        public static class Main {
            private double temp;
            private double temp_min;
            private double temp_max;
            private int humidity;
            private int pressure;
//            private int sea_level;
        }

        @Data
        @Builder
        public static class Wind {
            private double speed;
            private int deg;
            private double gust;
        }

        @Data
        @Builder
        public static class Clouds {
            private int all;
        }

        @Data
        @Builder
        public static class Sys {
            private long sunrise;
            private long sunset;
        }
}
