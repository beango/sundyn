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
    <table class="layui-table">
        <tr>
            <td align="center" valign="middle"
                background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.column.keyValue" />
            </td>
            <td align="center" valign="middle"
                background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.column.keyDescription" />
            </td>
            <td align="center" valign="middle"
                background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.column.quanValue" />
            </td>
            <td align="center" valign="middle"
                background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.column.isContent" />
            </td>
            <td align="center" valign="middle"
                background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.column.isUse" />
            </td>
        </tr>
        <c:forEach items="${list}" var="keyType">
            <tr>
                <td style="text-align: center;">
                    <s:text name="sundyn.apprieser.key" />${keyType.id}
                </td>
                <td style="text-align: center;">
                    <input type="text" value="${keyType.name}" id="name${keyType.id}" class="input_comm"/>
                </td>
                <td style="text-align: center;">
                    <input type="text" value="${keyType.ext1}" id="ext1${keyType.id}" style="width: 30px;" class="input_comm"/>
                </td>
                <td style="text-align: center;">
                    <input type="checkbox" id="isJoy${keyType.id}" <c:if test="${keyType.isJoy=='on'}">checked="checked"</c:if> lay-skin="switch" />
                </td>
                <td style="text-align: center;">
                    <input type="checkbox" id="yes${keyType.id}" <c:if test="${keyType.yes=='1' }">checked="checked"</c:if> lay-skin="switch" />
                </td>
            </tr>
        </c:forEach>
    </table>
    <div style="text-align: left;margin-left:15px;" class="layui-form-item">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.pic.save' />" onclick="keyTypeEditAll()" class="hand" />
            <img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()" class="hand" />
        </div>
        <div class="layui-form-mid layui-word-aux">权值最大为10</div>
    </div>
</div>
<script>
    layui.use('form', function(){
        var form = layui.form;
    });
</script>

</body></html>