package com.ecommerce.product_service.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Profile("!test")
@Component
public class ApiKeyFilter implements Filter {

    @Value("${api.key}")
    private String validApiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String apiKey = req.getHeader("X-API-KEY");

        if (req.getRequestURI().startsWith("/swagger") || req.getRequestURI().startsWith("/v3/api-docs")) {
            chain.doFilter(request, response); // permitir swagger
            return;
        }

        if (validApiKey.equals(apiKey)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid API KEY");
        }
    }
}
