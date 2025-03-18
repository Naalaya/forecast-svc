package com.tymex.homework.forecastsvc.service;

import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;

public interface ForecastConverterService {
    ForecastHistory convertToRecord(OpenWeatherResponse response);
    WeatherResponse convertApiResponse(ForecastHistory response);
}

