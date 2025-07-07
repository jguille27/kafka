package com.prueba.consumer.kafka;

import com.google.gson.Gson;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.repository.ClientRepository;
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
    private final ClientRepository clientRepository;

    @Autowired
    public OrderConsumer(KafkaService kafkaService, ClientRepository clientRepository) {
        this.kafkaService = kafkaService;
        this.clientRepository = clientRepository;
    }

    @KafkaListener(
            topics = "#{'${kafka.consumer.topic}'}",
            groupId = "consumer-Kafka",
            id = "consumer",
            clientIdPrefix = "consumer-client",
            autoStartup = "#{'${kafka.consumer.autoStartup}'}"
    )
    public void processJson(String orderJson){
        final String LogHead = "processJson:";
        Gson gson = new Gson();
        OrderVO orderVO = null;
        try {
            orderVO = gson.fromJson(orderJson, OrderVO.class);
            kafkaService.processRequest(orderVO, orderJson);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


// {"orderId":"1","customerId":"1","items":[{"productId":"P001","quantity":2}]}