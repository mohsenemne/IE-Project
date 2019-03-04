<%--
  Created by IntelliJ IDEA.
  User: Mohsen
  Date: 2019-03-03
  Time: 18:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="joboonja.domain.model.*" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Project</title>
    </head>
    <body>
        <ul>
            <li>id: ${requestScope.project.getID()}</li>
            <li>title: ${requestScope.project.getTitle()}</li>
            <li>description: ${requestScope.project.getDescription()}</li>
            <li>imageUrl: <img src="${requestScope.project.getImageURL()}" style="width: 50px; height: 50px;"></li>
            <li>budget: ${requestScope.project.getBudget()}</li>
        </ul>

        <c:if test="${requestScope.enableBid}">
            <form action="/project/${requestScope.project.getID()}" method="POST">
                <label for="bidAmount">Bid Amount:</label>
                <input type="number" name="bidAmount">
                <button>Submit</button>
            </form>
        </c:if>

    </body>
</html>

