package com.devmohamed.historical_weather_api.provider;

import com.devmohamed.historical_weather_api.model.HourlyForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

@Service
public class WeatherStatService {

    private static final Logger log = LoggerFactory.getLogger(WeatherStatService.class);

    private final OpenMeteoClientService openMeteoClientService;

    /**
     * Injects the existing client service so we can fetch data.
     */
    @Autowired
    public WeatherStatService(OpenMeteoClientService openMeteoClientService) {
        this.openMeteoClientService = openMeteoClientService;
    }

    /**
     * Calculates the maximum temperature from the hourly data for a given location and date range.
     *
     * @param latitude   Latitude for the location.
     * @param longitude  Longitude for the location.
     * @param startDate  Start date in "YYYY-MM-DD" format.
     * @param endDate    End date in "YYYY-MM-DD" format.
     * @return An OptionalDouble containing the max temperature, or an empty Optional if data is not available.
     */
    public OptionalDouble getMaximumTemperature(
            double latitude, double longitude, String startDate, String endDate) {

        // 1. Call your existing service to get the API response
        HourlyForecastResponse response = openMeteoClientService.getHourlyTemperature(
                latitude, longitude, startDate, endDate);

        // 2. Validate the response
        if (response == null || response.getHourly() == null || response.getHourly().getTemperature2m() == null) {
            log.warn("No hourly data returned from client for lat: {}, lon: {}", latitude, longitude);
            return OptionalDouble.empty();
        }

        List<Double> temperatures = response.getHourly().getTemperature2m();

        // 3. Calculate the maximum temperature using Java Streams
        return temperatures.stream()
                .filter(Objects::nonNull) // Filter out any null values in the list
                .mapToDouble(Double::doubleValue)
                .max();
    }

    /**
     * Calculates the minimum temperature for a given location and date range.
     *
     * @param latitude  Latitude.
     * @param longitude Longitude.
     * @param startDate Start date (YYYY-MM-DD).
     * @param endDate   End date (YYYY-MM-DD).
     * @return An OptionalDouble containing the min temperature, or empty if no data.
     */
    public OptionalDouble getMinimumTemperature(double latitude, double longitude, String startDate, String endDate) {
        List<Double> temperatures = getTemperatures(latitude, longitude, startDate, endDate);

        if (temperatures.isEmpty()) {
            return OptionalDouble.empty();
        }

        // Use Java Stream to find the minimum value
        return temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .min();
    }

    /**
     * Calculates the average temperature for a given location and date range.
     *
     * @param latitude  Latitude.
     * @param longitude Longitude.
     * @param startDate Start date (YYYY-MM-DD).
     * @param endDate   End date (YYYY-MM-DD).
     * @return An OptionalDouble containing the average temperature, or empty if no data.
     */
    public OptionalDouble getAverageTemperature(double latitude, double longitude, String startDate, String endDate) {
        List<Double> temperatures = getTemperatures(latitude, longitude, startDate, endDate);

        if (temperatures.isEmpty()) {
            return OptionalDouble.empty();
        }

        // Use Java Stream to calculate the average
        return temperatures.stream()
                .mapToDouble(Double::doubleValue)
                .average();
    }

    /**
     * Helper method to call the client and extract the temperature list.
     *
     * @return A list of temperatures, or an empty list if no data is found.
     */
    private List<Double> getTemperatures(double latitude, double longitude, String startDate, String endDate) {
        // Call the client service
        HourlyForecastResponse response = openMeteoClientService.getHourlyTemperature(
                latitude, longitude, startDate, endDate);

        // Check for null or empty response
        if (response == null || response.getHourly() == null || response.getHourly().getTemperature2m() == null) {
            return Collections.emptyList(); // Return an empty list to avoid NullPointerExceptions
        }

        return response.getHourly().getTemperature2m();
    }
}