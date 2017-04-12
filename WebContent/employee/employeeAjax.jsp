<%@ page  pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="employee" items="${list}" varStatus="index">
	<div  onmouseover="selectCurrent(this)" onclick="setcardnum('${employee.CardNum}')">
	   <span style="width:40%;float: left; color:#000;">${employee.CardNum}</span><span style="width:60%;float: right;text-align: right; color: red;">${employee.Name}</span>
	   <input type="hidden" id="e${index.index}" value="${employee.CardNum}" />
    </div>
</c:forEach>