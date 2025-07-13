package com.demo.service;

import com.demo.enums.EventEnum;
import com.demo.stream.request.EventRequest;
import com.demo.stream.response.EventResponse;

public interface EventHandler {

    EventResponse handle(EventRequest eventRequest);
    EventEnum getEvent();
    String responseTopic();
}
