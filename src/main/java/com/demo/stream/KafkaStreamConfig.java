package com.demo.stream;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public record KafkaStreamConfig(KafkaSender kafkaSender, ObjectMapper objectMapper) {

    @Bean
    public Function<Message<String>, String> process() {
        return message -> {
            System.out.println("Start process");

            String payload = message.getPayload();
            if (!isValidJson(payload)) {
                System.out.println("Invalid JSON " + payload);
                return null;
            }

            if (message.getHeaders().get("test") != null) {
                System.out.println("Finished after receiving test header");
                return null;
            }

            try {
                Map<String, Object> parsedPayload = objectMapper.readValue(payload, new TypeReference<>() {
                });
                Map<String, Object> output = new HashMap<>();
                output.put("process", parsedPayload);
                String outputJson = objectMapper.writeValueAsString(output);

                System.out.println("End process");
                return outputJson;
            } catch (Exception e) {
                return null;
            }
        };
    }

    @Bean
    public Consumer<String> consumeOnly() {
        return input -> {
            System.out.println("Start consumeOnly");

            if (!isValidJson(input)) {
                System.out.println("Invalid JSON " + input);
                return;
            }

            try {
                Map<String, Object> payload = objectMapper.readValue(input, new TypeReference<>() {
                });
                Map<String, Object> output = new HashMap<>();
                output.put("consumeOnly", payload);
                String outputJson = objectMapper.writeValueAsString(output);

                kafkaSender.sendMessage("output", outputJson);
                System.out.println("End consumeOnly");
            } catch (Exception e) {
            }
        };
    }

    private boolean isValidJson(String input) {
        try {
            objectMapper.readTree(input);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
