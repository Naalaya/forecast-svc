package com.tymex.homework.forecastsvc.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherRequest {

    @Getter
    @NotNull
    private String cityName;

    @Getter
    private String requestFunc;

//    public static WeatherRequest from(String cityName, String requestFunc){
//
//        return WeatherRequest.builder()
//                .cityName(cityName)
//                .requestFunc(requestFunc)
//                .build();
//    }
}
