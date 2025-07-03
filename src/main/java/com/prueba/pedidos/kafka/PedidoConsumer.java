package com.prueba.pedidos.kafka;

import com.google.gson.Gson;
import com.prueba.pedidos.model.PedidoVO;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PedidoConsumer {

    @KafkaListener(
            topics = "#{'${pedido.kafka.consumer.topic}'}",
            groupId = "pedidosKafka",
            id = "pedidos-consumer",
            clientIdPrefix = "pedidos-consumer-client",
            autoStartup = "#{'${pedido.kafka.consumer.autoStartup}'}"
    )
    public void procesarPedido(String pedidoJson){
        Gson gson = new Gson();
        PedidoVO pedidoVO = null;
        try {
            pedidoVO = gson.fromJson(pedidoJson, PedidoVO.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(pedidoJson);
    }
}


// {"orderId":"123","customerId":"C123","items":[{"productId":"P001","quantity":2}]}