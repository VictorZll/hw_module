<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" %>

<!DOCTYPE html>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <base href="<%=basePath%>">
    <title>Title</title>
    <title>首页</title>
    <link rel="stylesheet" href="css/bootstrap.css ">
    <link rel="stylesheet" href="css/font-awesome.css ">
    <link href="https://unpkg.com/bootstrap-table@1.15.4/dist/bootstrap-table.min.css" rel="stylesheet">
    <script type="text/javascript" src="js/jquery-1.11.3.js "></script>
    <script type="text/javascript" src="js/bootstrap.js "></script>
    <script type="text/javascript" src="js/main.js "></script>
    <script src="https://unpkg.com/bootstrap-table@1.15.4/dist/bootstrap-table.min.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=CdsDymFqpwTHbtRNoRqDHzFGatpISE6n"></script>
    <!-- 引入中文语言包 -->
    <script src="js/bootstrap-table-zh-CN.js"></script>
    <script src="js/bootbox.js"></script>
    <script src="js/bootbox.all.js"></script>
    <script src="js/bootbox.locales.js"></script>
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
    <form method="post" action="/version/uploadWar"  enctype="multipart/form-data" style="background:green;display: inline-block;">
        <input style="display:inline" type="file" name="war" placeholder="选择WAR文件"   />
        <input  type="submit"  value="点击上传WAR" />
    </form>
    <form method="post" action="/sensor/handle/uploadAPPBin"  enctype="multipart/form-data" style="background:green;display: inline-block;">
        <input  style="display:inline" type="file" name="APP" placeholder="选择APP二进制文件"   />
        <input type="submit"  value="点击上传APP" />
    </form>
    <button class="reset">发送指令按钮</button>
    <button class="udt1">UPDATE1</button>
    <button class="btn1">发送包PACK1</button>
    <button class="udt2">UPDATE2</button>
    <button class="btn2">发送包PACK2</button>
    <button class="udt3">UPDATE3</button>
    <button class="btn3">发送包PACK3</button>
</div>
<div>
    <form class="fm1" style="background:green;display: inline-block;">
        <input style="display:inline" type="text" class="ip" name="IP" placeholder="输入服务器IP"   />
        <input type="submit"  value="设置服务端IP" />
    </form>
    <form class="fm2" style="background:green;display: inline-block;">
        <input style="display:inline" type="text" class="port" name="PORT" placeholder="输入服务器PORT"   />
        <input type="submit"   value="设置服务端PORT" />
    </form>
    <button onclick="readIP()">读IP</button><button onclick="readPORT()">读PORT</button>
</div>
<hr style="width: 100%;background: red;"/>
<div class="view">
    暂无上线------------------
