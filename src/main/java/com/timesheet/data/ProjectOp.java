/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Project;
import java.util.List;

/**
 *
 * @author unixmac
 */
public interface ProjectOp
{
    public void addProject(Project project);
    public void deleteProject(Integer projectId);
    public List<Project> getProjectByUser(Integer user);
    public List<Project> getProjectByCompany(Integer companyId);
    public Project getProjectById(Integer projectId);
    public List<Project> getAllProject();
    public void updateProject(Project project);
    
}
