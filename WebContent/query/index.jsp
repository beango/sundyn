<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
      xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"/>
    <META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 1999 21:29:02 GMT"/>
    <title><s:text name='sundyn.title'/></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language'/>.css" type="text/css"></link>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="style/lib/jquery-1.9.0/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/i_line.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language'/>.js?1"></script>
    <script type="text/javascript" src="js/jscharts.js"></script>
</head>
<body>
<table style="width: 100%; height: 97%;border:0px;" border="0">
    <tr style="height: 48%;">
        <td style="width: 48%; height: 49%; min-height: 48%;">
            <div class="" style="height: 96%;">
                <div class="neirong_lanmu_top">
                    <span class="px13 left"><s:text name="sundyn.main.title1"/></span>
                    <span class="right"><a href="#" onclick="indexDetail('${deptIds}','${mk}','<s:text name="sundyn.main.title1"/>')">&nbsp;&nbsp;<s:text name="sundyn.main.more"/></a></span>
                </div>
                <table width="100%" cellpadding="0" cellspacing="0"
                       style="border-right: 1px solid #bad6ec;">
                    <tr>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.time"/></td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><c:if test="${bind==true}">
                            <s:text name='sundyn.column.deviceInfo'/>
                        </c:if> <c:if test="${bind==false}">
                            <s:text name='sundyn.column.cardNum'/>
                        </c:if></td>
                        <td align="center" valign="middle"
                            background="images/table_bg_03.jpg" class="px13_1"><c:if test="${bind==true}"><s:text name='sundyn.column.windowName'/>
                        </c:if> <c:if test="${bind==false}"><s:text name='sundyn.column.name'/></c:if></td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.appries"/></td>
                    </tr>
                    <c:forEach items="${l1}" var="p">
                        <tr style="height: 28px;">
                            <td align="center" valign="middle"><fmt:formatDate
                                    value="${p.JieshouTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td align="center" valign="middle">${p.CardNum}</td>
                            <td align="center" valign="middle">${p.employeeName}</td>
                            <td align="center" valign="middle">${p.keyName}</td>
                        </tr>
                    </c:forEach>
                </table>

            </div>
        </td>
        <td style="width: 48%; height: 49%; min-height: 49%;">
            <div style="height: 96%;">
                <div class="neirong_lanmu_top">
                    <span class="px13 left"><s:text name="sundyn.main.title2"/></span>
                    <span class="right"><a href="#"
                                           onclick="indexDetail('${deptIds}','${bmk}','<s:text name="sundyn.main.title2"/>')"><s:text
                            name="sundyn.main.more"/></a></span>
                </div>
                <table width="100%" cellpadding="0" cellspacing="0"
                       style="border-right: 1px solid #bad6ec;">
                    <tr>
                        <td align="center" valign="middle"
                            background="images/table_bg_03.jpg" class="px13_1"><s:text
                                name="sundyn.column.time"/></td>
                        <td align="center" valign="middle"
                            background="images/table_bg_03.jpg" class="px13_1"><c:if
                                test="${bind==true}">
                            <s:text name='sundyn.column.deviceInfo'/>
                        </c:if> <c:if test="${bind==false}">
                            <s:text name='sundyn.column.cardNum'/>
                        </c:if></td>
                        <td align="center" valign="middle"
                            background="images/table_bg_03.jpg" class="px13_1"><c:if
                                test="${bind==true}">
                            <s:text name='sundyn.column.windowName'/>
                        </c:if> <c:if test="${bind==false}">
                            <s:text name='sundyn.column.name'/>
                        </c:if></td>
                        <td align="center" valign="middle"
                            background="images/table_bg_03.jpg" class="px13_1"><s:text
                                name="sundyn.column.appries"/></td>
                    </tr>
                    <c:forEach items="${l0}" var="p">
                        <tr style="height: 28px;">
                            <td align="center" valign="middle"><fmt:formatDate
                                    value="${p.JieshouTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td align="center" valign="middle">${p.CardNum}</td>
                            <td align="center" valign="middle">${p.employeeName}</td>
                            <td align="center" valign="middle">${p.keyName}</td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </td>
    </tr>
    <tr style="height: 48%;">
        <td style="width: 48%; height: 49%; min-height: 49%;">
            <div style="height: 96%;">
                <div class="neirong_lanmu_top">
                    <span class="px13 left"><s:text name="sundyn.main.title5"/></span>
                    <span class="right"><a href="#" onclick="addTabMenu('首页','<s:text
                            name="sundyn.main.title5"/>','analyseContentRate.action','',true)"><s:text
                            name="sundyn.main.more"/></a></span>
                </div>
                <div id="chartcontainer1">

                </div>
            </div>
        </td>
        <td style="width: 48%; height: 49%; min-height: 49%;">
            <div style="height: 96%;">
                <div class="neirong_lanmu_top">
                    <span class="px13 left"><s:text name="sundyn.main.title4"/></span>
                    <span class="right"><a href="#" onclick="indexCake('${deptIds}')"><s:text name="sundyn.main.more"/></a></span>
                </div>
                <div id="chartcontainer2">

                </div>
            </div>
        </td>
    </tr>
</table>
<script language="javascript" type="text/javascript">
$(function () {
    //曲线图
    analyseContentRateIndexAjaxDay(7);
});
</script>
</body>
</html>
