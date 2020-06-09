let modalTrain = document.getElementById('openFormTrain');
let modalAddTrain = document.getElementById('addFormTrain');


window.onclick = function(event) {
    if (event.target === modalTrain) {
        modalTrain.style.display = "none";
    }
    if (event.target === modalAddTrain) {
        modalAddTrain.style.display = "none";
    }
}