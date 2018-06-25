<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><s:text name="sundyn.query.weburl"></s:text></title>

    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="css/dialog.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

    <script type="text/javascript" src="${ctx}/assets/javascripts/jquery-2.1.3.min.js" ></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>

    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script src="${ctx}/js/application.js"></script>
</head>
<body>
<div class="layui-main">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="txtTitle" id="txtTitle" value="<%=request.getParameter("key_title")==null?"":request.getParameter("key_title")%>" class="input_comm" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="images/04_03_10.jpg" width="55" height="25" onclick="query()" class="hand">
            <img src="<s:text name='sundyn.pic.add' />" width="55" height="25" onclick="weburToAdd();" class="hand" />
        </div>
    </div>
    <input type="hidden" name="managerId" id="managerId" value="${managerId}" />
    <div style="width: 100%;">
        <table id="tbl" width="100%" class="layui-table">
            <thead>
            <tr>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.weburl.name' /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${weburls}" var="webUrl" varStatus="index">
                <tr>
                    <td style="text-align: center;">${index.index+1}</td>
                    <td style="text-align: center;">
                        <div style="width: 170px; height: 20px; text-align: center; text-overflow: ellipsis; overflow: hidden;">${webUrl.name}</div>
                    </td>
                    <td style="text-align: center;">
                        <a href="javascript:weburlToUpate('${webUrl.id}');"><s:text name='sundyn.modifyOrupdate' /></a>
                        <a href="javascript:weburlDelete('${webUrl.id}');"><s:text name='sundyn.del' /></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div id="pp">
        </div>
    </div>
</div>
<div id="dialog-window" style="display: none;"></div>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    function query(){
        refreshTab({key_title: $("#txtTitle").val()});
    }
</script>
</body>
</html>
