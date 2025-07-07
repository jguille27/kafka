FROM openjdk:21
ADD target/orders-consumer.jar cliente.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/cliente.jar"]