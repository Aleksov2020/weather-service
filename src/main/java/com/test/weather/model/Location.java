package com.test.weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String cityName;
    private String countryName;
    private double lat;
    private double lon;

    public Location(String cityName, String countryName, double lat, double lon) {
        this.cityName = cityName;
        this.countryName = countryName;
        this.lat = lat;
        this.lon = lon;
    }
}
