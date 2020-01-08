$(".header li a").click(function () {
    $(this).css({"background": "#ff49ed"});
    $(this).parent("li").siblings().children("a").css({"background": "none"})
});

$(".picture .thumbnail").hover(function () {
    $(this).children("img").css({"border": "1px solid red"});
    $(this).addClass("active");
}, function () {
    $(this).children("img").css({"border": "1px solid white"});
    $(this).removeClass("active");
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