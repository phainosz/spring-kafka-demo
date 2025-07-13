package com.demo.stream;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public record KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        System.out.println("Sending message to topic: " + topic + " using kafkaTemplate");
    }
}
