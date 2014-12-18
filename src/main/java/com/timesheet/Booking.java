/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet;

import com.timesheet.utils.JsonDateSerializer;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.codehaus.jackson.map.annotate.JsonSerialize;


/**
 *
 * @author unixmac
 */
@Entity
@Table(name = "Booking")
public class Booking implements Serializable
{
    @Id
    @GeneratedValue
    private Integer bookingId;
    private String description;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date bookingDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startTime;
    @Transient
    private String startTimeStr;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endTime;
    @Transient
    private String endTimeStr;
    private String breakTime;
    private Integer duration;
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;
    @ManyToOne
    @JoinColumn(name="projectId")
    private Project project;
    @ManyToOne
    @JoinColumn(name="optionId")
    private BookingOption bookingOption;
    @Transient
    private Integer projectId;
    @Transient
    private Integer optionId;

    public Integer getBookingId()
    {
        return bookingId;
    }

    public void setBookingId(Integer bookingId)
    {
        this.bookingId = bookingId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getBookingDate()
    {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate)
    {
        this.bookingDate = bookingDate;
    }
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    @JsonSerialize(using=JsonDateSerializer.class)
    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public String getBreakTime()
    {
        return breakTime;
    }

    public void setBreakTime(String breakTime)
    {
        this.breakTime = breakTime;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Project getProject()
    {
        return project;
    }

    public void setProject(Project project)
    {
        this.project = project;
    }

    public BookingOption getBookingOption()
    {
        return bookingOption;
    }

    public void setBookingOption(BookingOption bookingOption)
    {
        this.bookingOption = bookingOption;
    }

    public String getStartTimeStr()
    {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr)
    {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr()
    {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr)
    {
        this.endTimeStr = endTimeStr;
    }

    public Integer getDuration()
    {
        return duration;
    }

    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }

    public Integer getProjectId()
    {
        return projectId;
    }

    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }

    public Integer getOptionId()
    {
        return optionId;
    }

    public void setOptionId(Integer optionId)
    {
        this.optionId = optionId;
    }
    
}
