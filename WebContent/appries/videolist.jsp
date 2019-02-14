<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet"
			href="css/common_<s:text name='sundyn.language' />.css"
			type="text/css" />
		<title><s:text name='sundyn.title' />
		</title>
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
	var ie = navigator.appName == "Microsoft Internet Explorer" ? true : false;
	function $(objID) {
		return document.getElementById(objID);
	}
</script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript"
			src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript">
		   	function playsound1(sFile){
//document.getElementById("__video").PlaySnd('\\User-disk\\sundyn\\' + sFile,0x20000);
               document.getElementById("videofile").PlaySnd('\\C:\apache-tomcat-6.0.14\apache-tomcat-6.0.14\webapps\android\download\\' + sFile); 
             }
		</script>
			
	</head>
	<body>
		<script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>
		<div id="man_zone">
			<div class="fengge">
				&nbsp;
			</div>
			
				<table width="90%" class="t">
					<tr>
					  
						<td width="10%" class="tdtitle">
							窗口名称
						</td>
						<td width="20%" class="tdtitle">
							录像文件
						</td>
						<td width="10%" class="tdtitle">
							录像
						</td>
					</tr>
					<c:forEach items="${videolist}" var="video">
						<tr>
							<td width="10%" class="td">
								${video.windowname}
							</td>
							<!-- 
							<td width="20%" class="td">
								${video.videofile}
							</td>
							-->
							<td width="10%" class="td">
							  <c:if test="${empty video.videofile}">
							         <c:out value="没有录像文件"></c:out>
							     </c:if>
							    <c:if test="${!empty video.videofile}">
							        ${video.videofile}
							     </c:if>
							</td>
							
							<td width="10%" class="td">
							       <c:if test="${!empty video.videofile}">
							        <a href="downloadVideo.action?videofile=${video.videofile}"><img src="images/lx.jpg"/></a>
							        </c:if>
							        <c:if test="${empty video.videofile}">
							           <c:out value="没有录像文件"></c:out>
							        </c:if>
							</td>
							<!--
							<c:if test="{empty video.videofile}">
							    
							</c:if>
							<td width="10%" class="td">
								<a href="downloadVideo.action?videofile=${video.videofile}"><img src="images/lx.jpg"/></a>
							</td>
						  -->

							
							
						</tr>
					</c:forEach>
				</table>
				<tr>
				</tr>
			</div>
	</body>
</html>

