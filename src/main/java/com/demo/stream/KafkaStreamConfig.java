package com.demo.stream;

import com.demo.stream.processor.MessageProcessor;
import java.util.function.Consumer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamConfig {

    private final MessageProcessor messageProcessor;

    public KafkaStreamConfig(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @Bean
    public Consumer<String> process() {
        return messageProcessor::process;
    }

}
