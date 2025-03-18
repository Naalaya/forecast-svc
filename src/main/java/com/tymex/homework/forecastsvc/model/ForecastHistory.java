package com.tymex.homework.forecastsvc.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.tymex.homework.forecastsvc.enums.Status;
import jakarta.persistence.*;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name ="cod", nullable = false)
    private int cod;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name= "message")
    private String message;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "weather_id")
    private int weatherId;

    @Column(name = "weather_main")
    private String weatherMain;

    @Column(name = "weather_description")
    private String weatherDescription;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "temperature_min")
    private double temperatureMin;

    @Column(name = "temperature_max")
    private double temperatureMax;

    @Column(name ="humidity")
    private int humidity;

    @Column(name ="pressure")
    private int pressure;

    @Column(name = "wind_speed")
    private double windSpeed;

    @Column(name = "wind_deg")
    private int windDeg;

    @Column(name = "cloud_percentage")
    private int cloudPercentage;

    @Column(name ="sunrise")
    private Timestamp sunrise;

    @Column(name = "sunset")
    private Timestamp sunset;

}
