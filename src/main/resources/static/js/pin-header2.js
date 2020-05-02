window.onscroll = function() {myFunction()};

var header = document.getElementById("myHeader1");
var sticky = header.offsetTop;

function myFunction() {
    if (window.pageYOffset > sticky) {
        header.style.paddingTop="55px";
        header.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
    }
}