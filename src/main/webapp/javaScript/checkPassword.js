window.onload = function () {
    document.getElementById("registerPassword").onchange = validatePassword;
    document.getElementById("registerRepeatPassword").onchange = validatePassword;
}

function validatePassword() {
    let pass2 = document.getElementById("registerPassword").value;
    let pass1 = document.getElementById("registerRepeatPassword").value;
    if (pass1 !== pass2)
        document.getElementById("registerRepeatPassword").setCustomValidity("Passwords Don't Match");
    else
        document.getElementById("registerRepeatPassword").setCustomValidity('');
}