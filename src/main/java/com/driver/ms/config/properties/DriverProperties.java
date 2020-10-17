package com.driver.ms.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@PropertySource("classpath:bootstrap.properties")
@Configuration
public class DriverProperties {

    @Value("${ms.driver.title}")
    private String title;

    @Value("${ms.driver.description}")
    private String description;

    @Value("${ms.driver.version}")
    private String version;

}
