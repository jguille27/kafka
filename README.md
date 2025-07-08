## Descripcion
Prueba Tecnica - Procesamiento de Pedidos con Kafka y Spring Boot.
El Microservicio procesa pedidos enviados a traves de Apache Kafka. Este
servicio  expone un endpoint REST que permite registrar pedidos, enviarlos a un topic de Kafka, y luego consumirlos desde otro microservicio para validarlos y almacenarlos en una base de datos.

## Requisitos
* Java 21
* Spring boot 3.5.3
* Maven
* Intellij
* Kafka
* Docker
* Mysql
* Lombok
* Gson

## Configuración
### Configuracion de equipo
* Puertos utilizados:
  Servicio Consumer: 8090
  Servicio Producer: 8080
  Kafka:9092
  Base de datos:3306

# Uso
1. Ejecutar Producer
2. Ejecutar Consumer
3. Para probar desde postman
* Producer:
  http://localhost:8080/pedidos/orders

Ejemplo Body (Json)

{"orderId":"001",
"customerId":"1",
"items":[{
"productId":"P001",
"quantity":2}]}

* Consumer:
  http://localhost:8090/orders/1

## Changelog

A continuacion se muestra un registro de cambios que detalla las versiones del proyecto y las actualizaciones en cada version:

### [1.0.0] - YYYY-MM-DD (Pedidos)

- feat: SE CREA MICROSERVICIO QUE PROCESA LOS PEDIDOS MEDIANTE KAFKA.
