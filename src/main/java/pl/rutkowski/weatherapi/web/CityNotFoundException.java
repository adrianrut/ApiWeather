package pl.rutkowski.weatherapi.web;

public class CityNotFoundException extends RuntimeException {

    public CityNotFoundException(String city) {
        super("Nie znaleziono miasta: " + city);
    }
}
