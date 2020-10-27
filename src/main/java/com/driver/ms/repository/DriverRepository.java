package com.driver.ms.repository;

import com.driver.ms.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    /**
     * Method that fetch the searched drivers from the database
     * @param firstname
     * @return list of drivers found
     */
    List<Driver> findByFirstname(@Param("firstname") String firstname);

    /**
     * Method for retrieving the driver by the phone search criteria
     * @param phone
     * @return a matching driver
     */
    Driver findByPhone(@Param("phone") String phone);
}
