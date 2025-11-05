package com.devmohamed.historical_weather_api.model;

public class ApiErrorResponse {

    private String error;
    private String message;

    /**
     * Constructs a new ApiErrorResponse.
     *
     * @param error   A short error category (e.g., "Not Found", "Bad Request").
     * @param message A detailed, human-readable message explaining the error.
     */
    public ApiErrorResponse(String error, String message) {
        this.error = error;
        this.message = message;
    }

    // Getters and Setters

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}