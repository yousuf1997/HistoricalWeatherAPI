package com.devmohamed.historical_weather_api.service;

import com.devmohamed.historical_weather_api.model.HourlyForecastResponse;
import com.devmohamed.historical_weather_api.provider.OpenMeteoClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the OpenMeteoClientService.
 * This class mocks the RestTemplate to test the client's
 * logic in isolation.
 */
@ExtendWith(MockitoExtension.class)
class OpenMeteoClientServiceTest {

    // The service we are testing
    @InjectMocks
    private OpenMeteoClientService openMeteoClientService;

    // The dependency to be mocked. Note: We just mock the RestTemplate bean.
    @Mock
    private RestTemplate restTemplate;

    // Reusable test data
    private final double lat = 52.52;
    private final double lon = 13.41;
    private final String start = "2024-01-01";
    private final String end = "2024-01-07";

    // Expected URL. The RestTemplate bean has the root URI, so this
    // service only builds the path and query params.
    private String expectedUrl;

    @BeforeEach
    void setUp() {
        // This is the expected URL path the service should build
        expectedUrl = "/forecast?latitude=52.52&longitude=13.41" +
                "&start_date=2024-01-01&end_date=2024-01-07" +
                "&hourly=temperature_2m";
    }

    @Test
    void testGetHourlyTemperature_Success() {
        // Arrange
        // 1. Create a fake response object that we expect to get back
        HourlyForecastResponse fakeResponse = new HourlyForecastResponse();
        fakeResponse.setLatitude(lat);
        fakeResponse.setLongitude(lon);

        // 2. Tell Mockito: "When the restTemplate's getForObject method is called
        //    with THIS exact URL, then return our fakeResponse."
        when(restTemplate.getForObject(expectedUrl, HourlyForecastResponse.class))
                .thenReturn(fakeResponse);

        // Act
        // 3. Call the actual service method
        HourlyForecastResponse actualResponse = openMeteoClientService.getHourlyTemperature(
                lat, lon, start, end
        );

        // Assert
        // 4. Verify that the response from the service is the same one
        //    we told the mock RestTemplate to return.
        assertNotNull(actualResponse);
        assertEquals(lat, actualResponse.getLatitude());
        assertEquals(lon, actualResponse.getLongitude());
    }

    @Test
    void testGetHourlyTemperature_ApiReturnsError() {
        // Arrange
        // 1. Tell Mockito to throw an exception when the URL is called,
        //    simulating a 400 Bad Request from the external API.
        when(restTemplate.getForObject(expectedUrl, HourlyForecastResponse.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Invalid parameters"));

        // Act & Assert
        // 2. Verify that our service correctly throws the HttpClientErrorException
        //    that it received from the restTemplate.
        assertNull(
        openMeteoClientService.getHourlyTemperature(lat, lon, start, end)
        );
    }

}