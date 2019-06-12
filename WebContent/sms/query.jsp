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
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
</head>
<body>
<script type="text/javascript">
    function query(){
        var key = $("#key").val();
        window.location.href = "smsQuery.action?key="+key + "&servicedate=" + $("#servicedate").val() + "&type="+$("#type").val();
    }
</script>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;"><s:text name="sms.search.smstype" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <select name="type" id="type">
                <option value="" <c:if test="${type==null || type==''}"> selected="selected"</c:if>><s:text name="main.select.all" /></option>
                <option value="1" <c:if test="${type=='1'}"> selected="selected"</c:if>><s:text name="sms.smstype.unstatis" /></option>
                <option value="2" <c:if test="${type=='2'}"> selected="selected"</c:if>><s:text name="sms.smstype.other" /></option>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query()" value="<s:text name="main.query" />">
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq" /></th>
                <th>
                    <s:text name="sms.column.hallname" />
                </th>
                <th>
                    <s:text name="sms.column.ywlsh" />
                </th>
                <th>
                    <s:text name="sms.column.smsreceiveuser" />
                </th>
                <th>
                    <s:text name="sms.column.smstype" />
                </th>
                <th>
                    <s:text name="main.column.ctime" />
                </th>
                <th>
                    <s:text name="sms.column.sendtime" />
                </th>
                <th>
                    <s:text name="sms.column.sendresult" />
                </th>
                <th>

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
                                ${data.deptname}
                        </td>
                        <td>
                                ${data.ywlsh}
                        </td>
                        <td><c:choose><c:when test="${data.type==1}"><s:text name="sms.smstype.unstatis" /></c:when><c:otherwise><s:text name="sms.smstype.other" /></c:otherwise></c:choose></td>
                        <td>
                            ${data.ctime}
                        </td>
                        <td>
                                ${data.sendtime}
                        </td>
                        <td>
                                ${data.sendresult}
                        </td>
                        <td>
                                <a title="${data.content}"><s:text name="main.column.detail" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div id="pp"></div>
    </div>
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
