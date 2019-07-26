<%@ page pageEncoding="UTF-8" %>
<%@page import="com.sundyn.util.SundynSet" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="powers" value="${pageContext.session.getAttribute('powers')}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>无标题文档</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.2.4.5.css" media="all">
    <script language="JavaScript" src="js/jquery.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <style type="text/css">
        .layui-nav .layui-nav-item{line-height:30px}
    </style>
</head>

<body style="background:url(images/topbg.gif) repeat-x; height:88px;">


<table style="width:100%;">
    <tr>
        <td style="min-width: 500px;">
            <div style="float:left;width:88px;">
                <a href="${ctx}/default.jsp" target="_parent"><img src="${ctx}/images/ic_launcher.png" style="width:73px;height:71px;" title="首页"/></a>
            </div>
            <div style="float:left;margin-top:25px;">
                <h2 style="font-size:24px;color:white;"><s:text name="zx.title"/></h2>
            </div>
        </td>
        <td style="text-align: right;">
            <div style="width:22px; min-width: 25px; float:left; margin-right:10px;">
                <div id="btnscrollleft" style="display:none; height: 78px; color:white; cursor:pointer;line-height:78px;" class="layui-icon layadmin-tabs-control layui-icon-prev" onclick="scrollLeft2(this)"></div>
            </div>
            <div style="float:left; white-space: nowrap; overflow: hidden;width:100%;" class="showMoreNChildren">
                <ul class="layui-nav" pagesize="5" lay-filter="" style="background-color: transparent;padding:0;">
                <c:forEach items="${json}" var="menu" varStatus="s">
                    <c:if test="${menu!=null && menu.parentId==0}">
                        <c:set var="hasAlert" value="${menu.text.indexOf('监控与预警')>-1}"></c:set>
                        <li class="layui-nav-item" style=""><a href="${ctx}/leftMenu.action?id=${menu.id}&isHome=${menu.menuorder==1}" target="leftFrame"><img src="${ctx}/images/${menu.iconCls}" title="${menu.text}" style="width:58px; height:49px;"/>
                            <h2>${menu.text}</h2></a></li>
                    </c:if>
                </c:forEach>
            </ul>
            </div>
            <div style="width:22px; min-width: 25px; float:left; margin-right:10px; ">
                <div id="btnscrollright" style="display:none; height: 78px; color:white; cursor:pointer;line-height:78px;" class="layui-icon layadmin-tabs-control layui-icon-next" onclick="scrollRight(this)"></div>
            </div>
            <%--<ul class="nav">
                <c:forEach items="${json}" var="menu" varStatus="s">
                    <c:if test="${menu!=null && menu.parentId==0}">
                        <c:set var="hasAlert" value="${menu.text.indexOf('监控与预警')>-1}"></c:set>
                        <li><a href="${ctx}/leftMenu.action?id=${menu.id}&isHome=${menu.menuorder==1}" target="leftFrame"><img src="${ctx}/images/${menu.iconCls}" title="${menu.text}" style="width:58px; height:49px;"/>
                            <h2>${menu.text}</h2></a></li>
                    </c:if>
                </c:forEach>
            </ul>--%>
        </td>
        <td style="min-width: 200px;">
            <div class="topright">
                <ul>
                    <li><a href="${ctx}/managerChangePsw.action" target="rightFrame"><s:text name="main.editpwd"/></a></li>
                    <li><span><a href="${ctx}/loginmsg.action" target="rightFrame"><h2><s:text name="main.profile"/></h2></a></span></li>
                    <li><a href="${ctx}/managerLogout.action" target="_parent"><s:text name="left.logout"/></a></li>
                </ul>

                <div class="user">
                    <span></span>
                </div>
            </div>
        </td>
    </tr>
</table>
<script type="text/javascript">
    layui.use('element');
    $(function(){
        $(".nav li a").click(function(){
            $(".nav li a.selected").removeClass("selected")
            $(this).addClass("selected");
        })

        $.ajax({
            url: "${ctx}/topframejson.action",
            dataType: 'json',
            success: function(data){
                $(".user>span").html(data.realname);
            }
        });
        $(".nav li:${hasAlert?"last":"first"} a").trigger("click");
    })
</script>


<script type="text/javascript">
    function scrollLeft2(obj){
        var _leftPos = $('.showMoreNChildren').scrollLeft();

        $(".showMoreNChildren").animate({scrollLeft: _leftPos - 196}, 300, function(){
            if ($('.showMoreNChildren').scrollLeft() == 0)
                $("#btnscrollleft").hide();
            if ($('.showMoreNChildren').scrollLeft()>0)
                $("#btnscrollright").show();
        });
    }
    function scrollRight(obj){
        var _leftPos = $('.showMoreNChildren').scrollLeft();

        $(".showMoreNChildren").animate({scrollLeft: _leftPos + 196}, 300, function(){
            if ($('.showMoreNChildren').scrollLeft() < _leftPos + 196)
                $("#btnscrollright").hide();
            if ($('.showMoreNChildren').scrollLeft()>0)
                $("#btnscrollleft").show();
        });
    }
    (function () {
        var printW = document.body.clientWidth;
        var navW = printW-200-500-60
        $(".showMoreNChildren").width(navW);
        var nvarSize = $(".showMoreNChildren").find("li").length;
        if (nvarSize * 98 > navW) $("#btnscrollright").show();
        console.log(nvarSize * 98, printW)
    })();
</script>

</body>
</html>
