package com.prueba.consumer.service;

import com.prueba.consumer.entity.*;
import com.prueba.consumer.kafka.ErrorProducer;
import com.prueba.consumer.model.Item;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.repository.ClientRepository;
import com.prueba.consumer.repository.OrderProductRepository;
import com.prueba.consumer.repository.OrdersRepository;
import com.prueba.consumer.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class KafkaServiceTest {

    private ClientRepository clientRepository;
    private ProductRepository productRepository;
    private OrdersRepository ordersRepository;
    private ErrorProducer errorProducer;
    private OrderProductRepository orderProductRepository;
    private KafkaService kafkaService;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        productRepository = mock(ProductRepository.class);
        ordersRepository = mock(OrdersRepository.class);
        errorProducer = mock(ErrorProducer.class);
        orderProductRepository = mock(OrderProductRepository.class);
        kafkaService = new KafkaService(clientRepository, errorProducer, productRepository, ordersRepository, orderProductRepository);
    }

    @Test
    void processRequest_validOrder_savesOrderAndOrderProducts() {
        int orderId = 1;
        int clientId = 10;
        String productId = "100";
        int quantity = 5;

        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setCustomerId(clientId);
        orderVO.setItems(List.of(new Item(productId, quantity)));
        String orderJson = "{\"orderId\":1,\"customerId\":10,\"items\":[{\"productId\":100,\"quantity\":5}]}";

        when(ordersRepository.findById(orderId)).thenReturn(Optional.empty());

        Client client = new Client();
        client.setClientId(clientId);
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        kafkaService.processRequest(orderVO, orderJson);

        verify(ordersRepository, times(1)).save(any(Orders.class));
        verify(orderProductRepository, times(1)).save(any(OrderProduct.class));
        verify(errorProducer, never()).sendRequestToTopic(anyString());
    }

    @Test
    void processRequest_orderAlreadyExists_sendsErrorAndDoesNotSave() {
        int orderId = 1;
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderId(orderId);
        orderVO.setCustomerId(10);
        orderVO.setItems(List.of(new Item("100", 1)));
        String orderJson = "{\"orderId\":1,\"customerId\":10,\"items\":[{\"productId\":100,\"quantity\":1}]}";

        // Simular que la orden ya existe
        Orders existingOrder = new Orders();
        existingOrder.setOrderId(orderId);
        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));

        kafkaService.processRequest(orderVO, orderJson);

        verify(errorProducer, times(1))
                .sendRequestToTopic(contains("already exists"));
        verify(ordersRepository, never()).save(any());
        verify(orderProductRepository, never()).save(any());
    }

}