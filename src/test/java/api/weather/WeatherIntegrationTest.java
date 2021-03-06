package api.weather;

import api.weather.clients.WeatherClient;
import api.weather.clients.response.Main;
import api.weather.clients.response.SysInfo;
import api.weather.clients.response.WeatherDescription;
import api.weather.clients.response.WeatherResponse;
import api.weather.domain.City;
import api.weather.domain.TemperatureUnit;
import api.weather.domain.Weather;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@Log4j2
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WeatherIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${weather.service.api-key}")
    private String apiKey;

    @MockBean
    private WeatherClient weatherClient;

    @Before
    public void setup() {
        given(weatherClient.getWeather(City.London, apiKey))
                .willReturn(WeatherResponse.builder()
                        .date(1476041385L)
                        .city(City.London.name())
                        .main(Main.builder().temp(284D).build())
                        .weather(Arrays.asList(WeatherDescription.builder().description("light rain").build()))
                        .sys(SysInfo.builder().sunrise(1475993779L).sunset(1476033461L).build())
                        .build());
    }

    @Test
    public void getWeather_for_london() {
        ResponseEntity<Weather> response = restTemplate.getForEntity("/weather?city=London", Weather.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Weather weather = response.getBody();
        assertEquals(LocalDate.of(2016, 10, 9), weather.getDate());
        assertEquals(LocalTime.of(06, 16), weather.getSunrise());
        assertEquals(LocalTime.of(17, 17), weather.getSunset());
        assertEquals(City.London.name(), weather.getCity());
        assertEquals("light rain", weather.getWeatherDescription());
        assertEquals(BigDecimal.valueOf(10.85), weather.getTemperature().get(0).getTemperature());
        assertEquals(TemperatureUnit.C, weather.getTemperature().get(0).getUnit());
        assertEquals(BigDecimal.valueOf(51.53), weather.getTemperature().get(1).getTemperature());
        assertEquals(TemperatureUnit.F, weather.getTemperature().get(1).getUnit());
    }
}
