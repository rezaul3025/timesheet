<%-- 
    Document   : assign_user
    Created on : Jul 19, 2014, 8:57:23 PM
    Author     : unixmac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <body>
        <!-- Assign user model -->
        <div class="modal fade" id="assignUserToProject" tabindex="-1" role="dialog" aria-labelledby="assignUserToProject" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                        <h4 class="modal-title" id="assignUserToProjectLevel"><span class="glyphicon glyphicon-remove"></span> Assign User to Project</h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="projectListAssignUser"> Project </label>
                             <select  class="form-control" id="projectListAssignUser">
                                <option value="-1">--<spring:message code="ts.selectproject" text="Select project" />--</option>
                                <c:if test="${!empty projects}" >
                                    <c:forEach items="${projects}" var="project" >
                                        <option value="${project.projectId}">${project.projectId}-${project.name}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>
                        <table id="userAssignTable" cellpadding="5" class="table-responsive ">
                            <tr>
                                <th>
                                    Available user(s)
                                </th>
                                <th>

                                </th>
                                <th>
                                    Assigned user(s)
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <select name="availavleUser" id="selAvailableUser" multiple size="15">
                                        <c:if test="${!empty users}" >
                                            <c:forEach items="${users}" var="user" >
                                                <option value="${user.userId}">${user.name}</option>
                                            </c:forEach>
                                        </c:if>
                                    </select>
                                </td>
                                <td>
                                    <button class=" btn btn-default" id="btnAssignUser" ><span class="glyphicon glyphicon-chevron-right"></span></button>
                                    <br/>
                                    <br/>
                                    <button class=" btn btn-default" id="btnRemoveUser" ><span class="glyphicon glyphicon-chevron-left"></span></button>
                                </td>
                                <td>
                                    <select name="assignedUser" id="selAssignedUser" multiple size="15">
                                        <option value="volvo">Volvo</option>
                                        <option value="saab">Saab</option>
                                        <option value="opel">Opel</option>
                                        <option value="audi">Audi</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
