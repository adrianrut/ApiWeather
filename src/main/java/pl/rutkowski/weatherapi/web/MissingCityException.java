package pl.rutkowski.weatherapi.web;

public class MissingCityException extends RuntimeException {

    public MissingCityException() {
        super("Podaj nazwę miasta.");
    }
}
