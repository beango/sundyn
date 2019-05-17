<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${ctx}/css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="${ctx}/css/dtree.css" type="text/css" />
		<title><s:text name='zx.title'/></title>
        <script type="text/javascript" src="${ctx}/js/jquery.js"></script>
		<script type="text/javascript" src="${ctx}/js/ddtree.js"></script>
		<script type="text/javascript" src="${ctx}/js/dojo.js"></script>
		<script type="text/javascript" src="${ctx}/js/dialog.js"></script>
		<script type="text/javascript" src="${ctx}/js/my_<s:text name='sundyn.language' />.js"></script>
		<style type="text/css">
		 table{
		  border: 0px solid red;
		 }
		 td{
		   border: 0px solid red;
		 }
		
		</style>
		
	</head>
	<body>
	<script>
	</script>
 	   <div id="man_zone">
 	   <div class="fengge" style="height: 49px;"></div>
 	   	  <div class="register_top"></div>
 	   	  <div class="register_2">
 	   	    <table width="463" border="0" cellspacing="0" cellpadding="0" style="border:0px; "   >
			  <tr>
			    <td width="120">&nbsp;</td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td><div align="right"><s:text name='sundyn.register.applyNum'/></div></td>
			    <td align="left"><input  type="text" name="ser" id="ser" style="width: 300px;height: 19px;border: 1px solid #a8acad;" value="${ser}"/>    </td>
			  </tr>
			  <tr>
			    <td><div align="right"><s:text name='sundyn.register.serialNum'/></div></td>
			    <td align="left"> <input  type="text" name="serTxt" id="serTxt" style="width: 123px;height: 18px;border: 1px solid #70a9d4;"/> </td>
			  </tr>
			  <tr>
			    <td  > &nbsp;</td>
			    <td align="left">
                    <input type="button" value="注册" onclick="registerReg()"
                           class="layui-btn"/></td>
			  </tr>
 			</table>
 			<div>${msg}</div>
 	   	  </div>
 	   </div>
		<div id="dialog" style="width: 600px; display: none;">
		</div>
	</body>
</html>
