package com.driver.ms.service.impl;

import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.DriverService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Log4j2
@Service
public class DriverServiceImpl implements DriverService {

    private DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(final DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public List<Driver> getListOfDriver() {
        log.info("Fetch the list of the driver");
        return driverRepository.findAll();
    }

    @Override
    public Driver createDriver(Driver driver) {
        log.info("Create a new driver");
        if (driver != null) {
            Long driverId = driver.getId();
            if (driverId != null && findDriverById(driverId) != null) {
                log.debug("Driver {} already exist", driver);
                /***TO DO : ERROR HANDLING*/
                return null;
            }
            log.debug("Drive has been created");
            return driverRepository.save(driver);
        }
        throw new NullPointerException();
    }

    @Override
    public List<Driver> findByFirstname(String name) {
        return driverRepository.findByFirstname(name);
    }

    @Override
    public Driver updateDriver(Driver driver) {
        if (driver != null) {
            Long driverId = driver.getId();
            if (driverId != null) {
                log.debug("find the driver by id : {} ", driver.getId());
                Driver driverById = findDriverById(driverId);
                if (driverById != null) {
                    log.debug("The driver has been saved");
                    return driverRepository.save(driver);
                }
            }
        }
        /**TO DO : EXCEPTION HANDLING***/
        return null;
    }

    @Override
    public Driver findDriverById(Long id) {
        log.debug("Find driver by id : {} ", id);
        return driverRepository.findById(id).orElse(null);
    }

    @Override
    public Driver findDriverByPhone(String phone) {
        if (phone.isEmpty() || phone.isBlank()) {
            log.debug("The argument {} is not valid", phone);
            /**TO DO : EXCEPTION HANDLING***/
            throw new NullPointerException();
        }
        log.debug("Find driver by phone {} : ", phone);
        return driverRepository.findByPhone(phone);
    }

    @Override
    public Map<Journey, List<Driver>> getGroupedDriversByJourney() {
        return driverRepository.findAll().stream().collect(Collectors.groupingBy(Driver::getJourney));
    }

    @Async(value = "Pool1")
    @Override
    public void testPerformance() {
        RestTemplate restTemplate = new RestTemplate();
        List<Driver> drivers = new ArrayList<>();
        for (int i = 1; i < 10000; i++) {
            Driver retrievedDriver = restTemplate.getForObject("http://localhost:8081/driver/api/driver/1", Driver.class);
            drivers.add(retrievedDriver);
        }
        List<StringBuilder> outputDrivers = new ArrayList<>();
        for (Driver d : drivers) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(d.getFirstname());
            stringBuilder.append(" ; ");
            outputDrivers.add(stringBuilder);
        }

        outputDrivers.forEach(System.out::println);

    }

    public static List<StringBuilder> getData() {
        List<Driver> drivers = new ArrayList<>();
        for (long i = 1; i < 1000000; i++) {
            Driver driver = Driver.builder().id(i).firstname("firstname" + i).build();
            drivers.add(driver);
        }
        List<StringBuilder> outputDrivers = new ArrayList<>();
        for (Driver d : drivers) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" ** ");
            stringBuilder.append(d.getFirstname());
            stringBuilder.append(" ; ");
            outputDrivers.add(stringBuilder);
        }
        outputDrivers.forEach(System.out::println);

        return outputDrivers;

    }

    @Override
    public Driver deleteDriverById(Long id) {
        if (id != null) {
            Driver driverById = findDriverById(id);
            if (driverById != null)
                driverRepository.delete(driverById);
            return driverById;
        }
        return null;
    }
}
