package br.com.amedigital.weather.api.service;

import br.com.amedigital.weather.api.model.partner.response.INPEWaveResponse;
import br.com.amedigital.weather.api.service.partner.INPEClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class WaveService {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private INPEClientService inpeClientService;

    public WaveService(INPEClientService inpeClientService) {
        this.inpeClientService = inpeClientService;
    }

    public Mono<INPEWaveResponse> findWave(int cityCode, int day) {
        return this.inpeClientService.findWave(cityCode, day);
    }
}
