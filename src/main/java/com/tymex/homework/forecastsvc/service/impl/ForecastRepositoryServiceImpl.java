package com.tymex.homework.forecastsvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.repository.WeatherRepository;
import com.tymex.homework.forecastsvc.service.ForecastRepositoryService;

@Service
@RequiredArgsConstructor
public class ForecastRepositoryServiceImpl implements ForecastRepositoryService {

    private final WeatherRepository weatherRepository;

//    public ForecastRepositoryServiceImpl(WeatherRepository weatherRepository) {
//        this.weatherRepository = weatherRepository;
//    }

    @Override
    public ForecastHistory save(ForecastHistory record) {
        return weatherRepository.save(record);
    }
}

