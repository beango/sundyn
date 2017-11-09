<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet"
	href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
<link rel="stylesheet"
	href="css/jquery-ui.css" type="text/css" />
<title><s:text name='sundyn.title' /></title>
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

/*想要改输入日历控件的样子就改下面的CSS样式就可以了*/
/*Date*/
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

.expire, .expire a:link, .expire a:visited {
	color: #999999;
}

.default, .default a:link, .default a:visited {
	color: #000000;
}

.checked, .checked a:link, .checked a:visited {
	color: #FF0000;
}

.today, .today a:link, .today a:visited {
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

#year, #month {
	padding-right: 10px;
	background: url(onbottom.gif) no-repeat center right;
} /*图片路径可以改成自己的*/
/*Date*/
</style>
<script>
	//这段脚本如果你的页面里有，就可以去掉它们了
	//欢迎访问我的网站queyang.com
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
	function $(objID) {
		return document.getElementById(objID);
	}
</script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript"
	    src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
	<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>

	<div id="man_zone">
		<div class="fengge">&nbsp;</div>
		<div class="title360">
			<h3 style="width: 100%;">请选择删除范围：</h3>
		</div>
		<div class="content360 kuang">
			<table width="100%" height="151" border="0" cellpadding="0"
				cellspacing="0" style="border-color: #FFFFFF;">
				<tr>
					<td width="28%" align="right" style="border-color: #FFFFFF;"><s:text
							name='sundyn.query.selectDept' /></td>
					<td width="72%" align="left" style="border-color: #FFFFFF;">
						<div id="dept" class="sundyn_content">
							<select name="deptId">
								<c:forEach items="${deptList}" var="dept" varStatus="index">
									<option value="${dept.id}"
										<c:if test="${index.index ==0 }">
											    selected="selected"
										</c:if>>
										${dept.name}</option>
								</c:forEach>
							</select> <img src="images/arrow.gif" style="cursor: pointer;"
								onclick="deptopen()" />
						</div>
					</td>
				</tr>
				<tr>
					<td width="28%" align="right" style="border-color: #FFFFFF;">
						<s:text name='sundyn.query.selectResult' />
					</td>
					<td width="72%" align="left" style="border-color: #FFFFFF;"><select
						id="keyNo" name="keyNo">
							<c:forEach items="${keyList}" var="key" varStatus="index">
								<option value="${key.keyNo}"
									<c:if test="${index.index==0}">selected="selected"</c:if>>
									${key.name}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td align="right" style="border-color: #FFFFFF;"><s:text
							name='sundyn.total.startDate' /></td>
					<td align="left" style="border-color: #FFFFFF;"><input
						type="text" id="startDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
				</tr>
				<tr>
					<td align="right" style="border-color: #FFFFFF;"><s:text
							name='sundyn.total.endDate' /></td>
					<td align="left" style="border-color: #FFFFFF;"><input
						type="text" class="input_comm" id="endDate"
						onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});" /></td>
				</tr>
				<tr>
					<td align="right" style="border-color: #FFFFFF;">&nbsp;</td>
					<td align="left" valign="bottom" style="border-color: #FFFFFF;"><img
						src="<s:text name='sundyn.total.pic.query'/>" width="80"
						height="25" class="hand" onclick="deleteVideoFileDeal()" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>
