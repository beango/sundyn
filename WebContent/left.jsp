<%@ page import="com.xuan.xutils.utils.StringUtils" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>菜单</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
    <script language="JavaScript" src="js/jquery.js"></script>

    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript">
        $(function () {
            //导航切换
            $(".menuson li").click(function () {
                $(".menuson li.active").removeClass("active")
                $(this).addClass("active");
            });

            $('.title').click(function () {
                var $ul = $(this).next('ul');
                $('dd').find('ul').slideUp();
                if ($ul.is(':visible')) {
                    $(this).next('ul').slideUp();
                } else {
                    $(this).next('ul').slideDown();
                }
            });
        })
    </script>
</head>

<body style="background:#f0f9fd;">
<%
String id = request.getParameter("id");
List menuList = (List)request.getAttribute("json");
if (StringUtils.isBlank(id)){//参数没有指定显示哪个模块的菜单，找到第一个模块并显示其菜单
    for (int i=0; i< menuList.size(); i++){
        Map m = (Map)menuList.get(i);
        if (m.get("parentId").toString().equals("0")) {
            id = m.get("id").toString();
            System.out.println(m.get("id"));
            break;
        }
    }
}
    request.setAttribute("pid", id);
%>
<dl class="leftmenu">
    <c:forEach items="${json}" var="menu" varStatus="s">
        <c:if test="${menu.id==pid}">
            <dd>
                <div class="title">
                    <span><img src="images/${menu.iconCls}" style="width:16px;height:16px;"/></span>${menu.text}
                    <c:set var="isAlert" value="${menu.text.indexOf('预警')>-1}"></c:set>
                </div>
                <ul class="menuson" style="display:block;">
                    <c:forEach items="${json}" var="menusub">
                        <c:if test="${menusub.parentId==menu.id}">
                    <li><cite></cite><a href="${menusub.url}" target="rightFrame">${menusub.text}</a><i></i></li>
                    </c:if>
                    </c:forEach>
                </ul>
            </dd></c:if>
    </c:forEach>
</dl>
<c:if test="${isAlert}">
<div id="treeContent" class="menuContent" style="position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
<script type="text/javascript" src="lib/util/deptselutil.js"></script>
<script language="javascript">
    $(document).ready(function () {
        initTree("?depttype=1&isOnlyLeaf=0", '<%=request.getParameter("deptId")%>', false, onClick);
        $("#rightFrame", window.parent.document).attr("src", "queuealert.action?deptid=");
    });

    function onClick(e, treeId, treeNode)
    {
        $("#rightFrame", window.parent.document).attr("src", "queuealert.action?deptid="+ treeNode.id);
        return false;
    }
</script>
</c:if>
<c:if test="${!isAlert}">
    <script language="javascript">
        $(document).ready(function () {
            var firstUrl = $(".leftmenu .menuson li:eq(0) a").attr("href");
            var isHome = '<%=request.getParameter("isHome")%>';
            if(isHome != 'null')
            {
                $(".leftmenu .menuson li:eq(0) a").trigger("click");
                $("#rightFrame", window.parent.document).attr("src", firstUrl);
            }
        });
    </script>
</c:if>
</body>
</html>
