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

import java.time.LocalDate;
import java.util.List;

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

    public Flux<WeatherResponse> findWeatherToCity(String cityName, int days) {
        return this.cityService.findCity(cityName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMapMany(inpeCityResponse -> weatherRepository
                        .findByCityCodeAndDateRange(
                                inpeCityResponse.getCities().get(0).getId(),
                                LocalDate.now(),
                                LocalDate.now().plusDays(days)
                        )
                        .collectList()
                        .flatMapMany(weatherEntities -> weatherEntities.size() == days ?
                                Flux.fromIterable(weatherEntities)
                                :
                                updateWeather(weatherEntities,
                                        inpeCityResponse.getCities().get(0).getId(),
                                        days)
                        )
                ).flatMap(entity -> Flux.just(mapper.entityToResponse(entity)));
    }

    public Flux<WeatherEntity> updateWeather(List<WeatherEntity> weatherEntities,
                                             int cityID,
                                             int days) {
        return weatherRepository.deleteAll(weatherEntities)
                .thenMany(
                        inpeClientService.findWeatherToCity(cityID, days)
                                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                                .flatMap(inpeWeatherCityResponse ->
                                        inpeWeatherCityResponse.getName() == null || inpeWeatherCityResponse.getName().equals("null") ?
                                                Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION))
                                                :
                                                Mono.just(inpeWeatherCityResponse))
                                .flatMapMany(response -> weatherRepository.saveAll(mapper.INPEWeatherCityResponseToEntity(response, cityID)))
                                .doOnError(throwable -> LOG.error("=== Error finding weather to city with code: {} ===", cityID))
                                .onErrorMap(throwable -> throwable)
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

    public Mono<Void> deleteWeather(String cityName, LocalDate day) {
        return this.cityService.findCity(cityName)
                .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                .flatMap(inpeCityResponse -> weatherRepository
                        .findByCityCodeAndDay(inpeCityResponse.getCities().get(0).getId(), day)
                        .switchIfEmpty(Mono.error(new NotFoundException(ErrorMessages.GENERIC_NOT_FOUND_EXCEPTION)))
                        .flatMap(o -> weatherRepository.delete(o.getId()))
                        .then()
                );
    }
}
