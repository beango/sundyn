<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name="sundyn.title" /> </title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			 <div class="fengge">&nbsp;</div>
		     <div style="width:732px;padding-left:5px">
				<table width="100%"  cellpadding="0" cellspacing="0">
				  <tr style="height: 35px;">
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.query.error.happenTime"/></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.employeeCardNum' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.demoMac' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.appriesKey' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.query.error.info"/></td>
				  </tr>
				  <c:forEach items="${pager.pageList}" var="errorInfo">
						<tr style="height: 30px;">
							<td  align="center" valign="middle">
								${errorInfo.appriestime}&nbsp
							</td>
							<td  align="center" valign="middle">
								${errorInfo.cardnum}&nbsp
							</td>
							<td  align="center" valign="middle">
								${errorInfo.reamark}&nbsp
							</td>
							<td  align="center" valign="middle">
								${errorInfo.keyId}&nbsp
							</td>
							<td  align="center" valign="middle">
								${errorInfo.ext1}&nbsp
							</td>
						</tr>
					</c:forEach>
 				</table>
			 </div> 
 				<div class="sundyn_row">
					${pager.pageTextCn}
				</div>
 		</div>
	</body>
</html>
