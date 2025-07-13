package com.demo.stream.request;

import com.demo.enums.EventEnum;

public record EventRequest(EventEnum event, String message) {}
