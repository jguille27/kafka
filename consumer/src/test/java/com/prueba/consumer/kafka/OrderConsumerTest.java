package com.prueba.consumer.kafka;

import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.service.KafkaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class OrderConsumerTest {

    private KafkaService kafkaService;
    private OrderConsumer orderConsumer;

    @BeforeEach
    void setUp() {
        kafkaService = mock(KafkaService.class);
        orderConsumer = new OrderConsumer(kafkaService);
    }

    @Test
    void processJson_validJson_callsKafkaService() {
        // Preparar JSON válido
        String json = "{\"orderId\":1,\"customerId\":2,\"items\":[{\"productId\":100,\"quantity\":3}]}";
        // Ejecutar método
        orderConsumer.processJson(json);
        // Verificar que se llama a kafkaService con datos parseados
        verify(kafkaService, times(1)).processRequest(any(OrderVO.class), eq(json));
    }

    @Test
    void processJson_invalidJson_shouldNotThrow() {
        // JSON mal formado
        String invalidJson = "{orderId:1, BAD_JSON}";
        // Ejecutar (no debe lanzar excepción)
        orderConsumer.processJson(invalidJson);
        // Verificar que NO se llama a kafkaService porque falló el parseo
        verify(kafkaService, never()).processRequest(any(), any());
    }
}