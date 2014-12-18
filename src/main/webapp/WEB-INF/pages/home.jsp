<%-- 
    Document   : home
    Created on : Jun 7, 2014, 7:42:04 PM
    Author     : unixmac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Employee time sheet </title>

        <link href="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />
        <link href="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/css/datepicker.css" />" rel="stylesheet" />
        <link href="<c:out value="${pageContext.request.contextPath}/resources/jquery/ui/jquery-ui.min.css" />" rel="stylesheet" />
        <link href="<c:out value="${pageContext.request.contextPath}/resources/jquery/ui/jquery.ui.theme.css" />" rel="stylesheet" />
        <link href="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/css/datetimepicker/bootstrap-datetimepicker.min.css" />" rel="stylesheet" />
        <link href="<c:out value="${pageContext.request.contextPath}/resources/DataTables-1.10.0/media/css/jquery.dataTables.min.css" />" rel="stylesheet" />
        <link href="<c:out value="${pageContext.request.contextPath}/resources/DataTables-1.10.0/media/css/jquery.dataTables_themeroller.min.css" />" rel="stylesheet" />

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
        <script src="<c:out value="${pageContext.request.contextPath}/resources/jquery/jquery-ui.min.js" />"></script>
        <script src="<c:out value="${pageContext.request.contextPath}/resources/DataTables-1.10.0/media/js/jquery.dataTables.min.js" />"></script>

        <style>
            body {
                padding-top: 100px;
                padding-bottom: 20px;
            }
            // .modal-content {
            //   width: 1000px;
            // margin-left: -200px;
            //}

            #bookingUpdateModel .modal-dialog
            {
                width: 100%;
            }

            .modal 
            {
                overflow-y: auto;
            }
            /* custom class to override .modal-open */
            .modal-noscrollbar 
            {
                margin-right: 0 !important;
            }
            table.dataTable thead th, table.dataTable thead td{
                border-bottom: 0 !important;
            }
            .dataTables_wrapper .no-footer 
            {
                border-bottom: 0 !important;
            }
            .col-md-12 .panel-body {
                padding: 0px !important;
                padding-bottom: 15px !important;
                padding-top: 15px !important;
            }

            .navbar-brand {
                float: left !important;
                font-size: 18px !important;
                height: 50px !important;
                line-height: 20px !important;
                padding: 12px 15px 12px 15px !important;
            }

            .navbar-nav > li > a {padding-top:25px !important; padding-bottom:5px !important;}
            .navbar,.navbar-collapse {min-height:80px !important}

            .selectedbk
            {
                background-color: #f5f5f5;
            }

            .delete-btn 
            {
                color: #d43f3a;;
            }

            #userAssignTable td
            {
                padding: 10px;
            }

            #userAssignTable  th
            {
                padding-left: 10px;
            }

            #userAssignTable  select
            {
                min-width: 200px;
            }

        </style>

        <script>
            $(document).ready(function() {

                /************************ Booking list table *******************************/
                $('#bookinglist').dataTable({
                    "oLanguage": {"sSearch": ""},
                    "bAutoWidth": false,
                    "bJQueryUI": false,
                    "scrollCollapse": true,
                    "paging": false,
                    "aoColumns": [
                        {"bSortable": false, sWidth: "22%"},
                        {"bSortable": false},
                        {"bSortable": false, sWidth: "9%"},
                        {"bSortable": false},
                        {"bSortable": false},
                        {"bSortable": false},
                        {"bSortable": false},
                        {"bSortable": false, sWidth: "30%", 'sClass': 'selectedbk'},
                        {"bSortable": false, sWidth: "3%"},
                        {"bSortable": false, sWidth: "3%"}
                    ]
                });

                //Remove default search option
                $("div#bookinglist_filter").css("display", "none");

                //remove the line before dialog button
                $("div#bookinglist_length").children().remove("label");

                //remove jobhome_info about number rows
                $("#bookinglist_info").remove();

                //Remove header background class
                $("#bookinglist thead tr th").removeClass("selectedbk");

                /************************ End Booking list table *******************************/

                /********Add current date value to booking form**********/
                var curDate = new Date();

                var mm = curDate.getMonth() + 1;
                var dd = curDate.getDate();
                var yyyy = curDate.getFullYear();
                var hh = curDate.getHours();
                var MM = curDate.getMinutes();

                if (dd.toString().length == 1)
                {
                    dd = "0" + dd;
                }

                if (mm.toString().length == 1)
                {
                    mm = "0" + mm;
                }

                if (MM.toString().length == 1)
                {
                    MM = "0" + MM;
                }

                $('#bookingDate').val(mm + "/" + dd + "/" + yyyy);
                $('#startTime').val(hh + ":" + MM);
                $('#endTime').val(hh + ":" + MM);
                $('#breakTime').val("00:00");
                /********End: Add current date value to booking form****/

                /*************************Add & delete booking option***************************/
                var counter = 0;
                $("#addBookingOption").click(function() {
                    counter++;
                    var label = $("#optionLabel").val();
                    var useBudget = $("#useBudget").prop('checked');
                    var isDefault = $("#isDefault").prop('checked');

                    $("#bookingOptionList ul").append("<li id='oplist" + counter + "'>" + label + " " + useBudget + " " + isDefault + " <a onclick='return removeOption($(this))' href='#'><span class='glyphicon glyphicon-remove'></span></a></li>");

                    //alert(label+useBudget);
                    $.ajax({
                        url: "/timesheet/addbookingoption",
                        type: 'GET',
                        dataType: 'json',
                        data: {
                            Id: "oplist" + counter,
                            label: label,
                            useBudget: useBudget,
                            isDefault: isDefault
                        },
                        contentType: 'application/json',
                        mimeType: 'application/json',
                        success: function(data) {
                            //alert(data.useBudget)
                        },
                        error: function(data, status, er) {
                            alert("error: ");
                        }
                    });
                })

                $("#bookingOptionList > a").click(function() {

                    alert("tom");
                    $(this).parent().html("");
                })

                /*************************End: Add & delete booking option***************************/

                /**********Project select event while create & update booking **********************/
                $("#projectList").change(function() {

                    var projectId = $(this).val();

                    $("#bookingOption").html("");

                    getBookingOption(projectId, "create");
                });

                //Project select event whule booing update
                $("#projectlistupdate").change(function() {

                    var projectId = $(this).val();

                    $("#bookingOptionUpdate").html("");

                    getBookingOption(projectId, "update");
                });
                
                //Project select event while search booing 
                $("#projectListSearchAd").change(function() {

                    var projectId = $(this).val();

                    $("#searchByBookingOption").html("");

                    getBookingOption(projectId, "search");
                });

                /**********End : Project select event while create & update booking **********************/

                /**********Set background on booking row on click**********************/

                var previousSelect = null;

                var lastSelect = null;

                if (lastSelect != null)
                {
                    //alert('pre : '+previousSelect);
                    $('#' + lastSelect).addClass('selectedbk');
                }

                $('#bookinglist tr').click(function()
                {
                    if (previousSelect != null)
                    {
                        //alert('pre : '+previousSelect);
                        $('#' + previousSelect).removeClass('selectedbk');
                    }

                    previousSelect = $(this).attr("id");

                    var currentSelect = $(this).attr("id");
                    lastSelect = $(this).attr("id");

                    $('#' + currentSelect).addClass('selectedbk');
                    //alert($(this).attr("id"));


                })

                /*********** Assign user to project ***************/

                var userList = "";

                $('#btnAssignUser').click(function() {
                    $("#selAvailableUser option:selected").each(function() {
                        //alert($(this).val()+" "+$(this).text());
                        userList += userList == "" ? $(this).val() : "," + $(this).val();
                        $(this).remove();
                        $('#selAssignedUser').append('<option value="' + $(this).val() + '" >' + $(this).text() + '</option>');
                    });

                    var projectId = $('#projectListAssignUser').val();

                    assignUser(projectId, userList);
                });

            });

            function assignUser(projectId, userList)
            {
                alert("pro : " + projectId + " userList : " + userList);

                $.ajax({
                    url: "/timesheet/assign-user",
                    type: 'GET',
                    dataType: 'text',
                    data: {
                        projectId: projectId,
                        userList: userList
                    },
                    success: function(data) {
                        //alert(data.useBudget)
                    },
                    error: function(data, status, er) {
                        alert("Error on assign user: " + er);
                    }
                });
            }

            /**********End : Set background on booking row on click**********************/

            /*************** Get booking option for display ****************************/
            function getBookingOption(projectId, operation)
            {
                $.ajax({
                    url: "/timesheet/getprojectbyid/" + projectId,
                    type: 'GET',
                    dataType: 'json',
                    data: {},
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function(data) {
                        var project = data;
                        $.each(project.bookingOptions, function(i, item)
                        {
                            // alert(data.bookingOptions[i].optionLabel);
                            var optionId = project.bookingOptions[i].optionId;
                            var optionLabel = project.bookingOptions[i].optionLabel;
                            var useBudget = project.bookingOptions[i].useBudget;
                            var isDefault = project.bookingOptions[i].IsDefault;
                            //location.reload();

                            if (operation == "create")
                            {
                                $("#bookingOption").append("<option value='" + optionId + "'>" + optionLabel + "</option>");
                            }
                            else if (operation == "update")
                            {
                                $("#bookingOptionUpdate").append("<option value='" + optionId + "'>" + optionLabel + "</option>");
                            }
                            else if (operation == "search")
                            {
                                $("#searchByBookingOption").append("<option value='" + optionId + "'>" + optionLabel + "</option>");
                            }
                        });
                    },
                    error: function(data, status, er) {
                        alert("error:1 " + er);
                    }
                });

            }

            function removeOption(ele)
            {
                $(ele).parent().remove();

            }

            function check(id)
            {
                //alert(id);

                $.ajax({
                    url: "/timesheet/getbookingbyid/" + id,
                    type: 'GET',
                    dataType: 'json',
                    data: {},
                    contentType: 'application/json',
                    mimeType: 'application/json',
                    success: function(data) {

                        var item = data.project.projectId;

                        $("#projectlistupdate option").each(function()
                        {
                            if (item == $(this).val())
                            {
                                $(this).attr("selected", true);
                            }
                        });
                        $('#bookingIdUpdate').val(data.bookingId);
                        $('#dateupdate').val(dateFormat(data.bookingDate));
                        $('#startTimeupdate').val(timeFromDate(data.startTime));
                        $('#endTimeupdate').val(timeFromDate(data.endTime));
                        $('#breakTimeupdate').val(data.breakTime);
                        var description = data.description.replace(/<br\/>/g, '\n');
                        $('#BookingDescupdate').val(description);

                        $.each(data.project.bookingOptions, function(i, item)
                        {
                            // alert(data.bookingOptions[i].optionLabel);
                            var optionId = data.project.bookingOptions[i].optionId;
                            var optionLabel = data.project.bookingOptions[i].optionLabel;
                            var useBudget = data.project.bookingOptions[i].useBudget;
                            var isDefault = data.project.bookingOptions[i].IsDefault;

                            if (data.bookingOption.optionId == optionId)
                            {
                                $("#bookingOptionUpdate").append("<option selected='true' value='" + optionId + "'>" + optionLabel + "</option>");
                            }
                            else
                            {
                                $("#bookingOptionUpdate").append("<option  value='" + optionId + "'>" + optionLabel + "</option>");
                            }
                        });
                    },
                    error: function(data, status, er) {
                        alert("error: " + er);
                    }
                });

                return false;
            }

            function deleteBooking(id)
            {
                $("#bookingDeleteBtn").attr("href", id);

                return false;
            }

            function dateFormat(dateStr)
            {
                //alert(dateStr);
                var date = new Date(dateStr);

                var dd = date.getDate();
                var mm = date.getMonth() + 1;
                var yy = date.getFullYear();

                return mm + "/" + dd + "/" + yy;
            }

            function timeFromDate(dateStr)
            {
                var date = new Date(dateStr);

                var hh = date.getHours();

                var mm = date.getMinutes();

                return hh + ":" + mm;
            }

            $('#bookingUpdateModel').on('shown', function() {
                $('body').on('wheel.modal mousewheel.modal', function() {
                    return false;
                });
            }).on('hidden', function() {
                $('body').off('wheel.modal mousewheel.modal');
            });


        </script>
    </head>
    <body>

        <c:choose >
            <c:when test="${ empty layout or layout eq null}" >
                <c:set var="layout_el" value="container" />
            </c:when>
            <c:otherwise>
                <c:set var="layout_el" value="${layout}" />
            </c:otherwise>
        </c:choose>

        <div class="navbar navbar-default  navbar-fixed-top" role="navigation">
            <div class="${layout_el}">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand"  href="#"><img  src="<c:url value="/loadlogo" />" width="120" height="56" /></a>
                    <!--a class="navbar-brand" href="#"> <spring:message code="ts.title" text="Time sheet" /> | <spring:message code="${displaymonth}" text="Month" /> 2014 <span class="glyphicon glyphicon-time"></span></a-->
                </div>
                <div class="navbar-collapse collapse">
                    <ul class="nav navbar-nav">                      
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="ts.changemonth" text="Change month" /> <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <c:forEach var="month" items="${months}">
                                    <li><a href="<c:url value="/changemonth/${month.key}" />"><spring:message code="${month.value}" text="month" /></a></li>             
                                </c:forEach>
                            </ul>
                        </li>
                        <c:if test="${userprofile.role eq 'admin'}" >
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="ts.admin" text="Admin" /> <b class="caret"></b></a>
                                <ul class="dropdown-menu">
                                    <li><a href="#" data-toggle="modal" data-target="#createProjectModel"><spring:message code="ts.createproject" text="Create project" /></a></li>
                                    <li><a href="#" data-toggle="modal" data-target="#createUserModel"><span class="glyphicon glyphicon-user"></span> <spring:message code="ts.adduser" text="Add user" /> </a></li>
                                    <li><a href="#" data-toggle="modal" data-target="#assignUserToProject"> <spring:message code="ts.assignToUser" text="Assign project to user" /> </a></li>
                                </ul>
                            </li>
                        </c:if>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="ts.changelayout" text="Change layout" /> <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/changelayout/container-fluid" />" ><spring:message code="ts.changelayout.full" text="Full window" /></a></li>
                                <li><a href="<c:url value="/changelayout/container" />" ><spring:message code="ts.changelayout.center" text="Center layout" /></a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><spring:message code="ts.language" text="Lanuage" /> <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="?lang=en" ><spring:message code="ts.english" text="English" /></a></li>
                                <li><a href="?lang=de" ><spring:message code="ts.german" text="German" /></a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-export"></span> <spring:message code="ts.export" text="Export" /><b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#" data-toggle="modal" data-target="#pdfDownloadOption" ><spring:message code="ts.export.pdf" text="Export as PDF" /></a></li>
                                <li><a href="<c:url value="/exportascsv" />" ><spring:message code="ts.export.csv" text="Export as CSV" /></a></li>
                            </ul>
                        </li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span class="glyphicon glyphicon-user"></span> ${userprofile.name} <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#" data-toggle="modal" data-target="#resetPassword" ><spring:message code="ts.resetPassword" text="Reset passowrd" /></a></li>
                            </ul>
                        </li>
                        <li><a href="<c:url value="/logout" />"> <span class="glyphicon glyphicon-log-out" ></span> <spring:message code="ts.logout" text="Logout" /></a></li>
                    </ul>
                    <!--form class="navbar-form navbar-right" role="form">
                        <div class="form-group">
                            <input type="text" placeholder="Email" class="form-control">
                        </div>
                        <div class="form-group">
                            <input type="password" placeholder="Password" class="form-control">
                        </div>
                        <button type="submit" class="btn btn-success">Sign in</button>
                    </form-->
                </div><!--/.navbar-collapse -->
            </div>
        </div>
        <!-- Main jumbotron for a primary marketing message or call to action -->
        <!--div class="jumbotron">

        </div-->
        <div class="${layout_el}">
            <!-- Example row of columns -->
            <div class="row">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <!--div class="${layout_el}"-->

                            <form:form role="form" method="post" action="/timesheet/createbooking"
                                       commandName="booking" enctype="multipart/form-data">
                                <div class="row">
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label  for="projectList"><spring:message code="ts.project" text="Project" /></label>
                                            <form:select path="projectId" class="form-control" id="projectList">
                                                <form:option value="select">-----<spring:message code="ts.selectproject" text="Select project" />--------</form:option>
                                                <c:if test="${!empty projects}" >
                                                    <c:forEach items="${projects}" var="project" >
                                                        <form:option value="${project.projectId}">${project.projectId}-${project.name}</form:option>
                                                    </c:forEach>
                                                </c:if>
                                            </form:select>
                                        </div>
                                        <div class="form-group" id="bookingOptionContainer">
                                            <label  for="bookingOption"><spring:message code="ts.option" text="Options" /></label>
                                            <form:select path="optionId" class="form-control" id="bookingOption">
                                            </form:select>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group">
                                                    <form:label path="bookingDate" for="date"><spring:message code="ts.date" text="Date" /></form:label>
                                                    <form:input path="bookingDate" type="text" class="form-control  datepicker"  id="bookingDate" placeholder="Date" />
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="form-group" >
                                                    <form:label path="startTimeStr" for="startTime"><spring:message code="ts.from" text="From" /></form:label>
                                                    <form:input  path="startTimeStr" type="text" data-date-format="HH:mm" class="form-control " id="startTime" placeholder="Start time" />  
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="form-group">
                                                    <form:label path="endTimeStr"  for="endTime"><spring:message code="ts.to" text="To" /></form:label>
                                                    <form:input path="endTimeStr" type="text"  data-date-format="HH:mm" class="form-control" id="endTime" placeholder="end time" />
                                                </div>
                                            </div>
                                            <div class="col-md-2">
                                                <div class="form-group">
                                                    <form:label path="breakTime" for="breakTime"><spring:message code="ts.break" text="Break" /></form:label>
                                                    <form:input path="breakTime" type="text" class="form-control" id="breakTime" placeholder="Break" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <form:label  path="description" for="BookingDesc"><spring:message code="ts.bdescription" text="Booking description" /></form:label>
                                            <form:textarea path="description" class="form-control" rows="8" cols="31" id="BookingDesc" placeholder="Enter booking description"></form:textarea>
                                            </div>
                                            <input type="submit"  class="btn btn-success pull-right" value="<spring:message code="ts.save" text="Save changes" />" />
                                    </div>
                                </div>
                            </form:form>
                            <!--/div-->
                            <hr/>
                            <h4><span class="glyphicon glyphicon-search"></span> <spring:message code="ts.search" text="Search" /></h4>
                            <div class="row">
                                <form:form role="form" method="post" action="/timesheet/search"
                                           commandName="search" enctype="multipart/form-data">
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <form:select path="searchProjectId" class="form-control" id="projectListSearch">
                                                <form:option value="-1">--<spring:message code="ts.selectproject" text="Select project" />--</form:option>
                                                <c:if test="${!empty projects}" >
                                                    <c:forEach items="${projects}" var="project" >
                                                        <c:set var="isProjectSelected" value="" />
                                                        <c:if test="${project.projectId eq searchkey.searchProjectId}" >
                                                            <c:set var="isProjectSelected" value="selected" />
                                                        </c:if>  
                                                        <option ${isProjectSelected} value="${project.projectId}">${project.projectId}-${project.name}</option>
                                                    </c:forEach>
                                                </c:if>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <form:select path="searchMonth" class="form-control">
                                                <form:option value="-1">--<spring:message code="ts.selectoption" text="Select month" />--</form:option>
                                                <c:forEach var="month" items="${months}">
                                                    <c:set var="isMonthSelected" value="" />
                                                    <c:if test="${month.key eq searchkey.searchMonth}" >
                                                        <c:set var="isMonthSelected" value="selected" />
                                                    </c:if>                   
                                                    <option ${isMonthSelected} value="${month.key}"><spring:message code="${month.value}" text="month" /></option>
                                                </c:forEach>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <form:select path="searchOptionId" class="form-control">
                                                <form:option value="-1">--<spring:message code="ts.selectoption" text="Select option" />--</form:option> 
                                                <form:option value="0">January</form:option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <fmt:formatDate pattern="MM/dd/yyyy" value="${searchkey.searchFromDate}" var="searchFromDate" />
                                            <form:input path="searchFromDate" value="${searchFromDate}"  class="form-control" id="searchFromDate1" placeholder="From date" />
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <div class="form-group">
                                            <fmt:formatDate pattern="MM/dd/yyyy" value="${searchkey.searchToDate}" var="searchToDate" />
                                            <form:input path="searchToDate" value="${searchToDate}" class="form-control" id="searchToDate1" placeholder= "To date" />
                                        </div>
                                    </div>
                                    <div class="col-md-2">
                                        <input type="submit" class="btn btn-primary " value="<spring:message code="ts.search" text="Search" />" />
                                         <input type="button" data-toggle="modal" data-target="#searchOptionsModel" class="btn btn-primary " value="Advance search" />
                                    </div>
                                </form:form>
                            </div>
                        </div>
                        <div class="panel-body">
                            <c:if test="${!empty bookings}" >
                                <c:set var = "bookingSum" value="0" />
                                <Table id="bookinglist" class="table table-responsive " cellspacing="0" width="100%">
                                    <thead>
                                        <tr>
                                            <th><spring:message code="ts.project" text="Project" /></th>
                                            <th><spring:message code="ts.option" text="Options" /></th>
                                            <th><spring:message code="ts.date" text="Date" /></th>
                                            <th><spring:message code="ts.from" text="From" /></th>
                                            <th><spring:message code="ts.to" text="To" /></th>
                                            <th><spring:message code="ts.break" text="Break" /></th>
                                            <th><spring:message code="ts.duration" text="Duration" /></th>
                                            <th><spring:message code="ts.bdescription" text="Description" /></th>
                                            <th><spring:message code="ts.edit" text="Edit" /></th>
                                            <th><spring:message code="ts.delete" text="Delete" /></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${bookings}" var="booking" >
                                            <tr id="bk${booking.bookingId}">
                                                <td>
                                                    <p><b>${booking.project.projectId} - ${booking.project.name}</b></p>
                                                </td>
                                                <td>
                                                    <c:if test="${!empty booking.project.bookingOptions and booking.project.bookingOptions ne null}" >
                                                        <c:forEach items="${booking.project.bookingOptions}" var="option" >          
                                                            <c:if test="${booking.bookingOption.optionId eq option.optionId}" >
                                                                <p>${option.optionLabel}</p>
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <fmt:formatDate pattern="yyyy-MM-dd" value="${booking.bookingDate}" var="bDate" />
                                                    <p>${bDate}</p>
                                                </td>
                                                <td>
                                                    <fmt:formatDate pattern="HH:mm" value="${booking.startTime}" var="sTime" />
                                                    ${sTime}
                                                </td>
                                                <td>
                                                    <fmt:formatDate pattern="HH:mm" value="${booking.endTime}" var="eTime" />
                                                    ${eTime}
                                                </td>
                                                <td>
                                                    <p>${booking.breakTime}</p>
                                                </td>
                                                <td>
                                                    <fmt:parseNumber var="hh" integerOnly="true" type="number" pattern="00"  value="${booking.duration/60}" />
                                                    <fmt:formatNumber  minIntegerDigits="2"  var="mm" value="${booking.duration%60}" />
                                                    <p><b>${hh}:${mm}</b></p>
                                                </td>
                                                <td>
                                                    <p >${booking.description}</p>
                                                </td>
                                                <td>
                                                    <a href="#" data-toggle="modal" data-target="#bookingUpdateModel" onclick='return check($(this).attr("id"))' id="${booking.bookingId}" title="Edit"  ><span class="glyphicon glyphicon-edit"></span></a>
                                                </td>
                                                <td>
                                                    <a class="delete-btn" href="#" data-toggle="modal" data-target="#bookingDeleteModel" onclick='return deleteBooking($(this).attr("id"))' id="<c:url value="/deletebooking/${booking.bookingId}" />"   title="Delete"><span class="glyphicon glyphicon-remove"></span></a>
                                                </td>
                                            </tr>
                                            <c:set var = "bookingSum" value="${bookingSum + booking.duration}" />
                                            <fmt:parseNumber var="hh_sum" integerOnly="true" type="number" pattern="00"  value="${bookingSum/60}" />
                                            <fmt:formatNumber  minIntegerDigits="2"  var="mm_sum" value="${bookingSum%60}" />
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </c:if>
                        </div>
                        <div class="panel-footer">
                            <h4><spring:message code="ts.sum" text="To" />  <b>${hh_sum}:${mm_sum}</b></h4>
                        </div>
                    </div>
                </div>
            </div>
            <hr>
            <footer>  
                <p>&copy; <spring:message code="ts.title" text="To" /> 2014</p>
            </footer>
        </div> <!-- /container --> 

        <jsp:include page="assign_user.jsp" ></jsp:include>
        <jsp:include page="reset_password.jsp" ></jsp:include>
        <jsp:include page="search.jsp" ></jsp:include>
        
            <!-- Create project Modal -->
            <div class="modal fade" id="createProjectModel" tabindex="-1" role="dialog" aria-labelledby="createProjectModel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Create project</h4>
                        </div>
                    <form:form role="form" method="post" action="/timesheet/createproject"
                               commandName="project" enctype="multipart/form-data">
                        <div class="modal-body">

                            <div class="form-group">
                                <form:label for="name" path="name">Name</form:label>
                                <form:input path="name" class="form-control"
                                            id="name" placeholder="Enter name" />
                            </div>
                            <div class="form-group">
                                <form:label path="description" for="description">Description</form:label>
                                <form:textarea path="description" class="form-control" rows="5" cols="31" id="description" placeholder="Enter description"></form:textarea>
                                </div>
                                <div class="form-group">
                                <form:label path="identifier" for="identifier">Project identifier</form:label>
                                <form:input path="identifier" class="form-control" id="identifier"
                                            placeholder="Enter identifier" />
                            </div>
                            <div class="form-group">
                                <form:label path="budgetLimit" for="budgetLimit">Budget limit</form:label>
                                <form:input path="budgetLimit" class="form-control" id="budgetLimit"
                                            placeholder="Budget limit" />
                            </div>
                            <label>Booking options</label>
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div class="form-group">
                                        <label for="optionLabel">Option label</label>
                                        <input class="form-control" id="optionLabel"  placeholder="Option label" />
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="useBudget"> Use budget ?
                                        </label>
                                    </div>
                                    <div class="checkbox">
                                        <label>
                                            <input type="checkbox" id="isDefault"> Is default ?
                                        </label>
                                    </div>
                                    <a href="#" class=" btn btn-default" id="addBookingOption" >Add</a>
                                    <br/><br/>
                                    <div id="bookingOptionList" ><ul></ul></div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-primary" value="Create project" />
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <!-- Create user Modal -->
        <div class="modal fade" id="createUserModel" tabindex="-1" role="dialog" aria-labelledby="createUserModel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-user"></span> Add user</h4>
                    </div>
                    <form:form role="form" method="post" action="/timesheet/adduser"
                               commandName="user" enctype="multipart/form-data">
                        <div class="modal-body">

                            <div class="form-group">
                                <form:label for="name" path="name">Name</form:label>
                                <form:input path="name" class="form-control"
                                            id="name" placeholder="Enter name" />
                            </div>
                            <div class="form-group">
                                <form:label path="department" for="department">Department</form:label>
                                <form:input path="department" class="form-control" id="department"
                                            placeholder="Enter department" />
                            </div>
                            <div class="form-group">
                                <form:label path="userIdentifier" for="userIdentifier">User identifier</form:label>
                                <form:input path="userIdentifier" class="form-control" id="userIdentifier"
                                            placeholder="Enter identifier" />
                            </div>
                            <div class="form-group">
                                <form:label path="email" for="emailId">Email</form:label>
                                <form:input path="email" class="form-control" id="emailId"
                                            placeholder="Enter email" />
                            </div>
                            <div class="form-group">
                                <form:label path="password" for="password">Password</form:label>
                                <form:password path="password" class="form-control" id="password"
                                               placeholder="Enter password" />
                            </div>
                            <div class="form-group">
                                <form:label path="role" for="role">Role</form:label>
                                <form:select path="role" class="form-control" id="role">
                                    <form:option value="select">-----Select role ---</form:option>      
                                    <form:option value="admin">Admin</form:option>
                                    <form:option value="enduser">End user</form:option>
                                </form:select>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                            <input type="submit" class="btn btn-primary" value="Add user" />
                        </div>
                    </form:form>
                </div>
            </div>
        </div>
        <!-- Time picker -->
        <!-- Booking update Modal -->
        <div class="modal container fade" id="bookingUpdateModel" tabindex="-1" role="dialog" aria-labelledby="bookingUpdateModel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-edit" ></span> Update booking</h4>
                    </div>
                    <form:form role="form" method="post" action="/timesheet/updatebooking"
                               commandName="bookingupdate" enctype="multipart/form-data">
                        <div class="modal-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <form:input type="hidden" path="bookingId" id="bookingIdUpdate"/>
                                    <div class="form-group">
                                        <label  for="projectupdate">Project</label>
                                        <form:select path="projectId" class="form-control" id="projectlistupdate">
                                            <form:option value="select">-----Select project --------</form:option>
                                            <c:if test="${!empty projects}" >
                                                <c:forEach items="${projects}" var="project" >
                                                    <form:option value="${project.projectId}">${project.projectId}-${project.name}</form:option>
                                                </c:forEach>
                                            </c:if>
                                        </form:select>
                                    </div>
                                    <div class="form-group" >
                                        <label  for="bookingOptionUpdate">Options</label>
                                        <form:select path="optionId" class="form-control" id="bookingOptionUpdate">
                                        </form:select>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-4">
                                            <div class="form-group">
                                                <form:label path="bookingDate" for="dateupdate">Date</form:label>
                                                <form:input path="bookingDate" type="text" class="form-control" id="dateupdate" placeholder="Date" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group" >
                                                <form:label path="startTimeStr" for="startTimeupdate">From</form:label>
                                                <form:input  path="startTimeStr" type="text" data-date-format="HH:mm" class="form-control " id="startTimeupdate" placeholder="Start time" />  
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="endTimeStr"  for="endTimeupdate">To</form:label>
                                                <form:input path="endTimeStr" type="text"  data-date-format="HH:mm" class="form-control" id="endTimeupdate" placeholder="end time" />
                                            </div>
                                        </div>
                                        <div class="col-md-2">
                                            <div class="form-group">
                                                <form:label path="breakTime" for="breakTimeupdate">Break</form:label>
                                                <form:input path="breakTime" type="text" class="form-control" id="breakTimeupdate" placeholder="Break" />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <form:label  path="description" for="BookingDescupdate">Booking description</form:label>
                                        <form:textarea path="description" class="form-control" rows="9" cols="31" id="BookingDescupdate" placeholder="Enter booking description"></form:textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                <input type="submit"  class="btn btn-success pull-right" value="Save changes" />
                            </div>
                    </form:form>
                </div>
            </div>
        </div>

        <!-- Booking delete confirmation Modal -->
        <div class="modal container fade" id="bookingDeleteModel" tabindex="-1" role="dialog" aria-labelledby="bookingDeleteModel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-remove"></span> Delete booking</h4>
                    </div>
                    <div class="modal-body">
                        <p>Do you really want to delete this booking ?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                        <a id="bookingDeleteBtn" class="btn btn-danger pull-right" />Delete</a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Pdf download option -->
        <div class="modal container fade" id="pdfDownloadOption" tabindex="-1" role="dialog" aria-labelledby="pdfDownloadOption" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-file"></span> Booking PDF option</h4>
                    </div>
                    <div class="modal-body">
                        <a id="btnDownload" href="<c:url value="/exportaspdf/download" />" class="btn btn-default" /><span class="glyphicon glyphicon-save"></span> <spring:message code="ts.download" text="Download" /></a>
                        <a id="btnView" href="<c:url value="/exportaspdf/view" />" target="_blank" class="btn btn-default " /><span class="glyphicon glyphicon-open"></span> <spring:message code="ts.view" text="View" /></a>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Booking update model -->

        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/js/bootstrap.min.js" />"></script>
        <!--script src="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/js/bootstrap-datepicker.js" />"></script-->
        <script src="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/js/datetimepicker/moment.js" />"></script>
        <script src="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/js/datetimepicker/bootstrap-datetimepicker.min.js" />"></script>

        <script type="text/javascript">

                                                        $('#startTime').datetimepicker({
                                                            defaultDate: $("#date").val(),
                                                            pickDate: false,
                                                            // disables the date picker
                                                            pickTime: true, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });

                                                        $('#startTimeupdate').datetimepicker({
                                                            defaultDate: $("#date").val(),
                                                            pickDate: false,
                                                            // disables the date picker
                                                            pickTime: true, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });

                                                        $('#endTime').datetimepicker({
                                                            defaultDate: $("#date").val(),
                                                            pickDate: false,
                                                            // disables the date picker
                                                            pickTime: true, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });
                                                        $('#endTimeupdate').datetimepicker({
                                                            defaultDate: $("#date").val(),
                                                            pickDate: false,
                                                            // disables the date picker
                                                            pickTime: true, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });
                                                        $('#dateupdate').datetimepicker({
                                                            defaultDate: $("#dateupdate").val(),
                                                            pickDate: true,
                                                            // disables the date picker
                                                            pickTime: false, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });

                                                        $('#bookingDate').datetimepicker({
                                                            //defaultDate: $("#date").val(),

                                                            pickDate: true,
                                                            // disables the date picker
                                                            pickTime: false, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });

                                                        $('#searchFromDate').datetimepicker({
                                                            //defaultDate: $("#date").val(),

                                                            pickDate: true,
                                                            // disables the date picker
                                                            pickTime: false, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });
                                                        $('#searchToDate').datetimepicker({
                                                            //defaultDate: $("#date").val(),

                                                            pickDate: true,
                                                            // disables the date picker
                                                            pickTime: false, // disables de time picker
                                                            use24hours: true, // enables the 12-hour format time picker
                                                            pickSeconds: false
                                                        });




        </script>

    </body>
</html>
