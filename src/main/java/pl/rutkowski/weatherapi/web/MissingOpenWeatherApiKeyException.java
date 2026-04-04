package pl.rutkowski.weatherapi.web;

public class MissingOpenWeatherApiKeyException extends RuntimeException {

    public MissingOpenWeatherApiKeyException() {
        super("Brakuje klucza API. Ustaw zmienną środowiskową OPENWEATHER_API_KEY.");
    }
}
