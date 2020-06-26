package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.controller.response.WeatherResponse;
import br.com.amedigital.weather.api.entity.WeatherEntity;
import br.com.amedigital.weather.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class WeatherController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/days/4")
    public Flux<WeatherResponse> findWeatherToCity(@RequestParam String cityName) {
        return weatherService.findWeatherToCity(cityName)
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city for 4 days ==="));
    }

    @GetMapping("/days/7")
    public Flux<WeatherResponse> findWeatherToCityFor7Days(@RequestParam String cityName) {
        return weatherService.findWeatherToCityFor7Days(cityName)
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city for 7 days ==="));
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
}
