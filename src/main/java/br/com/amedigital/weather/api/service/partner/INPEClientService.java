package br.com.amedigital.weather.api.service.partner;

import br.com.amedigital.weather.api.config.webclient.BaseWebClient;
import br.com.amedigital.weather.api.model.partner.response.INPECityResponse;
import br.com.amedigital.weather.api.model.partner.response.INPEWaveResponse;
import br.com.amedigital.weather.api.model.partner.response.INPEWeatherCityResponse;
import com.newrelic.api.agent.Trace;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponents;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class INPEClientService extends BaseWebClient {

    private static final Logger LOG = getLogger(INPEClientService.class);

    public static final String CITY_WEATHER = "cidade/#/previsao.xml";

    public static final String CITY_WEATHER_7_DAYS = "cidade/7dias/#/previsao.xml";

    public static final String CITY = "listaCidades";

    public static final String WAVE = "cidade/%d/dia/%d/ondas.xml";

    public INPEClientService(final WebClient webClient, @Value("${partner.url}") final String url) {
        super(webClient, url);
    }

    @Trace(dispatcher = true)
    public Mono<INPEWeatherCityResponse> findWeatherToCity(Integer cityCode) {
        LOG.debug("==== Find weather to city ====");

        return handleGenericMono(HttpMethod.GET, urlWeather(cityCode), INPEWeatherCityResponse.class, MediaType.APPLICATION_XML_VALUE)
                        .doOnError(throwable -> LOG.error("=== Error finding weather to city ===", throwable));
    }

    @Trace(dispatcher = true)
    public Mono<INPEWeatherCityResponse> findWeatherToCityFor7Days(int cityCode) {
        LOG.debug("==== Find weather to city ====");

        return handleGenericMono(HttpMethod.GET, urlWeatherFor7Days(cityCode), INPEWeatherCityResponse.class, MediaType.APPLICATION_XML_VALUE)
                .doOnError(throwable -> LOG.error("=== Error finding weather to city ===", throwable));
    }

    @Trace(dispatcher = true)
    public Mono<INPECityResponse> findCity(String cityName) {
        LOG.debug("==== Find weather to city ====");

        return handleGenericMono(HttpMethod.GET, urlFindCity(cityName), INPECityResponse.class, MediaType.APPLICATION_XML_VALUE)
                .doOnError(throwable -> LOG.error("=== Error finding weather to city ===", throwable));
    }

    @Trace(dispatcher = true)
    public Mono<INPEWaveResponse> findWave(int cityCode, int day) {
        LOG.debug("==== Find weather to city ====");

        return handleGenericMono(HttpMethod.GET, urlFindWave(cityCode, day), INPEWaveResponse.class, MediaType.APPLICATION_XML_VALUE)
                .doOnError(throwable -> LOG.error("=== Error finding weather to city ===", throwable));
    }

    protected UriComponents urlWeather(Integer cityCode) {
        return urlBuilder()
                .pathSegment(CITY_WEATHER.replaceAll("#", String.valueOf(cityCode)))
                .build();
    }

    protected UriComponents urlWeatherFor7Days(Integer cityCode) {
        return urlBuilder()
                .pathSegment(CITY_WEATHER_7_DAYS.replaceAll("#", String.valueOf(cityCode)))
                .build();
    }

    protected UriComponents urlFindCity(String cityName) {
        return urlBuilder()
                .pathSegment(CITY)
                .query("city=#".replaceAll("#", String.valueOf(cityName)))
                .build();
    }

    protected UriComponents urlFindWave(int cityCode, int day) {
        return urlBuilder()
                .pathSegment(String.format(WAVE, cityCode, day))
                .build();
    }
}
