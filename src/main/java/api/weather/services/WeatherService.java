package api.weather.services;

import api.weather.clients.response.WeatherResponse;
import api.weather.domain.Weather;

public interface WeatherService {

    Weather getWeather(String city);
}
