package com.HMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com")
public class HmsInSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(HmsInSpringBootApplication.class, args);
	}

}
