<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>

<!DOCTYPE html>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <style>
        [class^=btn]{
            opacity: 0;
            background: cornflowerblue;
        }
        [class^=udt]{
            opacity: 0;
            background: palevioletred;
        }
    </style>
</head>
<body>
<div>
    <form method="post" action="/sensor/handle/uploadAPPBin"  enctype="multipart/form-data" style="background:green;display: inline-block;">
        <input type="file" name="APP" placeholder="选择APP二进制文件"   />
        <input type="submit"  value="点击上传APP" />
    </form>
    <button class="udt1">UPDATE1</button>
    <button class="btn1">发送包PACK1</button>
    <button class="udt2">UPDATE2</button>
    <button class="btn2">发送包PACK2</button>
    <button class="udt3">UPDATE3</button>
    <button class="btn3">发送包PACK3</button>

</div>
<hr style="width: 100%;background: red;"/>
<div class="view">
    暂无上线------------------
</div>

</body>
<script>
    $(".btn1").click(function () {
        $.post("sensor/handle/sendAppPack",{pack:1},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    })
    $(".btn2").click(function () {
        $.post("sensor/handle/sendAppPack",{pack:2},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    })
    $(".btn3").click(function () {
        $.post("sensor/handle/sendAppPack",{pack:3},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    })
    $(".udt1").click(function () {
        $.post("sensor/handle/sendUPDATE",{udt:"UPDATE1"},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    })
    $(".udt2").click(function () {
        $.post("sensor/handle/sendUPDATE",{udt:"UPDATE2"},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    })
    $(".udt3").click(function () {
        $.post("sensor/handle/sendUPDATE",{udt:"UPDATE3"},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    })
    setInterval(function () {

        $.post("sensor/handle/countPackage",{},function (data) {
            var count=data.count;
            for (var i=1;i<count+1;i++){
                $(".btn"+i).css({"opacity":"1"})
                $(".udt"+i).css({"opacity":"1"})
            }
            console.log("count---"+count)
        });
        $.post("sensor/handle/listSensor",{},function (data) {
            if(data!=null&&data.data.length>0){
                // console.log(data.data)
                $(".view").empty();
                for(var i=0;i<data.data.length;i++){
                    console.log(data.data[i])
                    if($(".view div").size()<data.data.length){
                        $(".view").append("<div>" +
                            // "<span>"+data.data[i].split("\\:")[0]+"</span>" +
                            "<button>读IP</button><button>读PORT</button>"+data.data[i]+"</div><br/>")
                    }

                }

            }
        });
    },100)
</script>
</html>