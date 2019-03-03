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
        <title>Users</title>
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
                <th>Name</th>
                <th>Role</th>
            </tr>
            <c:forEach items="${requestScope.usersList}" var="user">
                <c:if test = "${user.getUsername() != '1'}">
                    <tr>
                        <td>${user.getUsername()}</td>
                        <td>${user.getName()}</td>
                        <td>${user.getJobTitle()}</td>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>