/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.timesheet.export;

import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.timesheet.Booking;
import com.timesheet.User;
import com.timesheet.utils.Utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author unixmac
 */
public class PdfGenerator extends AbstractITextPdfViewCustom
{
   
    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        
        /* Add header*/
        Font fontHeader = FontFactory.getFont(FontFactory.TIMES_ROMAN, 22, Font.BOLD);
        Paragraph headerPara = new Paragraph("Time sheet report", fontHeader);
        headerPara.setSpacingAfter(20f);
        document.add(headerPara);
        
        /*Add user info*/
        User userProfile = (User)model.get("userprofile");
        Font fontUserInfo = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD);
        document.add(new Paragraph("Name : "+userProfile.getName(), fontUserInfo));
        document.add(new Paragraph("User no. : "+userProfile.getUserIdentifier(), fontUserInfo));
        document.add(new Paragraph("Department : "+userProfile.getDepartment(), fontUserInfo));
        document.add(new Paragraph("Email : "+userProfile.getEmail(), fontUserInfo));
        Paragraph spacing = new Paragraph("");
        spacing.setSpacingAfter(20f);
        document.add(spacing);
        
        
         
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[] {5.0f, 3.0f, 3.0f, 3.0f, 10.0f});
        table.setSpacingBefore(10);
         
        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD);
        //font.setColor(BaseColor.WHITE);
   
        // define table header cell
        PdfPCell cell = new PdfPCell();
        //cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        cell.setBorderWidth(2.0f);
        cell.setBorder(Rectangle.BOTTOM);
        // get data model which is passed by the Spring container
        List<Booking> bookings = (List<Booking>) model.get("bookings");
        
        // write table header
        cell.setPhrase(new Phrase("Project", font));
        table.addCell(cell);
        // write table header
        cell.setPhrase(new Phrase("Option", font));
        table.addCell(cell);
        // write table header
        cell.setPhrase(new Phrase("Date", font));
        table.addCell(cell);
        // write table header
        cell.setPhrase(new Phrase("Duration", font));
        table.addCell(cell);
        // write table header
        cell.setPhrase(new Phrase("Description", font));
        table.addCell(cell);
        
        int sum = 0;
        
        Font fontData = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11);
        
        for(Booking booking : bookings)
        {
            PdfPCell cell1 = new PdfPCell(new Phrase(booking.getProject().getProjectId()+"-"+booking.getProject().getName(), fontData));
            cell1.setBorder(Rectangle.BOTTOM);
            cell1.setPadding(5);
            table.addCell(cell1);
            PdfPCell cell2 = new PdfPCell(new Phrase(booking.getBookingOption().getOptionLabel(), fontData));
            cell2.setBorder(Rectangle.BOTTOM);
            cell2.setPadding(5);
            table.addCell(cell2);
            PdfPCell cell3 = new PdfPCell(new Phrase(formatDate(booking.getBookingDate()), fontData));
            cell3.setBorder(Rectangle.BOTTOM);
            cell3.setPadding(5);
            table.addCell(cell3);
            int hh = booking.getDuration()/60;
            int mm = booking.getDuration()%60;
            PdfPCell cell4 = new PdfPCell(new Phrase(hh+":"+mm, fontData));
            cell4.setBorder(Rectangle.BOTTOM);
            cell4.setPadding(5);
            table.addCell(cell4);
            PdfPCell cell5 = new PdfPCell(new Phrase(StringEscapeUtils.escapeHtml(booking.getDescription()), fontData));
            cell5.setBorder(Rectangle.BOTTOM);
            cell5.setPadding(5);
            table.addCell(cell5);
            
            sum += booking.getDuration();
        }
        
        document.add(table);
        
        int sumHH = sum/60;
        int sumMM = sum%60;
        
        document.add(new Paragraph("Sum : "+sumHH+":"+sumMM, fontUserInfo));
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String formatDate(Date date)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        return dateFormat.format(date);
    }
    
}
