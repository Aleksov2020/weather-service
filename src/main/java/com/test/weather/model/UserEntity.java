package com.test.weather.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Query> userHistory = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Location> favoriteLocations = new ArrayList<>();
    public UserEntity(String username) {
        this.username = username;
    }

    public UserEntity addQuery(Query query) {
        this.userHistory.add(query);

        return this;
    }

    public UserEntity addLocation(Location location) {
        this.favoriteLocations.add(location);

        return this;
    }
}
