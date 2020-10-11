package com.driver.ride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class DriverRideApplication {

	public static void main(String[] args) {
		SpringApplication.run(DriverRideApplication.class, args);
	}

}
