<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css"></link>
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
		<link rel="stylesheet" href="css/dialog.css" type="text/css"></link>
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript" src="js/dtree.js"></script>
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
		<script>
			//这段脚本如果你的页面里有，就可以去掉它们了
			//欢迎访问我的网站queyang.com
			var ie =navigator.appName=="Microsoft Internet Explorer"?true:false;
			function $(objID){
				return document.getElementById(objID);
			}
		</script>
	</head>
	<body>
<%--		<script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>--%>
		 <s:if test='getText("sundyn.language") eq "en"'>
		<script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		</s:if>
		<s:else>
		    <script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>
		 </s:else>
		<div id="man_zone">
			<div class="fengge">&nbsp;</div>
			<div class="center_04">
			     <div class="center_04_left">
				      <div><img src="<s:text name='sundyn.query.pic.selectDept'/>" /></div>
					  <div class="center_04_left_01 kuang" style="text-align: left;">
						  <div class="dtree">
							<script type="text/javascript">
								d = new dTree('d');
								<c:forEach items="${deptList}" var="dept">
									d.add(${dept.id},${dept.fatherId},'${dept.name}');
								</c:forEach>
								document.write(d);
							</script>
						  </div>
 					  </div>
				 </div>
				 <div class="center_04_right">
				      <div class="title360A"><h3><s:text name='sundyn.query.please1'/></h3></div>
					  <div class="center_04_right_01 kuang">
					  <form id="form1" name="form1" method="post" action="">
					       <table width="80%" height="97" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
							  <tr>
							    <td width="32%" align="right" style="border-color:#FFFFFF;"><s:text name='sundyn.query.selectEmployee'/></td>
							    <td width="40%" align="left" style="border-color:#FFFFFF;">
							       <input type="hidden" id="id" name="id" value="0" />
								   <input type="text" id="keyword" name="keyword" class="input_comm" />
							    </td>
							    <td width="28%" align="left" style="border-color:#FFFFFF;"><img src="<s:text name='sundyn.pic.query'/>" onclick="queryEmployee()" width="55" height="25" style="cursor: pointer;" /></td>
							  </tr>
							  <tr>
							    <td align="right" style="border-color:#FFFFFF;"><s:text name='sundyn.total.startDate'/></td>
							    <td colspan="2" align="left" style="border-color:#FFFFFF;"><input type="text" <s:text name="sundyn.language.calendar.setDay"/>  id="startDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /> </td>
							    </tr>
							  <tr>
							    <td align="right" style="border-color:#FFFFFF;"><s:text name='sundyn.total.endDate'/></td>
							    <td colspan="2" align="left" style="border-color:#FFFFFF;"><input type="text" <s:text name="sundyn.language.calendar.setDay"/>  id="endDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  /></td>
						      </tr>
			                </table>
			          </form>
					  </div>
					  <div class="fengge">&nbsp;</div>
					  <div class="title360A"><h3><s:text name='sundyn.query.please2'/></h3></div>
					  <div class="center_04_right_02 kuang">
					     	<ul class="list">
							<c:forEach items="${keyList}" var="key">
								<li>
									${key.name}:
									<input type="checkbox" value="${key.keyNo}"
										name="key${key.keyNo}" id="key${key.keyNo}" />
								</li>
							</c:forEach>
						</ul>
					  </div>
					  <div class="center_04_right_03">
					    <table width="60%" border="0" cellspacing="0" cellpadding="0" style="border-color:#d6ecf8">
			              <tr>
			                <td width="40%" align="right" style="border-color:#d6ecf8" colspan="3"><img src="<s:text name='sundyn.total.pic.query'/>" width="81" height="25" style="cursor: pointer;" onclick="queryZhDeal()" class="hand" /></td>
			              </tr>
			            </table>
			   		 </div>
				 </div>
			</div>
 		</div>
		<div style="width: 479px; height: 328px; position: absolute; top: 50px; z-index: 101; left: 347.5px;display: none;" id="dialog"></div>
	</body>
</html>
