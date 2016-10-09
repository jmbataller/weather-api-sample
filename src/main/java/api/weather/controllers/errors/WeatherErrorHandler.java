package api.weather.controllers.errors;

import api.weather.exceptions.ErrorCode;
import api.weather.exceptions.ServiceClientException;
import api.weather.exceptions.ValidationException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixRuntimeException.FailureType;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice
public class WeatherErrorHandler extends AbstractWeatherExceptionHandler {

    /**
     * Handle validation errors
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorMessages> catchValidationErrors(ValidationException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        List<ErrorMessage> errorMessages = ex.getErrors().stream().map(err -> ErrorMessage.builder().code(err.getCode()).message(err.getMessage()).build()).collect(Collectors.toList());
        ErrorMessages errors = ErrorMessages.builder().errors(errorMessages).build();
        return new ResponseEntity<>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * Handle generic service client errors
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ServiceClientException.class)
    public ResponseEntity<ErrorMessages> catchSetupCardErrors(ServiceClientException ex) {
        log.error(ex.getMessage(), ex);
        List<ErrorMessage> errorMessages = ex.getErrors().stream().map(err -> ErrorMessage.builder().code(err.getCode()).message(err.getMessage()).build()).collect(Collectors.toList());
        ErrorMessages errors = ErrorMessages.builder().errors(errorMessages).build();
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle generic hystrix errors
     *
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(HystrixRuntimeException.class)
    public ResponseEntity<ErrorMessages> catchHystrixRuntimeErrors(HystrixRuntimeException ex, HttpServletRequest request) {
        log.error(ex.getFailureType() + " - " + ex.getMessage(), ex);
        ErrorCode err = getErrorCodeFromHystrixFailure(ex.getFailureType());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code(err.getCode())
                .message(ex.getMessage())
                .build();
        ErrorMessages errors = ErrorMessages.builder().errors(Arrays.asList(errorMessage)).build();
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Convert hystrix failure type to checkout error code
     *
     * @param failureType
     * @return
     */
    private ErrorCode getErrorCodeFromHystrixFailure(FailureType failureType) {
        switch (failureType) {
            case TIMEOUT:
                return ErrorCode.TIMEOUT_ERROR;
            case SHORTCIRCUIT:
                return ErrorCode.SHORTCIRCUIT_ERROR;
            case REJECTED_THREAD_EXECUTION:
                return ErrorCode.TOO_MANY_REQUESTS_ERROR;
            default:
                return ErrorCode.INTEGRATION_ERROR;
        }
    }

    /**
     * Handler unexpected errors
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessages> catchAll(Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorMessage err = ErrorMessage.builder().code(ErrorCode.UNEXPECTED_ERROR.getCode()).message(ErrorCode.UNEXPECTED_ERROR.getMessage()).reason(ExceptionUtils.getStackTrace(ex)).build();
        ErrorMessages errors = ErrorMessages.builder().errors(Arrays.asList(err)).build();
        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
