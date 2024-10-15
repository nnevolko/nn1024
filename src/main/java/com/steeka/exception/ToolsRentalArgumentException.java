package com.steeka.exception;

public class ToolsRentalArgumentException extends RuntimeException{

    public ToolsRentalArgumentException(String message) {
        super(message);
    }

    public ToolsRentalArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
