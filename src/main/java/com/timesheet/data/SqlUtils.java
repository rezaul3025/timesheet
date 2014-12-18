/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Project;

/**
 *
 * @author unixmac
 */
public interface SqlUtils
{
    public void insetIntoProjectUserAss(Integer projectId, String userList);
}
