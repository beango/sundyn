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
<div class="layui-form">
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>文件名称</th>
                <th>版本号</th>
                <th>文件地址</th>
                <th style="width:50%;">备注</th>
            </tr>
            </thead>
            <c:forEach items="${details}" var="entity" varStatus="s">
                <tr>
                    <td>
                            ${entity.filename}
                    </td>
                    <td>
                            ${entity.apkver}
                    </td>
                    <td>
                        <c:url value="download.action" var="url">
                            <c:param name="filepath" value="${entity.filepath}" />
                            <c:param name="filename" value="${entity.filename}" />
                        </c:url>
                        <a href="${url}" target="_blank">下载</a>
                    </td>
                    <td>
                        <div style="white-space:normal">${entity.comment}</div>
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
