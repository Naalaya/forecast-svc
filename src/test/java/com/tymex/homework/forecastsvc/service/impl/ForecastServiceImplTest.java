package com.tymex.homework.forecastsvc.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.ForecastConverterService;
import com.tymex.homework.forecastsvc.service.ForecastRepositoryService;
import com.tymex.homework.forecastsvc.service.WeatherService;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.HttpClientErrorException;

class ForecastServiceImplTest {

    @Mock
    private WeatherService weatherService;

    @Mock
    private ForecastConverterService forecastConverter;

    @Mock
    private ForecastRepositoryService forecastRepositoryService;

    @InjectMocks
    private ForecastServiceImpl forecastService;

    @Mock
    private KafkaTemplate<String ,Object> kafkaTemplate;

    @Mock
    private ConsumerFactory<String, Object> consumerFactory;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forecastService = new ForecastServiceImpl(weatherService, forecastConverter, forecastRepositoryService, kafkaTemplate, consumerFactory);
    }

    @Test
    void test_getForecastByCityName_Success() {
        // Arrange
        String cityName = "Hà Nội";
        OpenWeatherResponse openWeatherResponse = mock(OpenWeatherResponse.class);
        when(weatherService.getForecastByCityName(cityName)).thenReturn(openWeatherResponse);

        // Mocking the conversion and saving process
        ForecastHistory forecastHistory = mock(ForecastHistory.class);
        when(forecastConverter.convertToRecord(openWeatherResponse)).thenReturn(forecastHistory);
        when(openWeatherResponse.getName()).thenReturn(cityName);
        when(openWeatherResponse.getMessage()).thenReturn("Get forecast by City Name: " + cityName + " successfully");

        // Act
        ResponseEntity<OpenWeatherResponse> response = forecastService.getForecastByCityName(cityName);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cityName, response.getBody().getName());
//        verify(forecastRepositoryService, times(1)).save(forecastHistory);
    }
//
//    @Test
//    void test_getForecastByCityName_throws_HttpClientErrorException() {
//        // Arrange
//        String cityName = "Hanoi";
//        HttpClientErrorException exception = mock(HttpClientErrorException.class);
//        when(exception.getStatusCode()).thenReturn(HttpStatus.NOT_FOUND);
//        when(exception.getMessage()).thenReturn("Bad Request");
//
//        when(weatherService.getForecastByCityName(cityName)).thenThrow(exception);
//
//        // Act & Assert
//        HttpClientErrorException thrownException = assertThrows(HttpClientErrorException.class,
//                () -> forecastService.getForecastByCityName(cityName));
//
//        assertEquals(HttpStatus.NOT_FOUND, thrownException.getStatusCode());
//        // Verify that error record is saved
////        verify(forecastRepositoryService, times(1)).save(any(ForecastHistory.class));
//    }
}


