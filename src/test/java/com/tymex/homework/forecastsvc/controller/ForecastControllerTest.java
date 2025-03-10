package com.tymex.homework.forecastsvc.controller;

import com.tymex.homework.forecastsvc.service.ForecastService;
import com.tymex.homework.forecastsvc.service.model.WeatherResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class ForecastControllerTest {

    @Mock
    private ForecastService forecastService;

    @Mock
    private WeatherResponse weatherResponse;

    private MockMvc mockMvc;

    @InjectMocks
    private WeatherController weatherController;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
    }

    @Test
    void testGetForecastByCityName_Success() throws Exception {
        String cityName = "Hà Nội";
//        WeatherResponse weatherResponse = new WeatherResponse();
        ResponseEntity<WeatherResponse> responseEntity = new ResponseEntity<>(weatherResponse, HttpStatus.OK);

        Mockito.when(forecastService.getForecastByCityName(cityName)).thenReturn(responseEntity);

        mockMvc.perform(get("/api/v1/forecast/cityName")
                        .header("City-Name", cityName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Nested
    public class GetForecastByCityName_Failure {

        private Map<String, Object> createErrorResponse(String code, String message) {
            Map<String, Object> errorBody = new HashMap<>();
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("cod", code);
            errorDetails.put("message", message);
            errorBody.put("error", errorDetails);
            return errorBody;
        }

        @SneakyThrows
        @Test
        void testGetForecastByCityName_NotFound() {
            Map<String, Object> errorBody = createErrorResponse("404", "city not found");

            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(errorBody, HttpStatus.NOT_FOUND);
            Mockito.when(forecastService.getForecastByCityName(anyString())).thenReturn((ResponseEntity) responseEntity);

            mockMvc.perform(get("/api/v1/forecast/cityName")
                            .header("City-Name", anyString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpectAll(
                            status().isNotFound(),
                            jsonPath("$.error.cod", is("404")),
                            jsonPath("$.error.message", is("city not found"))
                    );
        }

        @SneakyThrows
        @Test
        void testGetForecastByCityName_MissingHeader() {
            mockMvc.perform(get("/api/v1/forecast/cityName")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @SneakyThrows
        @Test
        void testGetForecastByCityName_EmptyCityName() {
            String cityName = "";
            Map<String, Object> errorBody = createErrorResponse("400", "Nothing to geocode");

            ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
            Mockito.when(forecastService.getForecastByCityName(cityName)).thenReturn((ResponseEntity) responseEntity);

            mockMvc.perform(get("/api/v1/forecast/cityName")
                            .header("City-Name", cityName)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpectAll(
                            status().isBadRequest(),
                            jsonPath("$.error.cod", is("400")),
                            jsonPath("$.error.message", is("Nothing to geocode"))
                    );
        }
    }
}
