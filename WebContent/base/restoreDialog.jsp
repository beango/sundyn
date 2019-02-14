<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			还原数据
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
		   <c:forEach var="f" items="${ls}">
		     <div style="text-align: left;">${f} &nbsp;&nbsp;&nbsp;&nbsp; <a href="#" class="r" onclick="baseRestore('${f}')"> <img src="images/smallRestore.gif">  </a> <a href="#" class="r" onclick="baseDelRestore('${f}')"> <img src="images/smallDel.gif">  </a>  </div>
		   </c:forEach>
		</div>
	</div>
	<div class="bottom" id="bottom">
 		
	</div>
</div>
