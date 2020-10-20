package com.driver.ms.service.impl;

import com.driver.ms.entity.ContractType;
import com.driver.ms.entity.Driver;
import com.driver.ms.repository.DriverRepository;
import com.driver.ms.service.DriverService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
                log.debug("Driver already exist");
                /***TO DO : ERROR HANDLING*/
                return null;
            }
            log.debug("Drive has been created");
            return driverRepository.save(driver);
        }
        return null;
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
                Driver driverById = findDriverById(driverId);
                if (driverById != null) {
                    return driverRepository.save(driverById);
                }
            }
        }
        /**TO DO : EXCEPTION HANDLING***/
        return null;
    }

    @Override
    public Driver findDriverById(Long id) {
        return driverRepository.findById(id).orElse(null);
    }
}
