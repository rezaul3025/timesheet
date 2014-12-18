<%-- 
    Document   : search
    Created on : Dec 13, 2014, 9:18:10 PM
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
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Search model -->
        <div class="modal fade" id="searchOptionsModel" tabindex="-1" role="dialog" aria-labelledby="searchOptionsModel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">

                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="assignUserToProjectLevel"><span class="glyphicon glyphicon-remove"></span> Search options</h4>
                    </div>
                    <div class="modal-body">
                        <form:form role="form" method="post" action="/timesheet/search"
                                   commandName="search" enctype="multipart/form-data" class="form-horizontal" >
                            <div class="form-group">
                                <label for="projectListSearchAd" class="col-sm-3 control-label">Search by project</label>
                                <div class="col-sm-9">
                                    <form:select path="searchProjectId" class="form-control" id="projectListSearchAd">
                                        <form:option value="-1">--<spring:message code="ts.selectproject" text="Select project" />--</form:option>
                                        <c:if test="${!empty projects}" >
                                            <c:forEach items="${projects}" var="project" >
                                                <c:set var="isProjectSelectedAd" value="" />
                                                <c:if test="${project.projectId eq searchkey.searchProjectId}" >
                                                    <c:set var="isProjectSelectedAd" value="selected" />
                                                </c:if>  
                                                <option ${isProjectSelectedAd} value="${project.projectId}">${project.projectId}-${project.name}</option>
                                            </c:forEach>
                                        </c:if>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="searchByOptions" class="col-sm-3 control-label">Search by options</label>
                                <div class="col-sm-9">
                                    <form:select path="searchOptionId" class="form-control" id="searchByBookingOption">
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="searchByMonth" class="col-sm-3 control-label">Search by Month</label>
                                <div class="col-sm-9">
                                    <form:select path="searchMonth" class="form-control">
                                        <form:option value="-1">--<spring:message code="ts.selectoption" text="Select month" />--</form:option>
                                        <c:forEach var="month" items="${months}">
                                            <c:set var="isMonthSelected" value="" />
                                            <c:if test="${month.key eq searchkey.searchMonth}" >
                                                <c:set var="isMonthSelectedAd" value="selected" />
                                            </c:if>                   
                                            <option ${isMonthSelectedAd} value="${month.key}"><spring:message code="${month.value}" text="month" /></option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="searchByUser" class="col-sm-3 control-label">Search by user</label>
                                <div class="col-sm-9">
                                    <form:select path="searchUser" class="form-control">
                                        <form:option value="-1">--<spring:message code="ts.selectUser" text="Select User" />--</form:option>
                                        <c:forEach var="user" items="${users}">
                                            <c:set var="isUserSelectedAd" value="" />
                                            <c:if test="${user.userId eq searchkey.searchUser}" >
                                                <c:set var="isUserSelectedAd" value="selected" />
                                            </c:if>                   
                                            <option ${isUserSelectedAd} value="${user.userId}">${user.name}</option>
                                        </c:forEach>
                                    </form:select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="searchFromDate" class="col-sm-3 control-label">From date</label>
                                <div class="col-sm-9">
                                    <fmt:formatDate pattern="MM/dd/yyyy" value="${searchkey.searchFromDate}" var="searchFromDate" />
                                    <form:input path="searchFromDate" value="${searchFromDate}"  class="form-control" id="searchFromDate" placeholder="From date" />
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="searchToDate" class="col-sm-3 control-label">To date</label>
                                <div class="col-sm-9">
                                    <fmt:formatDate pattern="MM/dd/yyyy" value="${searchkey.searchToDate}" var="searchToDate" />
                                    <form:input path="searchToDate" value="${searchToDate}" class="form-control" id="searchToDate" placeholder= "To date" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-9 col-sm9">
                                    <input type="submit" class="btn btn-primary " value="<spring:message code="ts.search" text="Search" />" />
                                    <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
