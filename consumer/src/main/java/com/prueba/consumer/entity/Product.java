package com.prueba.consumer.entity;

import com.prueba.consumer.model.OrderProduct;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    /*@OneToMany(mappedBy = "product")
    private Set<OrderProduct> orderProducts = new HashSet<>();*/

    @ManyToMany
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Orders> orders = new HashSet<>();
}
