package com.prueba.pedidos.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private int clientId;

    @Column(name = "client_name")
    private String clientName;

    @OneToMany(mappedBy = "clientId")
    private List<Orders> orders = new ArrayList<>();
}
