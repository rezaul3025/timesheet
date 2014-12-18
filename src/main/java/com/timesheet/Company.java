/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author unixmac
 */
@Entity
@Table(name = "Company")
public class Company implements Serializable
{

    @Id
    @GeneratedValue
    private Integer companyId;
    private String name;
    private String description;
    private String address;
    private String email;
    private Integer comSize;
    private String comType;
    private Blob logo;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="companyId")
    private Set<Project> projects;
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="companyId")
    private Set<User> users;

    public Integer getCompanyId()
    {
        return companyId;
    }

    public void setCompanyId(Integer companyId)
    {
        this.companyId = companyId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getComSize()
    {
        return comSize;
    }

    public void setComSize(Integer comSize)
    {
        this.comSize = comSize;
    }

    public String getComType()
    {
        return comType;
    }

    public void setComType(String comType)
    {
        this.comType = comType;
    }

    public Blob getLogo()
    {
        return logo;
    }

    public void setLogo(Blob logo)
    {
        this.logo = logo;
    }

    @JsonManagedReference
    public Set<Project> getProjects()
    {
        return projects;
    }

    public void setProjects(Set<Project> projects)
    {
        this.projects = projects;
    }

    @JsonManagedReference
    public Set<User> getUsers()
    {
        return users;
    }

    public void setUsers(Set<User> users)
    {
        this.users = users;
    }
    
    
    
}
