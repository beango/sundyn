<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title>终端管理</title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			 <div class="fengge" style="height:15px;">&nbsp;</div>
			 <div style="width:99%;padding-left: 5px">
			 <table width="99%"   cellpadding="0" cellspacing="0" align="center">
			  <tr>
			    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.apprieser.index' /></td>
			    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.apprieser.apprieserName' /></td>
			    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
			  </tr>
			  <c:forEach items="${list}" var="apprieser" varStatus="index">
						<tr style="height: 30px;">
							<td align="center" valign="middle">
								${index.index+1}
							</td>
							<td align="center" valign="middle">
								<s:text name="sundyn.system.deviceManagement.sixKeyMachine"/>
							</td>
							<td align="center" valign="middle">
							   <div class="button" onclick="keyTypeQueryDialog(${apprieser.apprieserid})"><s:text name='sundyn.apprieser.keySteup' /></div>
							</td>
						</tr>
		 	 </c:forEach>
			 </table>
			</div>
		</div>
		<div id="dialog" style="width: 700px; display: none;">
	</body>
</html>
