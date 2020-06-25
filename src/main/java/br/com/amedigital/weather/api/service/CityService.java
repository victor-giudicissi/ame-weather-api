package br.com.amedigital.weather.api.service;

import br.com.amedigital.weather.api.entity.CityEntity;
import br.com.amedigital.weather.api.exception.NotFoundException;
import br.com.amedigital.weather.api.mapper.CityMapper;
import br.com.amedigital.weather.api.model.ErrorMessages;
import br.com.amedigital.weather.api.model.partner.response.INPECityResponse;
import br.com.amedigital.weather.api.service.partner.INPEClientService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CityService {
    private final INPEClientService inpeClientService;
    private final CityMapper mapper;

    public CityService(INPEClientService inpeClientService, CityMapper mapper) {
        this.inpeClientService = inpeClientService;
        this.mapper = mapper;
    }

    public Mono<INPECityResponse> findCity(String cityName) {
        return this.inpeClientService.findCity(cityName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)));
    }

}
