package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.model.partner.response.INPECityResponse;
import br.com.amedigital.weather.api.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/city")
public class CityController {
    private static final Logger LOG = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping
    public Mono<INPECityResponse> findCity(@RequestParam String cityName) {
        return this.cityService.findCity(cityName);
    }

}
