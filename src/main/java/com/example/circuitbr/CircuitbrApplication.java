package com.example.circuitbr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CircuitbrApplication {

	public static void main(String[] args) {
		SpringApplication.run(CircuitbrApplication.class, args);
	}

}
