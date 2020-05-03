window.onscroll = function() {myFunction()};

var header = document.getElementById("myHeader1");
var header1 = document.getElementById("myHeader");
var sticky = header.offsetTop;

function myFunction() {
    if (window.pageYOffset > sticky) {
        header.classList.add("sticky");
        header1.style.paddingTop="55px";
        header1.classList.add("sticky");
    } else {
        header.classList.remove("sticky");
        // header1.style.paddingTop="55px";
        header1.style.paddingTop="0px";
        header1.classList.remove("sticky");
    }
}