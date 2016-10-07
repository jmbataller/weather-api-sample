package api.weather.config;

import api.weather.controllers.WeatherController;
import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import static com.google.common.base.Predicates.or;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
@ComponentScan(basePackageClasses = {
        WeatherController.class
})
public class SwaggerConfig {

    @Autowired
    private Environment environment;

    @Autowired
    private TypeResolver typeResolver;

    @Bean
    public Docket weatherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("WeatherAPI")
                .apiInfo(publicInfo())
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(XMLGregorianCalendar.class, Date.class)
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build();
    }

    private Predicate<String> paths() {
        return or(
                regex("/carts.*"),
                regex("/fees.*"));
    }

    private ApiInfo publicInfo() {
        return new ApiInfoBuilder()
                .title("Weather API")
                .description("Weather API")
                .version(environment.getProperty("info.app.version"))
                .build();
    }
}
