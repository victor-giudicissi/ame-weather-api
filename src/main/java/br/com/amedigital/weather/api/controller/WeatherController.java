package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.controller.response.WeatherResponse;
import br.com.amedigital.weather.api.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping
public class WeatherController {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherController.class);

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{cityCode}")
    public Flux<WeatherResponse> findWeatherToCity(@PathVariable String cityCode) {
        return weatherService.findWeatherToCity(Integer.parseInt(cityCode))
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city ==="));
    }

    @GetMapping("/{cityCode}/days/7")
    public Flux<WeatherResponse> findWeatherToCityFor7Days(@PathVariable String cityCode) {
        return weatherService.findWeatherToCityFor7Days(Integer.parseInt(cityCode))
                .doOnTerminate(() -> LOG.info("=== Finish finding weather to city ==="));
    }
}
