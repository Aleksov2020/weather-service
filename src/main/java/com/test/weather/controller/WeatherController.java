package com.test.weather.controller;

import com.test.weather.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("api/v1/weather/")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping
    public ResponseEntity<String> getWeather(@RequestParam(name = "city") String cityName,
                                             @RequestParam(name = "country", required = false) Optional<String> countryName ) {

        return ResponseEntity.ok(
                weatherService.getWeather(cityName, countryName)
        );
    }
}
