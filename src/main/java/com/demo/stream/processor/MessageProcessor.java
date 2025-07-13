package com.demo.stream.processor;

import com.demo.service.EventHandler;
import com.demo.stream.KafkaProducer;
import com.demo.stream.request.EventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MessageProcessor {

    private static final Logger LOG = Logger.getLogger(MessageProcessor.class.getName());

    private final ObjectMapper objectMapper;
    private final List<EventHandler> eventHandlers;
    private final KafkaProducer kafkaProducer;
    private final String errorTopic;

    public MessageProcessor(ObjectMapper objectMapper,
                            List<EventHandler> eventHandlers,
                            KafkaProducer kafkaProducer,
                            @Value("${spring.kafka.handler.error-topic}") String errorTopic) {
        this.objectMapper = objectMapper;
        this.eventHandlers = eventHandlers;
        this.kafkaProducer = kafkaProducer;
        this.errorTopic = errorTopic;
    }

    public void process(String message) {
        try {
            if (isValidJson(message)) {
                var payload = fromJson(message, EventRequest.class);
                LOG.info("Payload received" + payload);

                var eventHandler = this.eventHandlers.stream().filter(handler -> handler.getEvent().equals(payload.event()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Event not found"));

                var eventResponse = eventHandler.handle(payload);

                kafkaProducer.sendMessage(eventHandler.responseTopic(), objectMapper.writeValueAsString(eventResponse));
                LOG.info("Finished processing message successfully.");
            }
        } catch (Exception ex) {
            LOG.warning("Error processing message.");
            var errorMessage = Map.of("input", fromJson(message, Map.class), "error", ex.getMessage());
            kafkaProducer.sendMessage(errorTopic, toJson(errorMessage));
        }
    }

    private boolean isValidJson(String json) {
        try {
            objectMapper.readTree(json);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    private <T> T fromJson(String value, Class<T> data) {
        try {
            return objectMapper.readValue(value, data);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert payload.");
        }
    }

    private String toJson(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception ex) {
            return "Failed to convert to json";
        }
    }
}
