package com.driver.ride.service.impl;

import com.driver.ride.repository.DriverRepository;
import com.driver.ride.entity.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.AssertionErrors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DriverServiceImplUTest {

    @InjectMocks
    private DriverServiceImpl userService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_return_the_list_of_employee() {
        Mockito.when(driverRepository.findAll()).thenReturn(Arrays.asList(
                Driver.builder()
                        .id(1L)
                        .firstname("firstname1")
                        .build(),
                Driver.builder()
                        .id(2L)
                        .firstname("firstname2")
                        .build())
        );

        List<Driver> listOfDriver = userService.getListOfDriver();

        AssertionErrors.assertEquals("List of user not empty",2, listOfDriver.size());
    }

    @Test
    void should_return_an_empty_list_of_employee() {
        Mockito.when(driverRepository.findAll()).thenReturn(new ArrayList<>());
        List<Driver> listOfDriver = userService.getListOfDriver();
        AssertionErrors.assertEquals("An empty list of user",0, listOfDriver.size());
    }

}