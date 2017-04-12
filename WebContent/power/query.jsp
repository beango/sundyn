<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title>角色管理</title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript" src="js/dtree.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div style="width:99%;" class="">
			    <div>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
				       <tr>
				         <td style="border-color:#FFFFFF;width:120px;" align="center">	<input name="keyword" id="keyword" class="input_comm" /></td>
				         <td style="border-color:#FFFFFF;width:60px;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerPowerQueryAjax()" class="hand"/></td>
				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="powerAddDialog()" class="hand"/></td>
				      </tr>
				     </table>
			    </div>
				<table width="100%" cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right:1px solid #bad6ec;">
				  <tr>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.roleName" /></td>
				     <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.systemSetup" /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.baseSetup" /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.dept" /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.operation" /></td>
				  </tr>
				    <c:forEach items="${pager.pageList}" var="power">
						<tr>
							<td style="text-align: center;">
								${power.name}
							</td>
							<td style="text-align: center;">
								<c:if test="${ power.baseSet==true    }">
									<s:text name='sundyn.yes' />
							    </c:if>
								<c:if test="${   power.baseSet==false   }">
									<s:text name='sundyn.no' />
							     </c:if>
							</td>
							<td style="text-align: center;">
								<c:if test="${power.dataManage ==  true}">
								   <s:text name='sundyn.yes' />
								</c:if>
								<c:if test="${power.dataManage ==  false}">
								   <s:text name='sundyn.no' /> 
								</c:if>
							</td>
							<td style="text-align: center;">
								${power.deptIdGroup}
							</td>
								<td style="text-align: center;">
								<a href="javascript:powerEditDialog(${power.id});"><s:text name='sundyn.modify' /></a>
								<a href="javascript:powerDel(${power.id});"><s:text name='sundyn.del' /></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			    <div class="fengge" style="height:15px;">&nbsp;</div>
				<div>${pager.pageTextAjax}</div>
		    </div> 
		</div>
		<div id="dialog" style="width: 600px; display: none;">
	</body>
</html>