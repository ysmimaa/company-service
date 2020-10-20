package com.driver.ms.repository;

import com.driver.ms.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    /**
     * Method that fetch the searched drivers from the database
     *
     * @param firstname
     * @param contractType
     * @return list of drivers found
     */
    //@Query("from Driver d where d.firstname=:firstname and d.contract_type=:#{#contractType} ")
    List<Driver> findByFirstname(@Param("firstname") String name);
}
