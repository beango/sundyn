<%@ page import="java.util.Dictionary" %>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.HashMap" %>
<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    HashMap<String, String> device15 = new HashMap<String, String>();
    device15.put("width", "2100");
    device15.put("height", "1200");
    HashMap<String, String> device10 = new HashMap<String, String>();
    device10.put("width", "1280");
    device10.put("height", "800");
    HashMap<String, String> device7 = new HashMap<String, String>();
    device7.put("width", "1024");
    device7.put("height", "600");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/interact.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <style>
        * {
            padding: 0px;
            margin: 0px;
        }

        .draggable {
            line-height: 50px;
            min-height: 10px;
            min-width: 10px;
            margin: 0px;
            position: absolute;
            padding: 1px;
            left: 0px;
            right: 0px;
            background-color: #234b5e;
            color: white;
            border-radius: 0px;
            padding: 0px;
            float: left;
            margin: 0px;
            overflow: hidden;
            opacity: 0.8;
            border:1px solid #d5d5d5;
        }

        div.h4 {
            text-align: center;
            position: absolute;
            bottom: 0px;
            left: 0px;
            right: 0px;
            color:black;
        }

        p.wh {
            font-size: 10px;
            position: absolute;
            bottom: 0px;
            right: 0px;
            line-height: 10px;
            padding: 2px;
            color:black;
        }

        p.lt {
            font-size: 10px;
            position: absolute;
            top: 0px;
            left: 0px;
            line-height: 10px;
            padding: 2px;
            color:black;
        }

        p.close {
            font-size: 10px;
            position: absolute;
            top: 0px;
            right: 0px;
            line-height: 10px;
            padding: 2px;
            cursor: pointer;
            display: none;
            color:black;
        }

        p.c {
            font-size: 10px;
            position: absolute;
            bottom: 50%;
            right: 50%;
            line-height: 10px;
            padding: 2px;
            color:black;
        }

        div.e button {
            font-size: 10px;
            position: absolute;
            bottom: 2px;
            left: 0px;
            line-height: 10px;
            padding: 2px;
            height:10px;
            background: none;
            color:black;
        }
        .content {
            border: 1px solid #ccc;
            width: 1080px;
            height: 768px;
            position: relative;
            margin: 0px auto;
        }

        h1 {
            text-align: center;
        }

        .left {
            background: #84acb3;
            float: left;
            width: 160px;
            height: 100%;
        }

        .right {
            background: #fff;
            float: right;
            width: 840px;
            height: 100%;
            position: relative;
        }

        .drag_l {
            background-color: #234b5e;
            border-radius: 11px;
            color: #f7ebca;
            cursor: pointer;
            margin-bottom: 10px;
            margin-left: 6px;
            padding: 8px;
            width: 128px;
            text-align: center;
        }

        .drag_l:hover {
            background-color: #577a8b;
        }

        pre {
            color: green;
            padding: 10px 15px;
            background: #f0f0f0;
            border: 1px dotted #333;
            font: 12px/1.5 Courier New;
            margin: 12px;
        }

        span {
            color: #999;
        }
    </style>
    <script type="text/javascript">
        var deviceTypes = {
            10 : {width: 1024, height: 600}
        }
    </script>
</head>
<body>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
        <c:if test="${deptObj!=null}"><li>${deptObj["name"]}</li></c:if>
    </ul>
