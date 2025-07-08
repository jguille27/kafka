package com.prueba.productor.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;

class OrdersProducerTest {

    private KafkaTemplate<String, String> kafkaTemplate;
    private KafkaProperties kafkaProperties;
    private OrdersProducer ordersProducer;

    @BeforeEach
    void setUp() {
        kafkaTemplate = mock(KafkaTemplate.class);
        kafkaProperties = mock(KafkaProperties.class);
        when(kafkaProperties.getBootstrapServers()).thenReturn(List.of("localhost:9092"));
        ordersProducer = new OrdersProducer(kafkaTemplate, kafkaProperties);
        try {
            java.lang.reflect.Field field = OrdersProducer.class.getDeclaredField("producerTopic");
            field.setAccessible(true);
            field.set(ordersProducer, "orders");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSendRequestToTopic() {
        String request = "mensaje de prueba";
        ordersProducer.sendRequestToTopic(request);
        verify(kafkaTemplate, times(1)).send("orders", request);
    }
}