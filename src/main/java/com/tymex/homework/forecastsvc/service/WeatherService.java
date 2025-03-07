package com.tymex.homework.forecastsvc.service;

import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

public interface WeatherService {
    OpenWeatherResponse getForecastFromOpenWeather(String cityName);
}
