<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>wagons</title>
    <style>
        <%@ include file="css/openForms.css" %>
    </style>
</head>
<body>
<h3>List of all wagons</h3>
<table>
    <tr>
        <td>Wagon ID</td>
        <td>Train ID</td>
        <td>Number of seats</td>
        <td>Price for one place</td>
        <td>Service class</td>
        <td>Actions</td>
    </tr>
    <c:forEach items="${wagons}" var="wagon">
        <tr>
            <td>${wagon.id}</td>
            <td>${wagon.trainId}</td>
            <td>${wagon.numberOfSeats}</td>
            <td>${wagon.price}</td>
            <td>${wagon.serviceClass}</td>
            <td>
                <form action="wagons" method="post">
                    <button type="submit" onclick="return confirm('Delete wagon #${wagon.id}?')">
                        Delete
                    </button>
                    <input type="hidden" name="wagon_id" value="${wagon.id}">
                    <input type="hidden" name="_method" value="delete">
                </form>
                <button onclick="document.getElementById('openFormWagon').style.display='block'">
                    Update
                </button>
                <div id="openFormWagon" class="modal">
                    <form class="modal-content animate" action="wagons" method="post">
                        <div class="container">
                            <label for="seats">Number of seats</label>
                            <input type="number" id="seats" min="1" max="100" value="${wagon.numberOfSeats}" name="seats" required>
                            <p></p>

                            <label for="price">Price</label>
                            <input type="number" id="price" min="1" value="${wagon.price}" name="price" required>
                            <p></p>

                            <label for="service">Service</label>
                            <input type="text" id="service" value="${wagon.serviceClass}" name="service" required>

                            <button type="submit">Update</button>
                            <button class="cancelbtn" type="button" onclick="document.getElementById('openFormWagon').style.display='none'">Close</button>
                            <input type="hidden" name="_method" value="put">
                            <input type="hidden" name="wagon_id" value="${wagon.id}">
                        </div>
                    </form>
                </div>
            </td>
        </tr>
    </c:forEach>
</table>
<button onclick="document.getElementById('addFormWagon').style.display='block'">Add Wagon</button>
<div id="addFormWagon" class="modal">
    <form class="modal-content animate" action="wagons" method="post">
        <div class="container">
            <label for="trainId">Train Number</label>
            <input type="number" min="1" id="trainId" name="train_id" required>
            <p></p>

            <label for="numOfSeats">Number of seats</label>
            <input type="number" min="1" max="100" id="numOfSeats" name="seats" required>
            <p></p>

            <label for="priceForOne">Price</label>
            <input type="number" min="1" id="priceForOne" name="price" required>
            <p></p>

            <label for="serviceClass">Service</label>
            <input type="text" id="serviceClass" name="service" required>

            <button type="submit">Add</button>
            <button class="cancelbtn" type="button" onclick="document.getElementById('addFormWagon').style.display='none'">Close</button>
        </div>
    </form>
</div>
</body>
</html>
