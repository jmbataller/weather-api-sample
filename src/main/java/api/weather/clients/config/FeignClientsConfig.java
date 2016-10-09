package api.weather.clients.config;

import api.weather.clients.errors.ServiceErrorDecoder;
import feign.Logger;
import feign.codec.ErrorDecoder;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@NoArgsConstructor
@Configuration
public class FeignClientsConfig {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ServiceErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}

