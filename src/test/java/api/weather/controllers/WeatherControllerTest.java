package api.weather.controllers;

import api.weather.clients.response.WeatherResponse;
import api.weather.domain.City;
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

import java.time.LocalDate;

import static org.mockito.BDDMockito.*;
import static org.junit.Assert.*;
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

    @Test
    public void getWeather_successfully() throws Exception {

        given(weatherService.getWeather(City.London)).willReturn(Weather.builder().date(LocalDate.now()).build());

        this.mvc.perform(get(WEATHER_ENDPOINT)
                .param(CITY, City.London.name())
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date").value("1"));
    }
}
