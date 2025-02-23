package com.tymex.homework.forecastsvc.service;

import com.tymex.homework.forecastsvc.model.ForecastHistory;

public interface ForecastRepositoryService {
    ForecastHistory save(ForecastHistory record);
}

