package com.bexos.order_service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order-service")
public class OrderServiceController {

    @GetMapping("/hello")
    public String Hello() {
        return "Hello from order service";
    }

    @GetMapping("/unstable")
    public ResponseEntity<String> unstableEndpoint() {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("Service Unavailable");
    }
}
