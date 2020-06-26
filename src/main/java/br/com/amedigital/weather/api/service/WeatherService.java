package br.com.amedigital.weather.api.service;

import br.com.amedigital.weather.api.controller.response.WeatherResponse;
import br.com.amedigital.weather.api.entity.WeatherEntity;
import br.com.amedigital.weather.api.exception.NotFoundException;
import br.com.amedigital.weather.api.mapper.WeatherMapper;
import br.com.amedigital.weather.api.model.ErrorMessages;
import br.com.amedigital.weather.api.repository.WeatherRepository;
import br.com.amedigital.weather.api.service.partner.INPEClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final INPEClientService inpeClientService;

    private final WeatherMapper mapper;

    private final WeatherRepository weatherRepository;

    private final CityService cityService;

    public WeatherService(INPEClientService inpeClientService, WeatherMapper mapper, WeatherRepository weatherRepository, CityService cityService) {
        this.inpeClientService = inpeClientService;
        this.mapper = mapper;
        this.weatherRepository = weatherRepository;
        this.cityService = cityService;
    }

    public Flux<WeatherResponse> findWeatherToCity(String cityName) {
        return this.cityService.findCity(cityName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMapMany(inpeCityResponse ->
                        inpeClientService.findWeatherToCity(inpeCityResponse.getCities().get(0).getId())
                                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                                .flatMap(inpeWeatherCityResponse -> inpeWeatherCityResponse.getName() == null || inpeWeatherCityResponse.getName().equals("null") ?
                                        Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)) : Mono.just(inpeWeatherCityResponse))
                                .flatMapMany(response -> weatherRepository.saveAll(mapper.INPEWeatherCityResponseToEntity(response, 1)))
                                .doOnError(throwable -> LOG.error("=== Error finding weather to city with code: {} ===", 1))
                                .onErrorMap(throwable -> throwable)
                                .flatMap(entity -> Flux.just(mapper.entityToResponse(entity)))
                );
    }

    public Flux<WeatherResponse> findWeatherToCityFor7Days(String cityName) {
        return this.cityService.findCity(cityName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMapMany(inpeCityResponse -> inpeClientService.findWeatherToCityFor7Days(inpeCityResponse.getCities().get(0).getId())
                        .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                        .flatMap(inpeWeatherCityResponse -> inpeWeatherCityResponse.getName() == null || inpeWeatherCityResponse.getName().equals("null") ?
                                Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)) : Mono.just(inpeWeatherCityResponse))
                        .flatMapMany(response -> weatherRepository.saveAll(mapper.INPEWeatherCityResponseToEntity(response, inpeCityResponse.getCities().get(0).getId())))
                        .doOnError(throwable -> LOG.error("=== Error finding weather to city with code: {} ===", inpeCityResponse.getCities().get(0).getId()))
                        .onErrorMap(throwable -> throwable)
                        .flatMap(entity -> Flux.just(mapper.entityToResponse(entity)))
                );
    }

    public Flux<WeatherResponse> findAll() {
        return this.weatherRepository.findAll()
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMap(weatherEntity -> Flux.just(mapper.entityToResponse(weatherEntity)));
    }

    public Mono<WeatherResponse> saveWeather(@RequestBody WeatherEntity weatherEntity) {
        return this.weatherRepository.save(weatherEntity)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMap(weatherEntityResult -> Mono.just(mapper.entityToResponse(weatherEntityResult)));
    }
}
