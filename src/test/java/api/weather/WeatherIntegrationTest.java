package api.weather;

import api.weather.clients.WeatherClient;
import api.weather.clients.response.Main;
import api.weather.clients.response.SysInfo;
import api.weather.clients.response.WeatherDescription;
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

import java.util.Arrays;

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
        given(weatherClient.getWeather("London", apiKey))
                .willReturn(WeatherResponse.builder()
                        .date(1476041385L)
                        .city("London")
                        .main(Main.builder().temp(284D).build())
                        .weather(Arrays.asList(WeatherDescription.builder().description("light rain").build()))
                        .sys(SysInfo.builder().sunrise(1475993779L).sunset(1476033461L).build())
                        .build());
    }

    @Test
    public void getWeather_for_london() {
        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity("/weather?city=London", WeatherResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        log.info(response.getStatusCode());
        log.info(response.getBody().getDate());
    }
}
