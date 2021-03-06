<%@ page import="org.json.JSONArray" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>员工事件数据查询</title>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body>
<script type="text/javascript">
    function query(){
        var managername = $("#managername").val();
        window.location.href = "syslog.action?managername="+managername;
    }
</script>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:63px;"><s:text name="syslog.field.user" /></label>
        <div class="layui-input-inline">
            <input id="managername" class="scinput" type="text" value="${param.get("managername")}" style="width:120px;" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
        </div>
    </div>
    <div>
        <table class="tablelist">
            <thead>
            <tr>
                <th style="text-align: center;"><s:text name="main.column.seq"/></th>
                <th style="text-align: center;">
                    <s:text name="syslog.column.name"/>
                </th>
                <th style="text-align: center;"><s:text name="syslog.column.moudle"/></th>
                <th style="text-align: center;"><s:text name="syslog.column.action"/></th>
                <th style="text-align: center;"><s:text name="syslog.column.type"/></th>
                <th style="text-align: center;"><s:text name="syslog.column.ip"/></th>
                <th style="text-align: center;"><s:text name="syslog.column.time"/></th>
                <th style="text-align: center;"><s:text name="syslog.column.desc"/></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td style="text-align: center;">
                                ${index.index+1}
                        </td>
                        <td style="text-align: center;">
                                ${data.realname}
                        </td>
                        <td style="text-align: center;">
                                ${data.moudlename}
                        </td>
                        <td style="text-align: center;">
                                ${data.action}
                        </td>
                        <td style="text-align: center;">
                                ${data.actionname==""?(data.action.indexOf("get")>-1?"查询":(data.action.indexOf("edit")>-1?"编辑":(data.action.indexOf("add")>-1?"添加":(data.action.indexOf("del")>-1?"删除":"")))) : data.actionname}
                        </td>
                        <td style="text-align: center;">
                                ${data.ipaddress}
                        </td>
                        <td style="text-align: center;">
                            <label style="height:20px;"><fmt:formatDate value="${data.actiontime}" type="both" /></label>
                            <c:if test="{data.endtime!=null}">　／　</c:if><label style="height:20px;"><fmtformatDate value="{data.endtime}" type="both" /></label>
                        </td>
                        <td>
                                ${data.note}
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div id="pp"></div>
    </div>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
</body>
<script type="text/javascript" src="lib/util/deptselutil.js"></script>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    $(function () {
        $('.tablelist tbody tr:odd').addClass('odd');
    });
</script>
</html>
