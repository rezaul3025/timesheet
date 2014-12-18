/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonBackReference;

/**
 *
 * @author unixmac
 */
@Entity
@Table(name="BookingOption")
public class BookingOption implements Serializable
{
    @Id
    @GeneratedValue
    private Integer optionId;
    private String optionLabel;
    private Boolean useBudget;
    private Boolean IsDefault;
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="projectId")
    private Project project;

    public Integer getOptionId()
    {
        return optionId;
    }

    public void setOptionId(Integer optionId)
    {
        this.optionId = optionId;
    }

    public String getOptionLabel()
    {
        return optionLabel;
    }

    public void setOptionLabel(String optionLabel)
    {
        this.optionLabel = optionLabel;
    }

    public Boolean isUseBudget()
    {
        return useBudget;
    }

    public void setUseBudget(Boolean useBudget)
    {
        this.useBudget = useBudget;
    }

    public Boolean isIsDefault()
    {
        return IsDefault;
    }

    public void setIsDefault(Boolean IsDefault)
    {
        this.IsDefault = IsDefault;
    }

    @JsonBackReference
    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }
    
}
