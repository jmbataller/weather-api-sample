package api.weather.clients.errors;

import api.weather.exceptions.ErrorCode;
import api.weather.exceptions.ServiceClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class ServiceErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        return new ServiceClientException(ErrorCode.PROVIDER_ERROR);
    }

}
