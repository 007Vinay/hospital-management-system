package com.hospital.hms.exception;

import java.time.LocalDateTime;

public class ErrorResponse {

    // Error type
    private String error;

    // Error message
    private String message;

    // Timestamp
    private LocalDateTime timestamp;

    // HTTP status code
    private int status;

    public ErrorResponse(String error, String message,
                         LocalDateTime timestamp, int status) {

        this.error = error;
        this.message = message;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }
}