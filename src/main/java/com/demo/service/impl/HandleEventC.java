package com.demo.service.impl;

import com.demo.enums.EventEnum;
import com.demo.service.EventHandler;
import com.demo.stream.request.EventRequest;
import com.demo.stream.response.EventResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HandleEventC implements EventHandler {

    private final String topic;

    public HandleEventC(@Value("${spring.kafka.handler.response-topic}") String topic) {
        this.topic = topic;
    }

    @Override
    public EventResponse handle(EventRequest eventRequest) {
        return new EventResponse(EventEnum.EVENT_C, "finished all events");
    }

    @Override
    public EventEnum getEvent() {
        return EventEnum.EVENT_C;
    }

    @Override
    public String responseTopic() {
        return topic;
    }
}
