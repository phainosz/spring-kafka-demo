package com.demo.service;

import com.demo.enums.EventEnum;
import com.demo.stream.KafkaProducer;
import com.demo.stream.request.EventRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StartStreamService {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper;
    private final String startInternalStreamTopic;

    public StartStreamService(KafkaProducer kafkaProducer,
                              ObjectMapper objectMapper,
                              @Value("${spring.kafka.handler.internal-topic}") String topic) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
        this.startInternalStreamTopic = topic;
    }

    public void startStream(String message) {
        var payload = new EventRequest(EventEnum.EVENT_A, message);
        try {
            kafkaProducer.sendMessage(startInternalStreamTopic, objectMapper.writeValueAsString(payload));
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Failed writing json message.", ex);
        }
    }
}
