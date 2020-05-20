<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@ include file="css/loginStyle.css" %>
    </style>
    <title>Login</title>
    <script>
        <%@ include file="javaScript/checkEmail.js" %>
    </script>
</head>
<body>


<form class="modal-content animate" action="login" method="post">
    <div class="container">
        <label for="userName"><b>Username</b></label>
        <input type="text" placeholder="Enter Username" name="userName" id="userName" required>

        <label for="password"><b>Password</b></label>
        <input type="password" placeholder="Enter Password" name="psw" id="password" required>

        <button type="submit" onclick="isEmail()">Login</button>
    </div>

    <div class="container" style="background-color:#f1f1f1">
        <button type="button" onclick="location.href='/';" class="cancelbtn">Cancel
        </button>
        <span class="psw">Or create new <a href="register">account</a></span>
    </div>
</form>
</body>
</html>
