package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class IprpgnsrvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(IprpgnsrvcApplication.class, args);
	}
}
