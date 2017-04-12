<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css" />
		<title> <s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/ddtree.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
	<script>
	</script>
 		<div id="man_zone">
 		  <div class="fengge" style="height:15px;">&nbsp;</div>
 		  <img src="<s:text name='sundyn.guide.pic.top1'/>" />
 		  <div class="guide_start2"></div>
 		  <div class="fengge" style="height:10px;">&nbsp;</div>
 		  <table style="width:731px;margin:0 auto;">
 		     <tr>
 		      <td width="50%" class="guidetitle"> <s:text name='sundyn.guide.s'/><input type="radio" name="multi"  id="multi" checked="checked"  />   </td>
 		      <td width="50%" class="guidetitle"><s:text name='sundyn.guide.m'/><input type="radio" name="multi"  id="multi2"/></td>
 		     </tr>
 		     <tr>
 		       <td height="250"><img src="<s:text name='sundyn.guide.pic.s'/>" /> </td>
 		       <td height="200"><img src="<s:text name='sundyn.guide.pic.m'/>" /></td>
 		     </tr>
 		     
 		  </table>
 		  <div class="fengge" style="height:21px;">&nbsp;</div>
 		  <div class="guide_bottom">
 		     <img src="<s:text name='sundyn.guide.pic.next'/>" onclick="guideSimpleOne()" />
 		     <img src="<s:text name='sundyn.guide.pic.leave'/>" onclick="guideComplete()" />
 		  </div>
   	   </div>
 	</body>
</html>
