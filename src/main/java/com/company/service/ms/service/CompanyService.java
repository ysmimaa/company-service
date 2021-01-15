package com.company.service.ms.service;

import com.company.service.ms.entity.Company;
import com.company.service.ms.entity.Driver;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface CompanyService {

    /**
     * Method that return a list of companies
     *
     * @return list of companies
     */
    List<Company> getListOfCompanies();

    /**
     * Method that add a driver to the company's list of driver
     *
     * @param driver
     * @return a company with the added driver
     */
    Company addDriverToListOfDrivers(Driver driver, Company company);

    /**
     * Method that add a list of drivers to the company's list of drivers
     *
     * @param company
     * @param driversToBeAdded
     * @return
     */
    Company addListOfDriversToCompany(Company company, List<Driver> driversToBeAdded);

    /**
     * @param companyId
     * @return
     */

    Company findCompanyByDriverFirstName(Long companyId, String firstName);

    /**
     * @param
     * @param
     * @return
     */
    Map<String, List<Driver>> findCompanyWithDriverGroupedCity(Long companyId);

    /**
     * @param functionByCriteria
     * @param <K>
     * @return
     */
    <K> Map<K, List<Driver>> findCompanyWithDriverGroupedByCriteria(Function<Driver, K> functionByCriteria, Long companyId);

    /**
     * Method that handle return a company by it name
     *
     * @return campany
     */
    Company findCompanyWithItsName(String companyName);

    String guessCompanyIsPluralOrSingular(String companyName, int count);

    /**
     * Method that create a new company
     *
     * @param company
     * @return created company
     */
    Company create(Company company);
}