</div>
<div class="layui-form">
    <table style="border:0px;">
        <tr>
            <td style="width:600px;" valign="top">
                <table class="layui-table">
                    <tr>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                            <s:text name="sundyn.column.keyValue" />
                        </td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                            <s:text name="sundyn.column.keyDescription" />
                        </td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                            <s:text name="sundyn.column.quanValue" />
                        </td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                            <s:text name="sundyn.column.isContent" />
                        </td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                            <s:text name="sundyn.column.isUse" />
                        </td>
                    </tr>
                    <c:forEach items="${list}" var="keyType">
                        <tr>
                            <td style="text-align: center;">
                                <s:text name="sundyn.apprieser.key" />${keyType.id}
                            </td>
                            <td style="text-align: center;">
                                <input type="text" value="${keyType.name}" id="name${keyType.id}" data-id="${keyType.id}" class="input_comm eval_name"/>
                            </td>
                            <td style="text-align: center;">
                                <input type="text" value="${keyType.ext1}" id="ext1${keyType.id}" style="width: 30px;" class="input_comm"/>
                            </td>
                            <td style="text-align: center;">
                                <input type="checkbox" id="isJoy${keyType.id}" <c:if test="${keyType.isJoy=='on'}">checked="checked"</c:if> lay-skin="switch" />
                            </td>
                            <td style="text-align: center;">
                                <input type="checkbox" id="yes${keyType.id}" <c:if test="${keyType.yes=='1' }">checked="checked"</c:if> lay-skin="switch" lay-filter="layfilter" data-text="${keyType.name}" />
                            </td>
                        </tr>
                    </c:forEach>
                </table><input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="keyTypeEditAll()" class="layui-btn"/>（<s:text name="keytype.tips.weightmax" /> 10）
            </td>

            <td valign="top">
                <div style="text-align: left;margin-left:15px;" class="layui-form-item">
                    <div class="layui-form-mid layui-word-aux">
                        <div id="content" class="content">
                        </div>
                        <div style="margin-top:5px;">
                            <s:text name="keytype.label.fontsize" /><input id="evalSize" type="text" value="30" style="width:40px;" />
                            <s:text name="keytype.label.devicesize" /><input id="fbl1" type="text" value="<%=device10.get("width")%>" style="width:40px;" /> x <input id="fbl2" type="text" value="<%=device10.get("height")%>" style="width:40px;" />
                            <input type="radio" value="15" id="r1" name="rgroup" lay-filter="layfilterrgroup" />15<s:text name="keytype.label.devicesizenut" />（<%=device15.get("width")%>x<%=device15.get("height")%>）
                            <input type="radio" value="10" id="r2" name="rgroup" lay-filter="layfilterrgroup" checked="checked" />10<s:text name="keytype.label.devicesizenut" />（<%=device10.get("width")%>x<%=device10.get("height")%>）
                            <input type="radio" value="7" id="r3" name="rgroup" lay-filter="layfilterrgroup" />7<s:text name="keytype.label.devicesizenut" />（<%=device7.get("width")%>x<%=device7.get("height")%>）
                            （<s:text name="keytype.tips.devicelayout" />）
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</div>

