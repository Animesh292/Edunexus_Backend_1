package com.edunexus.backend;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public Map<String, String> status() {
        return Map.of(
            "status", "UP",
            "service", "Edunexus backend"
        );
    }
}
