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
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>

<body>
<div class="layui-form">
    <table width="500" height="101" border="0" cellpadding="0" cellspacing="0" style="border:1px solid #e9f5fd;">
        <tr>
            <td align="right" rowspan="2" width="32%">批次号<s:text name='sundyn.colon' /><input type="hidden" id="id" value="<%=request.getParameter("id")%>"/></td>
            <td align="left"><span style="width:100px;margin-left:10px;">设备类型码</span><span style="width:100px;margin-left:60px;">年月</span>
                <span style="width:100px;margin-left:60px;">批次</span></td>
        </tr>
        <tr>
            <td align="left" style="border-color: #e9f5fd;">
                <input name="batchid_deviceclass_1" class="input_comm" id="batchid_deviceclass_1" value="${vo.batchid_deviceclass_1}" style="width: 40px;" />
                <input name="batchid_deviceclass_2" class="input_comm" id="batchid_deviceclass_2" value="${vo.batchid_deviceclass_2}" style="width: 40px;" />
                <input name="batchid_year" class="input_comm" id="batchid_year" value="${vo.batchid_year==null?curyear:vo.batchid_year}" style="width: 40px;" />
                <input name="batchid_month" class="input_comm" id="batchid_month" value="${vo.batchid_month==null?curmonth:vo.batchid_month}" style="width: 40px;" />
                <input name="batchid_no" class="input_comm" id="batchid_no" value="${vo.batchid_no}" style="width: 70px;" />
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
                <input name="batchdate" class="input_comm" id="batchdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})" value="${vo.batchdate}" />
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
