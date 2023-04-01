package pl.rutkowski.weatherapi;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.rutkowski.weatherapi.model.WeatherResponseDto;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {


    public void getCityName(City city) {
    }

    public List<WeatherDetails> getWeatherDetails(String city) {

        String cityToFind = city.toLowerCase();


        String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityToFind+"&appid=8495016b164251bae83628d23bc9e72f&units=metric";


        RestTemplate restTemplate = new RestTemplate();
        WeatherResponseDto response = restTemplate.getForObject(url, WeatherResponseDto.class);
        WeatherDetails weatherDetails = new WeatherDetails();
        assert response != null;
        weatherDetails.setCountry(response.getSys().getCountry());
        weatherDetails.setCity(response.getName());
        weatherDetails.setTemp(response.getMain().getTemp());
        weatherDetails.setPressure(response.getMain().getPressure());
        weatherDetails.setWindSpeed(response.getWind().getSpeed());
        List<WeatherDetails> list = new ArrayList<>();
        list.add(weatherDetails);
        return list;
    }

}
