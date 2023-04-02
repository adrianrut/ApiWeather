package pl.rutkowski.weatherapi.weatherservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private final String openWeatherApiKey;

    public WeatherService(@Value("${app.openWeatherApiKey}") String openWeatherApiKey) {
        this.openWeatherApiKey = openWeatherApiKey;
    }


    public WeatherDetails getWeatherDetails(String city) {

        String cityToFind = city.toLowerCase();

        String url = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric"
                .formatted(cityToFind, openWeatherApiKey);

        RestTemplate restTemplate = new RestTemplate();
        WeatherResponseDto response = restTemplate.getForObject(url, WeatherResponseDto.class);
        WeatherDetails weatherDetails = new WeatherDetails();
        assert response != null;
        weatherDetails.setCountry(response.getSys().getCountry());
        weatherDetails.setCity(response.getName());
        weatherDetails.setTemp(response.getMain().getTemp());
        weatherDetails.setPressure(response.getMain().getPressure());
        weatherDetails.setWindSpeed(response.getWind().getSpeed());
        return weatherDetails;
    }

}
