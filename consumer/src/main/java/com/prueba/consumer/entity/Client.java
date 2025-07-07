package com.prueba.consumer.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @Column(name = "client_id")
    private int clientId;

    @Column(name = "client_name")
    private String clientName;

    @OneToMany(mappedBy = "clientId")
    private Set<Orders> orders = new HashSet<>();
}
