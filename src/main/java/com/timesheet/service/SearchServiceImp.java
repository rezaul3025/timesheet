/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.service;

import com.timesheet.Booking;
import com.timesheet.Search;
import com.timesheet.data.SearchOp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Rezaul Karim
 */
@Service
public class SearchServiceImp implements SearchService
{

    @Autowired
    SearchOp searchOperation;

    @Override
    @Transactional
    public List<Booking> searchResult(Search searchOb)
    {
        return searchOperation.searchResult(searchOb);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
