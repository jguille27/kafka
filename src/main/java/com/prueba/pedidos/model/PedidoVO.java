package com.prueba.pedidos.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PedidoVO {

    @JsonProperty("orderId")
    private String orderId;

    @JsonProperty("customerId")
    private String customerId;
    private List<Item> items = new ArrayList<>();

    @Getter
    @Setter
    @ToString
    class Item {
        String productId;
        int quantity;
    }
}