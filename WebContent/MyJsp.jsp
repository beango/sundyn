<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
<HEAD>
    <link rel="stylesheet" href="js2/jquery-ui.css">
    <script src="js/jquery-2.1.3.min.js" type="text/javascript"></script>
    <script src="js/jquery-ui-1.9.2.custom.min.js"></script>
    <style>
        [class^="u-icon"]
        {
            display: inline-block;
            color: #fff;
            vertical-align: middle;
        }
        .u-icon-radioDelete
        {
            position: relative;
            width: 16px;
            height: 16px;
            border: 1px solid #8AADD8;
            -webkit-border-radius: 24px;
            border-radius: 24px;
            -webkit-transition: 300ms ease-in-out;
            transition: 300ms ease-in-out;
            cursor: pointer;
        }

        .u-icon-radioDelete::after
        {
            position: absolute;
            top: 7px;
            left: 4px;
            width: 8px;
            height: 2px;
            background-color: #8AADD8;
            color: #8AADD8;
            content: "\20";
        }

        .u-icon-radioDelete.on,.on .u-icon-radioDelete
        {
            -webkit-transform: rotateZ(90deg);
            -ms-transform: rotateZ(90deg);
        }
        .draggable { width: 80px; height: 80px; padding: 5px; float: left; margin: 0; font-size: .9em; border:1px solid red; }
        .ui-widget-header p, .ui-widget-content p { margin: 0; }
        #snaptarget { height: 140px; }
        #container-warpper{
            width: 410px;
            height: 210px;
            border: 1px solid #ccc;
            padding: 10px;
        }
    </style>
</HEAD>
<BODY style="margin: 0;">
<div id="container" class="ui-widget-header" style="border:1px solid #0e78c9;">
    <p>我是对齐目标</p>
</div>
<div id="container-warpper" style="border:1px solid #0e78c9;">

</div>
<span class='u-icon-delete'><i></i></span>
<div>
    宽：<input type="text" id="w" value="90" style="width:50px;" > 高：<input id="h" type="text" value="50" style="width:50px;">
    字体大小：<input id="fontsize" type="text" value="30" style="width:50px;"><input onclick="genlayout()" value="重新生成布局" type="button">
    <br><input onclick="printtest()" value="打印" type="button">
    <input onclick="printtest(true)" value="测试" type="button">
</div>
<script type='text/javascript' language='javascript'>
    var _istest = false;
    var txtlist = ["很满意","满意","基本满意","不满意","业务不熟","态度不好"];
    //宽>高
    function genlayout(){
        var barwidth = $("#w").val()*4, barheight=$("#h").val()*4;
        var obj = $("#barcodereview");
        obj.height(barheight);
        obj.width(barwidth);
        $('#container').html("");

        var txtnums = txtlist.length;
        for (var i = 0; i< txtnums; i++){
            $('<div id="text'+i+'" class="draggable ui-widget-content">'+txtlist[i]+'<span class=\"u-icon-radioDelete\" data=\"' + txtlist[i] + '\"></span></div>',{"border":"1px solid red"}).appendTo('#container-warpper');
            var test1 = $("#text"+i);
            test1.css("margin", 0);
            test1.draggable({ snap: true, containment: "#container-warpper", grid: [ 80, 80 ], scroll: false });
        }
    }

    genlayout();

    $(function(){
        $(".u-icon-radioDelete").click(function () {
            $(this).parent().remove();
            txtlist.remove($(this).attr("data"));
        });
    });
</SCRIPT>

</BODY>
</HTML>
