<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		
		
		<script type="text/javascript" src="js/select.js"></script>
		<style>
a:link {
	color: #464646;
	text-decoration: none;
}

a:visited {
	color: #464646;
	text-decoration: none;
}

a:hover {
	color: #ed145b;
	text-decoration: underline;
}

a:active {
	color: #ed145b;
	text-decoration: underline;
}

td {
	font-size: 12px
}

/*想要改输入日历控件的样子就改下面的CSS样式就可以了*/ /*Date*/
.header {
	font: 12px Arial, Tahoma !important;
	font-weight: bold !important;
	font: 11px Arial, Tahoma;
	font-weight: bold;
	color: #154BA0;
	background: #C2DEED;
	height: 25px;
	padding-left: 10px;
}

.header td {
	padding-left: 10px;
}

.header a {
	color: #154BA0;
}

.header input {
	background: none;
	vertical-align: middle;
	height: 16px;
}

.category {
	font: 12px Arial, Tahoma !important;
	font: 11px Arial, Tahoma;
	color: #92A05A;
	height: 20px;
	background-color: #FFFFD9;
}

.category td {
	border-bottom: 1px solid #DEDEB8;
}

.expire,.expire a:link,.expire a:visited {
	color: #999999;
}

.default,.default a:link,.default a:visited {
	color: #000000;
}

.checked,.checked a:link,.checked a:visited {
	color: #FF0000;
}

.today,.today a:link,.today a:visited {
	color: #00BB00;
}

#calendar_year {
	display: none;
	line-height: 130%;
	background: #FFFFFF;
	position: absolute;
	z-index: 10;
}

#calendar_year .col {
	float: left;
	background: #FFFFFF;
	margin-left: 1px;
	border: 1px solid #86B9D6;
	padding: 4px;
}

#calendar_month {
	display: none;
	background: #FFFFFF;
	line-height: 130%;
	border: 1px solid #86B9D6;
	padding: 4px;
	position: absolute;
	z-index: 11;
}

.tableborder {
	background: white;
	border: 1px solid #86B9D6;
}

#year,#month {
	padding-right: 10px;
	background: url(onbottom.gif) no-repeat center right;
} /*图片路径可以改成自己的*/ /*Date*/
</style>
 	</head>
	<body style="text-align: center;">
		<script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>
		<div id="man_zone" style="float: none; margin:0 auto;" >
	 		<div class="fengge">&nbsp;</div>
			<div class="title360" ><h3>个人查询</h3></div>
			<div class="content360 kuang">
				     <table width="100%" height="151" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
 					  <tr>
					    <td align="right" style="border-color:#FFFFFF;"><s:text name='sundyn.total.startDate'/></td>
					    <td align="left" style="border-color:#FFFFFF;"><input type="text" class="input_comm" id="startDate" <s:text name="sundyn.language.calendar.setDay"/> /></td>
					  </tr>
					  <tr>
					    <td align="right" style="border-color:#FFFFFF;"><s:text name='sundyn.total.endDate'/></td>
					    <td align="left" style="border-color:#FFFFFF;">	<input type="text" class="input_comm" id="endDate" <s:text name="sundyn.language.calendar.setDay"/> /></td>
					  </tr>
					  <tr>
					    <td align="right" style="border-color:#FFFFFF;">&nbsp;</td>
					    <td align="left" valign="bottom" style="border-color:#FFFFFF;"><img src="<s:text name='sundyn.total.pic.query'/>"   width="80" height="25" onclick="employeeDeal()" class="hand" /></td>
					  </tr>
				</table>
 			</div> 
		</div>
 	</body>
</html>