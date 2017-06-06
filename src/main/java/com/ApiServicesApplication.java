package com;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class ApiServicesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApiServicesApplication.class, args);
	}
}
