/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.Project;
import com.timesheet.data.SqlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author unixmac
 */
@Service
public class SqlUtilsServiceImp implements SqlUtilsService
{
    @Autowired
    SqlUtils sqlUtils;

    @Override
    @Transactional
    public void insetIntoProjectUserAss(Integer projectId, String userList)
    {
        //System.out.println("Test");
        sqlUtils.insetIntoProjectUserAss(projectId, userList);
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
