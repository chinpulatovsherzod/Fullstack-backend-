package com.example.shopbackend.config;

import jakarta.servlet.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public interface SimpleCorsFilters {
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException;

    void init(FilterConfig filterConfig0);
}
