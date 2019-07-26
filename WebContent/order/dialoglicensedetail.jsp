<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>订单管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
</head>
<body>
<script type="text/javascript">
    function query(){
        var start = document.getElementById("startDate").value;
        var end = document.getElementById("endDate").value;
        window.location.href = "orderquery.action?status=" + $("#status").val() + "&ispay=" + $("#ispay").val() + "&startDate=" + start + "&endDate=" + end;
    }
</script>
<div class="layui-form">
   <%-- <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
        </div>
    </div>--%>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>授权MAC地址</th>
                <th>授权时间</th>
                <th>下载次数</th>
                <th>下载时间</th>
            </tr>
            </thead>
            <c:forEach items="${details}" var="entity" varStatus="s">
                <tr>
                    <td>
                        ${entity.devicemac}
                    </td>
                    <td>
                        <fmt:formatDate value="${entity.usetime}" type="both" />
                    </td>
                    <td>${entity.downedtimes}</td>
                    <td>
                        <fmt:formatDate value="${entity.lstdowntime}" type="both" />
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
<script type="text/javascript">
    layui.use(['form', 'element','layer']);
</script>
</html>
