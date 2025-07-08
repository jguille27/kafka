package com.prueba.productor.controller;

import com.prueba.productor.service.OrdersProducer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductorController.class)
class ProductorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdersProducer ordersProducer;

    @MockBean
    private KafkaProperties kafkaProperties;

    @Test
    void registerOrder_shouldCallProducerAndReturn200() throws Exception {
        String orderJson = "{\"orderId\": 1, \"customerId\": 100, \"items\": [{\"productId\": 200}]}";
        mockMvc.perform(post("/pedidos/orders")
                        .contentType("application/json")
                        .content(orderJson))
                .andExpect(status().isOk());
        verify(ordersProducer).sendRequestToTopic(orderJson);
    }
}