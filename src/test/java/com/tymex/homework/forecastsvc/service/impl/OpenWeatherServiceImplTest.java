package com.tymex.homework.forecastsvc.service.impl;

import com.tymex.homework.forecastsvc.service.model.OpenWeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherServiceImplTest {

    @Mock
    private OpenWeatherResponse openWeatherResponse;

    @Mock
    private RestTemplate restTemplate;

    private OpenWeatherServiceImpl openWeatherServiceImpl;

    private final String openWeatherUrl = "https://api.openweathermap.org/data/2.5/weather?q={cityName}&appid={apiKey}&units=metric&lang=vi";
    private final String apiKey = "6015f94b4184a4025e54c6bde65f15fd";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        openWeatherServiceImpl = new OpenWeatherServiceImpl(restTemplate);
        setField(openWeatherServiceImpl, "openWeatherUrl", openWeatherUrl);
        setField(openWeatherServiceImpl, "apiKey", apiKey);
    }

    @Test
    public void testGetForecastFromOpenWeather_Success() {
        String cityName = "Hà Nội";
        String expectedUrl = openWeatherUrl.replace("{cityName}", cityName).replace("{apiKey}", apiKey);

        Mockito
                .when(restTemplate.getForObject(expectedUrl, OpenWeatherResponse.class))
                .thenReturn(openWeatherResponse);

        OpenWeatherResponse res = openWeatherServiceImpl.getForecastFromOpenWeather(cityName);

        assertEquals(openWeatherResponse, res);
    }

    @Test
    public void testGetForecastFromOpenWeather_Failure() {
        String cityName = "anyCity";
        String expectedUrl = openWeatherUrl.replace("{cityName}", cityName).replace("{apiKey}", apiKey);

        Mockito
                .when(restTemplate.getForObject(expectedUrl, OpenWeatherResponse.class))
                .thenThrow(new HttpClientErrorException(org.springframework.http.HttpStatus.NOT_FOUND));

        assertThrows(HttpClientErrorException.class, () -> openWeatherServiceImpl.getForecastFromOpenWeather(cityName));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            java.lang.reflect.Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException("Failed to set field " + fieldName, e);
        }
    }
}