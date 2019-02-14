<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
 	</head>
	<body>
		<div class="layui-main">
            <div><a href="m7OnlineExcel.action" target="_blank"><img src="<s:text name='sundyn.total.pic.excel'/>" /></a>&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <table width="98%" class="layui-table" >
                <tr>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                        <s:text name="sundyn.system.checkM7Info.macAddress"/>
                    </td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                        <s:text name="sundyn.column.windowName"/>
                    </td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                        <s:text name="sundyn.system.checkOnlineM7.unitName"/>
                    </td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                        <s:text name="sundyn.system.checkOnlineM7.onlineOrNot"/>
                    </td>
                </tr>
                <c:forEach items="${pager.pageList}" var="query">
                    <tr>
                        <td   class="td"  <c:if test="${query.online ==  '在线'}">  style="color:green;" </c:if>  <c:if test="${query.online ==  '不在线'}">  style="color:gray;" </c:if> >
                                ${query.remark}
                        </td>
                        <td   class="td"  <c:if test="${query.online ==  '在线'}">  style="color:green;" </c:if>  <c:if test="${query.online ==  '不在线'}">  style="color:gray;" </c:if> >
                                ${query.name}
                        </td>
                        <td   class="td"  <c:if test="${query.online ==  '在线'}">  style="color:green;" </c:if>  <c:if test="${query.online ==  '不在线'}">  style="color:gray;" </c:if> >
                                ${query.fatherName}
                        </td>
                        <td   class="td"  <c:if test="${query.online ==  '在线'}">  style="color:green;" </c:if>  <c:if test="${query.online ==  '不在线'}">  style="color:gray;" </c:if> >
                            <c:if test="${query.online ==  '在线'}">  <s:text name="sundyn.system.m7Online.online"/> ${(query.ipadd==null||query.ipadd.equals(""))?"":"(".concat(query.ipadd).concat(")")} </c:if>  <c:if test="${query.online ==  '不在线'}"> <s:text name="sundyn.system.m7Online.offline"/></c:if>
                        </td>
                    </tr>
                </c:forEach>
                <c:forEach items="${errorMac}" var="mac">
                    <tr>
                        <td   class="td" style="color:red;">
                                ${mac}
                        </td>
                        <td   class="td" style="color:red;">
                            <s:text name="sundyn.system.m7Online.noWindow"/>
                        </td>
                        <td   class="td" style="color:red;">
                            -
                        </td>
                        <td   class="td" style="color:red;">
                            <s:text name="sundyn.system.m7Online.online"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="pp"></div>
        </div>
 	</body>
    <script type="text/javascript">
        initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()},${pager.getPageSize()});
    </script>
</html>