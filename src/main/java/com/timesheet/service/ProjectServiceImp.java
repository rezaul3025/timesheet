/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.data.ProjectOp;
import com.timesheet.Project;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author unixmac
 */
@Service
public class ProjectServiceImp implements ProjectService
{

    @Autowired
    ProjectOp projectOpertion;
    
    @Override
    @Transactional
    public void addProject(Project project)
    {
       projectOpertion.addProject(project);
    }

    @Override
    @Transactional
    public void deleteProject(Integer projectId)
    {
        projectOpertion.deleteProject(projectId);
    }

    @Override
    public List<Project> getProjectByUser(Integer user)
    {
       List<Project> proList=new ArrayList<Project>();
        return proList;
    }

    @Override
    @Transactional
    public List<Project> getAllProject()
    {
        
        return projectOpertion.getAllProject();
    }

    @Override
    @Transactional
    public Project getProjectById(Integer projectId)
    {
        return  projectOpertion.getProjectById(projectId);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param project
     */
    @Override
    @Transactional
    public void updateProject(Project project)
    {
        this.projectOpertion.updateProject(project);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param companyId
     * @return
     */
    @Override
    @Transactional
    public List<Project> getProjectByCompany(Integer companyId)
    {
        return this.projectOpertion.getProjectByCompany(companyId);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
