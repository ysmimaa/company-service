package com.driver.ms.resource;

import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.entity.Driver;
import com.driver.ms.service.DriverService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static com.driver.ms.common.constant.CommonConstant.DRIVER_URL_BASE;

@CrossOrigin(origins = {"http://localhost:4200"})
@Tag(name = "Driver", description = "Driver's APIs")
@RestController
@RequestMapping(DRIVER_URL_BASE)
public class DriverRestController {

    private DriverService driverService;

    @Autowired
    public DriverRestController(final DriverService driverService) {
        this.driverService = driverService;
    }

    @Operation(method = "Get", description = "Fetch the list of driver", tags = "Driver")
    @ApiResponse(responseCode = "200",
            description = "List of driver",
            content = @Content(schema = @Schema(implementation = Driver.class)))
    @GetMapping(DriverConstant.DRIVERS)
    public ResponseEntity<List<Driver>> getDrivers() {
        List<Driver> listOfDriver = driverService.getListOfDriver();
        if (!CollectionUtils.isEmpty(listOfDriver)) {
            return new ResponseEntity<>(listOfDriver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(method = "Get", description = "Fetch the list of drivers based on the search criteria")
    @ApiResponse(responseCode = "200",
            description = "Return a list of drivers based on the filter criteria",
            content = @Content(schema = @Schema(implementation = Driver.class)))
    @GetMapping(DriverConstant.DRIVER_ADVANCED_SEARCH)
    public ResponseEntity<List<Driver>> getDriversByFirstnameOrContractType(@RequestParam("firstname") String firstname) {
        if (firstname.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Driver> drivers = driverService.findByFirstname(firstname);
        if (CollectionUtils.isEmpty(drivers)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(drivers, HttpStatus.OK);
    }

    @GetMapping(path = "driver/{id}")
    public ResponseEntity<Driver> getDriver(@PathVariable(name = "id") Long id) {
        if (id != null) {
            return new ResponseEntity<>(driverService.findDriverById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "driver/{id}")
    public ResponseEntity<Driver> deleteDriver(@PathVariable(name = "id") Long id) {
        if (id != null) {
            return new ResponseEntity<>(driverService.deleteDriverById(id), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = DriverConstant.CREATE_DRIVER)
    public ResponseEntity<Driver> createDriver(@RequestBody Driver driver) {
        if (driver != null) {
            Driver createdDriver = driverService.createDriver(driver);
            return new ResponseEntity<>(createdDriver, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = DriverConstant.UPDATE_DRIVER)
    public ResponseEntity<Driver> updateDriver(@RequestBody Driver driver) {
        if (driver != null) {
            Driver updatedDriver = driverService.updateDriver(driver);
            return new ResponseEntity<>(updatedDriver, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("fireUp")
    public void fireUp() {
        Instant startTime = Instant.now();
        driverService.testPerformance();
        Instant endTime = Instant.now();
        Duration duration = Duration.between(startTime, endTime);
        System.out.println(duration.getSeconds());
    }

    @GetMapping(path = DriverConstant.BASIC_AUTH)
    public ResponseEntity<Boolean> getAuth() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping(path = "exception")
    public String getException() {
        throw new RuntimeException("Testing the exception behavior");
    }
}
