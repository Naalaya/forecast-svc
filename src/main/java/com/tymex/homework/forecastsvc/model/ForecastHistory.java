package com.tymex.homework.forecastsvc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;


@Entity
@Table(name = "forecast_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Accessors(chain = true)
public class ForecastHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int cod;

    @Column(name= "message",nullable = true)
    private String message;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "weather_id", nullable = true)
    private int weatherId;

    @Column(name = "weather_main", nullable = true)
    private String weatherMain;

    @Column(name = "weather_description", nullable = true)
    private String weatherDescription;

    @Column(name = "temperature", nullable = true)
    private double temperature;

    @Column(name = "temperature_min", nullable = true)
    private double temperatureMin;

    @Column(name = "temperature_max", nullable = true)
    private double temperatureMax;

    @Column(name ="humidity",nullable = true)
    private int humidity;

    @Column(name ="pressure",nullable = true)
    private int pressure;

    @Column(name = "wind_speed", nullable = true)
    private double windSpeed;

    @Column(name = "wind_deg", nullable = true)
    private int windDeg;

    @Column(name = "wind_gust", nullable = true)
    private double windGust;

    @Column(name = "cloud_percentage", nullable = true)
    private int cloudPercentage;

    @Column(name ="sunrise",nullable = true)
    private Timestamp sunrise;

    @Column(name = "sunset",nullable = true)
    private Timestamp sunset;
}
