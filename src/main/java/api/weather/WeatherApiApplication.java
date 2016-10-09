package api.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@EnableFeignClients(basePackages = {"api.weather.clients"})
@ImportAutoConfiguration(FeignAutoConfiguration.class)		// Workaround for Camden.RELEASE

@SpringBootApplication
@EnableAutoConfiguration
@EnableCircuitBreaker
public class WeatherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);
	}
}
