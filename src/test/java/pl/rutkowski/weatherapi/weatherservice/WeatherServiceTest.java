package pl.rutkowski.weatherapi.weatherservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

class WeatherServiceTest {

    @Test
    void shouldMapWeatherResponseToWeatherDetails() {
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer server = MockRestServiceServer.bindTo(restTemplate).build();
        WeatherService weatherService = new WeatherService(restTemplate, "test-api-key");

        server.expect(requestTo("https://api.openweathermap.org/data/2.5/weather?q=Warsaw&appid=test-api-key&units=metric"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess("""
                        {
                          "name": "Warsaw",
                          "sys": {
                            "country": "PL"
                          },
                          "main": {
                            "temp": 21.5,
                            "pressure": 1012
                          },
                          "wind": {
                            "speed": 5.4
                          }
                        }
                        """, MediaType.APPLICATION_JSON));

        WeatherDetails weatherDetails = weatherService.getWeatherDetails("Warsaw");

        assertThat(weatherDetails.getCity()).isEqualTo("Warsaw");
        assertThat(weatherDetails.getCountry()).isEqualTo("PL");
        assertThat(weatherDetails.getTemp()).isEqualTo(21.5);
        assertThat(weatherDetails.getPressure()).isEqualTo(1012);
        assertThat(weatherDetails.getWindSpeed()).isEqualTo(5.4);
        server.verify();
    }
}
