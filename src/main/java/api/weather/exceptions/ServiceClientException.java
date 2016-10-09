package api.weather.exceptions;

public class ServiceClientException extends WeatherException {

    public ServiceClientException(ErrorCode errorCode) {
        super(errorCode);
    }
}

