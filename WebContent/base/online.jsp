 v<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name="sundyn.title" /></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
		  <div class="fengge">&nbsp;</div>
			<div style="width:640px;"><img src="<s:text name='sundyn.online.pic.title' />" /></div>
			<div style="width:638px; height:290px;" class="kuang">
			   <div class="online">
					<div>
						<s:text name='sundyn.online.currentOnlineEmployeeNum' >
							<s:param>${fn:length(list)}</s:param>
						</s:text>
 					</div>
					  <ul>
					    <c:forEach items="${list}" var="employee">
					    <li>  ${employee.deptName} ->  ${employee.name}   </li>
					    </c:forEach>  
 					  </ul>
				    </div>
			   </div> 
		</div>
	</body>
</html>