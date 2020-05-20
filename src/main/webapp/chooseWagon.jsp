<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <%@ include file="header.jsp" %>
    <style>
        <%@ include file="css/chooseWagon.css" %>
        <%@ include file="css/button.css" %>
    </style>
</head>
<body>
<h2>Zebra Striped Table</h2>
<div class="modal-content animate">
    <table>
        <tr>
            <th>Wagon number</th>
            <th>Service Class</th>
            <th>Number of free seats</th>
            <th>Price</th>
            <th>Action</th>
        </tr>
        <c:forEach items="${wagons}" var="wagon">

            <tr>
                <form action="checkout" method="get">
                    <td><b>#</b>${wagon.id}</td>
                    <td>${wagon.serviceClass}</td>
                    <td><input type="number" placeholder="1" min="1" max="${wagon.numberOfSeats}" name="countOfSeats"> / ${wagon.numberOfSeats}</td>
                    <td>${wagon.price}<b>$</b></td>
                    <td>

                        <input type="hidden" name="wagonPrice" value="${wagon.price}">
                        <input type="hidden" name="wagonService" value="${wagon.serviceClass}">
                        <input type="hidden" name="wagonID" value="${wagon.id}">
                        <button type="submit" class="btn">Choose wagon</button>

                    </td>
                </form>

            </tr>

        </c:forEach>
    </table>
</div>

</body>
</html>
