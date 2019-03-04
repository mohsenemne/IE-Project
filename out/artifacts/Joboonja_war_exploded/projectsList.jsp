<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Mohsen
  Date: 2019-03-03
  Time: 18:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Projects</title>
        <style>
            table {
                text-align: center;
                margin: 0 auto;
            }
            td {
                min-width: 300px;
                margin: 5px 5px 5px 5px;
                text-align: center;
            }
        </style>
    </head>
    <body>
        <table>

            <tbody>
            <tr>
                <th>ID</th>
                <th>Title</th>
                <th>Budget</th>
            </tr>
            <c:forEach items="${requestScope.projectsList}" var="project">
                <tr>
                    <td>${project.getID()}</td>
                    <td>${project.getTitle()}</td>
                    <td>${project.getBudget()}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>