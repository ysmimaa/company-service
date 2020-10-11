package com.driver.ride.service;

import com.driver.ride.entity.Driver;

import java.util.List;

public interface DriverService {

    /**
     * Method that return the list of driver
     * @return a list of driver
     */
    List<Driver> getListOfDriver();

    /**
     * Method for creating a driver
     * @param driver
     * @return the driver created
     */
    Driver createDriver(Driver driver);
}