</div>
<div id="dg"></div>
</body>
<script>
    $(".reset").click(function () {
        var a=prompt("请输入指令","***");
        $.post("sensor/handle/handleStr",{str:a},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    });
    //页面加锁
    // $(function () {
    //     var a=null;
    //     do{
    //         a=prompt("请输入口令：","******");
    //     }while (a!=741852);
    // })

    function readIP() {
        $.post("sensor/handle/ipHandle",{set:false},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    }
    function readPORT() {
        $.post("sensor/handle/portHandle",{set:false},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    }
    $(".fm1 [type=submit]").click(function (e) {
        e.preventDefault();
        $.post("sensor/handle/ipHandle",{ip:$(".ip").val(),set:true},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    })
    $(".fm2 [type=submit]").click(function (e) {
        e.preventDefault();
        $.post("sensor/handle/portHandle",{port:$(".port").val(),set:true},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    })
    function readVersion(tel) {
        alert(tel+"--开发中--readVersion")
    }
    function updateVersion(tel) {
        alert(tel+"--开发中--updateVersion")
    }


    var TableObj = {
        oTableInit: function () {
            $('#dg').bootstrapTable({
                url: "sensor/handle/listForm", //请求后台的URL（*）
                method: 'post', //请求方式（*）
                // toolbar: '#toolbar', //工具按钮用哪个容器
                striped: true, //是否显示行间隔色
                cache: false, //是否使用缓存，默认为true，一般情况下需要设置这个属性（*）
                pagination: true, //是否显示分页（*）
                sortable: false, //是否启用排序
                sortOrder: "asc", //排序方式
                // queryParams: function (params) {
                //     var param={
                //         tip_name:$.trim($("input[name=s_name]").val()),
                //         tip_depart:$.trim($("input[name=s_depart]").val()),
                //         tip_status:$.trim($("select[name=s_status]").val())
                //     }
                //     return param;
                // },//传递参数（*）
                sidePagination: "client", //分页方式：client分页，server分页（*）
                contentType: "application/x-www-form-urlencoded",
                dataType: 'json',
                pageNumber: 1, //初始化加载第一页，默认第一页
                pageSize: 15, //每页的记录行数（*）
                pageList: [10, 15, 20, 25], //可供选择的每页的行数（*）
                search: false, //是否显示表格搜索，此搜索是客户端搜索，意义不大
                strictSearch: true,
                showColumns: false, //是否显示所有的列
                showRefresh: false, //是否显示刷新按钮
                minimumCountColumns: 2, //最少允许的列数
                clickToSelect: true, //是否启用点击选中行
                singleSelect: true,
                //height: 800, //行高，如果未设置height属性，表格自动根据记录条数决定表格高度
                uniqueId: "id", //每一行的唯一标识，一般为主键列
                showToggle: false, //是否显示详细视图和列表视图的切换按钮
                cardView: false, //是否显示详细视图
                detailView: false, //是否显示父子表
                columns: [
                    {
                        title: '序号',
                        field: '',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var pageSize = $('#dg').bootstrapTable('getOptions').pageSize;     //通过table的#id 得到每页多少条
                            var pageNumber = $('#dg').bootstrapTable('getOptions').pageNumber; //通过table的#id 得到当前第几页
                            return index + 1;    // 返回每条的序号： 每页条数 *（当前页 - 1 ）+ 序号
                        }
                    },
                    {
                        title: '设备卡号',
                        field: 'tel',
                        align: 'left',
                    },
                    {
                        title: '设备IP',
                        field: 'clientIP',
                        align: 'left',
                    },
                    {
                        title: '设备PORT',
                        field: 'clientPORT',
                        align: 'left',
                    },

                    {
                        title: '服务器IP',
                        field: 'serverIP',
                        align: 'left',
                    },

                    {
                        title: '服务器PORT',
                        field: 'serverPORT',
                        align: 'left',
                    },
                    {
                        title: 'bootloader倒计时',
                        field: 'bootloaderTIMER',
                        align: 'left',
                    },
                    {
                        title: 'APP报文',
                        field: 'appText',
                        align: 'left',
                    },
                    {
                        title: '版本号',
                        field: 'version',
                        align: 'left',
                    },
                    {
                        title: 'RVERSION(读版本号)',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "rgb(59,154,255)"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='readVersion("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: '升级按钮',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "rgb(59,154,255)"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='updateVersion("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'UPDATE1',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "green"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='UPDATE1("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'PACK1',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "pink"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='PACK1("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'UPDATE2',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "green"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='UPDATE2("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'PACK2',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "pink"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='PACK2("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'UPDATE3',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "green"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='UPDATE3("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'PACK3',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "pink"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='PACK3("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'RIP',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "chocolate"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='RIP("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'RPORT',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "orange"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='RPORT("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'IP',
                        field: '',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "chocolate"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='IP("+row.tel+")'>点击</button>"
                        }
                    },
                    {
                        title: 'PORT',
                        field: 'sid',
                        align: 'center',
                        cellStyle: {
                            css: {"background": "orange"}
                        },
                        formatter:function (value, row, index) {
                            return "<button onclick='PORT("+row.tel+")'>点击</button>"
                        }
                    }
                ]
            });
        }
    }

    TableObj.oTableInit();


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
    function UPDATE1(tel) {
        $.post("sensor/handle/sendUPDATE",{udt:"UPDATE1",tel:tel},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    }
    function UPDATE2(tel) {
        $.post("sensor/handle/sendUPDATE",{udt:"UPDATE2",tel:tel},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    }
    function UPDATE3(tel) {
        $.post("sensor/handle/sendUPDATE",{udt:"UPDATE3",tel:tel},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    }
    function PACK1(tel) {
        $.post("sensor/handle/sendAppPack",{pack:1,tel:tel},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    }
    function PACK2(tel) {
        $.post("sensor/handle/sendAppPack",{pack:2,tel:tel},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    }
    function PACK3(tel) {
        $.post("sensor/handle/sendAppPack",{pack:3,tel:tel},function (data) {
            alert(data.status==1?"操作成功":"操作失败")
        })
    }
    function RIP(tel) {
        $.post("sensor/handle/ipHandle",{set:false,tel:tel},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    }
    function RPORT(tel) {
        $.post("sensor/handle/portHandle",{set:false,tel:tel},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    }
    function IP(tel) {
        var  a=prompt("请输入目标服务器IP","0.0.0.0");
        $.post("sensor/handle/ipHandle",{ip:a,set:true,tel:tel},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    }
    function PORT(tel) {
        var  a=prompt("请输入目标服务器PORT","0000");
        $.post("sensor/handle/portHandle",{port:a,set:true,tel:tel},function (data) {
            if(data.status==1){
                alert("操作成功！")
            }else {
                alert("操作失败！")
            }
        },"json");
    }
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
                            ""+data.data[i]+"</div><br/>")
                    }

                }

            }
        });
        // $("#dg").bootstrapTable("destroy");
        // TableObj.oTableInit();



        // $("#dg").bootstrapTable("refresh");
        // queryAll();
        $("#dg").bootstrapTable("refresh", {
            silent: true //静态刷新
        })
    },600)


    // function queryAll() {
    //     updateRealTimeData();
    // }
</script>
</html>