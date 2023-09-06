package com.test.weather.service;

import com.test.weather.dto.GeoCoordinatesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
public class GeoService {
    @Value("${api.secret}")
    private String apiGeoSecret;

    public GeoCoordinatesDTO getCoordinates(String city) {
        GeoCoordinatesDTO[] geoCoordinatesDTO = new RestTemplate()
                .getForObject(
                        "http://api.openweathermap.org/geo/1.0/direct?" +
                            "q=%s&".formatted(city) +
                            "limit=1&" +
                            "appid=%s".formatted(apiGeoSecret),
                        GeoCoordinatesDTO[].class
                );

        if (geoCoordinatesDTO == null || geoCoordinatesDTO.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UnknownCity");
        }

        return geoCoordinatesDTO[0];
    }

    public GeoCoordinatesDTO getCoordinates(String city, String country) {
        GeoCoordinatesDTO[] geoCoordinatesDTO = new RestTemplate()
                .getForObject(
                        "http://api.openweathermap.org/geo/1.0/direct?" +
                                "q=%s&".formatted(city + "," + country) +
                                "limit=1&" +
                                "appid=%s".formatted(apiGeoSecret),
                        GeoCoordinatesDTO[].class
                );

        if (geoCoordinatesDTO == null || geoCoordinatesDTO.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "UnknownCityOrCountry");
        }

        return geoCoordinatesDTO[0];
    }
}
