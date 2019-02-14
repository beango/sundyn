<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<ul>
   <c:forEach items="${ls}" var="dept">
   	<li><span>${dept.name }</span><img src="images/small_button_edit.gif" onclick="guideEdit(${dept.id})" /> &nbsp;&nbsp;<img src="images/small_button_del.gif" onclick="guideDel(${dept.id})" /></li>
   </c:forEach>
</ul>