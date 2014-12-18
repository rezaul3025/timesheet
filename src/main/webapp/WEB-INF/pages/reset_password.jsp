<%-- 
    Document   : reset_password
    Created on : Jul 24, 2014, 3:12:27 PM
    Author     : Rezaul Karim
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <body>
        <!-- Reset password -->
        <div class="modal container fade" id="resetPassword" tabindex="-1" role="dialog" aria-labelledby="resetPassword" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form:form commandName="resetPassword"  role="form" method="POST" action="/timesheet/reset-password" >
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="myModalLabel"><span class="glyphicon glyphicon-file"></span> Reset password</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <form:label path="newPassword" for="newPassword">New password</form:label>
                            <form:password path="newPassword" class="form-control" id="newPassword" placeholder="Enter new password" />
                        </div>
                        <div class="form-group">
                            <form:label path="conPassword" for="conPassword">Confirm password</form:label>
                            <form:password path="conPassword" class="form-control" id="conPassword" placeholder="Confirm password" />
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <input type="submit" class="btn btn-primary" value="Reset" />
                    </div>
                    </form:form>
                </div>
            </div>
        </div>
    </body>
</html>
