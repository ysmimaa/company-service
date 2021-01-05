package com.company.service.ms.feign;

import com.company.service.ms.entity.Driver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "proxy-service/driver-service")
public interface DriverFeignClient {

    @GetMapping(path = "/driver/findById/{driverId}")
    Optional<Driver> getDriverById(@PathVariable(name = "driverId") Long driverId);

    @GetMapping(path = "/drivers/findByIds/{driversToBeAdded}")
    List<Driver> getListOfDriversById(@PathVariable(name = "driversToBeAdded") List<Driver> driversToBeAdded);

    @GetMapping(path = "/drivers/byCompanyId/{companyId}")
    List<Driver> getListOfDriverByCompanyId(@PathVariable(name = "companyId") Long companyId);
}
