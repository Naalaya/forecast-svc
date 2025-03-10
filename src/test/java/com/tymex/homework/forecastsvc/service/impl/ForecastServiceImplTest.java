//package com.tymex.homework.forecastsvc.service.impl;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//import com.tymex.homework.forecastsvc.model.ForecastHistory;
//import com.tymex.homework.forecastsvc.repository.AuditLogsRepository;
//import com.tymex.homework.forecastsvc.repository.ForecastHistoryRepository;
//import com.tymex.homework.forecastsvc.service.ForecastConverterService;
//import com.tymex.homework.forecastsvc.service.OpenWeatherService;
//import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
//import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.web.client.HttpClientErrorException;
//
//class ForecastServiceImplTest {
//
//    @Mock
//    private OpenWeatherService openWeatherService;
//
//    @Mock
//    private ForecastConverterService forecastConverter;
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
//    @Mock
//    private ForecastHistoryRepository forecastHistoryRepository;
//
//    @Mock
//    private KafkaTemplate<String, String> kafkaTemplate;
//
//    @Mock
//    private AuditLogsRepository auditLogs;
//
//    @InjectMocks
//    private ForecastServiceImpl forecastService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        forecastService = new ForecastServiceImpl(openWeatherService, forecastConverter, forecastHistoryRepository, kafkaTemplate, auditLogs);
//    }
//
//    @Test
//    void test_getForecastByCityName_Success() {
//        // Arrange
//        String cityName = "Hà Nội";
//        OpenWeatherResponse mockResponse = openWeatherResponse;
//        mockResponse.setName(cityName);
//        when(openWeatherService.getForecastFromOpenWeather(cityName)).thenReturn(mockResponse);
//        when(forecastConverter.convertApiResponse(mockResponse)).thenReturn(weatherResponse);
//        when(forecastConverter.convertToRecord(mockResponse)).thenReturn(forecastHistory);
//
//        // Act
//        ResponseEntity<WeatherResponse> response = forecastService.getForecastByCityName(cityName);
//
//        // Assert
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(cityName, response.getBody().getCityName());
//        verify(forecastHistoryRepository, times(1)).save(forecastHistory); // Ensure forecast is saved
//        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), anyString()); // Ensure message is sent to Kafka
//        verify(auditLogs, times(1)).save(any()); // Ensure audit log is saved
//    }
//
//    @Test
//    void test_getForecastByCityName_Failure() {
//        // Arrange
//        String cityName = "anyCity";
//        HttpClientErrorException mockException = HttpClientErrorException.create(
//                HttpStatus.NOT_FOUND, "Not Found", null, null, null);
//        when(openWeatherService.getForecastFromOpenWeather(cityName)).thenThrow(mockException);
//
//        // Act and Assert
//        HttpClientErrorException thrown = assertThrows(HttpClientErrorException.class, () -> {
//            forecastService.getForecastByCityName(cityName);
//        });
//
//        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatusCode());
//        verify(forecastHistoryRepository, times(1)).save(any(ForecastHistory.class)); // Ensure forecast history is saved in failure case
//        verify(kafkaTemplate, times(1)).send(anyString(), anyString(), anyString()); // Ensure failure message is sent to Kafka
//        verify(auditLogs, times(1)).save(any()); // Ensure audit log is saved in failure case
//    }
//}
