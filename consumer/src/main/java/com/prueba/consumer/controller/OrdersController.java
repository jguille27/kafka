package com.prueba.consumer.controller;

import com.prueba.consumer.entity.Orders;
import com.prueba.consumer.model.Item;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrdersController {

    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderVO> getOrders(@PathVariable int orderId){
        /*OrderVO orders = ordersRepository.getOrderById(orderId);
        return new ResponseEntity<>(orders, HttpStatus.OK);*/
        Orders orders = ordersRepository.findById(orderId).orElse(null);
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orders.getOrderId());
        orderVO.setCustomerId(orders.getClient().getClientId());
        List<Item> items = orders.getOrderProducts().stream()
                .map(o -> new Item(o.getProduct().getProductId(),o.getQuantity()))
                        .collect(Collectors.toList());
        orderVO.setItems(items);
        return new ResponseEntity<>(orderVO, HttpStatus.OK);
    }
}
