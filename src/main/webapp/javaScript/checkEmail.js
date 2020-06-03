// function isEmail() {
//     let str = document.getElementById("email").value;
//     let status = document.getElementById("status");
//     let re = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
//     if (re.test(str)) status.innerHTML = "Адрес правильный";
//     else status.innerHTML = "Адрес неверный";
//     if(isEmpty(str)) status.innerHTML = "Поле пустое";
// }
// function isEmpty(str){
//     return (str == null) || (str.length === 0);
// }
function validate(form_id,email) {
    let reg = /^([A-Za-z0-9_\-.])+@([A-Za-z0-9_\-.])+\.([A-Za-z]{2,4})$/;
    let address = document.forms[form_id].elements[email].value;
    if(reg.test(address) === false) {
        alert('Please enter a valid email');
        return false;
    }
}