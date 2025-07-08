package com.prueba.productor.controller;

import com.prueba.productor.service.OrdersProducer;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
public class ProductorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductorController.class);
    private final OrdersProducer kafkaProducer;
    private final KafkaProperties kafkaProperties;

    @Autowired
    public ProductorController(OrdersProducer kafkaProducer, KafkaProperties kafkaProperties) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaProperties = kafkaProperties;
    }

    @PostMapping("/orders")
    public void registerOrder(HttpServletRequest servletRequest, @RequestBody String order){
        LOGGER.info("{} New request: {}",servletRequest.getRequestURL(),order);
        LOGGER.info("{} Kafka servers from controller: {}",servletRequest.getRequestURL(), String.join(", ", kafkaProperties.getBootstrapServers()));
        kafkaProducer.sendRequestToTopic(order);
    }
}