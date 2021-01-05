package com.company.service.ms.service;

import com.company.service.ms.common.DependenciesInitializer;
import com.company.service.ms.entity.Address;
import com.company.service.ms.entity.Company;
import com.company.service.ms.entity.Driver;
import com.company.service.ms.exception.EmptyCompanyListException;
import com.company.service.ms.feign.DriverFeignClient;
import com.company.service.ms.repository.CompanyRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

@DisplayName("<=******* Company specifications *******=>")
class CompanyServiceImplTest extends DependenciesInitializer {
    final String MEKNES_CITY = "Meknes";

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DriverFeignClient driverFeignClient;

    public CompanyServiceImplTest() {

    }

    @BeforeEach
    void init() {
        companyService = new CompanyServiceImpl(companyRepository, driverFeignClient);
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Given company display services")
    class CompanyDisplayServicesSpec {
        @Nested
        @DisplayName("When the list of companies is empty")
        class DisplayCompanyThrowExceptionSpec {
            @Test
            @DisplayName("Then an empty list is returned")
            void should_return_an_exception_when_a_list_of_companies_is_empty() {
                //Given
                List<Company> companies = new ArrayList<>();
                //When
                when(companyRepository.findAll()).thenReturn(companies);
                //Then
                Assertions.assertThrows(EmptyCompanyListException.class, () -> companyService.getListOfCompanies());
            }
        }
    }

    @DisplayName(value = "Given company add driver service")
    @Nested
    class CompanyAddDriverService {

        @DisplayName(value = "When user provide a valid driver")
        @Nested
        class AddValidDriver {

            @DisplayName("Then the driver is added to the list of drivers")
            @Test
            void should_add_a_driver_to_list_of_driver() {
                //Given
                Driver driverToBeAdded = Driver.builder()
                        .id(1L)
                        .firstname("driver-firstName1")
                        .lastname("driver-lastName1")
                        .build();

                List<Driver> drivers = new ArrayList<>();

                Optional<Company> company = Optional.of(Company.builder()
                        .id(1L)
                        .drivers(drivers)
                        .build());
                when(driverFeignClient.getDriverById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(driverToBeAdded));
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(company);

                Company returnedCompanyById = companyService.addDriverToListOfDrivers(driverToBeAdded, company.get());

                Assertions.assertTrue(returnedCompanyById.getDrivers().size() > 0, "The list of drivers should not be empty");
            }

        }

        @DisplayName(value = "When user provide a list of valid drivers")
        @Nested
        class AddListOfDrivers {

            @DisplayName("Then drivers are added to the company's list of drivers")
            @Test
            void should_add_a_list_of_drivers_to_the_company_s_list_of_drivers() {
                //Given
                List<Driver> driversToBeAdded = Arrays.asList(
                        Driver.builder()
                                .id(1L)
                                .firstname("driver-firstName1")
                                .lastname("driver-lastName1")
                                .build()
                        ,
                        Driver.builder()
                                .id(2L)
                                .firstname("driver-firstName2")
                                .lastname("driver-lastName2")
                                .build()
                );

                List<Driver> drivers = new ArrayList<>();

                Optional<Company> company = Optional.of(Company.builder()
                        .id(1L)
                        .drivers(drivers)
                        .build());
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(company);
                when(driverFeignClient.getListOfDriversById(ArgumentMatchers.anyList())).thenReturn(driversToBeAdded);

                Company returnedCompanyById = companyService.addListOfDriversToCompany(company.get(), driversToBeAdded);

                Assertions.assertTrue(returnedCompanyById.getDrivers().size() > 1, "The list of drivers should be greater than 1");
                Assertions.assertEquals(returnedCompanyById.getDrivers().get(0), driversToBeAdded.get(0));
            }

        }

        @DisplayName(value = "When user provides an invalid driver")
        @Nested
        class AddInvalidDriver {

            @DisplayName("Then invalid driver exception is thrown")
            @Test
            void should_throw_an_exception_when_provide_invalid_driver() {
                Assertions.assertThrows(InvalidParamException.class,
                        () -> companyService.addDriverToListOfDrivers(null, null));
            }

        }

    }

    @Nested
    @DisplayName("Given company driver search criteria services")
    class CompanyDriverSearchCriteriaServicesSpec {
        @Nested
        @DisplayName("When user provide a driver's firstName as a criteria")
        class DisplayDriversByFirstNameCriteriaSpec {
            @Test
            @DisplayName("Then display a list of drivers based on the firstName criteria")
            void should_return_a_list_of_driver_related_to_the_firstName_criteria() {
                List<Driver> listOfDrivers = Arrays.asList(
                        Driver.builder()
                                .id(1L)
                                .firstname("driver-firstName1")
                                .lastname("driver-lastName1")
                                .build()
                        ,
                        Driver.builder()
                                .id(2L)
                                .firstname("driver-firstName1")
                                .lastname("driver-lastName2")
                                .build()
                );

                Company mockCompany = Company.builder().drivers(listOfDrivers).build();
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockCompany));
                when(driverFeignClient.getListOfDriverByCompanyId(ArgumentMatchers.anyLong())).thenReturn(listOfDrivers);

                Company companyWithListOfDriversBasedOnFirstName = companyService.findCompanyByDriverFirstName(1L, "");

                Assertions.assertEquals(companyWithListOfDriversBasedOnFirstName.getDrivers().get(0).getFirstname(), listOfDrivers.get(0).getFirstname());
                Assertions.assertEquals(companyWithListOfDriversBasedOnFirstName.getDrivers().get(1).getFirstname(), listOfDrivers.get(1).getFirstname());
            }
        }
    }

    @Nested
    @DisplayName("Given company grouped driver by city service")
    class CompanyGroupedDriverByCityServicesSpec {
        @Nested
        @DisplayName("When user invoke the grouped driver by city service  ")
        class DisplayDriversByFirstNameCriteriaSpec {
            @Test
            @DisplayName("Then display a company with a grouped driver by city")
            void should_return_a_company_containing_a_grouped_list_of_driver_by_city() {
                List<Driver> listOfDrivers = new LinkedList<>(Arrays.asList(
                        Driver.builder()
                                .id(1L)
                                .firstname("driver-firstName1")
                                .address(
                                        Address.builder()
                                                .city(MEKNES_CITY)
                                                .build()
                                )
                                .lastname("driver-lastName1")
                                .build()
                        ,
                        Driver.builder()
                                .id(2L)
                                .firstname("driver-firstName1")
                                .address(
                                        Address.builder()
                                                .city(MEKNES_CITY)
                                                .build()
                                )
                                .lastname("driver-lastName2")
                                .build()
                ));

                Company mockCompany = Company.builder().drivers(new LinkedList<>()).build();
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockCompany));
                when(driverFeignClient.getListOfDriverByCompanyId(ArgumentMatchers.anyLong())).thenReturn(listOfDrivers);

                Map<String, List<Driver>> companyWithListOfDriversGroupedByCity = companyService.findCompanyWithDriverGroupedCity(1L);

                assertThat(companyWithListOfDriversGroupedByCity).containsKey(MEKNES_CITY);
                assertThat(companyWithListOfDriversGroupedByCity).containsValues(listOfDrivers);

            }
        }
    }

    @Nested
    @DisplayName("Given company grouped driver by criteria service")
    class CompanyGroupedDriverByCriteriaServicesSpec {
        @Nested
        @DisplayName("When user invoke the grouped driver any criteria service  ")
        class DisplayDriversByAnyCriteriaSpec {
            @Test
            @DisplayName("Then display a company with grouped drivers by the criteria")
            void should_return_a_company_with_a_grouped_list_of_driver_by_criteria() {

                List<Driver> listOfDrivers = new ArrayList<>(Arrays.asList(
                        Driver.builder()
                                .id(1L)
                                .firstname("driver-firstName1")
                                .address(
                                        Address.builder()
                                                .city(MEKNES_CITY)
                                                .build()
                                )
                                .lastname("driver-lastName1")
                                .build()
                        ,
                        Driver.builder()
                                .id(2L)
                                .firstname("driver-firstName2")
                                .address(
                                        Address.builder()
                                                .city(MEKNES_CITY)
                                                .build()
                                )
                                .lastname("driver-lastName2")
                                .build()
                ));

                Company mockCompany = Company.builder().drivers(new ArrayList<>()).build();
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockCompany));
                when(driverFeignClient.getListOfDriverByCompanyId(ArgumentMatchers.anyLong())).thenReturn(listOfDrivers);

                Map<String, List<Driver>> companyWithListOfDriversGroupedByCriteria = companyService.findCompanyWithDriverGroupedByCriteria(d -> d.getAddress().getCity(), 1L);

                assertThat(companyWithListOfDriversGroupedByCriteria).containsKey(MEKNES_CITY);

            }
        }
    }


}