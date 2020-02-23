package com.moxi.statistics.controller;

import com.moxi.statistics.dao.CompanyDao;
import com.moxi.statistics.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author https://www.chuckfang.top
 * @date Created on 2020/2/10 15:04
 */
@CrossOrigin(allowCredentials = "true", maxAge = 3600)
@RestController
public class ApiController {

    @Autowired
    CompanyDao companyDao;

    @GetMapping("/getCompanyList")
    public List<Company> getCompany() {
        return companyDao.getCompanyList();
    }
}
