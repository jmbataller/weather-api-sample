package api.weather.services;

import api.weather.clients.WeatherClient;
import api.weather.clients.response.WeatherResponse;
import api.weather.domain.City;
import api.weather.domain.Temperature;
import api.weather.domain.TemperatureUnit;
import api.weather.domain.Weather;
import api.weather.exceptions.ServiceClientException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.List;

/**
 * Weather service
 */
@Service
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;
    private final String apiKey;

    public WeatherServiceImpl(@Qualifier("weatherFeignClient") WeatherClient weatherClient, @Value("${weather.service.api-key}") String apiKey) {
        this.weatherClient = weatherClient;
        this.apiKey = apiKey;
    }

    /**
     * Get temperature by city
     * @param city
     * @return
     */
    @Override
    @Cacheable(cacheNames = "weather", key = "#city")
    @HystrixCommand(commandKey = "getWeather", ignoreExceptions = {ServiceClientException.class})
    public Weather getWeather(City city) {

        WeatherResponse response = weatherClient.getWeather(city, apiKey);

        LocalDate date = LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getDate()), ZoneId.of(ZoneOffset.UTC.getId())).toLocalDate();

        LocalTime sunrise = LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getSys().getSunrise()), ZoneId.of(ZoneOffset.UTC.getId())).toLocalTime();
        LocalTime sunset = LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getSys().getSunset()), ZoneId.of(ZoneOffset.UTC.getId())).toLocalTime();

        String weatherDescription = response.getWeather().stream().findFirst().map(d -> d.getDescription()).orElse(null);

        List<Temperature> temperature = convertTemperature(response.getMain().getTemp());

        return Weather.builder()
                .city(response.getCity())
                .date(date)
                .sunrise(sunrise)
                .sunset(sunset)
                .weatherDescription(weatherDescription)
                .temperature(temperature)
                .build();
    }

    /**
     * Convert Kelvin temperature to Celsius and Fahrenheit
     * @param kelvinTemp
     * @return
     */
    private List<Temperature> convertTemperature(Double kelvinTemp) {
        double celsius = kelvinTemp - 273.15;
        double fahrenheit = (1.8 * kelvinTemp) - 459.67;
        return Arrays.asList(Temperature.builder().temperature(BigDecimal.valueOf(celsius).setScale(2, BigDecimal.ROUND_HALF_UP)).unit(TemperatureUnit.C).build(),
                Temperature.builder().temperature(BigDecimal.valueOf(fahrenheit).setScale(2, BigDecimal.ROUND_HALF_UP)).unit(TemperatureUnit.F).build());
    }
}
