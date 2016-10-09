package api.weather.exceptions;

import lombok.*;

import java.util.Arrays;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class WeatherException extends RuntimeException {

    private List<ErrorCode> errors;

    public WeatherException(ErrorCode errorCode) {
        errors = Arrays.asList(errorCode);
    }
}
