/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet;

import java.util.Date;

/**
 *
 * @author Rezaul Karim
 */
public class Search
{
    private Integer searchProjectId;
    private Integer searchOptionId;
    private Integer searchMonth;
    private Date searchFromDate;
    private Date searchToDate;
    private Integer searchUser;

    public Integer getSearchProjectId()
    {
        return searchProjectId;
    }

    public void setSearchProjectId(Integer searchProjectId)
    {
        this.searchProjectId = searchProjectId;
    }

    public Integer getSearchOptionId()
    {
        return searchOptionId;
    }

    public void setSearchOptionId(Integer searchOptionId)
    {
        this.searchOptionId = searchOptionId;
    }

    public Integer getSearchMonth()
    {
        return searchMonth;
    }

    public void setSearchMonth(Integer searchMonth)
    {
        this.searchMonth = searchMonth;
    }

    public Date getSearchFromDate()
    {
        return searchFromDate;
    }

    public void setSearchFromDate(Date searchFromDate)
    {
        this.searchFromDate = searchFromDate;
    }

    public Date getSearchToDate()
    {
        return searchToDate;
    }

    public void setSearchToDate(Date searchToDate)
    {
        this.searchToDate = searchToDate;
    }

    public Integer getSearchUser()
    {
        return searchUser;
    }

    public void setSearchUser(Integer searchUser)
    {
        this.searchUser = searchUser;
    }
    
}
