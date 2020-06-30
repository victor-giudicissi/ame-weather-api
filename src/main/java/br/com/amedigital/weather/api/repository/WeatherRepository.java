package br.com.amedigital.weather.api.repository;

import br.com.amedigital.weather.api.entity.WeatherEntity;
import net.bytebuddy.asm.Advice;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.BeanMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class WeatherRepository extends BaseRepository {

    public WeatherRepository(Jdbi jdbi, Scheduler jdbcScheduler) {
        super(jdbi, jdbcScheduler);
    }

    public Flux<WeatherEntity> saveAll(List<WeatherEntity> entities) {

        List<WeatherEntity> weatherEntities = new ArrayList<>();

        return async(() -> jdbi.inTransaction(handle -> {

            entities.stream().forEach(entity -> {
                entity.setId(UUID.randomUUID().toString());
                handle.createUpdate(sqlLocator.locate("sql.save-weather"))
                        .bindBean(entity)
                        .execute();
                weatherEntities.add(entity);
            });

            return weatherEntities;
        })).flatMapIterable(e -> e);
    }

    public Mono<WeatherEntity> save(WeatherEntity entity) {
        return async(() -> jdbi.inTransaction(handle -> {
            entity.setId(UUID.randomUUID().toString());
            handle.createUpdate(sqlLocator.locate("sql.save-weather"))
                    .bindBean(entity)
                    .execute();

            return entity;
        }));
    }

    public Flux<WeatherEntity> findAll() {
        return async(() -> jdbi.inTransaction(handle -> {
            List<WeatherEntity> result = handle
                    .createQuery(sqlLocator.locate("sql.find-all-weather"))
                    .registerRowMapper(BeanMapper.factory(WeatherEntity.class))
                    .mapTo(WeatherEntity.class)
                    .list();

            return result;
        })).flatMapIterable(e -> e);
    }

    public Flux<WeatherEntity> findByCityCodeAndDay(int cityCode, LocalDate day) {
        return async(() -> jdbi.inTransaction(handle -> {
            List<WeatherEntity> result = handle
                    .createQuery(sqlLocator.locate("sql.find-weather-by-city-code-and-day"))
                    .bind("cityCode", cityCode)
                    .bind("day", day)
                    .registerRowMapper(BeanMapper.factory(WeatherEntity.class))
                    .mapTo(WeatherEntity.class)
                    .list();

            return result;
        })).flatMapIterable(e -> e);
    }

    public Flux<WeatherEntity> findByCityCodeAndDateRange(int cityCode, LocalDate init, LocalDate end) {
        return async(() -> jdbi.inTransaction(handle -> {
            List<WeatherEntity> result = handle
                    .createQuery(sqlLocator.locate("sql.find-weather-by-city-code-and-date-range"))
                    .bind("cityCode", cityCode)
                    .bind("init", init)
                    .bind("end", end)
                    .registerRowMapper(BeanMapper.factory(WeatherEntity.class))
                    .mapTo(WeatherEntity.class)
                    .list();

            return result;
        })).flatMapIterable(e -> e);
    }

    public Mono<Void> delete(String weatherId) {
        return async(() -> jdbi.inTransaction(handle -> handle.createUpdate(sqlLocator.locate("sql.delete-weather"))
                .bind("id", weatherId)
                .execute()
        ))
                .then();
    }

    public Mono<Void> deleteAll(List<WeatherEntity> entities) {
        return async(() -> jdbi.inTransaction(handle -> {
                entities.forEach(weatherEntity -> {
                    handle.createUpdate(sqlLocator.locate("sql.delete-weather"))
                            .bind("id", weatherEntity.getId())
                            .execute();
                });

                return entities;
        })).then();
    }
}
