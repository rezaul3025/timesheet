/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Booking;
import com.timesheet.Search;
import com.timesheet.utils.Utils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Rezaul Karim
 */
@Repository
public class SearchImp implements SearchOp
{
    @Autowired
    SessionFactory sessionFactory;
    @Autowired
    Utils utils;

    @Override
    public List<Booking> searchResult(Search searchOb)
    {
        List<Booking> searchResult = new ArrayList<Booking>();
        
        System.out.println(" @@@@@@@@@@@@@@@@@@@@@@ :"+searchOb.getSearchProjectId()+" ,"+searchOb.getSearchFromDate()+","+searchOb.getSearchUser());
        
        String searchSQL = utils.generatSearchSQL(searchOb);
        
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ : Search SQL : "+searchSQL);
        
        if(StringUtils.isNotBlank(searchSQL))
        {
            Query searchQuery = sessionFactory.getCurrentSession().createQuery(searchSQL);
            
            if(searchSQL.contains("userId"))
            {
                searchQuery.setInteger("userId", searchOb.getSearchUser());
            }
            
            if(searchSQL.contains("projectId"))
            {
                searchQuery.setInteger("projectId", searchOb.getSearchProjectId());
            }
            
            if(searchSQL.contains("optionId"))
            {
                searchQuery.setInteger("optionId", searchOb.getSearchOptionId());
            }
            
            if(searchSQL.contains("startDate"))
            {
                Date startDate = utils.getFirstDateOfMonth(searchOb.getSearchMonth());
                
                searchQuery.setDate("startDate", startDate);
            }
            
            if(searchSQL.contains("endDate"))
            {
                Date endDate = utils.getLastDateOfMonth(searchOb.getSearchMonth());
                
                searchQuery.setDate("endDate", endDate);
            }
            
            if(searchSQL.contains("fromDate"))
            {
                searchQuery.setDate("fromDate", searchOb.getSearchFromDate());
            }
            
            if(searchSQL.contains("toDate"))
            {
                
                searchQuery.setDate("toDate", searchOb.getSearchToDate());
            }
            
            searchResult = searchQuery.list();
        }
        
        return searchResult;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
