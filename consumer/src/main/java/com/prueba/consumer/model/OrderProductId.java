package com.prueba.consumer.model;

import java.io.Serializable;

/*@Getter
@Setter
@ToString
@Embeddable*/
public class OrderProductId implements Serializable {
    private Integer orderId;
    private String productId;
}
