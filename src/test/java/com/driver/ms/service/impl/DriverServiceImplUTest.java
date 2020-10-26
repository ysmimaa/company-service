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
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNull;

@DisplayName(" **** Driver services **** ")
class DriverServiceImplUTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverRepository driverRepository;

    @BeforeEach
    void init() {
        initMocks(this);
    }

    @DisplayName("Given the list of drivers")
    @Nested
    class DisplayAllDriversTest {

        @DisplayName("When invoking the find all drivers service")
        @Nested
        class AllDriversTest {
            @Test
            @DisplayName(value = "Then display all drivers available in the list ")
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
                        () -> assertEquals("List of user not empty", 2, listOfDriver.size()),
                        () -> Assertions.assertTrue(listOfDriver.stream().anyMatch(driver -> driver.getId().equals(1L)), "The list contains the driver %s")
                );

            }

            @Test
            @DisplayName(value = "Then display an empty list if there is none")
            void should_return_an_empty_list_of_drivers() {
                when(driverRepository.findAll()).thenReturn(new ArrayList<>());
                List<Driver> listOfDriver = driverService.getListOfDriver();
                Assertions.assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals("An empty list of user", 0, listOfDriver.size()),
                        () -> Assertions.assertTrue(listOfDriver.isEmpty(), "List is empty"));
                verify(driverRepository, times(1)).findAll();

            }
        }
    }

    @DisplayName("Given find drivers by criteria service")
    @Nested
    class DisplayDriversBySearchCriteria {

        @DisplayName("When passing the firstname criteria ")
        @Nested
        class DriverByFirstname {

            @Test
            @DisplayName(value = "Display drivers based on the search criteria available in the list")
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
                when(driverRepository.findByFirstname(anyString())).thenReturn(driversList);
                List<Driver> listOfDriver = driverService.findByFirstname("firstname1");
                Assertions.assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals("An empty list of user", 4, listOfDriver.size()));
                org.assertj.core.api.Assertions.assertThat(listOfDriver).contains(driversList.get(0));

                verify(driverRepository, times(1)).findByFirstname(anyString());
            }
        }
    }

    @Nested
    @DisplayName("Given a new driver")
    class AddNewDriver {

        @Nested
        @DisplayName("When user passes a new driver")
        class NewDriver {

            @DisplayName("Then a new driver should be created and added to the list")
            @Test
            void should_create_a_new_driver() {
                Driver driver = Driver.builder()
                        .id(1L)
                        .firstname("driver1")
                        .phone("062323236")
                        .build();

                when(driverRepository.save(any(Driver.class))).thenReturn(driver);

                Driver createdDriver = driverService.createDriver(driver);

                Assertions.assertAll("Check conditions",
                        () -> assertEquals("A new driver has been created", driver, createdDriver));
                verify(driverRepository, times(1)).save(any(Driver.class));
            }
        }

        @DisplayName("When user passes an invalid driver")
        @Nested
        class AddInvalidDriverThrowsException {

            @DisplayName("Then throw an exception")
            @Test
            void should_throw_exception_when_trying_to_add_an_empty_or_invalid_driver() {
                Driver driver = driverService.createDriver(null);
                Assertions.fail("should throw an exception");
            }
        }
    }

    @DisplayName("Given a new existing driver")
    @Nested
    class AddNewExistingDriver {

        @Nested
        @DisplayName("When the user passes a new existing driver")
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

                verify(driverRepository, times(1)).findById(anyLong());
                verify(driverRepository, times(0)).save(any(Driver.class));
            }
        }
    }

    @Nested
    @DisplayName("Given a driver to be updated")
    class UpdateDriver {
        @Nested
        @DisplayName("When the user passes a new driver for updating the existing one")
        class DriverToBeUpdated {

            @DisplayName("Then the existing driver should be updated")
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

                verify(driverRepository, times(1)).findById(anyLong());
                verify(driverRepository, times(1)).save(any(Driver.class));

            }
        }
    }
}