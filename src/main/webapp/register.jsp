<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        <%@ include file="css/loginStyle.css" %>
    </style>
    <title>Login</title>
</head>
<body>


<form class="modal-content animate" action="register" method="post">
    <div class="container">
        <label for="newUserName"><b>User Name</b></label>
        <input type="text" placeholder="Enter User Name" name="userName" id="newUserName" required>

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="Enter email" name="email" id="email" required>


        <label for="registerPassword"><b>Password</b></label>
        <input type="password" placeholder="*******" name="password" id="registerPassword" required>

        <label for="registerRepeatPassword"><b>Repeat Password</b></label>
        <input type="password" placeholder="*******" name="psw" id="registerRepeatPassword" required>

        <button type="submit">Register</button>
    </div>

    <div class="container" style="background-color:#f1f1f1">
        <button type="button" onclick="location.href='/';" class="cancelbtnReg">Cancel
        </button>
    </div>
</form>

<script>
    <%@ include file="javaScript/checkPassword.js" %>
</script>
</body>
</html>
