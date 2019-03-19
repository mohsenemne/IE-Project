<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mohsen
  Date: 2019-03-03
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="joboonja.domain.*" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page isELIgnored="false" %>--%>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>User</title>
    </head>
    <body>
        <ul>
            <li>id: ${requestScope.user.getUsername()}</li>
            <li>first name: ${requestScope.user.getFirstName()}</li>
            <li>last name: ${requestScope.user.getLastName()}</li>
            <li>jobTitle: ${requestScope.user.getJobTitle()}</li>
            <li>bio: ${requestScope.user.getBio()}</li>
                skills:

            <ul>
                <c:set var="db" scope="session" value="<%=Database.getInstance()%>"/>
                <c:forEach items="${requestScope.user.getSkills()}" var="skill">
                    <li>
                         ${skill.getName()}: ${skill.getPoints()}
                         <form action="/user/${requestScope.user.getUsername()}" method="POST">
                             <c:choose>
                                 <c:when test="${requestScope.user.equals(requestScope.visitor)}">
                                     <input type="hidden" name="action" value="delete"/>
                                     <input type="hidden" name="skill" value="${skill.getName()}"/>
                                     <button>Delete</button>
                                 </c:when>
                                 <c:otherwise>
                                     <c:if test="${!db.hasEndorsed(requestScope.visitor, requestScope.user, skill.getName())}">
                                         <input type="hidden" name="action" value="endorse"/>
                                         <input type="hidden" name="skill" value="${skill.getName()}"/>
                                         <input type="hidden" name="endorser" value="${requestScope.visitor.getUsername()}"/>
                                         <button>Endorse</button>
                                     </c:if>
                                 </c:otherwise>
                             </c:choose>
                         </form>
                    </li>
                </c:forEach>
            </ul>
        </ul>

        <c:if test="${requestScope.user.equals(requestScope.visitor)}">
            Add Skill:
            <form action="/user/${requestScope.user.getUsername()}" method="POST">
                <input type="hidden" name="action" value="addSkill"/>
                <select name="skills">
                    <c:forEach items="${db.getSkills()}" var="skill">
                        <c:if test="${!requestScope.visitor.hasSkill(skill)}">
                            <option value="${skill}">${skill}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <button>Add</button>
            </form>
        </c:if>
    </body>
</html>