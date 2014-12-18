/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.export;

import com.timesheet.Booking;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

/**
 *
 * @author unixmac
 */
public class ExcelExportView extends AbstractExcelView
{

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook hssfw, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception
    {
        hsr1.setHeader("Pragma", "public");
        hsr1.setHeader("Expires", "0");
        hsr1.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        hsr1.setHeader("Content-type", "application-download");
        hsr1.setHeader("Content-Disposition", "attachment; filename=bookings.xls");
        hsr1.setHeader("Content-Transfer-Encoding", "binary");
        
        HSSFSheet excelSheet = hssfw.createSheet("Time sheet report");

        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Project");
        excelHeader.createCell(1).setCellValue("Option");
        excelHeader.createCell(2).setCellValue("Date");
        excelHeader.createCell(3).setCellValue("Duration");
        excelHeader.createCell(4).setCellValue("Description");

        List<Booking> bookings = (List<Booking>) map.get("bookings");
        int record = 1;

        for (Booking booking : bookings)
        {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(booking.getProject().getProjectId() + "-" + booking.getProject().getName());
            excelRow.createCell(1).setCellValue(booking.getBookingOption().getOptionLabel());
            excelRow.createCell(2).setCellValue(booking.getBookingDate().toString());
            excelRow.createCell(3).setCellValue(booking.getDuration().toString());
            excelRow.createCell(4).setCellValue(booking.getDescription());
        }

    }

}
