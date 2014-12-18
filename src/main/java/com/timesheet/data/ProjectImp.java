/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Project;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author unixmac
 */
@Repository
public class ProjectImp implements ProjectOp
{
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addProject(Project project)
    {
        sessionFactory.getCurrentSession().save(project);
    }

    @Override
    public void deleteProject(Integer projectId)
    {
        Project project = (Project) sessionFactory.getCurrentSession().load(Project.class, projectId);

        if (null != project)
        {
            sessionFactory.getCurrentSession().delete(project);
        }
    }

    @Override
    public List<Project> getProjectByUser(Integer user)
    {
        List<Project> proList = new ArrayList<Project>();
        return proList;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Project> getAllProject()
    {
      
        return sessionFactory.getCurrentSession().createQuery("from Project").list();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Project getProjectById(Integer projectId)
    {
         Project project = (Project) sessionFactory.getCurrentSession().get(Project.class, projectId);
         
         return project;
        
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void updateProject(Project project)
    {
        System.out.println("Update project triggred");
        sessionFactory.getCurrentSession().update(project);
        
    }

    @Override
    public List<Project> getProjectByCompany(Integer companyId)
    {
        return sessionFactory.getCurrentSession().createQuery("from Project where companyId = :companyId").setInteger("companyId", companyId).list();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
