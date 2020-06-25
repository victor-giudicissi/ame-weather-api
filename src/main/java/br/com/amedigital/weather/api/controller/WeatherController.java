package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.controller.response.WeatherResponse;
import br.com.amedigital.weather.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
public class WeatherController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/")
    public Flux<WeatherResponse> findWeatherToCity(@RequestParam String cityName) {
        return weatherService.findWeatherToCity(cityName)
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city ==="));
    }

    @GetMapping("/days/7")
    public Flux<WeatherResponse> findWeatherToCityFor7Days(@RequestParam String cityName) {
        return weatherService.findWeatherToCityFor7Days(cityName)
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city ==="));
    }
}
