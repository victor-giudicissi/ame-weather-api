package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.controller.response.WeatherResponse;
import br.com.amedigital.weather.api.entity.WeatherEntity;
import br.com.amedigital.weather.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
public class WeatherController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/days/{days:4|7}")
    public Flux<WeatherResponse> findWeatherToCity(@RequestParam String cityName,
                                                   @PathVariable int days) {
        return weatherService.findWeatherToCity(cityName, days)
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city for 4 days ==="));
    }

    @GetMapping
    public Flux<WeatherResponse> findAll() {
        return weatherService.findAll()
                .doOnTerminate(() -> LOG.info("=== Finish finding all weather ==="));
    }

    @PostMapping
    public Mono<WeatherResponse> save(@RequestBody WeatherEntity weatherEntity) {
        return weatherService.saveWeather(weatherEntity)
                .doOnTerminate(() -> LOG.info("=== Finish saving weather ==="));
    }

    @DeleteMapping
    public Mono<Void> delete(@RequestParam String cityName,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
        return weatherService.deleteWeather(cityName, day)
                .doOnTerminate(() -> LOG.info("=== Finish saving weather ==="));
    }
}
