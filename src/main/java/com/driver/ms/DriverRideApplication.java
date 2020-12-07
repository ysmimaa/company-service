package com.driver.ms;

import com.driver.ms.entity.ContractType;
import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.service.DriverService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableConfigurationProperties
@SpringBootApplication
public class DriverRideApplication {

    public static void main(String[] args) {
        SpringApplication.run(DriverRideApplication.class, args);
    }

    /*@Bean
    CommandLineRunner runner(DriverService driverService) {
        return args -> Stream.of(
                Driver.builder()
                        .firstname("youssef")
                        .lastname("smimaa")
                        .phone("063232364")
                        .contractType(ContractType.CDI)
                        .hiredDate(LocalDateTime.now())
                        .build()
                ,
                Driver.builder()
                        .firstname("AZIZ")
                        .lastname("SMIMAA")
                        .phone("063232364")
                        .contractType(ContractType.CDI)
                        .build(),Driver.builder()
                        .firstname("youssef")
                        .lastname("smimaa")
                        .phone("063232364")
                        .contractType(ContractType.CDI)
                        .hiredDate(LocalDateTime.now())
                        .build()
                ,
                Driver.builder()
                        .firstname("AZIZ")
                        .lastname("SMIMAA")
                        .phone("063232364")
                        .contractType(ContractType.CDI)
                        .build(),Driver.builder()
                        .firstname("youssef")
                        .lastname("smimaa")
                        .phone("063232364")
                        .contractType(ContractType.CDI)
                        .hiredDate(LocalDateTime.now())
                        .build()
                ,
                Driver.builder()
                        .firstname("AZIZ")
                        .lastname("SMIMAA")
                        .phone("063232364")
                        .contractType(ContractType.CDI)
                        .hiredDate(LocalDateTime.now())
                        .build()
        ).forEach(driverService::createDriver);

    }*/

}
