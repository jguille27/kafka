package com.prueba.consumer.entity;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductId implements Serializable {
    private Integer orderId;
    private String productId;
}
