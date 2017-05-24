package com;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.softbistro.api.configuration.TwitterConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(TwitterConfiguration.class)
public class ApiServicesApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ApiServicesApplication.class, args);
	}
}
