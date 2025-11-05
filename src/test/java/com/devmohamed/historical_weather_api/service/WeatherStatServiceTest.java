package com.devmohamed.historical_weather_api.service;

import com.devmohamed.historical_weather_api.model.HourlyData;
import com.devmohamed.historical_weather_api.model.HourlyForecastResponse;
import com.devmohamed.historical_weather_api.provider.OpenMeteoClientService;
import com.devmohamed.historical_weather_api.provider.WeatherStatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the WeatherStatService.
 * This class mocks the OpenMeteoClientService to test the
 * statistic calculation logic in isolation.
 */
@ExtendWith(MockitoExtension.class)
class WeatherStatServiceTest {

    // The service we are testing
    @InjectMocks
    private WeatherStatService weatherStatService;

    // The dependency to be mocked
    @Mock
    private OpenMeteoClientService openMeteoClientService;

    // Reusable test data
    private final double lat = 52.52;
    private final double lon = 13.41;
    private final String start = "2024-01-01";
    private final String end = "2024-01-02";

    // Reusable mock response objects
    private HourlyForecastResponse mockResponse;
    private HourlyData mockHourlyData;

    @BeforeEach
    void setUp() {
        // We initialize these here for clarity
        mockResponse = new HourlyForecastResponse();
        mockHourlyData = new HourlyData();
        mockResponse.setHourly(mockHourlyData);
    }

    // --- Test Cases for getMaximumTemperature ---

    @Test
    void testGetMaximumTemperature_Success() {
        // Arrange
        List<Double> temps = Arrays.asList(10.5, 30.2, 15.0, 29.9);
        mockHourlyData.setTemperature2m(temps);
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMaximumTemperature(lat, lon, start, end);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(30.2, result.getAsDouble());
    }

    @Test
    void testGetMaximumTemperature_WithNullsInList() {
        // Arrange
        List<Double> temps = Arrays.asList(10.5, null, 30.2, 15.0, null);
        mockHourlyData.setTemperature2m(temps);
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMaximumTemperature(lat, lon, start, end);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(30.2, result.getAsDouble());
    }

    @Test
    void testGetMaximumTemperature_EmptyList() {
        // Arrange
        mockHourlyData.setTemperature2m(Collections.emptyList());
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMaximumTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetMaximumTemperature_NullList() {
        // Arrange
        mockHourlyData.setTemperature2m(null); // Explicitly set the list to null
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMaximumTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetMaximumTemperature_NullHourly() {
        // Arrange
        mockResponse.setHourly(null); // Set the entire hourly object to null
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMaximumTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetMaximumTemperature_NullResponse() {
        // Arrange
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(null);

        // Act
        OptionalDouble result = weatherStatService.getMaximumTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    // --- Test Cases for getMinimumTemperature ---

    @Test
    void testGetMinimumTemperature_Success() {
        // Arrange
        List<Double> temps = Arrays.asList(10.5, 30.2, 5.8, 15.0);
        mockHourlyData.setTemperature2m(temps);
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMinimumTemperature(lat, lon, start, end);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(5.8, result.getAsDouble());
    }

    @Test
    void testGetMinimumTemperature_EmptyList() {
        // Arrange
        mockHourlyData.setTemperature2m(Collections.emptyList());
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getMinimumTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetMinimumTemperature_NullData() {
        // Arrange
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(null);

        // Act
        OptionalDouble result = weatherStatService.getMinimumTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    // --- Test Cases for getAverageTemperature ---

    @Test
    void testGetAverageTemperature_Success() {
        // Arrange
        List<Double> temps = Arrays.asList(10.0, 20.0, 30.0); // Average is 20.0
        mockHourlyData.setTemperature2m(temps);
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getAverageTemperature(lat, lon, start, end);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(20.0, result.getAsDouble());
    }

    @Test
    void testGetAverageTemperature_EmptyList() {
        // Arrange
        mockHourlyData.setTemperature2m(Collections.emptyList());
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(mockResponse);

        // Act
        OptionalDouble result = weatherStatService.getAverageTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }

    @Test
    void testGetAverageTemperature_NullData() {
        // Arrange
        when(openMeteoClientService.getHourlyTemperature(lat, lon, start, end)).thenReturn(null);

        // Act
        OptionalDouble result = weatherStatService.getAverageTemperature(lat, lon, start, end);

        // Assert
        assertFalse(result.isPresent());
    }
}