function isEmail() {
    let str = document.getElementById("email").value;
    let status = document.getElementById("status");
    let re = /^[^\s()<>@,;:\/]+@\w[\w.-]+\.[a-z]{2,}$/i;
    if (re.test(str)) status.innerHTML = "����� ����������";
    else status.innerHTML = "����� ��������";
    if(isEmpty(str)) status.innerHTML = "���� ������";
}
function isEmpty(str){
    return (str == null) || (str.length === 0);
}