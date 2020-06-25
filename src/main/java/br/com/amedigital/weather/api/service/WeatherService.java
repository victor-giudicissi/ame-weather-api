package br.com.amedigital.weather.api.service;

import br.com.amedigital.weather.api.controller.response.WeatherResponse;
import br.com.amedigital.weather.api.exception.NotFoundException;
import br.com.amedigital.weather.api.mapper.WeatherMapper;
import br.com.amedigital.weather.api.model.ErrorMessages;
import br.com.amedigital.weather.api.repository.WeatherRepository;
import br.com.amedigital.weather.api.service.partner.INPEClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WeatherService {

    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private final INPEClientService inpeClientService;

    private final WeatherMapper mapper;

    private final WeatherRepository weatherRepository;

    public WeatherService(INPEClientService inpeClientService, WeatherRepository weatherRepository, WeatherMapper weatherMapper) {
        this.inpeClientService = inpeClientService;
        this.weatherRepository = weatherRepository;
        this.mapper = weatherMapper;
    }

    public Flux<WeatherResponse> findWeatherToCity(Integer cityCode) {
        return inpeClientService.findWeatherToCity(cityCode)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMap(inpeWeatherCityResponse -> inpeWeatherCityResponse.getName() == null || inpeWeatherCityResponse.getName().equals("null") ?
                        Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)) : Mono.just(inpeWeatherCityResponse))
                .flatMapMany(response ->  weatherRepository.save(mapper.INPEWeatherCityResponseToEntity(response, cityCode)))
                .doOnError(throwable -> LOG.error("=== Error finding weather to city with code: {} ===", cityCode))
                .onErrorMap(throwable -> throwable)
                .flatMap(entity -> Flux.just(mapper.entitytoResponse(entity)));
    }


    public Flux<WeatherResponse> findWeatherToCityFor7Days(Integer cityCode) {
        return inpeClientService.findWeatherToCityFor7Days(cityCode)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMap(inpeWeatherCityResponse -> inpeWeatherCityResponse.getName() == null || inpeWeatherCityResponse.getName().equals("null") ?
                        Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)) : Mono.just(inpeWeatherCityResponse))
                .flatMapMany(response ->  weatherRepository.save(mapper.INPEWeatherCityResponseToEntity(response, cityCode)))
                .doOnError(throwable -> LOG.error("=== Error finding weather to city with code: {} ===", cityCode))
                .onErrorMap(throwable -> throwable)
                .flatMap(entity -> Flux.just(mapper.entitytoResponse(entity)));
    }
}
