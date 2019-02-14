<%@ page pageEncoding="UTF-8" %>
<%@page import="com.sundyn.util.SundynSet" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getRealPath("/");
    SundynSet sundynSet = SundynSet.getInstance(path);
    String url = sundynSet.getM_content().get("requestAddress").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="js/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            //顶部导航切换
            $(".nav li a").click(function () {
                $(".nav li a.selected").removeClass("selected")
                $(this).addClass("selected");
            })

            $.ajax({
                url: "topframejson.action",
                dataType: 'json',
                success:function(data){
                    $(".user>span").html(data.realname);
                }
            });
            $(".nav li:first a").trigger("click");
        })
    </script>
</head>

<body style="background:url(images/topbg.gif) repeat-x;">

<div class="topleft" style="">
    <div style="float:left;width:88px;">
        <a href="default.jsp" target="_parent"><img src="images/公安.png" style="width:73px;height:71px;" title="系统首页"/></a>
    </div>
    <div style="float:left;margin-top:25px;">
        <h2 style="font-size:24px;color:white;">广州市车管所排队与评价综管系统<%--<s:text name="sundyn.title"/>--%></h2>
    </div>
</div>

<div class="topright">
    <ul>
        <li><span><img src="images/help.png" title="帮助" class="helpimg"/></span><a href="managerChangePsw.action" target="rightFrame">修改密码</a></li>
        <%--<li><a href="#">关于</a></li>--%>
        <li><a href="managerLogout.action" target="_parent">退出</a></li>
    </ul>

    <div class="user">
        <span></span>
    </div>
</div>

<ul class="nav">
    <c:forEach items="${json}" var="menu" varStatus="s">
        <c:if test="${menu.parentId==0}">
            <li><a href="leftMenu.action?id=${menu.id}&isHome=${menu.menuorder==1}" target="leftFrame"><img src="images/${menu.iconCls}" title="${menu.text}"/>
                <h2>${menu.text}</h2></a></li>
        </c:if>
    </c:forEach>
    <%--<li><a href="leftMenu.action?id=3" target="leftFrame"><img src="images/icon02.png" title="模型管理"/>
        <h2>模型管理</h2></a></li>
    <li><a href="imglist.html" target="rightFrame"><img src="images/icon03.png" title="模块设计"/>
        <h2>模块设计</h2></a></li>
    <li><a href="tools.html" target="rightFrame"><img src="images/icon04.png" title="常用工具"/>
        <h2>常用工具</h2></a></li>
    <li><a href="computer.html" target="rightFrame"><img src="images/icon05.png" title="文件管理"/>
        <h2>文件管理</h2></a></li>
    <li><a href="tab.html" target="rightFrame"><img src="images/icon06.png" title="系统设置"/>
        <h2>系统设置</h2></a></li>--%>
</ul>

</body>
</html>
