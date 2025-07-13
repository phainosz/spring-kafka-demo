package com.demo.stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamExample {

    private final ObjectMapper objectMapper;

    public KafkaStreamExample(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private static final Logger LOG = Logger.getLogger(KafkaStreamExample.class.getName());

    @Bean
    public Function<Message<String>, String> consumeAndProduce() {
        return message -> {
            LOG.info("Start consumeAndProduce");

            String payload = message.getPayload();

            if (message.getHeaders().get("test") != null) {
                LOG.info("Finished after receiving test header");
                return null;
            }

            try {
                Map<String, Object> parsedPayload = objectMapper.readValue(payload, new TypeReference<>() {
                });
                Map<String, Object> output = new HashMap<>();
                output.put("consumeAndProduce", parsedPayload);
                String outputJson = objectMapper.writeValueAsString(output);

                LOG.info("End consumeAndProduce");
                return outputJson;
            } catch (Exception e) {
                return null;
            }
        };
    }

    @Bean
    public Consumer<String> consumeOnly() {
        return input -> {
            LOG.info("Start consumeOnly");

            LOG.info("Consumed input: " + input);

            LOG.info("End consumeOnly");
        };
    }
}
