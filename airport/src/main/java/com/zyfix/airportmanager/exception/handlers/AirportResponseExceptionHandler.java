package com.zyfix.airportmanager.exception.handlers;

import com.zyfix.airportmanager.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AirportResponseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<String> badRequestHandler(BadRequestException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
