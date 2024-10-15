package com.steeka.exception;

public class ToolsRentalException extends RuntimeException {

    public ToolsRentalException(String message) {
        super(message);
    }

    public ToolsRentalException(String message, Throwable cause) {
        super(message, cause);
    }
}
