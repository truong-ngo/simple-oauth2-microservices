package com.example.springresource.controller;

import com.example.springresource.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MyController {

    private final MyService service;

    @GetMapping("/")
    public Map<String, String> greet() {
        return service.greet();
    }

}
