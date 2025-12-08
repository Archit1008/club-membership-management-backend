package com.hcl.guvi.capstonproject.eventmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class EventmanagementApplication {
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementApplication.class, args);
	}

}
