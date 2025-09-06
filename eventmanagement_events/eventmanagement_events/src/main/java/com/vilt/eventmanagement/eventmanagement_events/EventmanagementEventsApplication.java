package com.vilt.eventmanagement.eventmanagement_events;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class EventmanagementEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventmanagementEventsApplication.class, args);
	}

}
