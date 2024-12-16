package ru.terentyev.itq_orders_service.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClient;
import ru.terentyev.itq_orders_service.entities.ErrorResponse;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class CommonRestControllerAdvice {

    private static HttpHeaders putHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private static ErrorResponse prepareResponse(Exception e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage(e.getMessage());
        response.setTimestamp(LocalDateTime.now());
        return response;
    }
    @ExceptionHandler(ProductNotExistsException.class)
    public ResponseEntity<ErrorResponse> handleProductNotExistsException(ProductNotExistsException e) {
        ErrorResponse response = prepareResponse(e);
        response.setStatusCode(404);
        return new ResponseEntity<>(response, putHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAnyOtherException(Exception e) {
        e.printStackTrace();
        while (e.getCause() != null) {
            e = (Exception) e.getCause();
        }
        ErrorResponse response = prepareResponse(e);
        response.setStatusCode(400);
        return new ResponseEntity<>(response, putHeaders(), HttpStatus.BAD_REQUEST);
    }
}
