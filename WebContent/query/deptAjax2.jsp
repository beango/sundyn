<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${deptList.size()>0}">
<div id="deptitem${level}" class="layui-input-inline" style="width: 100px;">
    <select name="deptId${level}" lay-filter="queryDept${level}" dataType="${dataType}">
        <option value="" selected="selected">--全部--</option>
        <c:forEach items="${deptList}" var="dept" varStatus="index">
            <option value="${dept.id}" <c:if test="${index.index ==999999 }">selected="selected"</c:if>>${dept.name}</option>
        </c:forEach>
    </select>
</div>
</c:if>
