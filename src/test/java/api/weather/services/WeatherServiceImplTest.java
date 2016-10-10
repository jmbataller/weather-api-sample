package api.weather.services;

import api.weather.clients.WeatherClient;
import api.weather.clients.response.Main;
import api.weather.clients.response.SysInfo;
import api.weather.clients.response.WeatherDescription;
import api.weather.clients.response.WeatherResponse;
import api.weather.domain.City;
import api.weather.domain.TemperatureUnit;
import api.weather.domain.Weather;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherServiceImplTest {

    @Autowired
    private WeatherService weatherService;

    @MockBean
    private WeatherClient weatherClient;


    @Before
    public void setup() {

        // given
        given(weatherClient.getWeather(City.London, "598d8eeae293208a1f0601637c7c3c5f"))
                .willReturn(WeatherResponse.builder()
                        .date(1476041385L)
                        .city(City.London.name())
                        .main(Main.builder().temp(284D).build())
                        .weather(Arrays.asList(WeatherDescription.builder().description("light rain").build()))
                        .sys(SysInfo.builder().sunrise(1475993779L).sunset(1476033461L).build())
                        .build());

    }

    @Test
    public void test_get_weather() {

        // when
        Weather weather = weatherService.getWeather(City.London);

        // then
        assertEquals(LocalDate.of(2016, 10, 9), weather.getDate());
        assertEquals(LocalTime.of(06, 16, 19), weather.getSunrise());
        assertEquals(LocalTime.of(17, 17, 41), weather.getSunset());
        assertEquals(City.London.name(), weather.getCity());
        assertEquals("light rain", weather.getWeatherDescription());
        assertEquals(BigDecimal.valueOf(10.85), weather.getTemperature().get(0).getTemperature());
        assertEquals(TemperatureUnit.C, weather.getTemperature().get(0).getUnit());
        assertEquals(BigDecimal.valueOf(51.53), weather.getTemperature().get(1).getTemperature());
        assertEquals(TemperatureUnit.F, weather.getTemperature().get(1).getUnit());
    }
}
