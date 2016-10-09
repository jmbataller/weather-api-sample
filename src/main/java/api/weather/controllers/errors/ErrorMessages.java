package api.weather.controllers.errors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ErrorMessages {

    private List<ErrorMessage> errors;
}
