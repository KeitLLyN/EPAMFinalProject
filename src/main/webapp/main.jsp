<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="header.jsp" %>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        <%@ include file="css/main.css" %>
        <%@ include file="css/table2_0.css" %>
    </style>
</head>
<body>

<h2>Train tickets</h2>

<p>Select destination</p>

<div class="mainPage" align="center">
    <form autocomplete="off" action="main" method="get">
        <div class="autocomplete" style="width:300px;">
            <input id="fromInput" type="text" name="fromCountry" placeholder="From">
        </div>
        <div class="autocomplete" style="width:300px;">
            <input id="toInput" type="text" name="toCountry" placeholder="To">
        </div>
        <div class="autocomplete">
            <input type="date" class="form-control" name="date">
        </div>
        <input type="submit">
    </form>
    <p></p>
    <c:if test="${trains.size() > 0}">
        <c:forEach items="${trains}" var="train">
            <div class="table">
                <div class="row">
                    <div class="trainPath" style="background-color:#ccc;">
                            ${train.from} - ${train.to}
                    </div>
                    <div class="trainTimeInfo" style="background-color:#aaa;" align="center">
                        <div class="trainTime">${train.startTime}</div>
                        <div class="delimiter">
                            <span>time</span>
                        </div>
                        <div class="trainTime">${train.finishTime}</div>
                    </div>
                    <div class="column" style="background-color:#ccc;" align="center">
                        <c:forEach items="${train.serviceToSeats}" var="item">
                            <div class="trainService">
                                <span class="trainCarType">${item.key}</span>
                                <i>Amount of seats ${item.value}</i>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="column" style="background-color:#ccc;" align="center">
                        <c:forEach items="${train.serviceToPrice}" var="price">
                            <div class="trainService">
                                <span class="trainCarType">${price.value} $</span>
                                <i>for ${price.key} class</i>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="choose" style="background-color:#ccc;" align="right">
                        <c:forEach items="${train.serviceToPrice}" var="item">
                            <form action="main/choose" method="get">
                                <input type="hidden" name="wagonService" value="${item.key}">
                                <c:set var="train" value="${train}" scope="session"/>
                                <input type="submit" value="Choose" style="float:left">
                            </form>
                        </c:forEach>
                    </div>
                </div>
            </div>
            <p></p>
        </c:forEach>
    </c:if>
</div>


<script>
    <%@ include file="javaScript/autocompilete.js" %>
    autocomplete(document.getElementById("fromInput"), countries);
    autocomplete(document.getElementById("toInput"), countries);
</script>

</body>
</html>
