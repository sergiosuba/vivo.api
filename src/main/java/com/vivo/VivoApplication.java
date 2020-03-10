package com.vivo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VivoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VivoApplication.class, args);
	}

}
