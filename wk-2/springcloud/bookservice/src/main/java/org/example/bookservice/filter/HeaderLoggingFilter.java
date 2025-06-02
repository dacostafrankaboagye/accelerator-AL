package org.example.bookservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//@Component
public class HeaderLoggingFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(HeaderLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("gateway-Header");
        logger.info("the header from the gateway = {}", header);
        filterChain.doFilter(request, response);
    }
}
