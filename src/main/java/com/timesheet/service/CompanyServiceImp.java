/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.Company;
import com.timesheet.data.CompanyOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author unixmac
 */
@Service
public class CompanyServiceImp implements CompanyService
{
    @Autowired
    CompanyOp companyOperation;

    @Override
    @Transactional
    public Integer addCompany(Company company)
    {
        return companyOperation.addCompany(company);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteComapny(Integer companyId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Company getCompanyById(Integer companyId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
