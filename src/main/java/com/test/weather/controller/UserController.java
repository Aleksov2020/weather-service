package com.test.weather.controller;

import com.test.weather.model.Location;
import com.test.weather.model.Query;
import com.test.weather.model.UserEntity;
import com.test.weather.service.UserServiceImpl;
import com.test.weather.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user/")
@RequiredArgsConstructor
public class UserController {

    private final JwtUtil jwtUtil;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("register")
    public ResponseEntity<String> registerNewUser(@RequestParam String name) {
        return ResponseEntity.ok(
                jwtUtil.generateToken(
                        userServiceImpl.save(
                                name
                        ).getUsername()
                )
        );
    }

    @GetMapping("history")
    public ResponseEntity<List<Query>> getHistory() {
        return ResponseEntity.ok(
                userServiceImpl.getUserHistory(
                        SecurityContextHolder.getContext().getAuthentication().getName()
                )
        );
    }

    @PostMapping ("favorite")
    public ResponseEntity<UserEntity> setLocationToFavorite(@RequestParam(name = "city") String cityName,
                                            @RequestParam(name = "country", required = false) Optional<String> countryName ) {
        return ResponseEntity.ok(
                userServiceImpl.addFavoriteLocationToUser(
                        SecurityContextHolder.getContext().getAuthentication().getName(),
                        cityName,
                        countryName
                )
        );
    }

    @GetMapping ("favorite")
    public ResponseEntity<List<Location>> getAllFavoriteLocations() {
        return ResponseEntity.ok(
                        userServiceImpl.getAllFavoriteLocations(
                        SecurityContextHolder.getContext().getAuthentication().getName()
                )
        );
    }
}
