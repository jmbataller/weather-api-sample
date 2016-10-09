package api.weather.services;

import api.weather.domain.City;
import api.weather.domain.Weather;

public interface WeatherService {

    Weather getWeather(City city);
}
