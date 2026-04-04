package pl.rutkowski.weatherapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;
import pl.rutkowski.weatherapi.weatherservice.WeatherDetails;
import pl.rutkowski.weatherapi.weatherservice.WeatherService;
import pl.rutkowski.weatherapi.web.MissingCityException;


@Controller
public class HomeController {

    private final WeatherService weatherService;

    public HomeController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public String home(Model model) {
        City city = new City();
        model.addAttribute("city", city);
        return "home";
    }

    @PostMapping("/city")
    public String getCity(City city) {
        String cityName = city.getName();
        if (cityName == null || cityName.isBlank()) {
            throw new MissingCityException();
        }
        String redirectUrl = UriComponentsBuilder.fromPath("/weather")
                .queryParam("city", cityName.trim())
                .build()
                .encode()
                .toUriString();
        return "redirect:" + redirectUrl;
    }

    @GetMapping("/weather")
    public String showWeather(@RequestParam(required = false) String city, Model model) {
        if (city == null || city.isBlank()) {
            throw new MissingCityException();
        }
        WeatherDetails weatherDetails = weatherService.getWeatherDetails(city);
        model.addAttribute("weatherDetails", weatherDetails);
        model.addAttribute("city", weatherDetails.getCity());
        return "weather";
    }

}
