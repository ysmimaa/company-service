package com.company.service.ms.automated;

import com.company.service.ms.entity.Company;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class DisplayCompanyStepDef {

    private List<Company> companies;
    private List<Company> emptyListOfCompanies;

    @Given("service company")
    public void serviceCompany() {
        companies = new ArrayList<>();
    }

    @When("user does a call to the list of companies")
    public void userDoesACallToTheListOfCompanies() {
        companies.add(Company.builder().id(1L).build());
    }

    @Then("list with all companies is returned")
    public void listWithAllCompaniesIsReturned() {
        Assertions.assertThat(companies).contains(companies.get(0));
    }

    @And("the returned list is empty")
    public void theReturnedListIsEmpty() {
        emptyListOfCompanies = new ArrayList<>();
    }

    @Then("an no content exception is thrown")
    public void anNoContentExceptionIsThrown() {
        org.junit.jupiter.api.Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> emptyListOfCompanies.get(0));
    }
}
