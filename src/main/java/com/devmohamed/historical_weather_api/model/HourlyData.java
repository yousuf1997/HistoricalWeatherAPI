package com.devmohamed.historical_weather_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
class HourlyData {

    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature2m;

    // Getters and Setters
    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperature2m() {
        return temperature2m;
    }

    public void setTemperature2m(List<Double> temperature2m) {
        this.temperature2m = temperature2m;
    }
}