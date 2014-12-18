<%-- 
    Document   : company-registration
    Created on : Jul 6, 2014, 3:23:27 PM
    Author     : unixmac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Company Registration</title>
        <link href="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />

        <style>
            /* Sticky footer styles
-------------------------------------------------- */
            html {
                position: relative;
                min-height: 100%;
            }
            body {
                /* Margin bottom by footer height */
                padding-top: 45px;
                margin-bottom: 60px;
            }
            .footer {
                position: absolute;
                bottom: 0;
                width: 100%;
                /* Set the fixed height of the footer here */
                height: 60px;
                background-color: #f5f5f5;

            }
            .container {
                width: auto;
                max-width: 430px;
                padding: 0 15px;

            }
            .container .text-muted {
                margin: 20px 0;
            }
        </style>
    </head>
    <body>
        <!-- Fixed navbar -->
        <div class="navbar navbar-default navbar-fixed-top" role="navigation">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#"><span class="glyphicon glyphicon-time"></span> <spring:message code="ts.title" text="Time sheet" /></a>
                </div>
                <!--div class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Home</a></li>
                        <li><a href="#about">About</a></li>
                        <li><a href="#contact">Contact</a></li>
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Dropdown <span class="caret"></span></a>
                            <ul class="dropdown-menu" role="menu">
                                <li><a href="#">Action</a></li>
                                <li><a href="#">Another action</a></li>
                                <li><a href="#">Something else here</a></li>
                                <li class="divider"></li>
                                <li class="dropdown-header">Nav header</li>
                                <li><a href="#">Separated link</a></li>
                                <li><a href="#">One more separated link</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div>
        </div>

        <!-- Begin page content -->
        <div class="container">
            <div class="page-header">
                <h3>Company registration</h3>
            </div>
            <form:form role="form" method="post" action="/timesheet/registercompany"
                       commandName="company" enctype="multipart/form-data" >
                <div class="form-group">
                    <form:label path="name" for="name">Name</form:label>
                    <form:input path="name" class="form-control" id="name"
                                placeholder="Enter name" />
                </div>
                <div class="form-group">

                    <form:label path="description" for="description">Description</form:label>
                    <form:textarea path="description" class="form-control" rows="5" cols="31" id="description" placeholder="Enter description"></form:textarea>
                    </div>
                    <div class="form-group">
                    <form:label path="address" for="address">Address</form:label>
                    <form:textarea path="address" class="form-control" rows="5" cols="31" id="address" placeholder="Enter address"></form:textarea>
                    </div>
                    <div class="form-group">
                    <form:label path="email" for="email">Email</form:label>
                    <form:input path="email" class="form-control" id="email"
                                placeholder="Enter email" />
                </div>
                <div class="form-group">
                    <form:label path="comSize" for="comSize">Company size</form:label>
                    <form:input path="comSize" class="form-control" id="comSize"
                                placeholder="Company size" />
                </div>
                <div class="form-group">
                    <form:label path="comType" for="comType">Company type</form:label>
                    <form:input path="comType" class="form-control" id="comType"
                                placeholder="Company size" />
                </div>
                <div class="form-group">
                    <label for="logo">Upload logo</label>
                    <input type="file" name="file" class="form-control" id="logo">
                </div>
                <a href="<c:url value="/index" />" class="btn btn-default" >Cancel</a>
                <input type="submit"  class="btn btn-success" value="Register" />
            </form:form>
        </div>
        <br/>
        <br/>
        <div class="footer">
            <div class="container">
                <p class="text-muted">Place sticky footer content here.</p>
            </div>
        </div>
    </body>
</html>
