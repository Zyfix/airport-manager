package com.zyfix.airportmanager.exception;

public class AirplaneNotFoundException extends RuntimeException {

    public AirplaneNotFoundException() {
    }

    public AirplaneNotFoundException(String message) {
        super(message + ": Airplane not found");
    }

    public AirplaneNotFoundException(String message, Throwable cause) {
        super(message + ": Airplane not found", cause);
    }

    public AirplaneNotFoundException(Throwable cause) {
        super(cause);
    }

    public AirplaneNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
