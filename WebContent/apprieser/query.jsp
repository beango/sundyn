<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>终端管理</title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
        <script type="text/javascript" src="js/dojo.js"></script>

        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="lib/layer/layer.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
	</head>
	<body>
		<div id="layer-form">
            <div class="fengge" style="height:15px;">&nbsp;</div>
            <table class="layui-table">
                <tr>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.apprieser.index' /></td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.apprieser.apprieserName' /></td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
                </tr>
                <c:forEach items="${list}" var="apprieser" varStatus="index">
                    <tr style="height: 30px;">
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                            <s:text name="sundyn.system.deviceManagement.sixKeyMachine"/>
                        </td>
                        <td>
                            <div class="button" onclick="keyTypeQueryDialog(${apprieser.apprieserid})"><s:text name='sundyn.apprieser.keySteup' /></div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
		</div>
		<div id="dialog" style="width: 700px; display: none;">
	</body>
</html>
