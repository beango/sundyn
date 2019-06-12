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
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
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
        .layui-btn-xs{text-indent:0px;}
    </style>
</head>
<body>
<script type="text/javascript">
    function query(){
        var key = $("#key").val();
        window.location.href = "managerlogin.action?key="+key + "&servicedate=" + $("#servicedate").val() + "&logtype="+$("#logtype").val();
    }

    function proxyAdd(id){
        var action = id==undefined?"增加":"编辑";
        new dialog().iframe("jxDataAdd.action?id=" + id, {title: action+"", resize:false, w:"40%", h:"710px"});
    }

    function del(id){
        layer.confirm('真的删除么', function(index){
            $.post("jxDataDel.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    layer.msg('删除成功', {
                        icon: 1,
                        time: 800
                    }, function(){
                        refreshTab();
                    });
                }
                else{
                    layer.msg(resp, {
                        icon: 2,
                        time: 1200
                    }, function(){
                    });
                }
            });
            layer.close(index);
        });
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
<%
    HttpServletRequest req = ServletActionContext.getRequest();
    String logTypeEnumLogin = LocalizedTextUtil.findDefaultText("auditlog.logTypeEnumLogin", (Locale)req.getAttribute("Locale"));
    ServletActionContext.getRequest().setAttribute("logTypeEnumLogin", logTypeEnumLogin);

    String logTypeEnumLoginout = LocalizedTextUtil.findDefaultText("auditlog.logTypeEnumLoginout", (Locale)req.getAttribute("Locale"));
    ServletActionContext.getRequest().setAttribute("logTypeEnumLoginout", logTypeEnumLoginout);
%>
<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;">类型：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <select name="logtype" id="logtype">
                <option value="" <c:if test="${logtype==null || logtype==''}"> selected="selected"</c:if>>所有</option>
                <option value="${logTypeEnumLogin}" <c:if test='${logtype==logTypeEnumLogin}'> selected="selected"</c:if>>${logTypeEnumLogin}</option>
                <option value="${logTypeEnumLoginout}" <c:if test="${logtype==logTypeEnumLoginout}"> selected="selected"</c:if>>${logTypeEnumLoginout}</option>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand" onclick="query('')"/>
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>序号 </th>
                <th>
                    姓名
                </th>
                <th>
                    所属部门
                </th>
                <th>
                    事件类型
                </th>
                <th>
                    操作时间
                </th>
                <th>
                    操作结果
                </th>
                <th>
                    操作IP
                </th>
                <th align="center">
                    终端
                </th>
                <th>
                    详情
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
                        <td>${data.logtype}<font style="color:red;">${data.checkdigited==1?"(数据被篡改)":""}</font></td>
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
