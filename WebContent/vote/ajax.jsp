<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:forEach items="${ls}" var="vote" varStatus="index">
	<c:if test="${index.index % 2 ==0}">
		<div class="a718">
			<div <c:if test="${vote.voteIsUse!=1}"> class="tplefta"   </c:if><c:if test="${vote.voteIsUse==1}"> class="tpleftb"   </c:if>	id="vote${vote.voteId}">
				${vote.voteTitle}
			</div>
			<div class="tpright">
				<a href="#" onclick="voteResult(${vote.voteId})"><s:text name='sundyn.vote.voteResult' /></a>
				<a href="#" onclick="voteUse(${vote.voteId})"><s:text name='sundyn.vote.use' /></a>
				<a href="#" onclick="voteDel(${vote.voteId})"><s:text name='sundyn.del' /></a>
			</div>
		</div>
	</c:if>
	<c:if test="${index.index % 2 !=0}">
		<div class="b718">
			<div <c:if test="${vote.voteIsUse!=1}"> class="tplefta"   </c:if><c:if test="${vote.voteIsUse==1}"> class="tpleftb"   </c:if> id="vote${vote.voteId}">
				${vote.voteTitle}
			</div>
			<div class="tpright">
				<a href="#" onclick="voteResult(${vote.voteId})"><s:text name='sundyn.vote.voteResult' /></a>
				<a href="#" onclick="voteUse(${vote.voteId})"><s:text name='sundyn.vote.use' /></a>
				<a href="#" onclick="voteDel(${vote.voteId})"><s:text name='sundyn.del' /></a>
			</div>
		</div>
	</c:if>
</c:forEach>