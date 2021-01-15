package com.company.service.ms.automated;

import com.company.service.ms.entity.Company;
import com.company.service.ms.exception.InvalidParamException;
import com.company.service.ms.feign.DriverFeignClient;
import com.company.service.ms.repository.CompanyRepository;
import com.company.service.ms.service.CompanyService;
import com.company.service.ms.service.impl.CompanyServiceImpl;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;

import java.util.ArrayList;
import java.util.List;

public class SaveCompanyStepDef {

    private Company companyToCreate;
    private Company invalidCompany;

    private List<Company> companies = new ArrayList<>();

    @Given("company to be added")
    public void companyToBeAdded() {
        companyToCreate = Company.builder()
                .id(1L)
                .name("company1")
                .activity("activity1")
                .build();
    }

    @When("click on the button save")
    public void clickOnTheButtonSave() {
        companies.add(companyToCreate);
    }

    @Then("company will be added successfully")
    public void companyWillBeAddedSuccessfully() {
        Assertions.assertThat(companies).contains(companyToCreate);

    }

    @Given("invalid company to be added")
    public void invalidCompanyToBeAdded() {
        invalidCompany = null;
    }

    @Then("invalid param exception must be thrown")
    public void invalidParamExceptionMustBeThrown() {
        org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> invalidCompany.getId());
    }
}
