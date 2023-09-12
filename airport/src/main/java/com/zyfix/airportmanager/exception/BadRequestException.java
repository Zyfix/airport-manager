package com.zyfix.airportmanager.exception;

public class BadRequestException extends RuntimeException {

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message + ": Bad internal API request");
    }

    public BadRequestException(String message, Throwable cause) {
        super(message + ": Bad internal API request", cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }

    public BadRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message + ": Bad internal API request", cause, enableSuppression, writableStackTrace);
    }
}
