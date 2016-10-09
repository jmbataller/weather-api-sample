package api.weather.services;

import api.weather.clients.WeatherClient;
import api.weather.clients.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;
    private final String apiKey;

    public WeatherServiceImpl(@Qualifier("weatherFeignClient") WeatherClient weatherClient, @Value("${weather.service.api-key}") String apiKey) {
        this.weatherClient = weatherClient;
        this.apiKey = apiKey;
    }

    @Override
    public WeatherResponse getWeather(String city) {
        return weatherClient.getWeather(city, apiKey);
    }
}
