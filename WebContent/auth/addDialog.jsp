<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
</head>
<body>
<div class="layui-form">
    <table width="100%" height="173" border="0" cellpadding="0"
           cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td style="border-color: #e9f5fd;" width="32%" align="right">
                <s:text name='sundyn.role.roleName' /><s:text name="sundyn.colon" />
            </td>
            <td width="68%" align="left" style="border-color: #e9f5fd;">
                <input name="name" id="name" onblur="powerExist()" class="input_comm"/><span id="tip" style="font-size: 12px;color: red;"></span>
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.column.systemSetup' /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="checkbox" name="baseSet" id="baseSet" lay-skin="switch" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.column.baseSetup' /><s:text name="sundyn.colon" />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <input type="checkbox" name="dataManage" id="dataManage" lay-skin="switch" />
            </td>
        </tr>
        <tr>
            <td style="border-color: #e9f5fd;" align="right">
                <s:text name='sundyn.role.atDept' />
            </td>
            <td align="left" style="border-color: #e9f5fd;">
                <div class="layui-input-inline">
                <select name="deptId" id="deptId">
                    <c:forEach items="${list}" var="dept" varStatus="index">
                        <option value="${dept.id}" <c:if test="index.index==0">selected="selected"</c:if>><c:forEach begin="0" end="${dept.lenvel}">&nbsp;</c:forEach>${dept.name}</option>
                    </c:forEach>
                </select>
                </div>
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name='sundyn.pic.ok' />"  onclick="powerAdd()"
                     class="hand" />
                <img src="<s:text name='sundyn.pic.close' />"  onclick="closeDialog()"
                class="hand">
            </td>
        </tr>
    </table>
</div>
</body>
<script>
    //Demo
    layui.use('form', function(){
        var form = layui.form;
    });
</script>
</html>