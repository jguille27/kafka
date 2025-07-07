package com.prueba.consumer.entity;

import com.prueba.consumer.model.OrderProduct;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    @Column(name = "orders_id")
    private Integer orderId;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client clientId;

    /*@OneToMany(mappedBy = "order")
    private Set<OrderProduct> orderProducts = new HashSet<>();*/

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();
}
