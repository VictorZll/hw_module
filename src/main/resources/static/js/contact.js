$(".header li a").click(function () {
    $(this).css({"background": "#ff49ed"});
    $(this).parent("li").siblings().children("a").css({"background": "none"})
});
$(".rewrite").click(function () {
    $("input").val("");
    $("textarea").val("");
});
$("[type=text]").focusout(function () {
    $(this).append("<p>" + "只能写汉字" + "</p>")
});
// 右边
$(".car").mouseover(function () {
    $(".div-right").animate({"right": 0}, 400)
});
$(".div-to-top").mouseover(function () {
    $(".div-right").animate({"right": 0}, 400)
});
$(".right").mouseleave(function () {
    $(".div-right").animate({"right": "-40px"}, 400)
});

//标题
$(window).scroll(function () {
    var $top = $(this).scrollTop();
    if ($top > 50) {
        $(".logo").css({"position": "fixed", "background": "white", "opacity": "0.98", "z-index": "4", "top": "0"})
    } else {
        $(".logo").css({"position": "static"})
    }
});


//book
// 点击页面分别翻转
// $(".book .box>div").click(function(){
//     $(this).css("transform","rotateY(180deg)").fadeOut(1000);
// });
var i = 0;
var temp = 0;
var ye = 1;
$(".book .btn-right").click(function () {
    temp = i;
    $(".book .box>div").eq(i).css("animation", "book_2 1s").fadeOut(1000);
    if (i < 10) {
        i++;
    }
    if (ye < 11) {
        ye++
    }
    $(".number").text(ye);
});
$(".book .btn-left").click(function () {
    i = temp;
    $(".book .box>div").eq(temp).css("animation", "book_1 1s").fadeIn(1000);
    if (temp > 0) {
        temp--;
    }
    if (ye > 1) {
        ye--
    }
    $(".number").text(ye);
});


$(".book .reset").click(function () {
    $(".box>div").each(function () {
        $(this).css("animation", "book_1 1s").fadeIn(1000);
    });
    i = 0;
    temp = 0;
    ye = 0;
    $(".number").text(ye);

});