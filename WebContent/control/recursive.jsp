<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:forEach items="${deptJSON}" var="dept" varStatus="index"><c:if test="${dept.fatherId==param.fatherId}">
    <div data-options="iconCls:'fa fa-chevron-circle-right'">
        <c:if test="${param.selected==dept.id+''}"><c:set value="${param.namePath} > ${dept.name}" var="selectNamePath" ></c:set>
        <script type="text/javascript">document.getElementById('${param.objectid.split(',')[0]}').text='${param.namePath} > ${dept.name}'.substring(2);</script>
        </c:if>
    <span><a href="#" style="font-weight:${param.selected==dept.id+""?"bold":"inhert"};" onclick="CusControlSelect('${param.objectid}','${param.idPath}|${dept.id}','${param.namePath} > ${dept.name}','${dept.id}')">${dept.name}</a></span>
    <c:if test="${dept.childs>0}">
        <div>
            <jsp:include page="../control/recursive.jsp">
                <jsp:param name="objectid" value="${param.objectid}"></jsp:param>
                <jsp:param name="fatherId" value="${dept.id}"></jsp:param>
                <jsp:param name="idPath" value="${param.idPath}|${dept.id}"></jsp:param>
                <jsp:param name="namePath" value="${param.namePath} > ${dept.name}"></jsp:param>
                <jsp:param name="selected" value="${param.selected}"></jsp:param>
                <jsp:param name="showEmployee" value="${param.showEmployee}"></jsp:param>
            </jsp:include>
        </div>
    </c:if>
        <c:if test="${dept.childs==null and param.showEmployee}">
            <div>
                <jsp:include page="../control/recursiveEmployee.jsp">
                    <jsp:param name="objectid" value="${param.objectid}"></jsp:param>
                    <jsp:param name="fatherId" value="${dept.id}"></jsp:param>
                    <jsp:param name="idPath" value="${param.idPath}|${dept.id}"></jsp:param>
                    <jsp:param name="namePath" value="${param.namePath} > ${dept.name}"></jsp:param>
                    <jsp:param name="selected" value="${param.selected}"></jsp:param>
                    <jsp:param name="deptid" value="${param.selected}"></jsp:param>
                </jsp:include>
            </div>
        </c:if>
    </div>
</c:if></c:forEach>
