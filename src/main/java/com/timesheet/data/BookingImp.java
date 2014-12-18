/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.data;

import com.timesheet.Booking;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author unixmac
 */
@Repository
public class BookingImp implements BookingOp
{
    @Autowired
    SessionFactory sessionFactory;
    
    @Override
    public List<Booking> getBookingByUser(Integer userId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Booking> getBookingByDate(Date date, Integer userId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Booking> getBookingByDateRange(Date startDate, Date endDate, Integer userId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Booking> getBookingByMonth(Integer month, Integer year, Integer userId)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addBooking(Booking booking)
    {
        sessionFactory.getCurrentSession().save(booking);
    }

    @Override
    public void deleteBooking(Integer bookingId)
    {
        Booking booking = (Booking) sessionFactory.getCurrentSession().load(Booking.class, bookingId);

        if (null != booking)
        {
            sessionFactory.getCurrentSession().delete(booking);
        }
    }

    @Override
    public List<Booking> getAllBookings()
    {
        return sessionFactory.getCurrentSession().createQuery("from Booking").list();
    }

    @Override
    public Booking getBookingById(Integer bookingId)
    {
       
         Booking booking = (Booking) sessionFactory.getCurrentSession().get(Booking.class, bookingId);
         
         return booking;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateBooking(Booking booking)
    {
        sessionFactory.getCurrentSession().update(booking);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Booking> getBookingByUserAndMonth(Integer userId, Integer month)
    {
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(Calendar.MONTH, month);
        int lastDayOfMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        currentCal.set(Calendar.DAY_OF_MONTH, 1);
        Date startDate = currentCal.getTime();
        System.out.println("@@@@@@@@@@@@@@@@Start of month :"+startDate);
        currentCal.set(Calendar.DAY_OF_MONTH,lastDayOfMonth);
        Date endDate = currentCal.getTime();
        System.out.println("@@@@@@@@@@@@@@@@Start of month :"+endDate);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return sessionFactory.getCurrentSession().createQuery("from Booking where bookingDate >= :startDate AND bookingDate <= :endDate AND userId = :userId").setDate("startDate", startDate).setDate("endDate", endDate).setInteger("userId", userId).list();
    }

    
}
