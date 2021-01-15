package com.company.service.ms.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuessCompany {
    private String verb;
    private int number;
    private String company;

    public String guess(String name, int count) {
        if (count == 1) {
            return companySingular(name, count);
        } else if (count > 1) {
            return companyPlural(name, count);
        }
        throw new RuntimeException("Error has been occured");
    }

    private String companyPlural(String name, int count) {
        this.verb = "are";
        this.number = count;
        this.company = name;
        return String.format("There %s %s %s", verb, number, company);
    }

    private String companySingular(String name, int count) {
        this.verb="is";
        this.number = count;
        this.company = name;
        return String.format("There %s %s %s", verb, number, company);
    }
}
