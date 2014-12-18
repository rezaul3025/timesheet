/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.data;

import com.timesheet.Login;
import com.timesheet.User;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author unixmac
 */
@Repository
public class UserImp implements UserOp
{

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addUser(User user)
    {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    public void deleteUser(Integer userId)
    {
        User user = (User) sessionFactory.getCurrentSession().load(User.class, userId);

        if (null != user)
        {
            sessionFactory.getCurrentSession().delete(user);
        }
    }

    @Override
    public User getUserById(Integer userId)
    {
        User user = (User) sessionFactory.getCurrentSession().load(User.class, userId);
        
        return user;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllUsers()
    {
        return sessionFactory.getCurrentSession().createQuery("from User").list();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User verifyUser(Login login)
    {
        User user = (User)sessionFactory.getCurrentSession().createQuery("from User where email = :email AND password = :password").setString("email", login.getEmail()).setString("password", login.getPassword()).uniqueResult();
        
       return user;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(User user)
    {
        sessionFactory.getCurrentSession().update(user);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getUserByCompanyId(Integer companyId)
    {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return sessionFactory.getCurrentSession().createQuery("from User where companyId = :companyId").setInteger("companyId", companyId).list();
    }

}
