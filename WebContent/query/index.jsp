<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
<META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 1999 21:29:02 GMT" />
<title><s:text name='sundyn.title' /></title>
<link rel="stylesheet"
	href="css/common_<s:text name='sundyn.language'/>.css" type="text/css"></link>
<link rel="stylesheet"
	href="style/lib/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />

<STYLE type="text/css">
v\:* {
	Behavior: url(#default#VML)
}

o\:* {
	behavior: url(#default#VML)
}

#PieDiv {
	font-family: arial;
	line-height: normal;
}

#PieDiv div {
	font-size: 9px;
}

td {
	font-size: 12px;
}
</STYLE>
<script type="text/javascript" src="js/json2.js"></script>
<script type="text/javascript"
	src="style/lib/jquery-1.9.0/jquery-1.9.0.min.js"></script>
<script src="style/lib/ligerUI-1.3.3/js/ligerui.min.js"
	type="text/javascript"></script>
<script type="text/javascript" src="js/dojo.js"></script>
<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
<script type="text/javascript" src="js/i_line.js"></script>
<script type="text/javascript" src="js/pie.js"></script>
<script type="text/javascript" src="js/Pie3D.js"></script>
<%--  --%>
<script type="text/javascript"
	src="js/my_<s:text name='sundyn.language'/>.js"></script>
<script type="text/javascript" src="js/jscharts.js"></script>
</head>
<body>
	<iframe src="piedata.xml" style="display: none;"></iframe>
	<div id="man_zone">
		<div style="height: 100%;padding:5px;">
			<table style="width: 100%; height: 100%;border:0px;">
				<tr>
					<td
						style="width: 50%; height: 50%; min-height: 50%;border:0px;padding:5px;">
						<div class="" style="height: 96%;">
							<div class="neirong_lanmu_top">
								<span class="px13 left"><s:text name="sundyn.main.title1" />11</span>
								<span class="right"><a href="#"
									onclick="indexDetail('${deptIds}','${mk}')">&nbsp;&nbsp;<s:text
											name="sundyn.main.more" /></a></span>
							</div>
							<table width="100%" cellpadding="0" cellspacing="0"
								style="border-right: 1px solid #bad6ec;">
								<tr>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><s:text
											name="sundyn.column.time" /></td>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><c:if
											test="${bind==true}">
											<s:text name='sundyn.column.deviceInfo' />
										</c:if> <c:if test="${bind==false}">
											<s:text name='sundyn.column.cardNum' />
										</c:if></td>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><c:if
											test="${bind==true}">
											<s:text name='sundyn.column.windowName' />
										</c:if> <c:if test="${bind==false}">
											<s:text name='sundyn.column.name' />
										</c:if></td>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><s:text
											name="sundyn.column.appries" /></td>
								</tr>
								<c:forEach items="${l1}" var="p">
									<tr>
										<td align="center" valign="middle"><fmt:formatDate
												value="${p.JieshouTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td align="center" valign="middle">${p.CardNum}</td>
										<td align="center" valign="middle">${p.employeeName}</td>
										<td align="center" valign="middle">${p.keyName}</td>
									</tr>
								</c:forEach>
							</table>

						</div>
					</td>
					<td style="border:0px;padding:5px;">
						<div style="height: 96%;">
							<div class="neirong_lanmu_top">
								<span class="px13 left"><s:text name="sundyn.main.title2" /></span>
								<span class="right"><a href="#"
									onclick="indexDetail('${deptIds}','${bmk}')"><s:text
											name="sundyn.main.more" /></a></span>
							</div>
							<table width="100%" cellpadding="0" cellspacing="0"
								style="border-right: 1px solid #bad6ec;">
								<tr>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><s:text
											name="sundyn.column.time" /></td>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><c:if
											test="${bind==true}">
											<s:text name='sundyn.column.deviceInfo' />
										</c:if> <c:if test="${bind==false}">
											<s:text name='sundyn.column.cardNum' />
										</c:if></td>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><c:if
											test="${bind==true}">
											<s:text name='sundyn.column.windowName' />
										</c:if> <c:if test="${bind==false}">
											<s:text name='sundyn.column.name' />
										</c:if></td>
									<td align="center" valign="middle"
										background="images/table_bg_03.jpg" class="px13_1"><s:text
											name="sundyn.column.appries" /></td>
								</tr>
								<c:forEach items="${l0}" var="p">
									<tr>
										<td align="center" valign="middle"><fmt:formatDate
												value="${p.JieshouTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
										<td align="center" valign="middle">${p.CardNum}</td>
										<td align="center" valign="middle">${p.employeeName}</td>
										<td align="center" valign="middle">${p.keyName}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
					</td>
				</tr>
				<tr>
					<td style="border:0px;padding:5px;">
						<div style="height: 96%;">
							<div class="neirong_lanmu_top">
								<span class="px13 left"><s:text name="sundyn.main.title5" /></span>
								<span class="right"><a href="analyseContentRate.action"><s:text
											name="sundyn.main.more" /></a></span>
							</div>
							<div style="height: 100%;" class="kuang">
								<object id="columnChat2"
									classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									width="100%" height="100%">
									<param name="movie" value="images/columnChat.swf" />
									<param name="quality" value="high" />
									<param name="wmode" value="opaque" />
									<param name="swfversion" value="9.0.45.0" />
								</object>
							</div>
						</div> 
					</td>
					<td style="border:0px;padding:5px;">
						<div style="height: 96%;">
							<div class="neirong_lanmu_top">
								<span class="px13 left"><s:text name="sundyn.main.title4" /></span>
								<span class="right"><a href="#"
									onclick="indexCake('${deptIds}')"><s:text
											name="sundyn.main.more" /></a></span>
							</div>
							<div style="height: 100%;" class="kuang">
								<object id="columnChat"
									classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
									width="358" height="175">
									<param name="movie" value="images/pie.swf?piedata=piedata.xml" />
									<param name="quality" value="high" />
									<param name="wmode" value="opaque" />
									<param name="swfversion" value="8.0.35.0" />
									<!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
									<param name="expressinstall" value="Scripts/expressInstall.swf" />
									<!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
									<!--[if !IE]>-->
									<object type="application/x-shockwave-flash"
										data="images/pie.swf?piedata=piedata.xml" width="600"
										height="300">
										<!--<![endif]-->
										<param name="quality" value="high" />
										<param name="wmode" value="opaque" />
										<param name="swfversion" value="8.0.35.0" />
										<param name="expressinstall"
											value="Scripts/expressInstall.swf" />
										
										<!--[if !IE]>-->
									</object>
									<!--<![endif]-->
								</object>
							</div>
						</div>
					</td>
				</tr>
			</table>

		</div>
	</div>
</body>
</html>
<script language="javascript">
	//曲线图
	analyseContentRateIndexAjaxDay(7);
	// window.parent.parent.loadtest();

	
</script>