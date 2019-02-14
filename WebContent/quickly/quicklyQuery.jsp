<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
			 <div class="title732"><h3> <s:text name='sundyn.quickkyQuery.title'/></h3></div>
		     <div style="width:732px;">
				<table width="100%"  cellpadding="0" cellspacing="0" style="border-right: 1px solid #bad6ec;">
				  <tr style="height: 35px;">
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.queryName'/></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.createDate'/> </td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation'/></td>
				  </tr>
				  <c:forEach items="${quicklyList}" var="quickly">
						<tr>
							<td align="center">
								${quickly.name}
							</td>
							<td align="center">
								${quickly.startDate}
							</td>
							<td align="center">
							<img src="<s:text name='sundyn.quickkyQuery.pic.query'/>" class="hand" style="margin-top:3px;" onclick="quicklyQuery('${quickly.excuteSql}')" />
							&nbsp;
							<img src="<s:text name='sundyn.quickkyQuery.pic.del'/>" class="hand" style="margin-top:3px;" onclick="quicklyDel(${quickly.id})"  />
							</td>
						</tr>
					</c:forEach>
  				</table>
			 </div> 
			<div>
				<s:text name='sundyn.quickkyQuery.msg'/>
			</div>
	</div>
	<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
 	 </body>
</html>
