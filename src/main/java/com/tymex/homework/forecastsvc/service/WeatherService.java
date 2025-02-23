package com.tymex.homework.forecastsvc.service;

import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

public interface WeatherService {
    OpenWeatherResponse getForecastByCityName(String cityName);
}
