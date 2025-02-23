package com.tymex.homework.forecastsvc.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.repository.WeatherRepository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ForecastRepositoryServiceImplTest {

    @Mock
    private WeatherRepository weatherRepository;

    @InjectMocks
    private ForecastRepositoryServiceImpl forecastRepositoryService;

    private ForecastHistory forecastHistory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_saveForecastHistory() {
        forecastHistory = forecastHistory.builder()
                .id(1L)
                .cityName("abc")
                .build();
        when(weatherRepository.save(any(ForecastHistory.class))).thenReturn(forecastHistory);

        ForecastHistory savedForecast = forecastRepositoryService.save(forecastHistory);

        assertNotNull(savedForecast);
        assertEquals(1L, savedForecast.getId());
        assertEquals("abc", savedForecast.getCityName());

        verify(weatherRepository, times(1)).save(any(ForecastHistory.class));
    }
}
