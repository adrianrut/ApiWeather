package pl.rutkowski.weatherapi;

import lombok.Data;

@Data
public class WeatherDetails {
    private String city;
    private String country;
    private double temp;
    private double windSpeed;
    private int pressure;
}
