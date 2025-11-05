package com.devmohamed.historical_weather_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DTO for a successful weather statistic response.
 */
public class WeatherStatResponse {

    private double latitude;
    private double longitude;

    @JsonProperty("start_date")
    private String startDate;

    @JsonProperty("end_date")
    private String endDate;

    private String statistic;
    private double value;

    // Full-args constructor for easy creation in the controller
    public WeatherStatResponse(double latitude, double longitude, String startDate, String endDate, String statistic, double value) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statistic = statistic;
        this.value = value;
    }

    // Getters and Setters

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatistic() {
        return statistic;
    }

    public void setStatistic(String statistic) {
        this.statistic = statistic;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}