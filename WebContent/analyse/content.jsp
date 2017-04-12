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
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/line.js"></script>
		<script type="text/javascript" src="js/jscharts.js"></script>
		<script type="text/javascript" src="js/json.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
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
           <div class="fengge" style="height:15px;">&nbsp;</div>
           <div class="center_05">
           <div class="title640"><h3><s:text name="sundyn.analyse.title2" /></h3></div>
		   <div class="center_05_center kuang">
				 <form id="form1" name="form1" method="post" action="">
			       <table width="80%" height="93" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF; margin-top:10px;">
			         <tr>
			           <td style="border-color:#FFFFFF;" width="12%" align="right"><s:text name='sundyn.analyse.qucklyAnalyse' /></td>
			           <td style="border-color:#FFFFFF;" width="31%" align="center"><img src="<s:text name='sundyn.analyse.pic.week' />"  onclick="analyseContentAjaxDay(7)" style="cursor: pointer;" /></td>
			           <td style="border-color:#FFFFFF;" width="26%"><img src="<s:text name='sundyn.analyse.pic.15' />"    onclick="analyseContentAjaxDay(15)" style="cursor: pointer;" /></td>
			           <td style="border-color:#FFFFFF;" width="31%" align="center"><img src="<s:text name='sundyn.analyse.pic.month' />"    onclick="analyseContentAjaxDay(30)" style="cursor: pointer;" /></td>
			         </tr>
			         <tr>
			           <td style="border-color:#FFFFFF;" align="right"><s:text name='sundyn.total.startDate'/></td>
			           <td style="border-color:#FFFFFF;"><input type="text" id="startDate" class="input_comm" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
			           <td style="border-color:#FFFFFF;" align="right"><s:text name='sundyn.total.endDate'/></td>
			           <td style="border-color:#FFFFFF;"><input type="text" id="endDate"   class="input_comm" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" /></td>
			         </tr>
			         <tr>
			           <td style="border-color:#FFFFFF;" align="right">&nbsp;</td>
			           <td style="border-color:#FFFFFF;" align="center"><s:text name='sundyn.analyse.by' />
						   <select name="type" id="type">
								<option value="day" selected="selected">
									<s:text name='sundyn.analyse.day' />
								</option>
								<option value="month">
									<s:text name='sundyn.analyse.month' />
								</option>
								<option value="year">
									<s:text name='sundyn.analyse.year' />
								</option>
							</select>
					   </td>
			           <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.analyse.pic.dataAnalyse' />" width="94" height="25" onclick="analyseContentAjax()" style="cursor: pointer;" /></td>
			           <td style="border-color:#FFFFFF;">&nbsp;</td>
			         </tr>
			       </table>
			       </form>
				   <div class="fengge"></div>
				   <div id="msg" style="height:18px; background-color:#dff4ff; padding-top:6px;"></div>
				   <div style="height:220px;"> 
				      <object id="columnChat" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%">
						   <param name="movie" value="images/columnChat.swf" />
						   <param name="quality" value="high" />
						   <param name="wmode" value="opaque" />
						   <param name="swfversion" value="9.0.45.0" />
					  </object>
 				   </div>
				</div>
			   </div>
			</div>
	</body>
</html>
<script language="javascript">
analyseContentAjaxDay(7);
</script>