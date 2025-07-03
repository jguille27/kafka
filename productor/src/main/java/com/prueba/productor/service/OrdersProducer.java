package com.prueba.productor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrdersProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value("${productor.kafka.topic}")
    private String producerTopic;

    @Autowired
    public OrdersProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendRequestToTopic(String request){
        kafkaTemplate.send(producerTopic, request);
    }
}
