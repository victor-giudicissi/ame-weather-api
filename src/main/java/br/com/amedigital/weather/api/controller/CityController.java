package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.model.partner.response.INPECityResponse;
import br.com.amedigital.weather.api.service.CityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/city")
public class CityController {
    private static final Logger LOG = LoggerFactory.getLogger(CityController.class);

    private final CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{cityName}")
    public Mono<INPECityResponse> findCity(@PathVariable String cityName) {
        return this.cityService.findCity(cityName);
    }

}
