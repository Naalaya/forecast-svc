package com.tymex.homework.forecastsvc.service.impl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tymex.homework.forecastsvc.model.ForecastHistory;
import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;

import java.sql.Timestamp;
import java.util.List;

class ForecastConverterServiceImplTest {

    private ForecastConverterServiceImpl forecastConverterService;

    @Mock
    private OpenWeatherResponse mockResponse;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        forecastConverterService = new ForecastConverterServiceImpl();
    }

    @Test
    void testConvertToRecord() {

        when(mockResponse.getCod()).thenReturn(200);
        when(mockResponse.getMessage()).thenReturn("Get forecast by City Name: Bình Dương successfully");
        when(mockResponse.getName()).thenReturn("Binh Duong");
        when(mockResponse.getWeather()).thenReturn(List.of(
                OpenWeatherResponse.Weather.builder()
                        .id(803)
                        .main("Clouds")
                        .description("mây cụm")
                        .build()
        ));
        when(mockResponse.getMain()).thenReturn(OpenWeatherResponse.Main.builder()
                .temp(25.0)
                .temp_min(25.0)
                .temp_max(25.0)
                .humidity(73)
                .pressure(1017)
                .build());
        when(mockResponse.getWind()).thenReturn(OpenWeatherResponse.Wind.builder()
                .speed(5.06)
                .deg(9)
                .gust(6.56)
                .build());
        when(mockResponse.getClouds()).thenReturn(OpenWeatherResponse.Clouds.builder()
                .all(81)
                .build());
        when(mockResponse.getSys()).thenReturn(OpenWeatherResponse.Sys.builder()
                .sunrise(Timestamp.valueOf("1970-01-21 03:15:47"))
                .sunset(Timestamp.valueOf("1970-01-21 03:16:29"))
                .build());

        ForecastHistory result = forecastConverterService.convertToRecord(mockResponse);

        assertNotNull(result);
        assertEquals("Binh Duong", result.getCityName());
        assertEquals(200, result.getCod());
        assertEquals("Get forecast by City Name: Bình Dương successfully", result.getMessage());
        assertEquals(803, result.getWeatherId());
        assertEquals("Clouds", result.getWeatherMain());
        assertEquals("mây cụm", result.getWeatherDescription());
        assertEquals(25.0, result.getTemperature());
        assertEquals(25.0, result.getTemperatureMin());
        assertEquals(25.0, result.getTemperatureMax());
        assertEquals(73, result.getHumidity());
        assertEquals(1017, result.getPressure());
        assertEquals(5.06, result.getWindSpeed());
        assertEquals(9, result.getWindDeg());
        assertEquals(6.56, result.getWindGust());
        assertEquals(81, result.getCloudPercentage());
        assertEquals(Timestamp.valueOf("1970-01-21 03:15:47"), result.getSunrise());
        assertEquals(Timestamp.valueOf("1970-01-21 03:16:29"), result.getSunset());
    }
}
