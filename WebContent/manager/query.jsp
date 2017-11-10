<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title>用户管理</title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<input type="hidden" name="managerId" id="managerId" value="${managerId}" />
			<div style="width:99%; height:100%;" class="">
			    <div>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;" align="">
				       <tr>
				         <td style="border-color:#FFFFFF;width:120px;" align="center"><input name="keyword" id="keyword" class="input_comm" /></td>
				         <td style="border-color:#FFFFFF;width:60px;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerManagerQueryAjax()" class="hand"/></td>
				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="lowerManagerAddDialog()" class="hand"/></td>
				      </tr>
				     </table>
			    </div>
				<table width="100%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right: 1px solid #bad6ec;">
				  <tr>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.userName' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.realName' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.tip' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
				    </tr>
				   	<c:forEach items="${pager.pageList}" var="manager">
						<tr>

							<td style="text-align: center;">
								${manager.name}
							</td>

							<td style="text-align: center;">
								${manager.realname}
							</td>
							<td style="text-align: center;">
							   <div style="width:100px;height:20px;text-align: center;text-overflow:ellipsis;overflow:hidden;" >
								<c:if test="${manager.remark==0}"><s:text name='sundyn.user.select1' />  </c:if>
								<c:if test="${manager.remark==1}"><s:text name='sundyn.user.select2' /><s:text name='sundyn.colon' />${manager.ext1}</c:if>
								<c:if test="${manager.remark==2}"><s:text name='sundyn.user.select3' /><s:text name='sundyn.colon' />${manager.ext2}</c:if>
								<c:if test="${manager.remark==3}"><s:text name='sundyn.user.select2' /><s:text name='sundyn.colon' />${manager.ext1}<s:text name='sundyn.comma' /><s:text name='sundyn.user.select3' /><s:text name='sundyn.colon' />${manager.ext2}</c:if>
								</div>
							</td>
							<td style="text-align: center;">
								<a href="javascript:managerEditDialog(${manager.id});"><s:text name='sundyn.modify' /></a>
								<a href="javascript:managerDel(${manager.id});"><s:text name='sundyn.del' /></a>
								<a href="javascript:managerReset(${manager.id});"><s:text name='sundyn.employee.resetPassword' /></a>
							</td>
						</tr>
					</c:forEach>
				</table>
			    <div class="fengge" style="height:15px;">&nbsp;</div>
				<div> ${pager.pageTextAjax} </div>
		    </div>
		</div>
		<div id="dialog" style="width: 600px; display: none;">
	</body>
</html>
