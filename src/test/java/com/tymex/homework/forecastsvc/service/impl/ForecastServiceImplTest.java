package com.tymex.homework.forecastsvc.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.tymex.homework.forecastsvc.repository.ForecastHistoryRepository;
import com.tymex.homework.forecastsvc.service.AuditProducerService;
import com.tymex.homework.forecastsvc.service.OpenWeatherService;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

class ForecastServiceImplTest {

    @Mock
    private OpenWeatherService openWeatherService;

    @Mock
    private OpenWeatherResponse openWeatherResponse;

    @Mock
    private ForecastConverterService forecastConverter;

    @Mock
    private ForecastHistoryRepository forecastHistoryRepository;

    @Mock
    private AuditProducerService producer;

    private ForecastServiceImpl forecastService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forecastService = new ForecastServiceImpl(openWeatherService, forecastConverter, forecastHistoryRepository, producer);
    }

    @Test
    void testGetForecastByCityName_Success(){
        String cityName = "Hanoi";
        openWeatherResponse.setMessage("Success");
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setMsg("Weather data");

        when(openWeatherService.getForecastFromOpenWeather(cityName)).thenReturn(openWeatherResponse);
        ResponseEntity<WeatherResponse> response = forecastService.getForecastByCityName(cityName);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testGetForecastByCityName_NotFound(){
        String cityName = "UnknownCity";
        HttpClientErrorException exception = new HttpClientErrorException(HttpStatus.NOT_FOUND, "City not found");

        when(openWeatherService.getForecastFromOpenWeather(cityName)).thenThrow(exception);

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
}

