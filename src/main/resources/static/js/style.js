//轮播
$(function () {
    var timer1 = null;
    var timer2 = null;
    var length = $(".box>.item").length;
    var i = 0;
    j = 0;
    $(".box>.item").eq(i).fadeIn(0).siblings().fadeOut(0);

    function next() {
        i++;
        if (i == 3) {
            i = 0;
        }
        $(".box>.item").eq(i).fadeIn(1000).siblings().fadeOut(1000);
    }

    timer1 = setInterval(next, 2000);

    //3个小圆点
    function next2() {
        j++;
        if (j == 3) {
            j = 0;
        }
        $(".list-box>li").eq(j).addClass("active").siblings().removeClass("active");
    }

    timer2 = setInterval(next2, 2000);
    //轮播绑定
    $(".list-box>li").each(function () {
        var _index = $(this).index();
        $(this).click(function () {
            $(".list-box>li").eq(_index).addClass("active").siblings().removeClass("active");
            $(".box>.item").eq(_index).fadeIn(1000).siblings().fadeOut(1000);
        });
    });
    $(".prev").click(function () {
        if (j == 0) {
            j = 3
        }
        j--;
        $(".list-box>li").eq(j).addClass("active").siblings().removeClass("active");
    });
    $(".next").click(function () {
        j++;
        if (j == 3) {
            j = 0
        }
        document.title = j;
        $(".list-box>li").eq(j).addClass("active").siblings().removeClass("active");
    });
    //hover暂停轮播
    $(".box").hover(function () {
        clearInterval(timer1);
        clearInterval(timer2);
        clearInterval(timer)
    }, function () {
        timer1 = setInterval(next, 2000);
        timer2 = setInterval(next2, 2000);
        timer = setInterval(function () {
            t > 1 ? t = 1 : t++;
            // $(".bg-style").animate({"background":"url(../images/d"+t+".jpg)"},600);
            $(".bg-style").css({
                "background": "url(images/d" + t + ".jpg)  no-repeat center top",
                "background-size": "100%"
            }, 600);
        }, 2000);
    });
    //按钮特效
    $(".item>div").hover(function () {
        $(this).children("a").prev().animate({"top": "-20px"}, 600);
        $(this).children("a").next().animate({"top": "20px"}, 600)
    }, function () {
        $(this).children("a").prev().animate({"top": "-50px"}, 600);
        $(this).children("a").next().animate({"top": "50px"}, 600)
    })
});


//背景图变化
var t = 1;
var timer = null;
timer = setInterval(function () {
    t > 1 ? t = 1 : t++;
    // $(".bg-style").animate({"background":"url(../images/d"+t+".jpg)"},600);
    $(".bg-style").css({
        "background": "url(images/d" + t + ".jpg)  no-repeat center top",
        "background-size": "100%"
    }, 600);
}, 2000);

$(".header li a").click(function () {
    $(this).css({"color": "#edfffd"});
    $(this).parent("li").siblings().children("a").css({"background": "none"})
});
//new背景变化
var p = 0;
var k = 1;
var timer2 = null;
timer2 = setInterval(function () {
    if (p > 225 || p < 0) {
        k = -k;
    }
    if (k == 1) {
        $(".new").css({"background": "rgba(240," + p++ + ", 107, 0.73)"});
        $(".feature").css({"background": "rgba(" + p++ + ",94, 107, 0.73)"});
    }
    if (k == -1) {
        $(".new").css({"background": "rgba(240," + p-- + ", 107, 0.73)"});
        $(".feature").css({"background": "rgba(" + p-- + ",94, 107, 0.73)"});
    }

}, 30);


//ads
var oAd = document.getElementsByClassName("ad_lunbo")[0];
oAd.innerHTML += oAd.innerHTML;
var timer_ad = null;
var l = 0;
var dis = oAd.offsetWidth;
timer_ad = setInterval(function () {
    l < -dis / 2 ? l = 0 : l--;
    oAd.style.left = l + "px";
}, 30);
$(".ad_lunbo li").hover(function () {
    $(this).children(".cover1").animate({"left": "-64px", "opacity": "1"});
    $(this).children(".cover2").animate({"left": "16px", "opacity": "1"})
}, function () {
    $(this).children(".cover1").animate({"left": "-224px", "opacity": "0"});
    $(this).children(".cover2").animate({"left": "176px", "opacity": "0"})
});

//prev,ext
$(".lunbo .bg-style>a").css({"top": ($(".lunbo").height() - $(".lunbo .by-style>a").height()) / 2 + "px"});
//小li;
var i = 0;
var timer_color = null;
var oFea = document.getElementsByClassName("feature")[0];
var oUl = oFea.getElementsByTagName("ul")[0];
var mark = 1;

timer_color = setInterval(function () {
    var oLi = oUl.getElementsByTagName("li")[i];
    if (i % 2 == 0) {
        if (mark == 1) {
            oLi.style.backgroundColor = "red";
        } else {
            oLi.style.backgroundColor = "green";
        }

    } else {
        if (mark == 1) {
            oLi.style.backgroundColor = "green";
        } else {
            oLi.style.backgroundColor = "red";
        }
    }
    i > 9 ? (mark = -mark, i = 0) : i++;
}, 30);
//.orange h5
$(".orange h5").css({"margin-top": ($(".orange").height() - $(".orange h5").height()) / 2 + "px"});
// $(".orange-add h5").css({"margin-top":($(".orange-add+img").height()-$(".orange-add h5").height())/2+"px"});
$(window).resize(function () {
    $(".lunbo .bg-style>a").css({"top": ($(".lunbo").height() - $(".lunbo .by-style>a").height()) / 2 + "px"});
    $(".orange h5").css({"margin-top": ($(".orange").height() - $(".orange h5").height()) / 2 + "px"});
    // $(".orange-add h5").css({"margin-top":($(".orange_add").height()-$(".orange_add h5").height())/2+"px"});
});
$(".feature>ul").css({"margin-left": -($(".feature>ul").width()) / 2 + "px"});

$(window).scroll(function () {

    var $top = $(this).scrollTop();
    if ($top > 50) {
        $(".logo").css({"position": "fixed", "background": "white", "opacity": "0.98", "z-index": "4", "top": "0"})
    } else {
        $(".logo").css({"position": "static"})
    }
});