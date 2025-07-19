package com.demo.controller;

import jakarta.validation.constraints.NotEmpty;

public class StartStreamRequest {

    @NotEmpty(message = "Message is required")
    private String message;

    public StartStreamRequest(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
