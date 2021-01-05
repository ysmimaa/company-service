package com.company.service.ms.resource;

import com.company.service.ms.common.constant.CommonConstant;
import com.company.service.ms.entity.Company;
import com.company.service.ms.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = CommonConstant.BASE_URL)
public class CompanyRestController {

    private CompanyRepository companyRepository;

    @Autowired
    public CompanyRestController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping(path = CommonConstant.COMPANIES)
    public List<Company> finsAllCompanies() {
        return companyRepository.findAll();
    }
}

