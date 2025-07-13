package com.demo.stream;

import com.demo.processor.MessageProcessor;
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
public record KafkaStreamConfig(MessageProcessor messageProcessor) {

    @Bean
    public Consumer<String> process() {
        return messageProcessor::process;
    }

}
