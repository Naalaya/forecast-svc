package com.tymex.homework.forecastsvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import org.springframework.stereotype.Repository;

@Repository
public interface ForecastHistoryRepository extends JpaRepository<ForecastHistory, Long> {
//    ForecastHistory findByForecastId(Long forecastId);
}
