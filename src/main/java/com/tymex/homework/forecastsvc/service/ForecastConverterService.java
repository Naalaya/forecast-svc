package com.tymex.homework.forecastsvc.service;

import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

public interface ForecastConverterService {
    ForecastHistory convertToRecord(OpenWeatherResponse response);
}

