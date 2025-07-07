package com.prueba.consumer.controller;

import com.prueba.consumer.entity.Orders;
import com.prueba.consumer.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrders(@RequestParam("orderId") int orderId){
        Orders orders = ordersRepository.findById(orderId).orElse(new Orders());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
