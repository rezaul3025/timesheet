/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.data.BookingOp;
import com.timesheet.Booking;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author unixmac
 */
@Service
public class BookingServiceImp implements BookingService
{
    @Autowired
    BookingOp bookingOperation;

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
    @Transactional
    public void addBooking(Booking booking)
    {
       bookingOperation.addBooking(booking);
    }

    @Override
    @Transactional
    public void dleteBooking(Integer bookingId)
    {
       bookingOperation.deleteBooking(bookingId);
    }

    @Override
    @Transactional
    public List<Booking> getAllBookings()
    {
        return bookingOperation.getAllBookings();
    }

    @Override
    @Transactional
    public Booking getBookingById(Integer bookingId)
    {
       
        return bookingOperation.getBookingById(bookingId);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public void updateBooking(Booking booking)
    {
           bookingOperation.updateBooking(booking);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public List<Booking> getBookingByUserAndMonth(Integer userId, Integer month)
    {
        return bookingOperation.getBookingByUserAndMonth(userId, month);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
