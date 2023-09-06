package com.test.weather;

import com.test.weather.controller.AdviceController;
import com.test.weather.controller.UserController;
import com.test.weather.controller.WeatherController;
import com.test.weather.filter.QueryFilter;
import com.test.weather.model.Location;
import com.test.weather.model.Query;
import com.test.weather.model.UserEntity;
import com.test.weather.repository.UserRepository;
import com.test.weather.service.UserServiceImpl;
import com.test.weather.service.WeatherService;
import com.test.weather.util.JwtUtil;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class WeatherApplicationTests {
    private MockMvc mockMvc;
    private String authToken;
    private final UserEntity testUser = new UserEntity();
    private final String testUsername = "John";

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private UserController userController;
    @InjectMocks
    private WeatherController weatherController;
    @InjectMocks
    private AdviceController adviceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController, weatherController, adviceController)
                .build();

        testUser.setUsername(testUsername);
        when(authentication.getName()).thenReturn(testUser.getUsername());
        when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.ofNullable(testUser));
        when(userServiceImpl.save(testUsername)).thenReturn(testUser);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    public void testRegistration() throws Exception {
        authToken = jwtUtil.generateToken(testUsername);

        mockMvc.perform(
                        post("/api/v1/user/register")
                                .param("name", testUsername)
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetHistory() throws Exception {
        testUser.setUserHistory(
                Collections.singletonList(
                        new Query(
                                "testQuery",
                                new Date()
                        )
                )
        );

        when(userServiceImpl.getUserHistory(testUser.getUsername())).thenReturn(testUser.getUserHistory());

        mockMvc.perform(get("/api/v1/user/history")
                        .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testGetWeather() throws Exception {
        mockMvc.perform(get("/api/v1/weather/")
                            .header("Authorization", "Bearer " + authToken)
                            .param("city", "London")
                )
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/weather/")
                                .header("Authorization", "Bearer " + authToken)
                                .param("city", "London")
                                .param("country", "GB")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testSetFavorite() throws Exception {
        mockMvc.perform(post("/api/v1/user/favorite")
                        .header("Authorization", "Bearer " + authToken)
                        .param("city", "London")
                )
                .andExpect(status().isOk());
    }

    @Test
    public void testGetFavorite() throws Exception {
        testUser.setFavoriteLocations(
                Collections.singletonList(
                        new Location(
                                "London",
                                "",
                                51.5073219,
                                -0.1276474
                        )
                )
        );

        when(userServiceImpl.getAllFavoriteLocations(testUser.getUsername())).thenReturn(testUser.getFavoriteLocations());

        mockMvc.perform(get("/api/v1/user/favorite")
                        .header("Authorization", "Bearer " + authToken)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

}
