package br.com.amedigital.weather.api.controller;

import br.com.amedigital.weather.api.model.partner.response.INPEWaveResponse;
import br.com.amedigital.weather.api.service.WaveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/wave")
public class WaveController {
    private WaveService waveService;

    public WaveController(WaveService waveService) {
        this.waveService = waveService;
    }

    @GetMapping
    public Mono<INPEWaveResponse> findWave(@RequestParam int cityCode,
                                           @RequestParam int day) {
        return this.waveService.findWave(cityCode, day);
    }
}
