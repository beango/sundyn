<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="s" uri="/struts-tags" %>
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
<div class="fengge">&nbsp;</div>
<div style="width:640px;"><img src="images/13_02.gif" /></div>
<div style="width:638px; height:290px;" class="kuang">
	<div class="fengge" style="height:25px;">&nbsp;</div>
	 <div>
	  <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
	     <tr>
	       <td style="border-color:#FFFFFF;" align="center">	<input name="keyword" id="keyword" class="input_comm" /></td>
	       <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerPowerQueryAjax()" class="hand"/></td>
	       <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="powerAddDialog()" class="hand"/></td>
	    </tr>
	   </table>
	 </div>
    <div class="fengge" style="height:25px;">&nbsp;</div>
	<table width="90%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec;border-right:1px solid #bad6ec;">
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