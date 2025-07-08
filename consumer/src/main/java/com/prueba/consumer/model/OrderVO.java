package com.prueba.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class OrderVO {

    @JsonProperty("orderId")
    private Integer orderId;

    @JsonProperty("customerId")
    private Integer customerId;
    private List<Item> items = new ArrayList<>();

}