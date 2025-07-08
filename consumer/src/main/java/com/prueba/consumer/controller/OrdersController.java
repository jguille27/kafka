package com.prueba.consumer.controller;

import com.prueba.consumer.entity.Orders;
import com.prueba.consumer.model.Item;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.repository.OrdersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersController.class);
    private static final String LOG_PREFIX = "OrdersController::";
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderVO> getOrders(@PathVariable int orderId){
        String LogHead = LOG_PREFIX + "getOrders:";
        LOGGER.info("{}get orders with id {}",LogHead, orderId);
        Orders orders = ordersRepository.findById(orderId).orElse(null);
        if (orders!= null) {
            OrderVO orderVO = new OrderVO();
            orderVO.setOrderId(orders.getOrderId());
            orderVO.setCustomerId(orders.getClient().getClientId());
            List<Item> items = orders.getOrderProducts().stream()
                    .map(o -> new Item(o.getProduct().getProductId(), o.getQuantity()))
                    .collect(Collectors.toList());
            orderVO.setItems(items);
            LOGGER.info("{}orderVO: {}",LogHead, orderVO);
            return new ResponseEntity<>(orderVO, HttpStatus.OK);
        } else {
            LOGGER.info("{}order not found",LogHead);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
