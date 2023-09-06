package com.test.weather.filter;

import com.test.weather.service.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class QueryFilter extends OncePerRequestFilter {
    private UserServiceImpl userServiceImpl;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            userServiceImpl.addQuery(
                    SecurityContextHolder.getContext().getAuthentication().getName(),
                    "Method: %s; URL: %s; Query: %s;".formatted(
                            request.getMethod(),
                            request.getRequestURI(),
                            request.getQueryString()
                    )
            );
        }

        filterChain.doFilter(request, response);
    }
}
