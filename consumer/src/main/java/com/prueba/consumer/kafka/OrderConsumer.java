package com.prueba.consumer.kafka;

import com.google.gson.Gson;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.service.KafkaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    private final KafkaService kafkaService;

    @Autowired
    public OrderConsumer(KafkaService kafkaService) {
        this.kafkaService = kafkaService;
    }

    @KafkaListener(
            topics = "#{'${kafka.consumer.topic}'}",
            groupId = "consumer-Kafka",
            id = "consumer",
            clientIdPrefix = "consumer-client",
            autoStartup = "#{'${kafka.consumer.autoStartup}'}"
    )
    public void processJson(String orderJson){
        Gson gson = new Gson();
        OrderVO orderVO;
        try {
            orderVO = gson.fromJson(orderJson, OrderVO.class);
            kafkaService.processRequest(orderVO, orderJson);
        }catch (Exception e){
            LOGGER.info("Exception: ",e);
        }
    }
}