<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>manager page</title>
    <%@ include file="header.jsp" %>
    <style>
        <%@ include file="css/table.css" %>
        <%@ include file="css/managerPage.css" %>
    </style>
</head>
<body>
<button class="tablink" onclick="openPage('Trains', this, '#22cbe5')" id="defaultOpen">Trains</button>
<button class="tablink" onclick="openPage('Users', this, '#22cbe5')" >Users</button>
<button class="tablink" onclick="openPage('Wagons', this, '#22cbe5')">Wagons</button>


<div id="Trains" class="tabcontent">
    <%@ include file="trains.jsp" %>
</div>

<div id="Users" class="tabcontent">
    <%@ include file="users.jsp" %>
</div>

<div id="Wagons" class="tabcontent">
    <%@ include file="wagons.jsp" %>
</div>
<script>
    <%@ include file="javaScript/managerPage.js" %>
    document.getElementById("defaultOpen").click();
</script>
</body>
</html>
