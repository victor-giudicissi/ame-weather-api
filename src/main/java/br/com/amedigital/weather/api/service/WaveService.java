package br.com.amedigital.weather.api.service;

import br.com.amedigital.weather.api.entity.WaveEntity;
import br.com.amedigital.weather.api.mapper.WaveMapper;
import br.com.amedigital.weather.api.model.partner.response.INPEWaveResponse;
import br.com.amedigital.weather.api.repository.WaveRepository;
import br.com.amedigital.weather.api.service.partner.INPEClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WaveService {
    private static final Logger LOG = LoggerFactory.getLogger(WeatherService.class);

    private INPEClientService inpeClientService;

    private final WaveRepository waveRepository;

    private final WaveMapper waveMapper;

    public WaveService(INPEClientService inpeClientService, WaveRepository waveRepository, WaveMapper waveMapper) {
        this.inpeClientService = inpeClientService;
        this.waveRepository = waveRepository;
        this.waveMapper = waveMapper;
    }

    public Mono<INPEWaveResponse> findWave(int cityCode, int day) {
        return this.inpeClientService.findWave(cityCode, day)
                .flatMap(inpeWaveResponse -> waveRepository
                        .save(waveMapper.INPEWaveResponseToEntity(inpeWaveResponse))
                        .collectList()
                        .flatMap(waveEntities -> Mono.just(waveMapper.EntityToINPEWaveResponse(waveEntities)))
                );
    }
}
