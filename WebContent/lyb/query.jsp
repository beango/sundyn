<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title> <s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	 <body>
	 <div id="man_zone">
		 <div class="fengge">&nbsp;</div>
		 <div class="title732"><h3> <s:text name='sundyn.faq.title'/></h3></div>
	     <div class="faq" id="faq">
	         <c:forEach var="lyb" items="${list}" varStatus="index">
		         <h3 onclick="showFAQ(${index.index})">${index.index+1}.${lyb.lybTitle}</h3>
		         <ul id="c${index.index}"    <c:if test="${index.index==0 }">style="display:block;"</c:if>  >
		           <li><s:text name='sundyn.faq.ask'/>${lyb.lybAsk}</li>
		           <li><s:text name='sundyn.faq.answer'/>${lyb.lybAnswer}</li>
		         </ul>
	         </c:forEach>
		 </div> 
	</div>
	<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
 	 </body>
</html>