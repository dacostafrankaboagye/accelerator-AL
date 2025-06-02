package com.bexos.gateway_server;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackController {

    @GetMapping("/fallback/user-service")
    @ResponseStatus(HttpStatus.OK)
    public String fallbackUser() {
        return "User service is currently unavailable. Please try again later.";
    }
}
