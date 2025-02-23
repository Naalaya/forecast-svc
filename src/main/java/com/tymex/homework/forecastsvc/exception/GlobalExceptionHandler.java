package com.tymex.homework.forecastsvc.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;


@RestControllerAdvice
public class GlobalExceptionHandler {
    // ***********************************************
    // remake this one when call the not found city  *
    // ***********************************************
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        ErrorResponse errorResponse = new ErrorResponse();

//        errorResponse.setError_description(e.getMessage());

        var responseBody = e.getResponseBodyAs(Object.class);
        errorResponse.setError(responseBody);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

}
