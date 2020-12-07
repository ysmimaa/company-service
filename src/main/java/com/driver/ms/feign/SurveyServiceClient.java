package com.driver.ms.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "survey-service")
public interface SurveyServiceClient {

    @GetMapping(path = "/api/survey")
    String getSurveyInfo();
}
