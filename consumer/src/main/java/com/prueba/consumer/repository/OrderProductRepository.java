package com.prueba.consumer.repository;

import com.prueba.consumer.entity.OrderProduct;
import com.prueba.consumer.entity.OrderProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductId> {
}
