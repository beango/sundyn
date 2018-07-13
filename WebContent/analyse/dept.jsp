<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8"/>
    <title><s:text name='sundyn.title'/></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script src="${ctx}/assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/line.js"></script>
    <script type="text/javascript" src="js/jscharts.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?1"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
</head>
<body class="layui-form">
<div class="layui-select-cus layui-inline">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.query.selectDept'/></label>
    <div class="layui-form-mid layui-word-aux">
    </div>
</div>
<input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
<div class="layui-inline">
    <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
    <div class="layui-input-inline">
        <input type="text" class="input_comm" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </div>
</div>
<div class="layui-inline">
    <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
    <div class="layui-input-inline">
        <input type="text" class="input_comm" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </div>
</div>
<div class="layui-inline">
    <div class="layui-input-inline">
        <input type="radio" name="type" checked="checked" value="day" title="<s:text name='sundyn.analyse.day'/>"/>
        <input type="radio" name="type" value="month" title="<s:text name='sundyn.analyse.month'/>"/>
        <input type="radio" name="type" value="year" title="<s:text name='sundyn.analyse.year'/>"/>
        <img src="<s:text name="sundyn.analyse.businessNumAnalyse" />" onclick="analyseDeptAjax(w)" style="cursor: pointer; vertical-align: middle;"/>
        <img src="<s:text name="sundyn.analyse.contentNumAnalyse" />" onclick="analyseDeptContentAjax(w)" style="cursor: pointer; vertical-align: middle;"/>
        <img src="<s:text name="sundyn.analyse.contentDegreeAnalyse" />" onclick="analyseDeptContentRateAjax(w)" style="cursor: pointer; vertical-align: middle;"/>
    </div>
</div>


<div style="height:100%;width:100%;" id="chartcontainer">
</div>

</body>
<script type="text/javascript">
    //Demo
    layui.use('form', function(){
        var form = layui.form;
        var deptpath = '<%=request.getParameter("deptpath")==null?"":request.getParameter("deptpath")%>'.split(",");
        renderchild(form, -1, -1, deptpath,'window');
    });

    var w;
    $(function () {
        w = $("#chartcontainer").width();
        analyseDeptAjax(w);
    })
</script>
</html>
