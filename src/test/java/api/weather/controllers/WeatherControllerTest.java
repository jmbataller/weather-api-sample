package api.weather.controllers;

import api.weather.clients.response.WeatherResponse;
import api.weather.domain.City;
import api.weather.domain.Temperature;
import api.weather.domain.TemperatureUnit;
import api.weather.domain.Weather;
import api.weather.services.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static api.weather.controllers.ResourceConstants.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");

    @Test
    public void getWeather_successfully() throws Exception {

        given(weatherService.getWeather(City.London)).willReturn(Weather.builder()
                .date(LocalDate.of(2000, 01, 01))
                .city(City.London.name())
                .sunrise(LocalTime.of(0, 0))
                .sunset(LocalTime.of(0, 0))
                .weatherDescription("clear sky")
                .temperature(Arrays.asList(Temperature.builder().temperature(BigDecimal.ZERO).unit(TemperatureUnit.C).build()))
                .build());

        this.mvc.perform(get(WEATHER_ENDPOINT)
                .param(CITY, City.London.name())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value(LocalDate.of(2000, 01, 01).format(DATE_FORMAT)))
                .andExpect(jsonPath("$.sunrise").value(LocalTime.of(0, 0).format(TIME_FORMAT)))
                .andExpect(jsonPath("$.sunset").value(LocalTime.of(0, 0).format(TIME_FORMAT)))
                .andExpect(jsonPath("$.city").value(City.London.name()))
                .andExpect(jsonPath("$.weatherDescription").value("clear sky"))
                .andExpect(jsonPath("$.temperature[0].temperature").value(BigDecimal.ZERO))
                .andExpect(jsonPath("$.temperature[0].unit").value(TemperatureUnit.C.name()));
    }
}
