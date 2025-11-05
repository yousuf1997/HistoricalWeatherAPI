package com.devmohamed.historical_weather_api.controller;

import com.devmohamed.historical_weather_api.model.ApiErrorResponse;
import com.devmohamed.historical_weather_api.model.WeatherStatResponse;
import com.devmohamed.historical_weather_api.provider.WeatherStatService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.OptionalDouble;

@RestController
@RequestMapping("/api/v1/stats")
@Validated // Enables validation for @RequestParam constraints
public class WeatherStatsController {

    private final WeatherStatService weatherStatService;

    @Autowired
    public WeatherStatsController(WeatherStatService weatherStatService) {
        this.weatherStatService = weatherStatService;
    }

    /**
     * Endpoint to get the maximum temperature.
     */
    @GetMapping("/max")
    public ResponseEntity<?> getMaxTemperature(
            @RequestParam @Min(-90) @Max(90) double latitude,
            @RequestParam @Min(-180) @Max(180) double longitude,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format") String endDate) {

        OptionalDouble maxTemp = weatherStatService.getMaximumTemperature(latitude, longitude, startDate, endDate);

        if (maxTemp.isPresent()) {
            WeatherStatResponse response = new WeatherStatResponse(
                    latitude, longitude,
                    startDate,
                    endDate,
                    String.valueOf(maxTemp.getAsDouble()),
                    maxTemp.getAsDouble()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiErrorResponse error = new ApiErrorResponse(
                    "Not Found",
                    "No temperature data found for the specified range."
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get the minimum temperature.
     */
    @GetMapping("/min")
    public ResponseEntity<?> getMinTemperature(
            @RequestParam @Min(-90) @Max(90) double latitude,
            @RequestParam @Min(-180) @Max(180) double longitude,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format") String endDate) {

        OptionalDouble minTemp = weatherStatService.getMinimumTemperature(latitude, longitude, startDate, endDate);

        if (minTemp.isPresent()) {
            WeatherStatResponse response = new WeatherStatResponse( latitude, longitude,
                    startDate,
                    endDate,
                    String.valueOf(minTemp.getAsDouble()),
                    minTemp.getAsDouble()
            );
            return ResponseEntity.ok(response);
        } else {
            ApiErrorResponse error = new ApiErrorResponse(
                    "Not Found",
                    "No temperature data found for the specified range."
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to get the average temperature.
     */
    @GetMapping("/avg")
    public ResponseEntity<?> getAverageTemperature(
            @RequestParam @Min(-90) @Max(90) double latitude,
            @RequestParam @Min(-180) @Max(180) double longitude,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format") String startDate,
            @RequestParam @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in YYYY-MM-DD format") String endDate) {

        OptionalDouble avgTemp = weatherStatService.getAverageTemperature(latitude, longitude, startDate, endDate);

        if (avgTemp.isPresent()) {
            WeatherStatResponse response = new WeatherStatResponse(
                    latitude, longitude,
                    startDate,
                    endDate,
                    String.valueOf(avgTemp.getAsDouble()),
                    avgTemp.getAsDouble()
                    );
            return ResponseEntity.ok(response);
        } else {
            ApiErrorResponse error = new ApiErrorResponse(
                    "Not Found",
                    "No temperature data found for the specified range."
            );
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }
}