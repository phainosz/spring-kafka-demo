package com.demo.service.impl;

import com.demo.enums.EventEnum;
import com.demo.service.EventHandler;
import com.demo.stream.request.EventRequest;
import com.demo.stream.response.EventResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HandleEventA implements EventHandler {

    private final String topic;

    public HandleEventA(@Value("${spring.kafka.handler.internal-topic}") String topic) {
        this.topic = topic;
    }

    @Override
    public EventResponse handle(EventRequest eventRequest) {
        return new EventResponse(EventEnum.EVENT_B, "going to event b");
    }

    @Override
    public EventEnum getEvent() {
        return EventEnum.EVENT_A;
    }

    @Override
    public String responseTopic() {
        return topic;
    }
}
