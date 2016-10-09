package api.weather.clients.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponse {

    @JsonProperty("dt")
    private Long date;

    @JsonProperty("name")
    private String city;

    private Main main;

    private List<WeatherDescription> weather;

    private SysInfo sys;

}
