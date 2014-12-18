/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.timesheet.controller;

import com.timesheet.Booking;
import com.timesheet.BookingOption;
import com.timesheet.Company;
import com.timesheet.Login;
import com.timesheet.Project;
import com.timesheet.ResetPassword;
import com.timesheet.Search;
import com.timesheet.User;
import com.timesheet.service.BookingService;
import com.timesheet.service.CompanyService;
import com.timesheet.service.EmailService;
import com.timesheet.service.ProjectService;
import com.timesheet.service.SearchService;
import com.timesheet.service.SqlUtilsService;
import com.timesheet.service.UserService;
import com.timesheet.utils.Utils;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author unixmac
 */
@Controller
public class TimeSheetController
{

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;

    @Autowired
    SearchService searchService;

    @Autowired
    CompanyService companyService;

    @Autowired
    EmailService emailService;

    @Autowired
    SqlUtilsService sqlUtilsService;

    @Autowired
    Utils utils;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String initTimeSheet(Model model, Map<String, Object> attrMap)
    {
        attrMap.put("login", new Login());

        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String userLogin(@ModelAttribute("login") Login login, HttpSession session)
    {
        User user = userService.verifyUser(login);

        if (user != null)
        {
            //utils.setUserProfile(user);
            session.setAttribute("user", user);
            Calendar currentCal = Calendar.getInstance();
            //utils.setCurrentMonth(currentCal.get(Calendar.MONTH));
            session.setAttribute("currentMonth", currentCal.get(Calendar.MONTH));

            session.setAttribute("bookingsInSession", bookingService.getBookingByUserAndMonth(user.getUserId(), currentCal.get(Calendar.MONTH)));
            //utils.setDisplyMonth(utils.generateDisplayMonth(currentCal.get(Calendar.MONTH)));
            session.setAttribute("displayMonth", utils.generateDisplayMonth(currentCal.get(Calendar.MONTH)));

            return "redirect:/user-home";
        } 
        else
        {

            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session)
    {
        //utils.setUserProfile(null);
        session.setAttribute("user", null);

        return "redirect:/index";
    }

    @RequestMapping(value = "/user-home", method = RequestMethod.GET)
    public String loadUserProfile(Model model, Map<String, Object> attrMap, HttpSession session)
    {
        User userProfile = (User) session.getAttribute("user");

        if (userProfile != null)
        {
            attrMap.put("project", new Project());
            attrMap.put("user", new User());
            attrMap.put("booking", new Booking());
            attrMap.put("bookingupdate", new Booking());
            attrMap.put("search", new Search());
            attrMap.put("resetPassword", new ResetPassword());
            attrMap.put("bookings", session.getAttribute("bookingsInSession"));
            attrMap.put("layout", session.getAttribute("layout_se"));
            attrMap.put("projectbyid", session.getAttribute("projectbyid"));
            attrMap.put("userprofile", userProfile);
            attrMap.put("displaymonth", session.getAttribute("displayMonth"));
            attrMap.put("months", utils.allMonths());
            attrMap.put("searchkey", session.getAttribute("searchkeyInSession"));
            
            if(StringUtils.isNotBlank(userProfile.getRole()) && userProfile.getRole().equals("admin"))
            {
                attrMap.put("projects", projectService.getProjectByCompany(userProfile.getCompany().getCompanyId()));
                attrMap.put("users", userService.getUserByCompanyId(userProfile.getCompany().getCompanyId()));
            }
            else
            {
                attrMap.put("projects", userProfile.getProjects());
            }

            System.out.println("@@@@@@@@@@@@@ No. Pro assign :"+userProfile.getProjects().size());
            
            return "home";
        } 
        else
        {
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/changemonth/{month}", method = RequestMethod.GET)
    public String changeBookingMonth(@PathVariable("month") Integer month, HttpSession session)
    {
        User user = (User) session.getAttribute("user");

        if (user != null)
        {
            //utils.setCurrentMonth(month);
            //session.setAttribute("currentMonth", month);
            session.setAttribute("bookingsInSession", bookingService.getBookingByUserAndMonth(user.getUserId(), month));
            //utils.setDisplyMonth(utils.generateDisplayMonth(month));
            session.setAttribute("displayMonth", utils.generateDisplayMonth(month));
        }

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/createproject", method = RequestMethod.POST)
    public String createProject(@ModelAttribute("project") Project project, HttpSession session)
    {
        Map<String, BookingOption> bookingOption = utils.getBookingOptions();

        if (bookingOption != null && !bookingOption.isEmpty())
        {
            System.out.println("@@@@@@" + bookingOption.size());
            project.setBookingOptions(new HashSet(bookingOption.values()));
        }

        User userProfile = (User) session.getAttribute("user");

        if (userProfile != null)
        {
            project.setCompany(userProfile.getCompany());
        }

        projectService.addProject(project);

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/getprojectbyid/{projectId}", method = RequestMethod.GET)
    public @ResponseBody
    Project getProjectById(@PathVariable("projectId") Integer projectId, HttpSession session)
    {
        System.out.println("@@@@@@@@@@@@@@@@ " + projectService.getProjectById(projectId).getBookingOptions().size());

        //utils.setProjectById(projectService.getProjectById(projectId));
        session.setAttribute("projectbyid", projectService.getProjectById(projectId));

        return projectService.getProjectById(projectId);
    }

    @RequestMapping(value = "/adduser", method = RequestMethod.POST)
    public String addUser(@ModelAttribute("user") User user, HttpSession session, HttpServletRequest request)
    {
        User currentUser = (User) session.getAttribute("user");
        String password = utils.pwdGenerator();
        
        user.setCompany(currentUser.getCompany());
        user.setPassword(password);
        
        userService.addUser(user);
       
        //login link
        String srverName = request.getServerName();
        System.out.println(" @@@@@@@@@@@@@@@@@@@@@@ Email server :"+srverName);
        srverName = srverName.substring(srverName.indexOf(":")+1);
        System.out.println(" @@@@@@@@@@@@@@@@@@@@@@ Email server11 :"+srverName);
        
        //Send confirmation email
        String to = user.getEmail();
        String subject = "You are invited to join time sheet for "+currentUser.getCompany().getName();
        String body = "Your login information : Email " + user.getEmail() + "<br/> Password : " + password;
               body += "<br/><br/> Please <a href='http://"+srverName+":"+request.getLocalPort()+request.getContextPath()+"/index' > Login </a> and reset your password as soon as possible";

        try
        {
            emailService.sendMail(to, subject, body);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(TimeSheetController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/createbooking", method = RequestMethod.POST)
    public String createBooking(@ModelAttribute("booking") Booking booking, HttpSession session)
    {
        //Replace new line with html break comming from textarea
        if (booking.getDescription().contains("\n"))
        {
            String description = booking.getDescription();

            description = description.replace("\n", "<br/>");

            booking.setDescription(description);
        }

        User userProfile = (User) session.getAttribute("user");

        if (userProfile != null)
        {
            User user = new User();
            user.setUserId(userProfile.getUserId());

            booking.setUser(user);

            bookingService.addBooking(utils.manipulateBooking(booking));

            session.setAttribute("bookingsInSession", bookingService.getBookingByUserAndMonth(userProfile.getUserId(), (Integer) session.getAttribute("currentMonth")));
            
            Search search = (Search) session.getAttribute("searchkeyInSession");
            
            if(search != null)
            {
                session.setAttribute("searchkeyInSession", null);
            }
        }

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/updatebooking", method = RequestMethod.POST)
    public String updateBooking(@ModelAttribute("bookingupdate") Booking booking, HttpSession session)
    {
        //Replace new line with html break comming from textarea
        if (booking.getDescription().contains("\n"))
        {
            String description = booking.getDescription();

            description = description.replace("\n", "<br/>");

            booking.setDescription(description);
        }

        User userProfile = (User) session.getAttribute("user");

        if (userProfile != null)
        {
            User user = new User();
            user.setUserId(userProfile.getUserId());
            booking.setUser(user);
            bookingService.updateBooking(utils.manipulateBooking(booking));
                
            Search search = (Search) session.getAttribute("searchkeyInSession");
            
            if(search == null)
            {
                session.setAttribute("bookingsInSession", bookingService.getBookingByUserAndMonth(userProfile.getUserId(), (Integer) session.getAttribute("currentMonth")));
            }
            else
            {
                session.setAttribute("bookingsInSession", searchService.searchResult(search));
            }
        }

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/getbookingbyid/{bookingId}", method = RequestMethod.GET)
    public @ResponseBody
    Booking getBookingbyid(@PathVariable("bookingId") Integer bookingId)
    {
        //System.out.println("@@@@@@@@@ dId"+bookingId+" "+bookingService.getBookingById(bookingId).getProject().getBookingOptions().size());
        return bookingService.getBookingById(bookingId);

    }

    @RequestMapping(value = "/deletebooking/{bookingId}", method = RequestMethod.GET)
    public String deleteBooking(@PathVariable("bookingId") Integer bookingId, HttpSession session)
    {
        bookingService.dleteBooking(bookingId);

        User userProfile = (User) session.getAttribute("user");

        if (userProfile != null)
        {
            Search search = (Search) session.getAttribute("searchkeyInSession");
            
            if(search == null)
            {
                session.setAttribute("bookingsInSession", bookingService.getBookingByUserAndMonth(userProfile.getUserId(), (Integer) session.getAttribute("currentMonth")));
            }
            else
            {
                session.setAttribute("bookingsInSession", searchService.searchResult(search));
            }    
        }

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/changelayout/{layout}", method = RequestMethod.GET)
    public ModelAndView changeLayout(HttpSession session, @PathVariable("layout") String layout)
    {
        session.setAttribute("layout_se", layout);
        ModelAndView mav = new ModelAndView("redirect:/user-home");
        mav.addObject("layout_mav", layout);
        //utils.setPageLayout(layout);
        return mav;
    }

    @RequestMapping(value = "/addbookingoption", method = RequestMethod.GET)
    public @ResponseBody
    BookingOption addBookingOption(@RequestParam("Id") String id, @RequestParam("label") String label, @RequestParam("useBudget") boolean useBudget, @RequestParam("isDefault") boolean isDefault)
    {
        BookingOption bookingOption = new BookingOption();

        bookingOption.setOptionLabel(label);
        bookingOption.setUseBudget(useBudget);
        bookingOption.setIsDefault(isDefault);

        System.out.println("@@@@@@@@@@@" + label);

        utils.getBookingOptions().put(id, bookingOption);

        System.out.println("@@@@@@@@@@@ Op No." + utils.getBookingOptions().size());

        return bookingOption;
    }

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public String search(@ModelAttribute("search") Search search, HttpSession session)
    {
        Integer userFromSearch = search.getSearchUser();
        
        if(userFromSearch == null || userFromSearch == -1)
        {
            User userProfile = (User) session.getAttribute("user");
            
            search.setSearchUser(userProfile.getUserId());
        }
            
        session.setAttribute("bookingsInSession", searchService.searchResult(search));
        
        session.setAttribute("searchkeyInSession", search);

        return "redirect:/user-home";
    }

    @RequestMapping(value = "/exportaspdf/{pdfoption}", method = RequestMethod.GET)
    public ModelAndView exportAsPDF(HttpSession session, @PathVariable("pdfoption") String pdfoption)
    {
        ModelAndView mav = new ModelAndView("bookingsPDFview");

        User userProfile = (User) session.getAttribute("user");

        List<Booking> bookings = (List<Booking>) session.getAttribute("bookingsInSession");

        mav.addObject("pdfoption", pdfoption);
        mav.addObject("userprofile", userProfile);
        mav.addObject("bookings", bookings);

        return mav;//new ModelAndView("bookingsPDFview", "bookings", bookings);
    }

    @RequestMapping(value = "/exportascsv", method = RequestMethod.GET)
    public ModelAndView exportAsCSV(HttpSession session)
    {
        List<Booking> bookings = (List<Booking>) session.getAttribute("bookingsInSession");

        return new ModelAndView("bookingsExcelview", "bookings", bookings);
    }

    @RequestMapping(value = "/company-reg", method = RequestMethod.GET)
    public ModelAndView initCompanyReg()
    {
        ModelAndView mav = new ModelAndView("company-registration");

        mav.addObject("company", new Company());

        return mav;
    }

    @RequestMapping(value = "/registercompany", method = RequestMethod.POST)
    public ModelAndView registerComapny(@ModelAttribute("company") Company company, @RequestParam("file") MultipartFile file)
    {
        ModelAndView mav = new ModelAndView("redirect:/company-reg");

        try
        {

            Blob blob = new javax.sql.rowset.serial.SerialBlob(file.getBytes());

            company.setLogo(blob);

            Integer compnayId = companyService.addCompany(company);

            System.out.println("@@@@@@@@@@@@@@@ Company ID:" + compnayId);
            String password = utils.pwdGenerator();

            User user = new User();
            user.setCompany(company);
            user.setDepartment("Admin");
            user.setName(company.getName());
            user.setEmail(company.getEmail());
            user.setRole("admin");
            user.setPassword(password);
            user.setUserIdentifier("admin");

            userService.addUser(user);

            //Send confirmation email
            String to = company.getEmail();
            String subject = "Time sheet registration for your company ";
            String body = "Thank you for registration , Your login information : Email " + company.getEmail() + "\n Password : " + password;

           
                emailService.sendMail(to, subject, body);
            

        } 
        catch (SQLException ex)
        {
            Logger.getLogger(TimeSheetController.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex)
        {
            Logger.getLogger(TimeSheetController.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (MessagingException ex)
        {
            Logger.getLogger(TimeSheetController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return mav;
    }

    @RequestMapping(value = "/loadlogo")
    public void loadCompanyLogo(HttpServletResponse response, HttpSession session)
    {
        User userProfile = (User) session.getAttribute("user");

        response.setContentType("text/html;charset=UTF-8");

        try
        {
            response.setHeader("Content-Disposition", "inline;filename=tom");
            OutputStream out = response.getOutputStream();

            response.setContentType("");
            IOUtils.copy(userProfile.getCompany().getLogo().getBinaryStream(), out);
            out.flush();
            out.close();
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException ex)
        {
            Logger.getLogger(TimeSheetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @RequestMapping(value = "/assign-user" , method = RequestMethod.GET)
    //@ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity assignUser(@RequestParam("projectId") Integer projectId, @RequestParam("userList") String userList)
    {
        String[] userListArr = userList.split(",");

        System.out.println("Assign user @@@@@@@@@@@@@@@@@@@@@ " + userList);

        sqlUtilsService.insetIntoProjectUserAss(projectId, userList);
        
       return new ResponseEntity(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/reset-password" , method = RequestMethod.POST)
    public String resetPassword(@ModelAttribute("resetPassword") ResetPassword resetPassword, HttpSession session)
    {
        User user = (User) session.getAttribute("user");
        
        if(resetPassword.getNewPassword().equals(resetPassword.getConPassword()))
        {
            user.setPassword(resetPassword.getNewPassword());
        }
        
        this.userService.updateUser(user);
        
        return "redirect:/user-home";
    }

}
