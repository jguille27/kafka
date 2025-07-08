package com.prueba.consumer.controller;

import com.prueba.consumer.entity.Client;
import com.prueba.consumer.entity.OrderProduct;
import com.prueba.consumer.entity.Orders;
import com.prueba.consumer.entity.Product;
import com.prueba.consumer.model.Item;
import com.prueba.consumer.model.OrderVO;
import com.prueba.consumer.repository.OrdersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrdersControllerTest {

    private OrdersRepository ordersRepository;
    private OrdersController ordersController;

    @BeforeEach
    void setup() {
        ordersRepository = mock(OrdersRepository.class);
        ordersController = new OrdersController(ordersRepository);
    }

    @Test
    void getOrders_whenOrderExists_shouldReturnOrderVO() {
        int orderId = 1;
        // Preparar objetos para simular la entidad Orders con sus relaciones
        Client client = new Client();
        client.setClientId(10);
        Product product1 = new Product();
        product1.setProductId("100");
        OrderProduct orderProduct1 = new OrderProduct();
        orderProduct1.setProduct(product1);
        orderProduct1.setQuantity(5);
        Orders order = new Orders();
        order.setOrderId(orderId);
        order.setClient(client);
        order.setOrderProducts(Set.of(orderProduct1));

        // Mockear repositorio para devolver Optional con la orden
        when(ordersRepository.findById(orderId)).thenReturn(Optional.of(order));
        // Ejecutar método
        ResponseEntity<OrderVO> response = ordersController.getOrders(orderId);
        // Verificar resultados
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        OrderVO orderVO = response.getBody();
        assertEquals(orderId, orderVO.getOrderId());
        assertEquals(10, orderVO.getCustomerId());

        List<Item> items = orderVO.getItems();
        assertEquals(1, items.size());
        assertEquals("100", items.get(0).getProductId());
        assertEquals(5, items.get(0).getQuantity());

        // Verificar interacción con repositorio
        verify(ordersRepository, times(1)).findById(orderId);
    }

    @Test
    void getOrders_whenOrderDoesNotExist_shouldReturnNotFound() {
        int orderId = 1;
        when(ordersRepository.findById(orderId)).thenReturn(Optional.empty());
        ResponseEntity<OrderVO> response = ordersController.getOrders(orderId);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(ordersRepository, times(1)).findById(orderId);
    }
}