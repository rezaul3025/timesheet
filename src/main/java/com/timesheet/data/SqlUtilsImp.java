/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author unixmac
 */
@Repository
public class SqlUtilsImp implements SqlUtils
{
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void insetIntoProjectUserAss(Integer projectId, String userList)
    {
        Session session = sessionFactory.getCurrentSession();
        
        //Transaction tx  = session.beginTransaction();
        
        String[] userListArr = userList.split(",");
        
        if(userListArr.length > 0)
        {
            for(String userId : userListArr)
            {
                if(StringUtils.isNumeric(userId))
                {
                    String sql = "INSERT INTO ProjectUser(projectId, userId) VALUES(:projectId, :userId)";

                    //System.out.println("Inide manual sql @@@@@@@@@@@@"+projectId);

                    Query query = session.createSQLQuery(sql);

                    query.setInteger("projectId", projectId);

                    query.setInteger("userId", Integer.parseInt(userId));

                    query.executeUpdate();

                    session.flush();

                    session.clear();
                }
            }
        }
        
        //tx.commit();
        //session.close();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
