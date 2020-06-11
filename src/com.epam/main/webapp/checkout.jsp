<!DOCTYPE html>
<html>
<head>
    <%@ include file="header.jsp" %>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <style>
        <%@ include file="css/checkout.css" %>
    </style>
</head>
<body>

<h2>Checkout Form</h2>
<p></p>
<div class="row">
    <div class="col-75">
        <div class="container">
            <form action="main/checkout" method="post">

                <div class="row">
                    <div class="col-50">
                        <h3>Billing Address</h3>
                        <label for="fname"><i class="fa fa-user"></i> Full Name</label>
                        <input type="text" id="fname" name="firstname" value="${user.name}" placeholder="John M. Doe">

                        <label for="email"><i class="fa fa-envelope"></i> Email</label>
                        <input type="text" id="email" name="email" value="${user.email}" placeholder="john@example.com">

                        <label for="adr"><i class="fa fa-address-card-o"></i> Address</label>
                        <input type="text" id="adr" name="address" placeholder="542 W. 15th Street">


                        <div class="row">
                            <div class="col-50">
                                <label for="state">State</label>
                                <input type="text" id="state" name="state" placeholder="NY">
                            </div>
                            <div class="col-50">
                                <label for="zip">Zip</label>
                                <input type="text" id="zip" name="zip" placeholder="10001">
                            </div>
                        </div>
                    </div>

                    <div class="col-50">
                        <h3>Payment</h3>

                        <label for="cname">Name on Card</label>
                        <input type="text" id="cname" name="cardname" placeholder="John More Doe">
                        <label for="ccnum">Credit card number</label>
                        <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
                        <label for="expmonth">Exp Month</label>
                        <input type="text" id="expmonth" name="expmonth" placeholder="September">
                        <div class="row">
                            <div class="col-50">
                                <label for="expyear">Exp Year</label>
                                <input type="text" id="expyear" name="expyear" placeholder="2018">
                            </div>
                            <div class="col-50">
                                <label for="cvv">CVV</label>
                                <input type="text" id="cvv" name="cvv" placeholder="352">
                            </div>
                        </div>
                    </div>

                </div>
                <input type="hidden" name="wagonID" value="${wagonID}">
                <input type="submit" value="Continue to checkout" class="btn">
            </form>
        </div>
    </div>
    <div class="col-25">
        <div class="container">
            <h4>Cart <span class="price" style="color:black"><i class="fa fa-shopping-cart"></i> <b>${countOfSeats}</b></span></h4>
            <p>Wagon #${wagonID}, service class ${service} <span class="price">${price}$ * ${countOfSeats}</span></p>

            <hr>
            <p>Total <span class="price" style="color:black"><b>$${price * countOfSeats}</b></span></p>
        </div>
    </div>
</div>

</body>
</html>
