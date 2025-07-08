package com.prueba.consumer.repository;

import com.prueba.consumer.entity.Orders;
import com.prueba.consumer.model.OrderVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {

    @Query(value = "select * from orders where order_id = ?", nativeQuery = true)
    OrderVO getOrderById(Integer orderId);
}
