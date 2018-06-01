<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312"/>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="css/dialog.css" type="text/css"/>
    <title><s:text name='sundyn.title'/></title>
    <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/application.css?1442799557" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/main.css?1514875963" rel="stylesheet" media="screen">
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/line.js"></script>
    <script type="text/javascript" src="js/jscharts.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body class="container theme-default easyui-layout">
<form id="form1" name="form1" method="post" action="">
    <table width="100%" height="50" border="0" cellpadding="0" cellspacing="0"
           style="border-color:#FFFFFF;">
        <tr>
            <td style="border-color:#FFFFFF;" align="left">
                <div style="float:left;"><s:text name='sundyn.query.selectEmployee'/></div>
                <div id="dept" class="sundyn_content" style="float: left;">
                    <a id="deptSelectMenu" class="easyui-menubutton l-btn-primary" data-options="menu:'#mm-deptSelectMenu'" href="javascript:void(0)">-- 请选择 --</a>
                    <div data-options='itemHeight:32' id='mm-deptSelectMenu' style="display: none;">
                        <jsp:include page="../control/recursive.jsp">
                            <jsp:param name="objectid" value="deptSelectMenu,id"></jsp:param>
                            <jsp:param name="fatherId" value="-1"></jsp:param>
                            <jsp:param name="idPath" value='-1'></jsp:param>
                            <jsp:param name="namePath" value=""></jsp:param>
                            <jsp:param name="selected" value="${id}"></jsp:param>
                            <jsp:param name="showEmployee" value="true"></jsp:param>
                        </jsp:include>
                    </div>
                </div>
                <input type="hidden" name="id" id="id"/>
                <s:text name='sundyn.total.startDate'/>
                <input type="text" id="startDate" class="input_comm" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                <s:text name='sundyn.total.endDate'/>
                <input type="text" id="endDate" class="input_comm"<s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                <s:text name="sundyn.analyse.danwei"/>
                <label><input type="radio" name="type" checked="checked" value="day"/><s:text name='sundyn.analyse.day'/></label>
                <label><input type="radio" name="type" value="month"/><s:text name='sundyn.analyse.month'/></label>
                <label><input type="radio" name="type" value="year"/><s:text name='sundyn.analyse.year'/></label>
                <img src="<s:text name="sundyn.analyse.businessNumAnalyse" />" onclick="analyseEmployeeAjax(w)" style="cursor: pointer; vertical-align: middle;"/>
                <img src="<s:text name="sundyn.analyse.contentNumAnalyse" />" onclick="analyseEmployeeContentAjax(w)" style="cursor: pointer; vertical-align: middle;"/>
                <img src="<s:text name="sundyn.analyse.contentDegreeAnalyse" />" onclick="analyseEmployeeContentRateAjax(w)" style="cursor: pointer; vertical-align: middle;"/>
            </td>
        </tr>
    </table>
</form>
<div style="height:100%;width:100%;" id="chartcontainer">
</div>
<div id="dialog" style="width: 479px; height: 328px; display: none;">
</div>
</body>
<script src="${ctx}/js/easyui-1.5.3/jquery.easyui.min.js"></script>
<script src="${ctx}/js/main.js"></script>
<script type="text/javascript">
    var w;
    $(function(){
        w  = $("#chartcontainer").width();
        analyseEmployeeAjax(w);
    });
</script>
</html>
