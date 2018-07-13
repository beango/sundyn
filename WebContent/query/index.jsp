<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
      xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"/>
    <META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 1999 21:29:02 GMT"/>
    <title><s:text name='sundyn.title'/></title>
    <link href="${ctx}/assets/stylesheets/application.css?14427995576" rel="stylesheet"/>
    <link href="${ctx}/assets/stylesheets/fontawesome.css?1440992355" rel="stylesheet"/>
    <!-- /### You don't need include in your project -->
    <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet"/>

    <link rel="stylesheet"
          href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="style/lib/jquery-1.9.0/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/i_line.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language'/>.js?11"></script>
    <script type="text/javascript" src="js/jscharts.js"></script>
    <style type="text/css">
        table thead tr{size: 16pt;}
        table td {border:1px solid #bad6ec;padding:5px;}
    </style>
</head>
<body class="theme-default">
<div data-options="region:'center', border:false">
<div style="margin-bottom:5px" class='col-6 mb-10'>
    <div class='easyui-panel' data-options="cls:'panel-primary-no', iconCls:'fa fa-bullhorn', closable:false"
         title='<s:text name="sundyn.main.title1"/>'>
        <table width="100%" cellpadding="0" cellspacing="0"
               style="border-right: 1px solid #bad6ec;">
            <thead>
            <tr>
                <td align="center" valign="middle" class="px13_1"><s:text name="sundyn.column.time"/></td>
                <td align="center" valign="middle" class="px13_1"><c:if test="${bind==true}">
                    <s:text name='sundyn.column.deviceInfo'/>
                </c:if> <c:if test="${bind==false}">
                    <s:text name='sundyn.column.cardNum'/>
                </c:if></td>
                <td align="center" valign="middle" class="px13_1"><c:if test="${bind==true}"><s:text name='sundyn.column.windowName'/>
                </c:if> <c:if test="${bind==false}"><s:text name='sundyn.column.name'/></c:if></td>
                <td align="center" valign="middle" class="px13_1"><s:text name="sundyn.column.appries"/></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${l1}" var="p">
                <tr style="height: 28px;">
                    <td align="center" valign="middle">${p.JieshouTime}</td>
                    <td align="center" valign="middle">${p.CardNum}</td>
                    <td align="center" valign="middle">${p.employeeName}</td>
                    <td align="center" valign="middle">${p.keyName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class='col-6 mb-10 col-last'>
    <div class='easyui-panel' data-options="cls:'panel-primary-no', iconCls:'fa fa-warning', closable:false"
         title='<s:text name="sundyn.main.title2"/>'>
        <table width="100%" cellpadding="0" cellspacing="0"
               style="border-right: 1px solid #bad6ec;">
            <tr>
                <td align="center" valign="middle" class="px13_1"><s:text
                        name="sundyn.column.time"/></td>
                <td align="center" valign="middle" class="px13_1"><c:if
                        test="${bind==true}">
                    <s:text name='sundyn.column.deviceInfo'/>
                </c:if> <c:if test="${bind==false}">
                    <s:text name='sundyn.column.cardNum'/>
                </c:if></td>
                <td align="center" valign="middle"class="px13_1"><c:if
                        test="${bind==true}">
                    <s:text name='sundyn.column.windowName'/>
                </c:if> <c:if test="${bind==false}">
                    <s:text name='sundyn.column.name'/>
                </c:if></td>
                <td align="center" valign="middle" class="px13_1"><s:text
                        name="sundyn.column.appries"/></td>
            </tr>
            <c:forEach items="${l0}" var="p">
                <tr style="height: 28px;">
                    <td align="center" valign="middle">${p.JieshouTime}</td>
                    <td align="center" valign="middle">${p.CardNum}</td>
                    <td align="center" valign="middle">${p.employeeName}</td>
                    <td align="center" valign="middle">${p.keyName}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>

<div class='col-6 mb-10'>
    <div class='easyui-panel' data-options="cls:'panel-primary-no', iconCls:'fa fa-user', closable:false"
         title='<s:text name="sundyn.main.title5"/>'>
        <div id="chartcontainer1">

        </div>
    </div>
</div>

<div class='col-6 col-last'>
    <div class='easyui-panel' data-options="cls:'panel-primary-no', iconCls:'fa fa-tags', closable:false"
         title='<s:text name="sundyn.main.title4"/>'>
        <div id="chartcontainer2">

        </div>
    </div>
</div>
</div>
<script src="${ctx}/js/jquery-2.1.3.min.js"></script>
<script src="${ctx}/js/easyui-1.5.3/jquery.easyui.min.js"></script>
<script language="javascript" type="text/javascript">
$(function () {
    //曲线图
    analyseContentRateIndexAjaxDay(7);
});
</script>
</body>
</html>
