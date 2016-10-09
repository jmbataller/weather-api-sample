[![Build Status](https://travis-ci.org/jmbataller/weather-api-sample.svg?branch=master)](https://travis-ci.org/jmbataller/weather-api-sample)
[![codecov.io](https://codecov.io/github/jmbataller/weather-api-sample/coverage.svg?branch=master)](https://codecov.io/github/jmbataller/weather-api-sample?branch=master)

# weather-api-sample
Weather API sample

### Requirements:

- Java 8
- Maven 3

---

### Build

> mvn clean install

### Run

> java -jar target/weather-api-0.0.1-SNAPSHOT.jar

### API Swagger docs

> http://localhost:8080/swagger-ui.html



Features:

- Caching using Caffeine cache - time to live of the cache is 60 seconds
- Circuit breaker when calling weather 3rd party API

TODOs:

- Add more integration and unit tests

