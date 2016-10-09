package api.weather.exceptions;

public class ValidationException extends WeatherException {

    public ValidationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

