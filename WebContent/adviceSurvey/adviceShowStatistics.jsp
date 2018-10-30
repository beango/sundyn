<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
		<title><s:text name='sundyn.advice.statistics' /></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/application.js"></script>
	</head>
	<body>
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:forEach items="${navbar_menuname}" var="menu">
                <li><a href="#">${menu.name}</a></li>
            </c:forEach>
            <c:if test="${deptObj!=null}"><li>${deptObj["name"]}</li></c:if>
        </ul>
    </div>
		<div id="layui-form">
            <input type="hidden" name="managerId" id="managerId" value="${managerId}" />
            <table width="100%" class="layui-table">
                <tr>
                    <td width="40px" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
                    <td align="center" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.advice.question' /></td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.advice.statistics' /></td>
                </tr>
                <s:iterator value="adviceStatistics" var="ll" status="num">
                    <s:iterator value="ll" id="index1" var="m1">
                    <tr>
                        <td style="text-align: center;">
                                ${num.index+1 }
                        </td>
                        <td>
                            <s:property value="key"/>
                        </td>
                        <td style="text-align: left;">
                                <table border="0" style="border:0px;width:100%;">
                                <s:iterator value="#m1.value" var="lll">
                                    <s:iterator value="lll" var="m2" status="number">
                                        <tr>
                                            <td width="30%" style="border:0px;">
                                                <s:text name='sundyn.advice.check' /><s:text name='sundyn.colon' /><s:property value="key"/>&nbsp;&nbsp;</td>
                                            <td width="30%" style="border:0px;">
                                                <s:iterator value="value" id="entry">
                                                <s:if test="value==0.0"><div style="width:5px;height:20px;background-color:green;float:left;" nowrap></s:if> <s:else>  <div style="width:${value*1.2 }px;height:20px;background-color:green;float:left;" nowrap></s:else></div>
                                                </s:iterator>
                                            </td>
                                            <td width="20%" style="border:0px;">
                                                <s:iterator value="value" id="entry"><s:property value="value"/>%
                                                </s:iterator>
                                            </td>
                                            <td width="20%" style="border:0px;">
                                                <s:iterator value="value" id="entry">
                                                    <s:property value="key"/>
                                                </s:iterator>
                                            </td>
                                        </tr>
                                    </s:iterator>

                                </s:iterator>
                                </table>
                        </td>
                    </tr>
                    </s:iterator>
                </s:iterator>
            </table>

            <div id="pp"></div>
		</div>
	</body>
</html>
