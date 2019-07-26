<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>安全日志查询</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
</head>
<body>
<script type="text/javascript">
    function query(){
        var key = $("#key").val();
        window.location.href = "auditlogin.action?key="+key + "&servicedate=" + $("#servicedate").val() + "&logtype="+$("#logtype").val();
    }
</script>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="deptId" value="${deptId}"/>
<form class="layui-form" action="">
    <div class="layui-form-item">
        <label class="layui-form-label" style="width:60px;"><s:text name="auditlog.field.type" /></label>
        <div class="layui-input-inline">
            <select name="logtype" id="logtype">
                <option value="" <c:if test="${logtype==null || logtype==''}"> selected="selected"</c:if>><s:text name="main.all"/></option>
                <option value="高频访问" <c:if test="${logtype=='高频访问'}"> selected="selected"</c:if>><s:text name="auditlog.logTypeEnumAccessPerTimes"/></option>
                <option value="特殊访问时间段" <c:if test="${logtype=='特殊访问时间段'}"> selected="selected"</c:if>><s:text name="auditlog.logTypeEnumSpecialTime"/></option>
                <option value="账号长期未使用" <c:if test="${logtype=='账号长期未使用'}"> selected="selected"</c:if>><s:text name="auditlog.logTypeEnumAccountLongTimeUnused"/></option>
                <option value="访问非常规业务" <c:if test="${logtype=='访问非常规业务'}"> selected="selected"</c:if>><s:text name="auditlog.logTypeEnumNotGeneral"/></option>
                <option value="访问核心功能" <c:if test="${logtype=='访问核心功能'}"> selected="selected"</c:if>><s:text name="auditlog.logTypeEnumCoreService"/></option>
                <option value="数据被篡改" <c:if test="${logtype=='数据被篡改'}"> selected="selected"</c:if>><s:text name="auditlog.logTypeEnumDataTampered"/></option>
            </select>
        </div>
        <div class="layui-input-line">
            <div class="layui-inline">
                <div class="layui-input-inline">
                    <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
                </div>
            </div>
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq"/></th>
                <th>
                    <s:text name="auditlogin.column.name"/>
                </th>
                <th>
                    <s:text name="auditlogin.column.dept"/>
                </th>
                <th>
                    <s:text name="auditlogin.column.type"/>
                </th>
                <th>
                    <s:text name="auditlogin.column.time"/>
                </th>
                <th>
                    <s:text name="auditlogin.column.result"/>
                </th>
                <th>
                    <s:text name="auditlogin.column.ip"/>
                </th>
                <th align="center">
                    <s:text name="auditlogin.column.client"/>
                </th>
                <th>
                    <s:text name="auditlogin.column.desc"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.realname} / ${data.name}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>${data.logtypei18n}<c:if test="${data.checkdigited==1}"><font style="color:red;"><s:text name="main.data.noverify" /></font></c:if>
                        <td>
                                ${data.ctime}
                        </td>
                        <td>
                                ${data.logrst}
                        </td>
                        <td>
                                ${data.ipadd}
                        </td>
                        <td>
                                ${data.logdevice}
                        </td><td>
                            ${data.logrstdesc}
                    </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div id="pp"></div>
    </div>
</form>

</body>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
    layui.use(['form','element']);
</script>
</html>
