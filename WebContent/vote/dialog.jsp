<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.vote.dialogTitle' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			 <div class="tpdialog">${m.voteTitle}</div>
			 <c:forEach items="${ls}" var="vote" varStatus="index">
			   <div class="tpdialog">  <div class="left">${index.index+1}、${vote.voteSelect}</div> <div class="right"> <div style="height: 100%;width:${(vote.p+1)/1.5}%; background-color:${vote.c};float: left;"></div><div class="left" style="width:70px; ">${vote.voteHit}(${vote.p}%)</div></div></div> 
			 </c:forEach>
		</div>
	</div>
	<div class="bottom">
  			<img src="<s:text name='sundyn.pic.close' />" /  onclick="closeDialog()"
				 class="hand">
 	</div>
</div>