package com.driver.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableConfigurationProperties
@SpringBootApplication
public class DriverRideApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverRideApplication.class, args);
	}

}
