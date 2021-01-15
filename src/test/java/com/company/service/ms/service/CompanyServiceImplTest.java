package com.company.service.ms.service;

import com.company.service.ms.common.CompanyParameterResolver;
import com.company.service.ms.common.DependenciesInitializer;
import com.company.service.ms.entity.Company;
import com.company.service.ms.entity.Driver;
import com.company.service.ms.exception.EmptyCompanyListException;
import com.company.service.ms.exception.InvalidParamException;
import com.company.service.ms.feign.DriverFeignClient;
import com.company.service.ms.repository.CompanyRepository;
import com.company.service.ms.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

@ExtendWith(CompanyParameterResolver.class)
@DisplayName("<=******* Company specifications *******=>")
class CompanyServiceImplTest extends DependenciesInitializer {
    final String MEKNES_CITY = "Meknes";

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private DriverFeignClient driverFeignClient;

    private List<Driver> driversList;

    @BeforeEach
    void init(Map<String, Driver> driverMap) {
        companyService = new CompanyServiceImpl(companyRepository, driverFeignClient);
        driversList = new ArrayList<>(driverMap.values());
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    @DisplayName("Given company display services")
    class CompanyDisplayServicesSpec {
        @Nested
        @DisplayName("When a user calls the list of companies service and the result is empty")
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
    class CompanyAddDriverServiceSpec {

        @DisplayName(value = "When user provide a valid driver")
        @Nested
        class AddValidDriver {

            @DisplayName("Then the driver is added to the list of drivers")
            @Test
            void should_add_a_driver_to_list_of_driver() {
                //Given
                List<Driver> drivers = new ArrayList<>();

                Optional<Company> company = Optional.of(Company.builder()
                        .id(1L)
                        .drivers(drivers)
                        .build());
                when(driverFeignClient.getDriverById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(driversList.get(0)));
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(company);

                Company returnedCompanyById = companyService.addDriverToListOfDrivers(driversList.get(0), company.get());

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
                List<Driver> drivers = new ArrayList<>();

                Optional<Company> company = Optional.of(Company.builder()
                        .id(1L)
                        .drivers(drivers)
                        .build());
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(company);
                when(driverFeignClient.getListOfDriversById(ArgumentMatchers.anyList())).thenReturn(driversList);

                Company returnedCompanyById = companyService.addListOfDriversToCompany(company.get(), driversList);

                Assertions.assertTrue(returnedCompanyById.getDrivers().size() > 1, "The list of drivers should be greater than 1");
                Assertions.assertEquals(returnedCompanyById.getDrivers().get(0), driversList.get(0));
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
        @DisplayName("When user provides a driver's firstName as a criteria")
        class DisplayDriversByFirstNameCriteriaSpec {
            @Test
            @DisplayName("Then display a list of drivers based on the firstName criteria")
            void should_return_a_list_of_driver_related_to_the_firstName_criteria() {
                Company mockCompany = Company.builder().drivers(driversList).build();
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockCompany));

                Company companyWithListOfDriversBasedOnFirstName = companyService.findCompanyByDriverFirstName(1L, "driver-firstName1");

                Assertions.assertEquals(companyWithListOfDriversBasedOnFirstName.getDrivers().get(0).getFirstname(), driversList.get(0).getFirstname());
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
                Company mockCompany = Company.builder().drivers(new LinkedList<>()).build();
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockCompany));
                when(driverFeignClient.getListOfDriverByCompanyId(ArgumentMatchers.anyLong())).thenReturn(driversList);

                Map<String, List<Driver>> companyWithListOfDriversGroupedByCity = companyService.findCompanyWithDriverGroupedCity(1L);

                assertThat(companyWithListOfDriversGroupedByCity).containsKey(MEKNES_CITY);
                assertThat(companyWithListOfDriversGroupedByCity).containsValues(driversList);

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
                Company mockCompany = Company.builder().drivers(new ArrayList<>()).build();
                when(companyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(mockCompany));
                when(driverFeignClient.getListOfDriverByCompanyId(ArgumentMatchers.anyLong())).thenReturn(driversList);

                Map<String, List<Driver>> companyWithListOfDriversGroupedByCriteria = companyService.findCompanyWithDriverGroupedByCriteria(d -> d.getAddress().getCity(), 1L);

                assertThat(companyWithListOfDriversGroupedByCriteria).containsKey(MEKNES_CITY);

            }
        }
    }

    @Nested
    @DisplayName(value = "Given search company services")
    class SearchCompanyServicesSpec {

        @Nested
        @DisplayName(value = "When user provides a name company criteria")
        class SearchCompanyByNameService {

            @DisplayName(value = "Then a company with the name is displayed")
            @Test
            void should_return_a_company_when_providing_the_name_criteria() {
                when(companyRepository.findCompanyByName(anyString())).thenReturn(Optional.of(Company.builder()
                        .id(1L)
                        .name("company1")
                        .build())
                );
                Company companyWithItsName = companyService.findCompanyWithItsName("company1");
                Assertions.assertEquals(companyWithItsName.getName(), "company1");

            }

        }

        @Nested
        @DisplayName(value = "When user provides an invalid name company criteria")
        class SearchCompanyByNameInvalidCriteriaService {

            @DisplayName(value = "Then invalid parameter exception is thrown")
            @Test
            void should_throw_invalid_parameter_exception_when_providing_invalid_name_criteria() {
                Assertions.assertThrows(InvalidParamException.class, () -> companyService.findCompanyWithItsName(""));
            }

        }
    }

    @DisplayName("Given a company/companies")
    @Nested
    class GuessPluralOrSingleCompanyExistence {

        @Nested
        @DisplayName("When user provides a single company")
        class SingleCompanyGuess {

            @DisplayName("Then display company in a singular")
            @Test
            void should_return_company_in_singular() {
                String guessCompany = companyService.guessCompanyIsPluralOrSingular("company", 1);
                Assertions.assertEquals("There is 1 company",guessCompany);

            }

        }


    }
}