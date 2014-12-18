/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Company;

/**
 *
 * @author unixmac
 */
public interface CompanyOp
{
    public Integer addCompany(Company company);
    public void deleteComapny(Integer companyId);
    public Company getCompanyById(Integer companyId);
}
