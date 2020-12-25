package com.journey.service.ms;

import com.journey.service.ms.entity.Journey;
import com.journey.service.ms.service.JourneyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@EnableDiscoveryClient
@EnableEurekaClient
@SpringBootApplication
public class JourneyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JourneyServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(JourneyService journeyService) {
        return journey -> Stream.of(
                Journey.builder()
                        .nbrOfPlaces(22)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().minusDays(1L))
                        .build(),
                Journey.builder()
                        .nbrOfPlaces(12)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().minusDays(1L))
                        .build(),
                Journey.builder()
                        .nbrOfPlaces(20)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().minusDays(1L))
                        .build())
                .forEach(journeyService::create);
    }

}