<script>
    var dh = 220, dw=165;
    function getRandomColor() {
        return "#ffffff";
    }
    layui.use(['form', 'upload'], function(){
        var form = layui.form,upload = layui.upload;
        form.on('switch(layfilter)', function(data){
            var id = data.elem.id.replace("yes","");
            if(data.elem.checked && $("#e"+id).length==0){
                var text = data.elem.getAttribute('data-text');
                var t = {"id":id,"text":text,"width":dw,"height":dh,"b":"0","g":"0","img":"eval_0.png","lx":0,"ly":0,"r":"0","size":$("#evalSize").val(),"x":"0","y":"0"}
                addEvalBtn(t);
            }
            if(!data.elem.checked) {
                $("#e"+id).remove();
            }
        });
        form.on('radio(layfilterrgroup)', function(data){
            switch (data.value) {
                case "15":
                    $("#fbl1").val("<%=device15.get("width")%>");
                    $("#fbl2").val("<%=device15.get("height")%>");
                    break;
                case "10":
                    $("#fbl1").val("<%=device10.get("width")%>");
                    $("#fbl2").val("<%=device10.get("height")%>");
                    break;
                case "7":
                    $("#fbl1").val("<%=device7.get("width")%>");
                    $("#fbl2").val("<%=device7.get("height")%>");
                    break;
            }
            $("#content").width($("#fbl1").val()).height($("#fbl2").val());
        });
        $("#fbl1").bind("change", function () {
            $("#content").width($("#fbl1").val()).height($("#fbl2").val());
        })
        $("#fbl2").bind("change", function () {
            $("#content").width($("#fbl1").val()).height($("#fbl2").val());
        })
        //普通图片上传
        evalbutton(1, upload);
        evalbutton(2, upload);
        evalbutton(3, upload);
        evalbutton(4, upload);
        evalbutton(5, upload);
        evalbutton(6, upload);
        evalbutton(7, upload);
        var reg =/(^[1-9]\d*$)/;

        $("#evalSize").change(function(){
            if(!reg.test($(this).val()))
            {
                alert("<s:text name="keytype.valid.intformat" />");
                return;
            }
            $(".content .draggable div.h4").css("font-size", parseInt($(this).val()));
        });

        $("input.eval_name").change(function(){
            var id = $(this).attr("data-id");
            $("#e" + id).find(".h4").html($(this).val());
        });
    });

    function evalbutton(id, upload){
        var uploadInst = upload.render({
            elem: '#evalbutton' + id
            ,url: 'employeeUpload.action'
            ,before: function(obj){
                obj.preview(function(index, file, result){
                    //$('#imgName').attr('src', result); //图片链接（base64）
                });
            },done: function(res){
                if(res.rst == "success" && res.path && res.path.length>0){
                    $('#e' + id).css("background-image", "url(" + res.path[0] + ")"); //图片链接（base64）
                    $('#e' + id).attr('data-img', res.path[0].substr(res.path[0].indexOf('/')+1));
                    return layer.msg('<s:text name="main.upload.succ" />');
                }
                alert(res.msg);
            }
            ,error: function(){
                layer.msg('<s:text name="main.upload.fail" />');
            }
        });
    }

    function addEvalBtn(t, i, r){
        $('.content').append('<div class="draggable" id="e' + t.id + '" style="width:'+t.width+'px;height:'+t.height+'px;background:'
            + getRandomColor() + ';transform: translate('+t.lx+'px, '+t.ly+'px);" data-x="'+t.lx+'" data-y="'+t.ly+'" data-img="'+t.img+'">\n' +
            '            <div class="h4" style="font-size:'+t.size+'px;">' + t.text + '</div>\n' +
            '            <p class="lt"><s:text name="main.left"/>'+t.lx+'<s:text name="main.top"/>'+t.ly+'px</p>\n' +
            '            <p class="wh">'+t.width+'×'+t.height+'px</p>\n' +
            '            <div class="layui-upload e"><button type="button" class="layui-btn" id="evalbutton'+t.id+'"><s:text name="main.choosepic"/></button></div>\n' +
            '        </div>');
        if (t.img!=null && t.img !=''){
            $("#e" + t.id).css({"background-image": "url(/upload/" + t.img + ")",
                "background-repeat": "no-repeat",
                "background-size": "100% 100%",
                "-moz-background-size":"100% 100%"}); //图片链接（base64）;
        }
        $("#e" + t.id + " .close").click(function(){
            $(this).parent().remove();
        });
    }

    (function () {
        var arr = [];
        var evalbuttons1 = ${evalbuttons};
        for(var idx=0; idx<evalbuttons1.length;idx++){
            arr.push(evalbuttons1[idx]);
        }
        var size = 4;//每行默认4个
        var s = arr.length-size;
        var data = [];
        while (s>=0){
            data.push(arr.slice(s, s+size));
            s -= size;
        }
        if(s+size>0)
            data.push(arr.slice(0, s+size));
        data.reverse();

        $(function () {
            data.map(function (t1, i1) {
                t1.map(function (t, i) {
                    addEvalBtn(t, i, i1);

                });
            });
            mydrag();
        });
        var every_x = [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null];
        var every_y = [null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null];

        var status1=true,status2=true,status3=true,status4=true;

        function removeByValue(arr, val) {
            for (var i = 0; i < arr.length; i++) {
                if (arr[i] == val) {
                    arr.splice(i, 1);
                    break;
                }
            }
        }

        Array.prototype.insert = function (index, item) {
            this.splice(index, 1, item);
        };


        var mydrag = function () {
            interact('.draggable').draggable({
                    inertia: false,
                    restrict: {
                        restriction: "parent",
                        endOnly: true,
                        elementRect: {top: 0, left: 0, bottom: 1, right: 1}
                    },
                    maxPerElement: 100,
                    onmove: dragMoveListener,
                    onend: function (event) {
                        var textEl = event.target.querySelector('p.lt');
                        textEl && (textEl.textContent =
                            position(event));
                        drag_s(event);
                        myclose(event);
                    }
                }).resizable({
                    edges: {left: true, right: true, bottom: true, top: true},
                    restrictEdges: {
                        outer: 'parent',
                        endOnly: true
                    },
                    restrictSize: {
                        min: {width: 0, height: 0}
                    },
                    inertia: false
                }).on('resizemove', function (event) {
                    var s = $(event.target);
                    var target = event.target,
                        x = (parseFloat(target.getAttribute('data-x')) || 0),
                        y = (parseFloat(target.getAttribute('data-y')) || 0),
                        xr=(parseFloat(target.getAttribute('data-x')) || 0)+s.width(),
                        yb=(parseFloat(target.getAttribute('data-y')) || 0)+s.height();
                    x += event.deltaRect.left;
                    y += event.deltaRect.top;
                    xr += event.deltaRect.right;
                    yb += event.deltaRect.bottom;

                    var s = $(event.target);
                    var sid = s.attr('id');
                    var ns = parseInt(sid);

                    var i = ns * 2
                    every_y[i] = null;
                    every_x[i] = null;
                    every_y[i + 1] = null;
                    every_x[i + 1] = null;

                    for (ax in every_x) {
                        if ((parseInt(every_x[ax]) - 3) <= x && x <= (parseInt(every_x[ax]) + 3)) {

                            x = parseInt(every_x[ax]);
                        }else {
                        }
                    }
                    for (ay in every_y) {
                        if ((parseInt(every_y[ay]) - 3) <= y && y <= (parseInt(every_y[ay]) + 3)) {
                            y = parseInt(every_y[ay]);
                        }else {
                        }
                    }

                    for (a_x in every_x) {
                        if ((parseInt(every_x[a_x]) - 3) <= xr && xr <= (parseInt(every_x[a_x]) + 3)) {
                            xr = parseInt(every_x[a_x]);
                        }else {
                        }
                    }
                    for (a_y in every_y) {
                        if ((parseInt(every_y[a_y]) - 3) <= yb&& yb <= (parseInt(every_y[a_y]) + 3)) {
                            yb = parseInt(every_y[a_y]);
                        }else {
                        }
                    }

                    target.style.width = (xr-x) + 'px';
                    target.style.height =(yb-y) + 'px';
                    target.style.webkitTransform = target.style.transform = 'translate(' + x + 'px,' + y + 'px)';
                    target.setAttribute('data-x', x);
                    target.setAttribute('data-y', y);
                    target.querySelector('p.wh').textContent =(xr-x) + '\u00D7' +(yb-y) + 'px';
                    var textEl = event.target.querySelector('p.lt');
                    textEl && (textEl.textContent = position(event));
                }).on('resizeend', function (event) {
                    drag_s(event)
                });

            function dragMoveListener(event) {
                var target = event.target,
                    x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
                    y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

                var s = $(event.target)
                var sid = s.attr('id');
                var ns = parseInt(sid);

                var i = ns * 2
                every_y[i] = null;
                every_x[i] = null;
                every_y[i + 1] = null;
                every_x[i + 1] = null;

                for (ax in every_x) {
                    if ((parseInt(every_x[ax]) - 3) <= x && x <= (parseInt(every_x[ax]) + 3)) {
                        x = parseInt(every_x[ax]);
                    }
                }
                for (ay in every_y) {
                    if ((parseInt(every_y[ay]) - 3) <= y && y <= (parseInt(every_y[ay]) + 3)) {
                        y = parseInt(every_y[ay]);
                    }
                }

                for (a_x in every_x) {
                    if ((parseInt(every_x[a_x]) - 3) <= (x+s.width()) && (x+s.width()) <= (parseInt(every_x[a_x]) + 3)) {
                        x = parseInt(every_x[a_x])-s.width();
                    }
                }
                for (a_y in every_y) {
                    if ((parseInt(every_y[a_y]) - 3) <= (y+s.height()) && (y+s.height()) <= (parseInt(every_y[a_y]) + 3)) {
                        y = parseInt(every_y[a_y])-s.height();
                    }
                }
                target.style.webkitTransform =
                    target.style.transform =
                        'translate(' + x + 'px, ' + y + 'px)';
                target.setAttribute('data-x', x);
                target.setAttribute('data-y', y);
            }

            function position(e) {
                return '<s:text name="main.left" />' + e.target.getAttribute('data-x') + "<s:text name="main.top" />" + e.target.getAttribute('data-y') + 'px'
            }

            function myclose(event) {
                $(event.target).find(".close").click(function () {
                    $(this).parent().remove();
                    drag_c(event)
                })
            }

            function drag_s(event) {
                var s = $(event.target)
                var sid = s.attr('id');
                var ns = parseInt(sid);
                var i = ns * 2
                every_y.insert(i, parseFloat(s.attr('data-y')));
                every_x.insert(i, parseFloat(s.attr('data-x')) + parseFloat(s.width()));
                every_y.insert(i + 1, parseFloat(s.attr('data-y')) + parseFloat(s.height()));
                every_x.insert(i + 1, parseFloat(s.attr('data-x')));
            }

            function drag_c(event) {
                var s = $(event.target)
                var sid = s.attr('id');
                var ns = parseInt(sid);
                var i = ns * 2
                every_y.insert(i, 0);
                every_x.insert(i, 0);
                every_y.insert(i + 1, 0);
                every_x.insert(i + 1, 0);
            };
            window.dragMoveListener = dragMoveListener;
        };
    })()

    function getlayout(){
        var items = $(".content .draggable");
        var data = [];
        for (var i=0; i<items.length; i++){
            var x = $(items[i]).attr("data-x"),
                y = $(items[i]).attr("data-y"),
                w = $(items[i]).width(),
                h = $(items[i]).height(),
                id = $(items[i]).attr("id").replace('e',''),
                text = $(items[i]).find("div.h4").html().trim(),
                img = $(items[i]).attr("data-img"),
                size = $("#evalSize").val();
            if (img==null) img = "";
            data.push({'id': id, 'text': text, 'size':size, 'r': 0, 'g': 0, 'b': 0, 'lx': x, "ly": y, 'x': i, "y": 0, "width": w, "height": h, "img": img});
        }
        $.post("keyTypeLayoutEdit.action", {data: JSON.stringify(data)},function(result){
        })
    }


    function keyTypeEditAll() {
        var r=/^[-]?\d+$/;
        var ext1=document.getElementById("ext11").value;
        var ext2=document.getElementById("ext12").value;
        var ext3=document.getElementById("ext13").value;
        var ext4=document.getElementById("ext14").value;
        var ext5=document.getElementById("ext15").value;
        var ext6=document.getElementById("ext16").value;
        var ext7=document.getElementById("ext17").value;

        if(r.test(ext1)&&r.test(ext2)&&r.test(ext3)&&r.test(ext4)&&r.test(ext5)&&r.test(ext6)&&r.test(ext7)){
            if((ext1 && ext1>10) || (ext2 && ext2>10) || (ext3 && ext3>10) || (ext4 && ext4>10) || (ext5 && ext5>10) || (ext6 && ext6>10) || (ext7 && ext7>10))
            {
                alert("权值最大为10");
                return;
            }
            var idx = 1;
            var ids = "", names = "", isJoys="", yess = "", ext1s = "";
            while (idx<=7){
                var name = document.getElementById("name" + idx).value;
                var isJoy = document.getElementById("isJoy" + idx);
                var yes=document.getElementById("yes"+idx);
                var ext1=document.getElementById("ext1"+idx).value;
                if (isJoy.checked) {
                    isJoy = "on";
                } else {
                    isJoy = "";
                }
                if (yes.checked) {
                    yes = 1;
                } else {
                    yes = 0;
                }
                ids += idx+",";
                names += name + ",";
                isJoys += isJoy + ",";
                yess += yes + ",";
                ext1s += ext1 + ",";
                idx++;
            }
            if(("," + yess).indexOf(",1,")==-1)
            {
                alert("<s:text name="keytype.valid.key.notnull" />");
                return;
            }
            var items = $(".content .draggable");
            var data = [];
            for (var i=0; i<items.length; i++){
                var x = $(items[i]).attr("data-x"),
                    y = $(items[i]).attr("data-y"),
                    w = $(items[i]).width(),
                    h = $(items[i]).height(),
                    id = $(items[i]).attr("id").replace('e',''),
                    text = document.getElementById("name" + id).value,//$(items[i]).find(".h4").html().trim(),
                    img = $(items[i]).attr("data-img"),
                    size = $("#evalSize").val();
                if (img==null) img = "";
                data.push({'id': id, 'text': text, 'size':size, 'r': 0, 'g': 0, 'b': 0, 'lx': x, "ly": y, 'x': i, "y": 0, "width": w, "height": h, "img": img});
            }
            var lwidth = $("#fbl1").val();
            var lheight = $("#fbl2").val();
            if (lwidth === "<%=device10.get("width")%>" && lheight === "<%=device10.get("height")%>"){
                lwidth = 0;
                lheight = 0;
            }
            $.post("keyTypeLayoutEdit.action", {lwidth: lwidth, lheight: lheight, data: JSON.stringify(data)});
            $.post("keyTypeEdit.action", {ids:ids, names:names, isJoys:isJoys,yess:yess,ext1s:ext1s},function(resp){
                succ(resp);
            });
        } else {
            alert("<s:text name="keytype.valid.weight.notint" />");
        }
    }
</script>
</body>
</html>
