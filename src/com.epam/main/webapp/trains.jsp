<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>trains</title>
    <style>
        <%@ include file="css/openForms.css" %>
    </style>
</head>
<body>
<h3>List of all trains</h3>
<table>
    <tr>
        <td>Train ID</td>
        <td>From country</td>
        <td>To country</td>
        <td>Date</td>
        <td>Start Time</td>
        <td>Finish Time</td>
        <td>Actions</td>
    </tr>

    <c:forEach items="${trains}" var="train">
        <tr>
            <td>${train.id}</td>
            <td>${train.from}</td>
            <td>${train.to}</td>
            <td>${train.date}</td>
            <td>${train.startTime}</td>
            <td>${train.finishTime}</td>
            <td>
                <form action="trains" method="post">
                    <form action="trains" method="post">
                        <button type="submit" onclick="return confirm('Delete train #${train.id}?')">
                            Delete
                        </button>
                        <input type="hidden" name="train_id" value="${train.id}">
                        <input type="hidden" name="_method" value="delete">
                    </form>
                </form>
                <button onclick="document.getElementById('openFormTrain').style.display='block'">
                    Update
                </button>
                <div id="openFormTrain" class="modal">
                    <form class="modal-content animate" action="trains" method="post">
                        <div class="container">
                            <label for="fromCountry">From Country</label>
                            <input type="text" id="fromCountry" value="${train.from}" name="from" required>

                            <label for="toCountry">To Country</label>
                            <input type="text" id="toCountry" value="${train.to}" name="to" required>

                            <label for="date">Date</label>
                            <input type="date" id="date" value="${train.date}" name="date" required>

                            <label for="startTime">Start Time</label>
                            <input type="time" id="startTime" value="${train.startTime}" name="startTime" required>

                            <label for="finishTime">Finish Time</label>
                            <input type="time" id="finishTime" value="${train.finishTime}" name="finishTime" required>

                            <button type="submit">Update</button>
                            <input type="hidden" name="_method" value="put">
                            <input type="hidden" name="train_id" value="${train.id}">
                        </div>
                    </form>
                </div>

            </td>
        </tr>
    </c:forEach>
</table>
<button class="roundBtn" onclick="document.getElementById('addFormTrain').style.display='block'">Add Train</button>
<div id="addFormTrain" class="modal">
    <form class="modal-content animate" action="trains" method="post">
        <div class="container">
            <label for="from">From Country</label>
            <input type="text" id="from" name="from" required>

            <label for="to">To Country</label>
            <input type="text" id="to" name="to" required>

            <label for="date_">Date</label>
            <input type="date" id="date_" name="date" required>

            <label for="start">Start Time</label>
            <input type="time" id="start" name="startTime" required>

            <label for="finish">Finish Time</label>
            <input type="time" id="finish" name="finishTime" required>

            <button type="submit">Add</button>
        </div>
    </form>
</div>

<script>
    <%@ include file="javaScript/openForm.js" %>
</script>
</body>
</html>
