/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.Booking;
import com.timesheet.Search;
import java.util.List;

/**
 *
 * @author Rezaul Karim
 */
public interface SearchService
{
     public List<Booking> searchResult(Search searchOb);
}
