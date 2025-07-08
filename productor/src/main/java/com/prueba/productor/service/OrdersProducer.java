package com.prueba.productor.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class OrdersProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaProperties kafkaProperties;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersProducer.class);
    @Value("${productor.kafka.topic}")
    private String producerTopic;

    @Autowired
    public OrdersProducer(KafkaTemplate<String, String> kafkaTemplate, KafkaProperties kafkaProperties) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProperties = kafkaProperties;
    }

    public void sendRequestToTopic(String request){
        LOGGER.info("Bootstrap Servers: {}", kafkaProperties.getBootstrapServers().stream().collect(Collectors.joining(", ")));
        kafkaTemplate.send(producerTopic, request);
    }
}
