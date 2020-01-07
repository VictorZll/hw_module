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
</head>
<body>
<div class="view">
    暂无上线------------------
</div>
</body>
<script>

    setInterval(function () {
        $.post("sensor/handle/listSensor",{},function (data) {
            if(data!=null){
                console.log(data.data)
                $(".view").empty();
                for(var i=0;i<data.data.length;i++){
                    console.log(data.data[i])
                    if($(".view div").size()<data.data.length){

                        $(".view").append("<div>"+data.data[i]+"</div><br/>")
                    }

                }

            }
        });
    },100)
</script>
</html>