package com.driver.ms.resource;

import com.driver.ms.service.DriverService;
import com.driver.ms.common.constant.DriverConstant;
import com.driver.ms.entity.Driver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Driver", description = "Driver's APIs")
@RestController
@RequestMapping(DriverConstant.DRIVER_URL_BASE)
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
}
