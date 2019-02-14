<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<select id="windowId" name="windowId">
		<c:forEach items="${list}" var="dept" varStatus="index">
			<option <c:if test="${index.index==0}">  selected="selected" </c:if>
				value="${dept.id }">
				${dept.name}
			</option>
		</c:forEach>
	</select>
