package com.tymex.homework.forecastsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tymex.homework.forecastsvc.model.ForecastHistory;


public interface ForecastHistoryRepository extends JpaRepository<ForecastHistory, Long> {
}
