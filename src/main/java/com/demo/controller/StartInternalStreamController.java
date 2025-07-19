package com.demo.controller;

import com.demo.service.StartStreamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/demo")
public class StartInternalStreamController {

    private final StartStreamService startStreamService;

    public StartInternalStreamController(StartStreamService startStreamService) {
        this.startStreamService = startStreamService;
    }

    @PostMapping
    public ResponseEntity<Void> startStream(@Valid @RequestBody StartStreamRequest request) {
        startStreamService.startStream(request.getMessage());

        return ResponseEntity.noContent().build();
    }
}
