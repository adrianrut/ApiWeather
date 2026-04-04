package pl.rutkowski.weatherapi.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingCityException.class)
    public String handleMissingCity(MissingCityException exception, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler(CityNotFoundException.class)
    public String handleCityNotFound(CityNotFoundException exception, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }

    @ExceptionHandler({MissingOpenWeatherApiKeyException.class, WeatherApiException.class})
    public String handleWeatherApiError(RuntimeException exception, Model model, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        model.addAttribute("errorMessage", exception.getMessage());
        return "error";
    }
}
