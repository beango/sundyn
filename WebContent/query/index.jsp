<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 1999 21:29:02 GMT" />
		<title><s:text name='sundyn.title'/></title>
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language'/>.css" type="text/css"></link>
		 <STYLE type="text/css">
			 v\:* { Behavior: url(#default#VML) }
	         o\:* { behavior: url(#default#VML) }
	         #PieDiv{
		         font-family:arial;
 		      	 line-height: normal;
  	         }
  	         #PieDiv div{
  	            font-size: 9px;
             }
             td{
             font-size: 12px;
              }
    		 </STYLE>
		<script type="text/javascript" src="js/dojo.js"></script> 
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/i_line.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
 		<script type="text/javascript" src="js/json.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language'/>.js"></script>
 		<script type="text/javascript" src="js/jscharts.js"></script>
	</head>
	<body>
		<iframe src="piedata.xml" style="display: none;"></iframe>
		<div id="man_zone">
			<div class="fengge">
				&nbsp;
			</div>
			<div class="neirong">
				<div class="neirong_lanmu" style="float: left; height:170px;">
					<div class="neirong_lanmu_top">
						<span class="px13 left"><s:text name="sundyn.main.title1"/></span>
						<span class="right" ><a href="#" onclick="indexDetail('${deptIds}','${mk}')">&nbsp;&nbsp;<s:text name="sundyn.main.more"/></a></span>
					</div>
					<table width="100%"   cellpadding="0" cellspacing="0" style="border-right: 1px solid #bad6ec;">
						<tr>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<s:text name="sundyn.column.time"/>
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<c:if test="${bind==true}"><s:text name='sundyn.column.deviceInfo' />  </c:if>
								<c:if test="${bind==false}"><s:text name='sundyn.column.cardNum' />  </c:if>
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<c:if test="${bind==true}"><s:text name='sundyn.column.windowName' />  </c:if>
								<c:if test="${bind==false}"><s:text name='sundyn.column.name' />  </c:if>
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<s:text name="sundyn.column.appries"/>
							</td>
						</tr>
						<c:forEach items="${l1}" var="p">
							<tr>
								<td align="center" valign="middle">
								<fmt:formatDate value="${p.JieshouTime}"   pattern="yyyy-MM-dd HH:mm:ss"/>
 								</td>
								<td align="center" valign="middle">
									${p.CardNum}
								</td>
								<td align="center" valign="middle">
									${p.employeeName}
								</td>
								<td align="center" valign="middle">
									${p.keyName}
								</td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<div class="neirong_lanmu" style="float: right;height:170px;">
					<div class="neirong_lanmu_top">
						<span class="px13 left"><s:text name="sundyn.main.title2"/></span>
						<span class="right"><a href="#" onclick="indexDetail('${deptIds}','${bmk}')"><s:text name="sundyn.main.more"/></a></span>
					</div>
					<table width="100%"  cellpadding="0" cellspacing="0" style="border-right: 1px solid #bad6ec;">
						<tr>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<s:text name="sundyn.column.time"/>
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<c:if test="${bind==true}"><s:text name='sundyn.column.deviceInfo' />  </c:if>
								<c:if test="${bind==false}"><s:text name='sundyn.column.cardNum' />  </c:if>
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<c:if test="${bind==true}"><s:text name='sundyn.column.windowName' />  </c:if>
								<c:if test="${bind==false}"><s:text name='sundyn.column.name' />  </c:if>
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								<s:text name="sundyn.column.appries"/>
							</td>
						</tr>
						<c:forEach items="${l0}" var="p">
							<tr>
								<td align="center" valign="middle">
									<fmt:formatDate value="${p.JieshouTime}"   pattern="yyyy-MM-dd HH:mm:ss"/>
								</td>
								<td align="center" valign="middle">
									${p.CardNum}
								</td>
								<td align="center" valign="middle">
									${p.employeeName}
								</td>
								<td align="center" valign="middle">
									${p.keyName}
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="fengge">
					&nbsp;
				</div>
				<div class="neirong_lanmu" style="float: left;">
					<div class="neirong_lanmu_top">
						<span class="px13 left"><s:text name="sundyn.main.title5"/></span>
						<span class="right"><a href="analyseContentRate.action"><s:text name="sundyn.main.more"/></a></span>
					</div>
					<div style="height: 175px;" class="kuang">
 						  <object id="columnChat2" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%">
						   <param name="movie" value="images/columnChat.swf" />
						   <param name="quality" value="high" />
						   <param name="wmode" value="opaque" />
						   <param name="swfversion" value="9.0.45.0" />
					  	 </object>
					</div>
				</div>
				<div class="neirong_lanmu" style="float: right;">
					<div class="neirong_lanmu_top">
						<span class="px13 left"><s:text name="sundyn.main.title4"/></span>
						<span class="right"><a href="#" onclick="indexCake('${deptIds}')"><s:text name="sundyn.main.more"/></a></span>
					</div>
					<div style="height: 175px;" class="kuang">
 					   <object id="columnChat" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="358" height="175">
						  <param name="movie" value="images/pie.swf?piedata=piedata.xml" />
						  <param name="quality" value="high" />
						  <param name="wmode" value="opaque" />
						  <param name="swfversion" value="8.0.35.0" />
						  <!-- 此 param 标签提示使用 Flash Player 6.0 r65 和更高版本的用户下载最新版本的 Flash Player。如果您不想让用户看到该提示，请将其删除。 -->
						  <param name="expressinstall" value="Scripts/expressInstall.swf" />
						  <!-- 下一个对象标签用于非 IE 浏览器。所以使用 IECC 将其从 IE 隐藏。 -->
						  <!--[if !IE]>-->
						  <object type="application/x-shockwave-flash" data="images/pie.swf?piedata=piedata.xml" width="600" height="600">
						    <!--<![endif]-->
						    <param name="quality" value="high" />
						    <param name="wmode" value="opaque" />
						    <param name="swfversion" value="8.0.35.0" />
						    <param name="expressinstall" value="Scripts/expressInstall.swf" />
						    <!-- 浏览器将以下替代内容显示给使用 Flash Player 6.0 和更低版本的用户。 -->
						    <div>
						      <h4>此页面上的内容需要较新版本的 Adobe Flash Player。</h4>
						      <p><a href="http://www.adobe.com/go/getflashplayer"><img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="获取 Adobe Flash Player" width="112" height="33" /></a></p>
						    </div>
						    <!--[if !IE]>-->
						  </object>
						  <!--<![endif]-->
						</object>
 					</div>
				</div>
			</div>
		</div>
 	</body>
</html>
 <script language="javascript">
 	//曲线图
	 analyseContentRateIndexAjaxDay(7);
	// window.parent.parent.loadtest();
 </script>