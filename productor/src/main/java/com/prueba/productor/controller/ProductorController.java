package com.prueba.productor.controller;

import com.prueba.productor.service.OrdersProducer;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("pedidos")
public class ProductorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductorController.class);
    private final OrdersProducer kafkaProducer;

    @Autowired
    public ProductorController(OrdersProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/orders")
    public void registerOrder(HttpServletRequest servletRequest, @RequestBody String order){
        LOGGER.info("{} New request: {}",servletRequest.getRequestURL(),order);
        kafkaProducer.sendRequestToTopic(order);
    }
}

// curl --header "Content-Type: application/json" --request POST --data '{"orderId":"123","customerId":"C123","items":[{"productId":"P001","quantity":2}]}' http://localhost:8080/pedidos/orders