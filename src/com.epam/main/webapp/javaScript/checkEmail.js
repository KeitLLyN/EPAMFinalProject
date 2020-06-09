function validate(form_id,email) {
    let reg = /^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,4})$/;
    let address = document.forms[form_id].elements[email].value;
    if(reg.test(address) === false) {
        alert('Please enter a valid email');
        return false;
    }
}