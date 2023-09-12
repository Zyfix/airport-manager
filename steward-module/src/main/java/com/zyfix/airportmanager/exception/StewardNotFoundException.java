package com.zyfix.airportmanager.exception;

public class StewardNotFoundException extends RuntimeException {
    public StewardNotFoundException() {
    }

    public StewardNotFoundException(String message) {
        super(message+ ": Steward not found");
    }

    public StewardNotFoundException(String message, Throwable cause) {
        super(message+ ": Steward not found", cause);
    }

    public StewardNotFoundException(Throwable cause) {
        super(cause);
    }

    public StewardNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
