package com.prueba.productor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class ProductorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductorApplication.class, args);
	}

}
