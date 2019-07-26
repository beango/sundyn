<%@ page import="org.apache.struts2.ServletActionContext" %>
<%@ page import="com.opensymphony.xwork2.util.LocalizedTextUtil" %>
<%@ page import="java.util.Locale" %>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>登录日志查询</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
</head>
<body>
<script type="text/javascript">
    function query(){
        var key = $("#key").val();
        window.location.href = "managerlogin.action?key="+key + "&servicedate=" + $("#servicedate").val() + "&logtype="+$("#logtype").val();
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

<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;"><s:text name="auditlog.field.type" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <select name="logtype" id="logtype">
                <option value="" <c:if test="${logtype==null || logtype==''}"> selected="selected"</c:if>><s:text name="main.all" /></option>
                <option value="${logTypeEnumLogin}" <c:if test='${logtype==logTypeEnumLogin}'> selected="selected"</c:if>>${logTypeEnumLoginStr}</option>
                <option value="${logTypeEnumLoginout}" <c:if test="${logtype==logTypeEnumLoginout}"> selected="selected"</c:if>>${logTypeEnumLoginoutStr}</option>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq" /></th>
                <th>
                    <s:text name="managerlogin.column.name" />
                </th>
                <th>
                    <s:text name="managerlogin.column.dept" />
                </th>
                <th>
                    <s:text name="managerlogin.column.logtype" />
                </th>
                <th>
                    <s:text name="managerlogin.column.ctime" />
                </th>
                <th>
                    <s:text name="managerlogin.column.logrst" />
                </th>
                <th>
                    <s:text name="managerlogin.column.ip" />
                </th>
                <th align="center">
                    <s:text name="managerlogin.column.terminal" />
                </th>
                <th>
                    <s:text name="main.desc" />
                </th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.realname} / ${data.name}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>${data.logtypei18n}<font style="color:red;"><c:if test="data.checkdigited==1">(<s:text name="main.data.noverify" />)</c:if></font></td>
                        <td>
                                ${data.ctime}
                        </td>
                        <td>
                                ${data.logrst}
                        </td>
                        <td>
                                ${data.ipadd}
                        </td>
                        <td>
                                ${data.logdevice}
                        </td><td>
                                ${data.logrstdesc}
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

<style type="text/css">
    .autocut {
        width:300px;
        overflow:hidden;
        white-space:nowrap;
        text-overflow:ellipsis;
        -o-text-overflow:ellipsis;
        -icab-text-overflow: ellipsis;
        -khtml-text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        -webkit-text-overflow: ellipsis;
    }
</style>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });
</script>
</html>
