<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>users</title>
</head>
<body>
<h3>List of all users</h3>
<table>
    <tr>
        <td>User ID</td>
        <td>Name</td>
        <td>Email</td>
        <td>Password</td>
        <td>Role</td>
        <td>Actions</td>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td>${user.password}</td>
            <td>${user.role}</td>
            <td>
                <form action="users" method="post">
                    <button type="submit" onclick="return confirm('Delete user #${user.id}?')">
                        Delete
                    </button>
                    <input type="hidden" name="user_id" value="${user.id}">
                    <input type="hidden" name="_method" value="delete">
                </form>
                <form action="users" method="post">
                    <button type="submit" onclick="return confirm('Do you want to give administrator role to user#${user.id}?')">
                        Update
                    </button>
                    <input type="hidden" name="user_id" value="${user.id}">
                    <input type="hidden" name="_method" value="put">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
