package com.test.weather;

import com.test.weather.dto.GeoCoordinatesDTO;
import com.test.weather.service.GeoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GeoServiceTest {
    @InjectMocks
    private GeoService geoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(geoService, "apiGeoSecret", "f1ddba5ec7cec64b811ee08846198fed");
    }

    @Test
    public void testCorrectCity() {
        GeoCoordinatesDTO city = geoService.getCoordinates("London");

        assertEquals(51.5073219, city.getLat());
        assertEquals(-0.1276474, city.getLon());
    }

    @Test
    public void testCorrectCityCorrectCountry() {
        GeoCoordinatesDTO cityCountry = geoService.getCoordinates("London", "GB");

        assertEquals(51.5073219, cityCountry.getLat());
        assertEquals(-0.1276474, cityCountry.getLon());
    }

    @Test
    public void testIncorrectCity() {
        assertEquals(
                HttpStatus.BAD_REQUEST,
                assertThrows(
                        ResponseStatusException.class,
                        () -> geoService.getCoordinates("UUUUIIIII")
                ).getStatusCode()
        );
    }

    @Test
    public void testIncorrectCityCorrectCountry() {
        assertEquals(
                HttpStatus.BAD_REQUEST,
                assertThrows(
                        ResponseStatusException.class,
                        () -> geoService.getCoordinates("UUUUIIIII", "US")
                ).getStatusCode()
        );
    }

    @Test
    public void testIncorrectCityIncorrectCountry() {
        assertEquals(
                HttpStatus.BAD_REQUEST,
                assertThrows(
                        ResponseStatusException.class,
                        () -> geoService.getCoordinates("UUUUIIIII", "UUUUIIIII")
                ).getStatusCode()
        );
    }

    @Test
    public void testCorrectCityIncorrectCountry() {
        GeoCoordinatesDTO cityCountryIncorrect = geoService.getCoordinates("London", "IncorrectCountry44");

        //Interesting that if we have any incorrect country we receive different coordinates for equals cities
        assertEquals(51.5073359, cityCountryIncorrect.getLat());
        assertEquals(-0.12765, cityCountryIncorrect.getLon());
    }
}
