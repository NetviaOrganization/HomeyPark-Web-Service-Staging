package com.homeypark.web_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HomeyparkApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeyparkApplication.class, args);
	}
}