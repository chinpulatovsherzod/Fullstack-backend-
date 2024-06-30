package com.example.shopbackend.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter, SimpleCorsFilters {


    @Value("${app.client.url}")
    private String clientAppUrl = "";

    public SimpleCorsFilter(){

    }

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException{
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        Map<String, String> map = new HashMap<>();
        String originHeader = httpServletRequest.getHeader("origin");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Origin", originHeader);
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Methods","POST, GET, OPTIONS, DELETE");
        ((HttpServletResponse) response).setHeader("Access-Control-Max-Age","3600");
        ((HttpServletResponse) response).setHeader("Access-Control-Allow-Headers","*");

        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) request).getMethod())){
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_OK);
        }else {
            chain.doFilter(request,response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig0){

    }



}
