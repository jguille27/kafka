package com.prueba.consumer.service;

import com.prueba.consumer.entity.Client;
import com.prueba.consumer.entity.Orders;
import com.prueba.consumer.entity.Product;
import com.prueba.consumer.kafka.ErrorProducer;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.repository.ClientRepository;
import com.prueba.consumer.repository.OrdersRepository;
import com.prueba.consumer.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KafkaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaService.class);

    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final ErrorProducer errorProducer;

    @Autowired
    public KafkaService(ClientRepository clientRepository, ErrorProducer errorProducer, ProductRepository productRepository, OrdersRepository ordersRepository) {
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.ordersRepository = ordersRepository;
        this.errorProducer = errorProducer;
    }

    public void processRequest(OrderVO orderVO, String orderJson) {
        final String LogHead = "processRequest:";
        if (isValidOrder(orderVO, orderJson)) {
            List<Product> products = new ArrayList<>();
            boolean productNotFound = false;
            Orders order = ordersRepository.findById(orderVO.getOrderId()).orElse(new Orders());
            if (order.getOrderId()!=null) {
                LOGGER.warn("{} Order {} already exists", LogHead, orderVO.getOrderId());
                errorProducer.sendRequestToTopic(orderJson + ": OrderId " + orderVO.getOrderId() + " already exists");
            } else {
                Client client = clientRepository.findById(orderVO.getCustomerId()).orElse(null);
                if (client == null) {
                    LOGGER.warn("{} Client {} not found", LogHead, orderVO.getCustomerId());
                    errorProducer.sendRequestToTopic(orderJson + ": Client " + orderVO.getCustomerId() + " not found");
                } else {
                    for (OrderVO.Item i : orderVO.getItems()) {
                        Product p = productRepository.findById(i.getProductId()).orElse(null);
                        if (p != null)
                            products.add(p);
                        else {
                            productNotFound = true;
                            LOGGER.warn("{} Item: " + i.getProductId() + "] not found", LogHead);
                            errorProducer.sendRequestToTopic(orderJson + ": Item " + i.getProductId() + " not found");
                        }
                    }
                    if (!productNotFound) {
                        order.setOrderId(orderVO.getOrderId());
                        order.setClientId(client);
                        //order.setProducts(products);
                        //products.forEach(p -> order.getProducts().add(p));
                        ordersRepository.save(order);
                    }
                }
            }
        }
    }

    private boolean isValidOrder(OrderVO order, String orderJson) {
        final String LogHead = "isValidOrder:";
        if (order.getCustomerId()==null || order.getCustomerId() < 1){
            LOGGER.warn("{} Invalid CustomerId",LogHead);
            errorProducer.sendRequestToTopic(orderJson + ": Invalid CustomerId value");
            return false;
        }
        if (order.getOrderId()==null || order.getOrderId() < 1){
            LOGGER.warn("{} Invalid OrderId",LogHead);
            errorProducer.sendRequestToTopic(orderJson + ": Invalid OrderId value");
            return false;
        }
        if (order.getItems()==null || order.getItems().isEmpty()){
            LOGGER.warn("{} Invalid Items", LogHead);
            errorProducer.sendRequestToTopic(orderJson + ": Invalid Items value");
            return false;
        }
        return true;
    }
}