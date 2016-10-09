package api.weather.services;

import api.weather.clients.response.WeatherResponse;

public interface WeatherService {

    WeatherResponse getWeather(String city);
}
