/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.utils;

import com.timesheet.Booking;
import com.timesheet.BookingOption;
import com.timesheet.Project;
import com.timesheet.Search;
import com.timesheet.User;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 *
 * @author unixmac
 */
@Service
public class Utils
{

    private String pageLayout;

    private Map<String, BookingOption> bookingOptions;

    private Project projectById;

    private User userProfile;

    private Integer currentMonth;

    private String displyMonth;

    Utils()
    {
        bookingOptions = new HashMap<String, BookingOption>();
    }

    public Date dateModification(Date date, String time)
    {

        Date modifiedDate = null;

        if (date != null && StringUtils.isNotBlank(time))
        {
            String hhStr = time.split(":")[0];
            String mmStr = time.split(":")[1];

            Calendar calendar = Calendar.getInstance();

            calendar.setTime(date);

            calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + Integer.parseInt(hhStr));

            calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + Integer.parseInt(mmStr));

            modifiedDate = calendar.getTime();
        }

        return modifiedDate;
    }

    public int dateDiffInMin(Date startDate, Date endDate, String breakTime)
    {
        long diffLong = endDate.getTime() - startDate.getTime();

        /*Date diff = new Date(endDate.getTime() - startDate.getTime());

         Calendar calendar = Calendar.getInstance();
         calendar.setTime(diff);
       
         int hours = calendar.get(Calendar.HOUR_OF_DAY);
         int minutes = calendar.get(Calendar.MINUTE);
         System.out.println("@@@@@@@@@@@@@@@@@@@@@@@"+hours+":"+minutes);
         return (int) (hours-1*60)+minutes; */
        int diffInt = (int) diffLong / (60 * 1000);

        if (breakTime.contains(":") && breakTime.length() == 5)
        {
            int bHH = Integer.parseInt(breakTime.split(":")[0]);
            int bMM = Integer.parseInt(breakTime.split(":")[1]);

            int breakTimeInMin = bHH * 60 + bMM;

            diffInt = diffInt - breakTimeInMin;
        }

        return diffInt;
    }

    public String generateDisplayMonth(int month)
    {
        String displayMonth = "";

        if (month == 0)
        {
            displayMonth = "ts.jan";
        } else if (month == 1)
        {
            displayMonth = "ts.feb";
        } else if (month == 2)
        {
            displayMonth = "ts.mar";
        } else if (month == 3)
        {
            displayMonth = "ts.apr";
        } else if (month == 4)
        {
            displayMonth = "ts.may";
        } else if (month == 5)
        {
            displayMonth = "ts.jun";
        } else if (month == 6)
        {
            displayMonth = "ts.jul";
        } else if (month == 7)
        {
            displayMonth = "ts.aug";
        } else if (month == 8)
        {
            displayMonth = "ts.sept";
        } else if (month == 9)
        {
            displayMonth = "ts.oct";
        } else if (month == 10)
        {
            displayMonth = "ts.nov";
        } else if (month == 11)
        {
            displayMonth = "ts.dec";
        }

        return displayMonth;
    }

    public String generatSearchSQL(Search search)
    {
        String sql = null;

        if (search != null)
        {
            StringBuilder sqlBuilder = new StringBuilder();
            
            if(search.getSearchUser() != null && search.getSearchUser() != -1)
            {
                sqlBuilder.append(" userId = :userId");
            }
            
            if(search.getSearchProjectId() != null && search.getSearchProjectId() != -1)
            {
                if (sqlBuilder.toString().length() > 1)
                {
                    sqlBuilder.append(" AND ");
                }
                
                sqlBuilder.append(" projectId = :projectId");
            }
            
            if(search.getSearchOptionId() !=null && search.getSearchOptionId() != -1)
            {
                if (sqlBuilder.toString().length() > 1)
                {
                    sqlBuilder.append(" AND ");
                }

                sqlBuilder.append("optionId = :optionId");
            }
            
            if(search.getSearchMonth() != null && search.getSearchMonth() != -1)
            {
                if (sqlBuilder.toString().length() > 1)
                {
                    sqlBuilder.append(" AND ");
                }

                Date startDate = getFirstDateOfMonth(search.getSearchMonth());
                Date endDate = getLastDateOfMonth(search.getSearchMonth());

                sqlBuilder.append("bookingDate >= :startDate AND bookingDate <= :endDate");
            }

            if (search.getSearchFromDate() != null && search.getSearchToDate() != null && !sqlBuilder.toString().contains("startDate"))
            {
                if (sqlBuilder.toString().length() > 1)
                {
                    sqlBuilder.append(" AND (bookingDate >= :fromDate AND bookingDate <= :toDate)");
                } else
                {
                    sqlBuilder.append("bookingDate >= :fromDate AND bookingDate <= :toDate");
                }
            } 
            else if (search.getSearchFromDate() != null && !sqlBuilder.toString().contains("startDate"))
            {
                if (sqlBuilder.toString().length() > 1)
                {
                    sqlBuilder.append(" AND ");
                }

                sqlBuilder.append("bookingDate >= :fromDate");
            } 
            else if (search.getSearchToDate() != null && !sqlBuilder.toString().contains("startDate"))
            {
                if (sqlBuilder.toString().length() > 1)
                {
                    sqlBuilder.append(" AND ");
                }

                sqlBuilder.append("bookingDate <= :toDate");
            }

            if (StringUtils.isNotBlank(sqlBuilder.toString()))
            {
                sql = "from Booking where " + sqlBuilder.toString();
            }
        }

        return sql;
    }

    public Booking manipulateBooking(Booking booking)
    {
        Date bookingTime = booking.getBookingDate();

        String startTimeStr = booking.getStartTimeStr();
        Date startTime = dateModification(bookingTime, startTimeStr);

        if (startTime != null)
        {
            booking.setStartTime(startTime);
        }

        String endTimeStr = booking.getEndTimeStr();
        Date endTime = dateModification(bookingTime, endTimeStr);

        if (endTime != null)
        {
            booking.setEndTime(endTime);
        }

        if (startTime != null && endTime != null)
        {
            int duration = dateDiffInMin(startTime, endTime, booking.getBreakTime());

            booking.setDuration(duration);
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ test booing submit" + booking.getProjectId());

        Integer projectId = booking.getProjectId();
        Project project = new Project();
        project.setProjectId(projectId);

        booking.setProject(project);

        Integer optionId = booking.getOptionId();
        BookingOption bookingOption = new BookingOption();
        bookingOption.setOptionId(optionId);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + optionId);
        booking.setBookingOption(bookingOption);

        return booking;
    }

    public Date getFirstDateOfMonth(Integer month)
    {
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.MONTH, month);
        currentCal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = currentCal.getTime();

        return startDate;
    }

    public Date getLastDateOfMonth(Integer month)
    {
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.MONTH, month);
        int lastDayOfMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentCal.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
        Date endDate = currentCal.getTime();

        return endDate;
    }

    public String pwdGenerator()
    {
        char[] chars = "abcdefghijklmnopqrstuvwxyzABSDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();

        Random r = new Random(System.currentTimeMillis());

        char[] id = new char[8];

        for (int i = 0; i < 8; i++)
        {
            id[i] = chars[r.nextInt(chars.length)];
        }

        return new String(id);
    }

    public Map<Integer, String> allMonths()
    {
        Map<Integer, String> months = new HashMap<Integer, String>();

        months.put(0, "ts.jan");
        months.put(1, "ts.feb");
        months.put(2, "ts.mar");
        months.put(3, "ts.apr");
        months.put(4, "ts.may");
        months.put(5, "ts.jun");
        months.put(6, "ts.jul");
        months.put(7, "ts.aug");
        months.put(8, "ts.sept");
        months.put(9, "ts.oct");
        months.put(10, "ts.nov");
        months.put(11, "ts.dec");

        return months;
    }

    public String getPageLayout()
    {
        return pageLayout;
    }

    public void setPageLayout(String pageLayout)
    {
        this.pageLayout = pageLayout;
    }

    public Map<String, BookingOption> getBookingOptions()
    {
        return bookingOptions;
    }

    public void setBookingOptions(Map<String, BookingOption> bookingOptions)
    {
        this.bookingOptions = bookingOptions;
    }

    public Project getProjectById()
    {
        return projectById;
    }

    public void setProjectById(Project projectById)
    {
        this.projectById = projectById;
    }

    public User getUserProfile()
    {
        return userProfile;
    }

    public void setUserProfile(User userProfile)
    {
        this.userProfile = userProfile;
    }

    public Integer getCurrentMonth()
    {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth)
    {
        this.currentMonth = currentMonth;
    }

    public String getDisplyMonth()
    {
        return displyMonth;
    }

    public void setDisplyMonth(String displyMonth)
    {
        this.displyMonth = displyMonth;
    }

}
