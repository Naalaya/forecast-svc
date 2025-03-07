//package com.tymex.homework.forecastsvc.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.tymex.homework.forecastsvc.model.ForecastHistory;
//import com.tymex.homework.forecastsvc.service.ForecastConverterService;
//import com.tymex.homework.forecastsvc.service.ForecastRepositoryService;
//import com.tymex.homework.forecastsvc.service.WeatherService;
//import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
//import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.ConsumerFactory;
//import org.springframework.kafka.core.KafkaTemplate;
//
//class ForecastServiceImplTest {
//
//    @Mock
//    private WeatherService weatherService;
//
//    @Mock
//    private ForecastConverterService forecastConverter;
//
//    @Mock
//    private ForecastRepositoryService forecastRepositoryService;
//
//    @Mock
//    private WeatherResponse weatherResponse;
//
//    @Mock
//    private OpenWeatherResponse openWeatherResponse;
//
//    @Mock
//    private ForecastHistory forecastHistory;
//
//    @InjectMocks
//    private ForecastServiceImpl forecastService;
//
//    @Mock
//    private KafkaTemplate<String ,String> kafkaTemplate;
//
//    @Mock
//    private ConsumerFactory<String, String> consumerFactory;
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        forecastService = new ForecastServiceImpl(weatherService, forecastConverter, forecastRepositoryService, kafkaTemplate);
//    }
//
//    @Test
//    void test_getForecastByCityName_Success() {
//
//        // Arrange
//        String cityName = "Hà Nội";
//
//        when(weatherService.getForecastByCityName(cityName)).thenReturn(openWeatherResponse);
//        when(forecastConverter.convertApiResponse(openWeatherResponse)).thenReturn(weatherResponse);
//        when(weatherResponse.getCityName()).thenReturn(cityName);
//        when(forecastConverter.convertToRecord(openWeatherResponse)).thenReturn(forecastHistory);
//
//        // Act
//        ResponseEntity<WeatherResponse> response = forecastService.getForecastByCityName(cityName);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(cityName, response.getBody().getCityName());
//        verify(forecastRepositoryService, times(1)).save(forecastHistory);
//    }
//
////    @Test
////    void test_getForecastByCityName_Failure() {
////
////        String cityName = "anyCity";
////        openWeatherResponse.builder()
////                .name(cityName)
////                .cod(404)
////                .build();
////        when(weatherService.getForecastByCityName(cityName)).thenReturn(openWeatherResponse);
////        when(forecastConverter.convertApiResponse(openWeatherResponse)).thenReturn(weatherResponse);
////        when(weatherResponse.getCityName()).thenReturn(cityName);
////        ResponseEntity<WeatherResponse> response = forecastService.getForecastByCityName(cityName);
////        assertNotNull(response);
////        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
////    }
//}
//
//
