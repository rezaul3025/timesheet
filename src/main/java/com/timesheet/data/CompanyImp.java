/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author unixmac
 */
@Repository
public class CompanyImp implements CompanyOp
{

    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public Integer addCompany(Company company)
    {
       Session session = sessionFactory.getCurrentSession();
       
       session.save(company);
       
       session.flush();
       
       return company.getCompanyId();
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
