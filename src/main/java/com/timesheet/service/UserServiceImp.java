/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.Login;
import com.timesheet.data.UserOp;
import com.timesheet.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author unixmac
 */
@Service
public class UserServiceImp implements UserService
{
    @Autowired
    UserOp userOperation;

    @Override
    @Transactional
    public void addUser(User user)
    {
        userOperation.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId)
    {
        userOperation.deleteUser(userId);
    }

    /**
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public User getUserById(Integer userId)
    {
       User user = userOperation.getUserById(userId);
        
       //System.out.println("User in service layer @@@@@@@@@@@@@@@@@@@@@ ");
       return user;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public List<User> getAllUsers()
    {
        return userOperation.getAllUsers();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public User verifyUser(Login login)
    {
        return userOperation.verifyUser(login);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public void updateUser(User user)
    {
        userOperation.updateUser(user);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public List<User> getUserByCompanyId(Integer companyId)
    {
       return userOperation.getUserByCompanyId(companyId);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
