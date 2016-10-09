package api.weather;

import api.weather.clients.WeatherClient;
import api.weather.clients.response.WeatherResponse;
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

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;

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
        given(weatherClient.getWeather("London", apiKey)).willReturn(WeatherResponse.builder().date(1).build());
    }

    @Test
    public void getWeather_for_london() {
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity("/weather?city=London", WeatherResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        log.info(response.getStatusCode());
        log.info(response.getBody().getDate());
    }
}
