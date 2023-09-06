package com.test.weather.service;

import com.test.weather.dto.GeoCoordinatesDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${api.secret}")
    private String apiSecret;

    private final GeoService geoService;

    public String getWeather(String city, Optional<String> country) {
        GeoCoordinatesDTO geoCoordinatesDTO = country
                .map(c -> geoService.getCoordinates(city, c))
                .orElseGet(() -> geoService.getCoordinates(city));

        return new RestTemplate()
                        .getForObject(
                                "https://api.openweathermap.org/data/2.5/weather?" +
                                    "lat=%s&".formatted(geoCoordinatesDTO.getLat()) +
                                    "lon=%s&".formatted(geoCoordinatesDTO.getLon()) +
                                    "appid=%s".formatted(apiSecret),
                                    String.class
                        );
    }
}
