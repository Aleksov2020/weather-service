package com.test.weather.service;

import com.test.weather.dto.GeoCoordinatesDTO;
import com.test.weather.model.Location;
import com.test.weather.model.Query;
import com.test.weather.model.UserEntity;
import com.test.weather.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final GeoService geoService;

    public void addQuery(String username, String query) {
        userRepository.save(
                userRepository.findByUsername(username)
                        .orElseThrow(
                                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                        ).addQuery(
                                new Query(
                                      query,
                                      new Date()
                                )
                        )
        );
    }

    public List<Query> getUserHistory(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getUserHistory();
    }

    public UserEntity addFavoriteLocationToUser(String username, String cityName, Optional<String> countryName) {
        GeoCoordinatesDTO geoCoordinatesDTO = countryName
                .map(c -> geoService.getCoordinates(cityName, c))
                .orElseGet(() -> geoService.getCoordinates(cityName));

        return userRepository.save(
                    userRepository.findByUsername(username).orElseThrow(
                            () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
                    ).addLocation(
                            new Location(
                                    cityName,
                                    countryName.orElse(""),
                                    geoCoordinatesDTO.getLat(),
                                    geoCoordinatesDTO.getLon()
                            )
                    )
        );
    }

    public List<Location> getAllFavoriteLocations(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        ).getFavoriteLocations();
    }

    public UserEntity save(String username) {
        return userRepository.save(
                new UserEntity(
                        username
                )
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(
                userRepository.findByUsername(username).orElseThrow(
                            () -> new UsernameNotFoundException("Username not found")
                ).getUsername(),
                "",
                new ArrayList<>()
        );
    }
}
