package com.example.take5.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan("com.baeldung.persistence.model")
@SpringBootApplication
public class Take5Application {

	public static void main(String[] args) {
		SpringApplication.run(Take5Application.class, args);
	}

}
