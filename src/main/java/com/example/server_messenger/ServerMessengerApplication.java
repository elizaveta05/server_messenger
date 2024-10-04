package com.example.server_messenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.server_messenger") 
public class ServerMessengerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerMessengerApplication.class, args);
	}

}
