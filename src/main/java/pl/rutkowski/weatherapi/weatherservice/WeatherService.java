package pl.rutkowski.weatherapi.weatherservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pl.rutkowski.weatherapi.web.CityNotFoundException;
import pl.rutkowski.weatherapi.web.MissingOpenWeatherApiKeyException;
import pl.rutkowski.weatherapi.web.WeatherApiException;

@Service
public class WeatherService {

    private final String openWeatherApiKey;
    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate,
                          @Value("${openweather.api.key:}") String openWeatherApiKey) {
        this.restTemplate = restTemplate;
        this.openWeatherApiKey = openWeatherApiKey;
    }


    public WeatherDetails getWeatherDetails(String city) {
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City must not be blank");
        }

        if (openWeatherApiKey == null || openWeatherApiKey.isBlank()) {
            throw new MissingOpenWeatherApiKeyException();
        }

        String normalizedCity = city.trim();
        String url = UriComponentsBuilder
                .fromHttpUrl("https://api.openweathermap.org/data/2.5/weather")
                .queryParam("q", normalizedCity)
                .queryParam("appid", openWeatherApiKey)
                .queryParam("units", "metric")
                .build()
                .encode()
                .toUriString();

        try {
            WeatherResponseDto response = restTemplate.getForObject(url, WeatherResponseDto.class);
            return mapResponse(response, normalizedCity);
        } catch (HttpClientErrorException exception) {
            if (exception.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new CityNotFoundException(normalizedCity);
            }
            throw new WeatherApiException("OpenWeather API zwróciło błąd: " + exception.getStatusCode().value(), exception);
        } catch (ResourceAccessException exception) {
            throw new WeatherApiException("Nie udało się połączyć z OpenWeather API.", exception);
        } catch (RestClientException exception) {
            throw new WeatherApiException("Wystąpił problem podczas pobierania danych pogodowych.", exception);
        }
    }

    private WeatherDetails mapResponse(WeatherResponseDto response, String requestedCity) {
        if (response == null || response.getMain() == null || response.getWind() == null || response.getSys() == null) {
            throw new WeatherApiException("OpenWeather API zwróciło niepełne dane dla miasta: " + requestedCity);
        }

        if (response.getName() == null || response.getName().isBlank()) {
            throw new WeatherApiException("Brakuje nazwy miasta w odpowiedzi OpenWeather API.");
        }

        if (response.getMain().getTemp() == null || response.getMain().getPressure() == null || response.getWind().getSpeed() == null) {
            throw new WeatherApiException("Brakuje wymaganych pól pogodowych w odpowiedzi OpenWeather API.");
        }

        WeatherDetails weatherDetails = new WeatherDetails();
        weatherDetails.setCountry(response.getSys().getCountry() == null ? "-" : response.getSys().getCountry());
        weatherDetails.setCity(response.getName());
        weatherDetails.setTemp(response.getMain().getTemp());
        weatherDetails.setPressure(response.getMain().getPressure());
        weatherDetails.setWindSpeed(response.getWind().getSpeed());
        return weatherDetails;
    }
}
