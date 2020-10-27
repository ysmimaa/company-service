package com.driver.ms.service.impl;

import com.driver.ms.entity.Driver;
import com.driver.ms.entity.Journey;
import com.driver.ms.repository.DriverRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static com.driver.ms.entity.Driver.builder;

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
                        builder()
                                .id(1L)
                                .firstname("firstname1")
                                .build(),
                        builder()
                                .id(2L)
                                .firstname("firstname2")
                                .build())
                );

                List<Driver> listOfDriver = driverService.getListOfDriver();

                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(2, listOfDriver.size(), "List of user not empty"),
                        () -> assertTrue(listOfDriver.stream().anyMatch(driver -> driver.getId().equals(1L)), "The list contains the driver %s")
                );

            }

            @DisplayName("When invoking the grouping by journey service")
            @Nested
            class DriversGroupedByJourneyTest {
                @Test
                @DisplayName(value = "Then display drivers grouped by journey ")
                void should_return_the_list_of_drivers() {
                    Journey journey = Journey.builder()
                            .id(1L)
                            .city("city1")
                            .nbrOfPlaces(22)
                            .build();
                    Driver expectedDriver = builder()
                            .id(1L)
                            .journey(journey)
                            .build();
                    List<Driver> drivers = Arrays.asList(
                            expectedDriver,
                            builder()
                                    .id(1L)
                                    .journey(
                                            Journey.builder()
                                                    .id(1L)
                                                    .city("city2")
                                                    .nbrOfPlaces(22)
                                                    .build())
                                    .build(),

                            builder()
                                    .id(1L)
                                    .journey(
                                            Journey.builder()
                                                    .id(1L)
                                                    .city("city3")
                                                    .nbrOfPlaces(22)
                                                    .build())
                                    .build()
                    );

                    when(driverRepository.findAll()).thenReturn(drivers);

                    Map<Journey, List<Driver>> groupedDriversByJourney = driverService.getGroupedDriversByJourney();
                    assertAll("Check all the following asserts",
                            () -> assertEquals(groupedDriversByJourney.size(), 3),
                            () -> assertEquals(groupedDriversByJourney.get(journey).get(0), expectedDriver));


                }
            }

            @Test
            @DisplayName(value = "Then display an empty list if there is none")
            void should_return_an_empty_list_of_drivers() {
                when(driverRepository.findAll()).thenReturn(new ArrayList<>());
                List<Driver> listOfDriver = driverService.getListOfDriver();
                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(0, listOfDriver.size(), "An empty list of user"),
                        () -> assertTrue(listOfDriver.isEmpty(), "List is empty"));
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
                        builder()
                                .id(1L)
                                .firstname("firstname1")
                                .build(),
                        builder()
                                .id(2L)
                                .firstname("firstname2")
                                .build(),
                        builder()
                                .id(3L)
                                .firstname("firstname3")
                                .build(),
                        builder()
                                .id(4L)
                                .firstname("firstname4")
                                .build());
                when(driverRepository.findByFirstname(anyString())).thenReturn(driversList);
                List<Driver> listOfDriver = driverService.findByFirstname("firstname1");
                assertAll("Verify conditions for displaying the drivers",
                        () -> assertEquals(4, listOfDriver.size(), "An empty list of user"));
                assertThat(listOfDriver).contains(driversList.get(0));

                verify(driverRepository, times(1)).findByFirstname(anyString());
            }
        }

        @DisplayName("When passing the phone criteria ")
        @Nested
        class DriverByPhone {

            @Test
            @DisplayName(value = "Display driver based on the phone search criteria")
            void should_return_a_driver_based_on_the_phone_search_criteria() {
                Driver driver = builder()
                        .id(2L)
                        .firstname("firstname2")
                        .phone("070605060")
                        .build();
                when(driverRepository.findByPhone(anyString())).thenReturn(driver);

                Driver driverByPhone = driverService.findDriverByPhone("060606060");
                assertEquals(driver, driverByPhone, "The matching driver");
                verify(driverRepository, times(1)).findByPhone(anyString());
            }
        }

        @DisplayName("When passing an invalid phone criteria ")
        @Nested
        class DriverByInvalidPhone {

            @Test
            @DisplayName(value = "Throw an exception")
            void should_throw_an_exception() {
                assertThrows(NullPointerException.class, () -> driverService.findDriverByPhone(""), "Please provide a valid search criteria");
                verify(driverRepository, times(0)).findByPhone(anyString());
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
                Driver driver = builder()
                        .id(1L)
                        .firstname("driver1")
                        .phone("062323236")
                        .build();

                when(driverRepository.save(any(Driver.class))).thenReturn(driver);

                Driver createdDriver = driverService.createDriver(driver);

                assertAll("Check conditions",
                        () -> assertEquals(driver, createdDriver, "A new driver has been created"));
                verify(driverRepository, times(1)).save(any(Driver.class));
            }
        }

        @DisplayName("When user passes an invalid driver")
        @Nested
        class AddInvalidDriverThrowsException {

            @DisplayName("Then throw an exception")
            @Test
            void should_throw_exception_when_trying_to_add_an_empty_or_invalid_driver() {
                assertThrows(NullPointerException.class, () -> driverService.createDriver(null),
                        "should throw a NullPointerException exception");
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
                Driver driver = builder()
                        .id(1L)
                        .firstname("driver1")
                        .phone("062323236")
                        .build();

                when(driverRepository.save(any(Driver.class))).thenReturn(driver);
                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
                Driver createdDriver = driverService.createDriver(driver);
                assertAll("Check condition",
                        () -> Assertions.assertNull(createdDriver, "The new driver already exists"));

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
                Driver driver = builder()
                        .id(1L)
                        .firstname("driver1")
                        .phone("062323236")
                        .build();
                when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));
                when(driverRepository.save(any(Driver.class))).thenReturn(driver);

                Driver updatedDriver = driverService.updateDriver(driver);
                assertAll("Check condition",
                        () -> assertEquals(driver, updatedDriver, "A driver has been updated"));

                verify(driverRepository, times(1)).findById(anyLong());
                verify(driverRepository, times(1)).save(any(Driver.class));
            }
        }
    }
}