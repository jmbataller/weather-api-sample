[![Build Status](https://travis-ci.org/jmbataller/weather-api-sample.svg?branch=master)](https://travis-ci.org/jmbataller/weather-api-sample)

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

### API operation samples:

> curl http://localhost:8080//weather?city=London
> curl http://localhost:8080//weather?city=HongKong

Features:

- Caching using Caffeine cache - time to live of the cache is 60 seconds
- Circuit breaker when calling weather 3rd party API

TODOs:

- Add more integration and unit tests

