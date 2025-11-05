package com.devmohamed.historical_weather_api.provider;
import com.devmohamed.historical_weather_api.model.HourlyForecastResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class OpenMeteoClientService {

    private static final Logger log = LoggerFactory.getLogger(OpenMeteoClientService.class);
    private final RestTemplate restTemplate;

    /**
     * We use @Qualifier to ensure Spring injects the specific bean
     * named "OpenMeteoRestTemplate" from your RestTemplateConfig.
     */
    @Autowired
    public OpenMeteoClientService(@Qualifier("OpenMeteoRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Fetches historical HOURLY temperature data from the Open-Meteo API.
     *
     * @param latitude   Latitude for the location.
     * @param longitude  Longitude for the location.
     * @param startDate  Start date in "YYYY-MM-DD" format.
     * @param endDate    End date in "YYYY-MM-DD" format.
     * @return A DTO mapping the hourly API response, or null if an error occurs.
     */
    public HourlyForecastResponse getHourlyTemperature(
            double latitude, double longitude, String startDate, String endDate) {

        // Build the URL for the hourly endpoint.
        // The base URL is already set in the RestTemplate bean.
        String url = UriComponentsBuilder.fromPath("/forecast")
                .queryParam("latitude", latitude)
                .queryParam("longitude", longitude)
                .queryParam("start_date", startDate)
                .queryParam("end_date", endDate)
                .queryParam("hourly", "temperature_2m") // Requesting the hourly metric
                .toUriString();

        log.info("Contacting Open-Meteo API for HOURLY data: {}", url);

        try {
            // Make the GET request and map the JSON to our HourlyForecastResponse DTO
            HourlyForecastResponse response = restTemplate.getForObject(
                    url,
                    HourlyForecastResponse.class
            );
            log.info("Successfully received hourly response from Open-Meteo.");
            return response;

        } catch (RestClientException e) {
            log.error("Error calling Open-Meteo API for hourly data: {}", e.getMessage());
            // In a real app, you would throw a custom exception here
            // to be handled by your controller.
            return null;
        }
    }
}
