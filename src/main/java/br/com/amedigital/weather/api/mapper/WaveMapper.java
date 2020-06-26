package br.com.amedigital.weather.api.mapper;

import br.com.amedigital.weather.api.entity.WaveEntity;
import br.com.amedigital.weather.api.model.partner.response.INPEWaveResponse;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class WaveMapper {

    public List<WaveEntity> INPEWaveResponseToEntity(INPEWaveResponse inpeWaveResponse) {

        DateTimeFormatter updateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<WaveEntity> waveList = new ArrayList<>();

        WaveEntity entity = new WaveEntity();

        entity.setCityName(inpeWaveResponse.getName());
        entity.setState(inpeWaveResponse.getState());
        entity.setUpdatedAt(LocalDate.parse(inpeWaveResponse.getUpdate(), updateFormatter));
        entity.setPeriod(0);
        entity.setWaveDay(LocalDate.parse(inpeWaveResponse.getMorning().getDay().substring(0, 10), updateFormatter));
        entity.setUnrest(inpeWaveResponse.getMorning().getUnrest());
        entity.setHeight(inpeWaveResponse.getMorning().getHeigth());
        entity.setDirection(inpeWaveResponse.getMorning().getDirection());
        entity.setWind(inpeWaveResponse.getMorning().getWind());
        entity.setWindDirection(inpeWaveResponse.getMorning().getWindDirection());

        waveList.add(entity);

        entity = new WaveEntity();

        entity.setCityName(inpeWaveResponse.getName());
        entity.setState(inpeWaveResponse.getState());
        entity.setUpdatedAt(LocalDate.parse(inpeWaveResponse.getUpdate(), updateFormatter));
        entity.setPeriod(1);
        entity.setWaveDay(LocalDate.parse(inpeWaveResponse.getAfternoon().getDay().substring(0, 10), updateFormatter));
        entity.setUnrest(inpeWaveResponse.getAfternoon().getUnrest());
        entity.setHeight(inpeWaveResponse.getAfternoon().getHeigth());
        entity.setDirection(inpeWaveResponse.getAfternoon().getDirection());
        entity.setWind(inpeWaveResponse.getAfternoon().getWind());
        entity.setWindDirection(inpeWaveResponse.getAfternoon().getWindDirection());

        waveList.add(entity);

        entity = new WaveEntity();

        entity.setCityName(inpeWaveResponse.getName());
        entity.setState(inpeWaveResponse.getState());
        entity.setUpdatedAt(LocalDate.parse(inpeWaveResponse.getUpdate(), updateFormatter));
        entity.setPeriod(2);
        entity.setWaveDay(LocalDate.parse(inpeWaveResponse.getNight().getDay().substring(0, 10), updateFormatter));
        entity.setUnrest(inpeWaveResponse.getNight().getUnrest());
        entity.setHeight(inpeWaveResponse.getNight().getHeigth());
        entity.setDirection(inpeWaveResponse.getNight().getDirection());
        entity.setWind(inpeWaveResponse.getNight().getWind());
        entity.setWindDirection(inpeWaveResponse.getNight().getWindDirection());

        waveList.add(entity);

        return waveList;
    }

    public INPEWaveResponse EntityToINPEWaveResponse(List<WaveEntity> waves) {
        INPEWaveResponse response = new INPEWaveResponse();

        response.setName(waves.get(0).getCityName());
        response.setState(waves.get(0).getState());
        response.setUpdate(waves.get(0).getUpdatedAt().toString());

        response.setMorning(buildWave(waves.stream().filter(waveEntity -> waveEntity.getPeriod() == 0).findFirst().get()));
        response.setAfternoon(buildWave(waves.stream().filter(waveEntity -> waveEntity.getPeriod() == 1).findFirst().get()));
        response.setNight(buildWave(waves.stream().filter(waveEntity -> waveEntity.getPeriod() == 2).findFirst().get()));

        return response;
    }

    public INPEWaveResponse.Wave buildWave(WaveEntity entity) {
        INPEWaveResponse.Wave wave = new INPEWaveResponse.Wave();

        wave.setDay(entity.getWaveDay().toString());
        wave.setDirection(entity.getDirection());
        wave.setHeigth(entity.getHeight());
        wave.setUnrest(entity.getUnrest());
        wave.setWind(entity.getWind());
        wave.setWindDirection(entity.getWindDirection());

        return wave;
    }
}
