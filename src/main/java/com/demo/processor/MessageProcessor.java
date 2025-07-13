package com.demo.processor;

import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public record MessageProcessor() {

    private static final Logger LOG = Logger.getLogger(MessageProcessor.class.getName());

    public void process(String message) {
        LOG.info(String.format("Message %s", message));
    }
}
