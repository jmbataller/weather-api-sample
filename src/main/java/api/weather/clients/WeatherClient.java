package api.weather.clients;

import api.weather.clients.errors.ServiceErrorDecoder;
import api.weather.clients.response.WeatherResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "weather", url = "${weather.service.url}", configuration = ServiceErrorDecoder.class)
//@FeignClient(name = "example", url = "http://example.com")
public interface WeatherClient {

    String CITY = "q";
    String API_KEY = "APPID";

    @RequestMapping(value = "/weather", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE})
    WeatherResponse getWeather(@RequestParam(CITY) String city, @RequestParam(API_KEY) String apiKey);
}
