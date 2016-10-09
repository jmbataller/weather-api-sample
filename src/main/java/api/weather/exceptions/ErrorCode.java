package api.weather.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * Validation errors - 1XX
     */
    INVALID_REQUEST_ERROR("WEATHER_100", "Invalid request."),

    /**
     * Provider errors - 2XX
     */
    PROVIDER_ERROR("WEATHER_200", "Provider error."),

    /**
     * System errors - 5XX
     */
    UNEXPECTED_ERROR("WEATHER_500", "Unexpected error."),
    TIMEOUT_ERROR("WEATHER_501", "Timeout error when calling provider."),
    INTEGRATION_ERROR("WEATHER_502", "Error when calling provider."),
    SHORTCIRCUIT_ERROR("WEATHER_503", "Short circuit due to unstable provider or increased provider errors"),
    TOO_MANY_REQUESTS_ERROR("WEATHER_504", "Too many requests");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
