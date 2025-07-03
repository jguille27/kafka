package com.prueba.consumer.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ErrorProducer {

    private final static Logger LOGGER = LoggerFactory.getLogger(ErrorProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${kafka.error.topic}")
    private String errorTopic;

    @Autowired
    public ErrorProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequestToTopic(String message){
        try {
            kafkaTemplate.send(errorTopic, message);
        } catch (Exception e) {
            LOGGER.error("Exception sending the message to topic: {}",errorTopic, e);
        }
    }
}
