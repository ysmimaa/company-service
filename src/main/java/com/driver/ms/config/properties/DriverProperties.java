package com.driver.ms.config.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@PropertySource("classpath:application.properties")
@Configuration
public class DriverProperties {

    @Value("${ms.driver.title}")
    private String title;

    @Value("${ms.driver.description}")
    private String description;

    @Value("${ms.driver.version}")
    private String version;

    @Value("${ms.driver.group}")
    private String group;

    @Value("${ms.driver.package-to-scan}")
    private String packageToScan;

    @Value("${ms.driver.path}")
    private String path;
}
