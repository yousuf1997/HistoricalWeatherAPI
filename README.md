# HistoricalWeatherAPI
Historical Weather API for CSU Pomona Course Project using AI

Here is a comprehensive GitHub README for your project, based entirely on the provided SRS document.

-----

# Historical Weather API

[](https://www.google.com/search?q=https://github.com/example/historical-weather-api)
[](https://opensource.org/licenses/MIT)
[](https://spring.io/projects/spring-boot)
[](https://www.java.com)

[cite\_start]A simple, focused Spring Boot API for meteorological researchers, providing historical temperature data (min, max, and average)[cite: 40, 41].

[cite\_start]This project acts as a simplifying facade, consuming the public [Open-Meteo API](https://open-meteo.com/en/docs) to provide specific, high-demand data points based on user-provided coordinates[cite: 41, 56, 76].

## 🎯 Purpose

[cite\_start]This API is designed to accelerate meteorological research by replacing the time-consuming, manual process of finding and parsing complex historical weather data[cite: 50]. [cite\_start]It provides a simple, stable interface for the most commonly needed data points[cite: 51].

## ✨ Features

  * [cite\_start]**RESTful API:** A simple, predictable RESTful API built with Spring Boot[cite: 53].
  * [cite\_start]**Three Core Endpoints:** Provides endpoints for minimum, maximum, and average temperature[cite: 55].
  * [cite\_start]**Stateless Facade:** The API is a stateless, pass-through facade[cite: 61]. [cite\_start]It does not store any weather data locally[cite: 61, 93].
  * [cite\_start]**JSON Format:** All responses are delivered in JSON format[cite: 57, 94].
  * [cite\_start]**Secure:** All communication is encrypted via HTTPS[cite: 95, 151].

-----

## 🛠️ Technical Overview

  * [cite\_start]**Framework:** Spring Boot [cite: 91]
  * [cite\_start]**Language:** Java (requires JRE 17 or newer) [cite: 86, 87]
  * [cite\_start]**Data Source:** All data is fetched live from the external [Open-Meteo API](https://open-meteo.com/en/docs)[cite: 92, 100].

### Prerequisites

To run this application, you will need:

1.  [cite\_start]**Java Runtime Environment (JRE) 17** or newer[cite: 86, 87].
2.  [cite\_start]**Internet Access:** The host environment must have stable, outbound internet access to `https://api.open-meteo.com` on port 443 (HTTPS)[cite: 88, 89].

### 🚀 Running the Application

[cite\_start]This application is packaged as a self-contained `.jar` file[cite: 85, 159].

```bash
# Run the application from the command line
java -jar historical-weather-api-1.0.0.jar
```

Once running, the API documentation will be available at:

  * **Swagger UI:** `http://localhost:8080/swagger-ui.html`
  * **OpenAPI Spec:** `http://localhost:8080/v3/api-docs`

*(Note: The above assumes a default `localhost:8080` configuration. This may vary based on your deployment environment.)*

-----

## 📖 API Documentation

[cite\_start]All endpoints are exposed over **HTTPS**[cite: 95, 156].

### Common Request Parameters

[cite\_start]The following query parameters are **required** for all endpoints[cite: 111, 119, 127]:

| Parameter | Type | Required | Description |
| :--- | :--- | :--- | :--- |
| `latitude` | `float` | Yes | [cite\_start]The latitude in WGS84 coordinates[cite: 102]. |
| `longitude` | `float` | Yes | [cite\_start]The longitude in WGS84 coordinates[cite: 102]. |
| `start_date`| `string` | Yes | [cite\_start]The start of the date range (Format: YYYY-MM-DD)[cite: 111, 119, 127]. |
| `end_date` | `string` | Yes | [cite\_start]The end of the date range (Format: YYYY-MM-DD)[cite: 111, 119, 127]. |

### Endpoints

#### 1\. Request Minimum Temperature

[cite\_start]Retrieves the minimum temperature for the specified location and date range[cite: 108].

  * [cite\_start]**Endpoint:** `GET /api/v1/weather/min` [cite: 107]
  * [cite\_start]**Calls:** Open-Meteo `temperature_2m_min` variable[cite: 112].

#### 2\. Request Maximum Temperature

[cite\_start]Retrieves the maximum temperature for the specified location and date range[cite: 116].

  * [cite\_start]**Endpoint:** `GET /api/v1/weather/max` [cite: 115]
  * [cite\_start]**Calls:** Open-Meteo `temperature_2m_max` variable[cite: 120].

#### 3\. Request Average Temperature

[cite\_start]Retrieves the average temperature for the specified location and date range[cite: 124].

  * [cite\_start]**Endpoint:** `GET /api/v1/weather/avg` [cite: 123]
  * [cite\_start]**Calls:** Open-Meteo `temperature_2m_mean` variable[cite: 128].

-----

### Example Usage

**Example Request:**

```bash
# Get the average temperature for a specific location and date range
curl -X GET "https://api.your-domain.com/api/v1/weather/avg?latitude=38.8951&longitude=-77.0369&start_date=2023-01-01&end_date=2023-01-05"
```

[cite\_start]**Example Success Response (HTTP 200 OK)[cite: 128]:**

```json
{
  "location": {
    "latitude": 38.8951,
    "longitude": -77.0369
  },
  "date_range": {
    "start": "2023-01-01",
    "end": "2023-01-05"
  },
  "unit": "celsius",
  "data_type": "average_temperature",
  "daily_data": [
    { "date": "2023-01-01", "value": 5.2 },
    { "date": "2023-01-02", "value": 4.8 },
    { "date": "2023-01-03", "value": 6.1 },
    { "date": "2023-01-04", "value": 5.5 },
    { "date": "2023-01-05", "value": 7.0 }
  ]
}
```

### Error Handling

[cite\_start]The API returns clear, JSON-formatted error messages[cite: 159].

| Status Code | Meaning | Reason |
| :--- | :--- | :--- |
| `400 Bad Request` | The request was invalid. | [cite\_start]\<ul\>\<li\>A required parameter is missing or malformed (e.g., `latitude`, `start_date`) [cite: 112, 120, 128][cite\_start].\</li\>\<li\>The date range exceeds 365 days [cite: 163][cite\_start].\</li\>\<li\>`start_date` is later than `end_date`[cite: 163].\</li\>\</ul\> |
| `429 Too Many Requests`| Rate limit exceeded. | [cite\_start]The API implements rate limiting to prevent abuse (e.g., 100 requests/minute)[cite: 156]. |
| `502 Bad Gateway` | Upstream error. | [cite\_start]The external Open-Meteo API is unreachable or returned an error[cite: 112, 120, 128]. The API itself is still operational. |

-----

## 🚫 Out of Scope

[cite\_start]This project (V1.0) explicitly **does not** include[cite: 58]:

  * [cite\_start]A graphical user interface (GUI)[cite: 59, 131].
  * [cite\_start]Weather forecasting or real-time weather data[cite: 60].
  * [cite\_start]User authentication or account management[cite: 62].
