package api.weather.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Weather {

    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    private String city;
    @JsonFormat(pattern="h:mm a")
    private LocalTime sunrise;
    @JsonFormat(pattern="h:mm a")
    private LocalTime sunset;
    private String weatherDescription;
    private List<Temperature> temperature;

}
