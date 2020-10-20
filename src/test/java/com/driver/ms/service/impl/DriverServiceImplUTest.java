package com.driver.ms.service.impl;

import com.driver.ms.entity.Driver;
import com.driver.ms.repository.DriverRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

class DriverServiceImplUTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @DisplayName("Given : waiting for the list of drivers")
    @Nested
    class DisplayDriversTest {

        @DisplayName("When invoking the find all drivers service")
        @Nested
        class AllDriversTest {
            @Test
            @DisplayName(value = "Then you display all drivers")
            void should_return_the_list_of_drivers() {
                when(driverRepository.findAll()).thenReturn(Arrays.asList(
                        Driver.builder()
                                .id(1L)
                                .firstname("firstname1")
                                .build(),
                        Driver.builder()
                                .id(2L)
                                .firstname("firstname2")
                                .build())
                );

                List<Driver> listOfDriver = driverService.getListOfDriver();

                Assertions.assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals("List of user not empty", 2, listOfDriver.size()));
            }

            @Test
            @DisplayName(value = "Then display an empty list of drivers")
            void should_return_an_empty_list_of_drivers() {
                when(driverRepository.findAll()).thenReturn(new ArrayList<>());
                List<Driver> listOfDriver = driverService.getListOfDriver();
                Assertions.assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals("An empty list of user", 0, listOfDriver.size()));

            }
        }

        @DisplayName("When invoking the find drivers by criteria service")
        @Nested
        class DriversBySearchCriteria {
            @Test
            @DisplayName(value = "Should return a driver based on the search criteria")
            void should_return_a_list_of_driver_based_on_a_search_criteria() {
                List<Driver> driversList = Arrays.asList(
                        Driver.builder()
                                .id(1L)
                                .firstname("firstname1")
                                .build(),
                        Driver.builder()
                                .id(2L)
                                .firstname("firstname2")
                                .build(),
                        Driver.builder()
                                .id(3L)
                                .firstname("firstname3")
                                .build(),
                        Driver.builder()
                                .id(4L)
                                .firstname("firstname4")
                                .build());
                when(driverRepository.findByFirstname(null)).thenReturn(driversList);
                List<Driver> listOfDriver = driverService.findByFirstname(null);
                Assertions.assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals("An empty list of user", 4, listOfDriver.size()));
            }
        }

        @Nested
        @DisplayName("Given a new driver")
        class AddNewDriver {

            @Nested
            @DisplayName("When driver passe a new driver")
            class NewDriver {
                @Test
                void should_create_a_new_driver() {
                    Driver driver = Driver.builder()
                            .id(1L)
                            .firstname("driver1")
                            .phone("062323236")
                            .build();

                    when(driverRepository.save(any(Driver.class))).thenReturn(driver);

                    Driver createdDriver = driverService.createDriver(driver);
                    Assertions.assertAll("Check condition",
                            () -> assertEquals("A new driver has been created", driver, createdDriver));
                }
            }
        }

        @DisplayName("Given a new existing driver")
        @Nested
        class AddNewExistingDriver {

            @Nested
            @DisplayName("When driver passed is a new existing driver")
            class NewDriver {

                @DisplayName("Then the new existing driver must be rejected")
                @Test
                void should_reject_a_new_existing_driver() {
                    Driver driver = Driver.builder()
                            .id(1L)
                            .firstname("driver1")
                            .phone("062323236")
                            .build();

                    when(driverRepository.save(any(Driver.class))).thenReturn(driver);
                    when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
                    Driver createdDriver = driverService.createDriver(driver);
                    Assertions.assertAll("Check condition",
                            () -> assertNull("The new driver already exists", createdDriver));
                }
            }
        }

        @Nested
        @DisplayName("Given a driver to be updated")
        class UpdateDriver {

            @Nested
            @DisplayName("When driver passe a new driver")
            class DriverToBeUpdated {
                @Test
                void should_update_a_driver() {
                    Driver driver = Driver.builder()
                            .id(1L)
                            .firstname("driver1")
                            .phone("062323236")
                            .build();
                    when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
                    when(driverRepository.save(any(Driver.class))).thenReturn(driver);

                    Driver updatedDriver = driverService.updateDriver(driver);
                    Assertions.assertAll("Check condition",
                            () -> assertEquals("A driver has been updated", driver, updatedDriver));
                }
            }
        }
    }
}