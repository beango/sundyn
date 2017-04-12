<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach items="${deptList}" var="people">
	<a href="#" onclick="selectPeople('${people.Id}','${people.Name}')" title="${people.deptName}->${people.Name}(${people.CardNum})"><li> ${people.Name}</li></a>
</c:forEach>
			