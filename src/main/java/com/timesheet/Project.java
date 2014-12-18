/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 *
 * @author unixmac
 */
@Entity
@Table(name="Project")
public class Project implements Serializable
{
    @Id
    @GeneratedValue
    private Integer projectId;
    private String name;
    private String description;
    private String identifier;
    private Integer budgetLimit;
    
    @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="projectId")
    private Set<BookingOption> bookingOptions;
    
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="companyId")
    private Company company;
    
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinTable(name="ProjectUser", 
                joinColumns={@JoinColumn(name="projectId")}, 
                inverseJoinColumns={@JoinColumn(name="userId")})
    private Set<User> users;

    public Integer getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
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

    public String getIdentifier()
    {
        return identifier;
    }

    public void setIdentifier(String identifier)
    {
        this.identifier = identifier;
    }

    public Integer getBudgetLimit()
    {
        return budgetLimit;
    }

    public void setBudgetLimit(Integer budgetLimit)
    {
        this.budgetLimit = budgetLimit;
    }

    @JsonManagedReference
    public Set<BookingOption> getBookingOptions()
    {
        return bookingOptions;
    }

    public void setBookingOptions(Set<BookingOption> bookingOptions)
    {
        this.bookingOptions = bookingOptions;
    }
    
    @JsonBackReference
    public Company getCompany()
    {
        return company;
    }

    public void setCompany(Company company)
    {
        this.company = company;
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
