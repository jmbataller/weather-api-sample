package api.weather.controllers;

import api.weather.clients.response.WeatherResponse;
import api.weather.services.WeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static api.weather.controllers.ResourceConstants.WEATHER_API;
import static api.weather.controllers.ResourceConstants.WEATHER_ENDPOINT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Api(value = WEATHER_API, description = WEATHER_API)
@Log4j2
@RestController
@RequestMapping(WEATHER_ENDPOINT)
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(final WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @ApiOperation(value = "Get weather")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Weather retrieved successfully", response = String.class),
            @ApiResponse(code = 400, message = "Bad Request.", response = String.class),
            @ApiResponse(code = 404, message = "Not Found.", response = String.class),
            @ApiResponse(code = 415, message = "Unsupported Media Type.", response = String.class),
            @ApiResponse(code = 422, message = "Unprocessable entity.", response = String.class),
            @ApiResponse(code = 500, message = "Internal Server error.", response = String.class)
    })
    @ResponseStatus(OK)
    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    public WeatherResponse getWeather() {
        return weatherService.getWeather("London");
    }
}
