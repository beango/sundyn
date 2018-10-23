<%@ page import="java.util.Date" %>
<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <link rel="stylesheet" href="css/dialog.css" type="text/css"/>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="css/dtree.css" type="text/css"/>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <title><s:text name='sundyn.title'/></title>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/ddtree.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?<%=new Date().getTime()%>"></script>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <script type="text/javascript" src="lib/layer/layer.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<div id="man_zone">
    <div class="center_04" style="width: 730px;">
        <div class="center_04_left">
            <div class="dtree kuang" id="tree" style="text-align: left;">
                <script type="text/javascript">
                    d = new dTree('d');
                    <c:forEach items="${list}" var="dept">
                    d.add(${dept.id}, ${dept.fatherId}, '${dept.name}', 'javascript:employeeManage(${dept.id})');
                    </c:forEach>
                    document.write(d);
                </script>
            </div>
        </div>
        <input type="hidden" id="deptId"/>
        <div class="center_04_right" style="width: 480px; height: 100%;" id="employeeView">
            <div style="height: 380px;overflow: hidden;" class="kuang1">
                <%--<s:text name='sundyn.employee.info'/><br/>
                <div class="employeeHr"></div>
                <img src="<s:text name='sundyn.employee.pic.employeeExcel' />" class="hand" onclick="employeeExcel()"/>--%>
            </div>
        </div>
    </div>
</div>
<div id="dialog" style="width: 700px; display: none;">
</div>
</body>
</html>
