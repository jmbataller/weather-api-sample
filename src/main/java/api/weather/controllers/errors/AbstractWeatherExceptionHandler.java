package api.weather.controllers.errors;

import api.weather.exceptions.ErrorCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@Log4j2
public abstract class AbstractWeatherExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorMessages errors = createBadRequestErrorMessages(ex);
        return new ResponseEntity<Object>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorMessages errors = createBadRequestErrorMessages(ex);
        return new ResponseEntity<Object>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorMessages errors = createBadRequestErrorMessages(ex);
        return new ResponseEntity<Object>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorMessages errors = createBadRequestErrorMessages(ex);
        return new ResponseEntity<Object>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorMessages errors = createBadRequestErrorMessages(ex);
        return new ResponseEntity<Object>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ErrorMessages errors = createBadRequestErrorMessages(ex);
        return new ResponseEntity<Object>(errors, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ErrorMessages createBadRequestErrorMessages(Exception ex) {
        ErrorMessage error = ErrorMessage.builder().code(ErrorCode.INVALID_REQUEST_ERROR.getCode()).message(ErrorCode.INVALID_REQUEST_ERROR.getMessage() + " " + ex.getMessage()).build();
        return ErrorMessages.builder().errors(Arrays.asList(error)).build();
    }

}
