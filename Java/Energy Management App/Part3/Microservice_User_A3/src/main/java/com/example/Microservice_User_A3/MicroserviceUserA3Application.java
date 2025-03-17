package com.example.Microservice_User_A3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})

public class MicroserviceUserA3Application {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceUserA3Application.class, args);
	}

}
