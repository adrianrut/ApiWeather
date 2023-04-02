package pl.rutkowski.weatherapi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.rutkowski.weatherapi.weatherservice.WeatherDetails;
import pl.rutkowski.weatherapi.weatherservice.WeatherService;


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
        return "redirect:/weather=" + city.getName().toLowerCase();
    }

    @GetMapping("/weather={city}")
    public String showWeather(@PathVariable String city, Model model) {
        WeatherDetails weatherDetails = weatherService.getWeatherDetails(city);
        model.addAttribute("weatherDetails", weatherDetails);
        model.addAttribute("city", city);
        return "weather";
    }

}
