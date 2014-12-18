/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Login;
import com.timesheet.User;
import java.util.List;

/**
 *
 * @author unixmac
 */
public interface UserOp
{
    public void addUser(User user);
    public void deleteUser(Integer userId);
    public User getUserById(Integer userId);
    public List<User> getAllUsers();
    public void updateUser(User user);
    public List<User> getUserByCompanyId(Integer companyId );
    
    public User verifyUser(Login login);
}
