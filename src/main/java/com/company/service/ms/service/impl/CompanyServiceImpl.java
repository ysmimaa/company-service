package com.company.service.ms.service.impl;

import com.company.service.ms.entity.Company;
import com.company.service.ms.entity.Driver;
import com.company.service.ms.entity.GuessCompany;
import com.company.service.ms.exception.EmptyCompanyListException;
import com.company.service.ms.exception.InvalidParamException;
import com.company.service.ms.feign.DriverFeignClient;
import com.company.service.ms.repository.CompanyRepository;
import com.company.service.ms.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private CompanyRepository companyRepository;
    private DriverFeignClient driverFeignClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, DriverFeignClient driverFeignClient) {
        this.companyRepository = companyRepository;
        this.driverFeignClient = driverFeignClient;
    }

    @Override
    public List<Company> getListOfCompanies() {
        List<Company> listOfCompanies = companyRepository.findAll();
        if (!CollectionUtils.isEmpty(listOfCompanies) || listOfCompanies.size() == 0) {
            throw new EmptyCompanyListException("List of companies is empty");
        }
        return new ArrayList<>();
    }

    @Override
    public Company addDriverToListOfDrivers(Driver driver, Company company) {
        if (driver == null || driver.getId() == null) {
            throw new InvalidParamException("Please provide a valid driver");
        }
        Long driverId = driver.getId();
        Long companyId = company.getId();

        Driver foundDriver = driverFeignClient.getDriverById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        return companyRepository.findById(companyId)
                .flatMap(companyWithNewDriver -> {
                    companyWithNewDriver.getDrivers().add(foundDriver);
                    return Optional.of(companyWithNewDriver);
                })
                .orElseThrow(() -> new RuntimeException("No company has been found"));
    }

    @Override
    public Company addListOfDriversToCompany(Company company, List<Driver> driversToBeAdded) {
        if (CollectionUtils.isEmpty(driversToBeAdded)) {
            throw new InvalidParamException("");
        }
        Long companyId = company.getId();

        return companyRepository.findById(companyId)
                .flatMap(companyWithNewDrivers -> {
                    List<Driver> listOfDriversById = driverFeignClient.getListOfDriversById(driversToBeAdded);
                    if (!CollectionUtils.isEmpty(listOfDriversById)) {
                        companyWithNewDrivers.getDrivers().addAll(listOfDriversById);
                    }
                    return Optional.of(companyWithNewDrivers);
                })
                .orElseThrow(() -> new RuntimeException("No company has been found"));
    }

    @Override
    public Company findCompanyByDriverFirstName(Long companyId, String firstName) {
        if (companyId == null) {
            throw new InvalidParamException("Please provide a valid param");
        }
        return companyRepository.findById(companyId).flatMap(company -> {
            List<Driver> driversByFirstName = company.getDrivers().stream()
                    .filter(driver -> driver.getFirstname().equals(firstName))
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(driversByFirstName)) {
                company.setDrivers(driversByFirstName);
            }
            return Optional.of(company);
        }).orElseThrow(() -> new RuntimeException("No company has been found"));
    }

    @Override
    public Map<String, List<Driver>> findCompanyWithDriverGroupedCity(Long companyId) {
        if (companyId == null) {
            throw new InvalidParamException("Please provide a valid param");
        }
        return companyRepository.findById(companyId)
                .flatMap(company -> {
                    List<Driver> listOfDriverByCompanyId = driverFeignClient.getListOfDriverByCompanyId(companyId);
                    if (!CollectionUtils.isEmpty(listOfDriverByCompanyId)) {
                        company.getDrivers().addAll(listOfDriverByCompanyId);
                    }
                    return Optional.of(company);
                })
                .map(Company::getDrivers)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(driver -> driver.getAddress().getCity()));
    }

    @Override
    public <K> Map<K, List<Driver>> findCompanyWithDriverGroupedByCriteria(Function<Driver, K> functionByCriteria, Long companyId) {
        return companyRepository.findById(companyId).flatMap(company -> {
            List<Driver> listOfDriverByCompanyId = driverFeignClient.getListOfDriverByCompanyId(companyId);
            if (!CollectionUtils.isEmpty(listOfDriverByCompanyId)) {
                company.getDrivers().addAll(listOfDriverByCompanyId);
            }
            return Optional.of(company);


        }).map(Company::getDrivers)
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(functionByCriteria));
    }

    @Override
    public Company findCompanyWithItsName(String companyName) {
        if (StringUtils.isEmpty(companyName)) {
            throw new InvalidParamException("Please provide a valid parameter");
        }
        return companyRepository.findCompanyByName(companyName)
                .orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public String guessCompanyIsPluralOrSingular(String companyName, int count) {
        return new GuessCompany().guess(companyName,count);
    }

    @Override
    public Company create(Company company) {
        return null;
    }
}
