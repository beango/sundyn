<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${employeeTreeData}" var="emp" varStatus="index"><c:if test="${emp.deptid==param.fatherId}">
    <c:if test="${param.selected==emp.id+''}"><c:set value="${param.namePath} > ${dept.name}" var="selectNamePath" ></c:set>
        <script type="text/javascript">document.getElementById('${param.objectid.split(',')[0]}').text='${param.namePath} > ${emp.name}'.substring(2);</script>
    </c:if>
    <div data-options="iconCls:'fa fa-chevron-circle-right'" onclick="CusControlSelect('${param.objectid}','${param.idPath}|${dept.id}','${param.namePath} > ${emp.name}','${emp.id}')">
    <span><a href="#" style="font-weight:${param.selected==dept.id+""?"bold":"inhert"};">${emp.Name}</a></span>
    </div>
</c:if></c:forEach>
