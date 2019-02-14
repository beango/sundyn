<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <title><s:text name='sundyn.title' /></title>

    <script src="assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
</head>
<body>
<div class="layui-main layui-form">
    <div class="layui-form-item">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.query.selectDept' /></label>
    <div class="layui-select-cus layui-inline" style="padding-left:0; margin-left:0;">
        <div class="layui-form-mid layui-word-aux">
        </div>
    </div>
    </div>
    <div class="layui-form-item">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.query.selectResult' /></label>
    <div class="layui-input-inline">
        <select id="keyNo" name="keyNo">
            <c:forEach items="${keyList}" var="key" varStatus="index">
                <option value="${key.keyNo}" <c:if test="${index.index==0}">selected="selected"</c:if>>${key.name}</option>
            </c:forEach>
        </select>
    </div>
    </div>
    <div class="layui-form-item">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.total.startDate' /></label>
    <div class="layui-input-inline">
        <input type="text" class="input_comm" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
    </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.total.endDate' /></label>
        <div class="layui-input-inline">
            <input type="text" class="input_comm" id="endDate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" />
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:90px;"></label>
        <div class="layui-input-inline">
            <a href="#" class="layui-btn" width="80" height="25" class="hand" onclick="deleteVideoFileDeal()">开始清理</a>
        </div>
    </div>
</div>
</body>
<script>
    layui.use('form', function(){
        var form = layui.form;
        var deptpath = '<%=request.getParameter("deptpath")==null?"":request.getParameter("deptpath")%>'.split(",");
        renderchild(form, -1, -1, deptpath,"dating");
    });
</script>
</html>
