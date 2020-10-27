package com.driver.ms.service.impl;

import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.DriverService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
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
                    return driverRepository.save(driverById);
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
}
