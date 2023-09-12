package com.zyfix.airportmanager.exception.handlers;


import com.zyfix.airportmanager.exception.StewardNotFoundException;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class StewardResponseExceptionHandler {

    @ResponseBody
    @ExceptionHandler(StewardNotFoundException.class)
    ResponseEntity<APIError> employeeNotFoundHandler(StewardNotFoundException ex) {
        return new ResponseEntity<>(new APIError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Schema(title = "Error dto if no steward was found")
    public record APIError(String message) {}
}
