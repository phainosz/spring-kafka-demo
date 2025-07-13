package com.demo.stream;

import com.demo.stream.processor.MessageProcessor;
import java.util.logging.Logger;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private static final Logger LOG = Logger.getLogger(KafkaProducer.class.getName());

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
        LOG.info(String.format("Sending message %s to topic %s", message, topic));
    }
}
