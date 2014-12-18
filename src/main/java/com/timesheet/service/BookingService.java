/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.service;

import com.timesheet.Booking;
import java.util.Date;
import java.util.List;

/**
 *
 * @author unixmac
 */
public interface BookingService
{
    public List<Booking> getAllBookings();
    public List<Booking> getBookingByUser(Integer userId);
    public List<Booking> getBookingByUserAndMonth(Integer userId, Integer month);
    public List<Booking> getBookingByDate(Date date, Integer userId);
    public List<Booking> getBookingByDateRange(Date startDate , Date endDate, Integer userId);
    public List<Booking> getBookingByMonth(Integer month, Integer year, Integer userId);
    
    public Booking getBookingById(Integer bookingId);
    public void addBooking(Booking booking);
    public void updateBooking(Booking booking);
    public void dleteBooking(Integer bookingId);
}
