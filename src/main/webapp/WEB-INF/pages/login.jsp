<%-- 
    Document   : login
    Created on : Jul 1, 2014, 8:53:02 PM
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
        <link href="<c:out value="${pageContext.request.contextPath}/resources/bootstrap-3.1.1/css/bootstrap.min.css" />" rel="stylesheet" />
        <title>Sign IN</title>
        <style>
            html {
                position: relative;
                min-height: 100%;
            }
            body {
                /* Margin bottom by footer height */
                margin-bottom: 60px;
                //background-color: #eee;
            }
            .footer {
                position: absolute;
                bottom: 0;
                width: 100%;
                /* Set the fixed height of the footer here */
                height: 60px;
                background-color: #f5f5f5;
            }


            /* Custom page CSS
            -------------------------------------------------- */
            /* Not required for template or sticky footer method. */

            .container {
                width: auto;
                max-width: 330px;
                padding: 0 15px;
            }
            .container .text-muted {
                margin: 20px 0;
            }

            form-signin {
                max-width: 330px;
                padding: 15px;
                margin: 0 auto;
            }
            .form-signin .form-signin-heading,
            .form-signin .checkbox {
                margin-bottom: 10px;
            }
            .form-signin .checkbox {
                font-weight: normal;
            }
            .form-signin .form-control {
                position: relative;
                height: auto;
                -webkit-box-sizing: border-box;
                -moz-box-sizing: border-box;
                box-sizing: border-box;
                padding: 10px;
                font-size: 16px;
            }
            .form-signin .form-control:focus {
                z-index: 2;
            }
            .form-signin input[type="email"] {
                margin-bottom: -1px;
                border-bottom-right-radius: 0;
                border-bottom-left-radius: 0;
            }
            .form-signin input[type="password"] {
                margin-bottom: 10px;
                border-top-left-radius: 0;
                border-top-right-radius: 0;
            }

        </style>
    </head>
    <body>
        <div class="nav pull-right container">
            
                    <a href="?lang=en" ><spring:message code="ts.en" text="English" /></a> |
                    <a href="?lang=de" ><spring:message code="ts.de" text="German" /></a>
               
        </div>
        <!-- Begin page content -->
        <div class="container">
            <div class="page-header">
                <h2 class="form-signin-heading"><span class="glyphicon glyphicon-time"></span> <spring:message code="ts.title" text="Time sheet" /> </h2>
            </div>
            <form:form class="form-signin" role="form" method="post" action="/timesheet/login"
                       commandName="login" enctype="multipart/form-data">
                <h4><spring:message code="ts.plsignin" text="Please sign in" /></h4>
                <form:input path="email" type="email" class="form-control" placeholder="Email address" required="required" autofocus="autofocus" />
                <form:input path="password" type="password" class="form-control" placeholder="Password" required="required" />
                <div class="checkbox">
                    <label>
                        <input type="checkbox" value="remember-me"> <spring:message code="ts.rememberme" text="Remember me" /> 
                    </label>
                </div>
                        <input class="btn btn-lg btn-primary btn-block" type="submit"  value="<spring:message code="ts.signin" text="Sign in " />"/>
                        <h4>Please <a href="<c:url value="/company-reg"/>" > <spring:message code="ts.comreg" text="register" /> </a> your company</h4>
            </form:form>
        </div>

        <div class="footer">
            <div class="container">
                <p class="text-muted">@Time sheet 2014</p>
            </div>
        </div>
    </body>
</html>
