package com.demo.stream.response;

import com.demo.enums.EventEnum;

public record EventResponse(EventEnum event, String message) {}
