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
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<div class="layui-form">
    <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
        <tr>
            <td width="32%" align="right" style="border-color: #e9f5fd;">
                <input type="hidden" id="id" value="<%=request.getParameter("id")%>"/>
                批次号<s:text name='sundyn.colon' />
            </td>

            <td align="left" style="border-color: #e9f5fd;">
                <input name="batchid" class="input_comm" id="batchid" value="${vo.batchid}" />
            </td>
        </tr>
        <tr >
            <td align="right"  style="border-color: #e9f5fd;">批次名<s:text name='sundyn.colon' /></td>
            <td align="left"  style="border-color: #e9f5fd;">
                <input name="batchname" class="input_comm" id="batchname"  value="${vo.batchname}" />
            </td>
        </tr>
        <tr >
            <td align="right"  style="border-color: #e9f5fd;">日期<s:text name='sundyn.colon' /></td>
            <td align="left"  style="border-color: #e9f5fd;">
                <input name="batchdate" class="input_comm" id="batchdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="${vo.batchdate}" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <img src="<s:text name="sundyn.pic.ok" />" onclick="batchAdd();"  style="cursor: pointer;"/>
                <img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
            </td>
        </tr>
    </table>
</body>
</html>