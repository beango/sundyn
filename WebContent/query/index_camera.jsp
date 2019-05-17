<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title>首页</title>
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
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
		<script type="text/javascript" src="js/i_line.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
		<script type="text/javascript" src="js/json.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div class="fengge">
				&nbsp;
			</div>
			<div class="neirong">
				<div class="neirong_lanmu" style="float: left; height:170px;">
					<div class="neirong_lanmu_top">
						<span class="px13">评价信息</span>
						<span style="padding-left: 190px;"><a href="queryPeopley.action">&nbsp;&nbsp;详细>></a></span>
					</div>
					<table width="100%"   cellpadding="0" cellspacing="0" style="border-right: 1px solid #bad6ec;">
						<tr>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								时间
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								卡号
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								姓名
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								评价
							</td>
							<td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
								录像
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
								<td align="center" valign="middle">
								   <a href="http://10.95.10.86/playback.jsp?IP=${fn:replace(p.dept_camera_url,',','&Channel=')}&StartTime=${p.serviceDate}|${p.appriesTime}&StopTime=${p.serviceDate}|${p.CustorTime}&Port=8000&UserName=a&Password=a" target="_blank"><img src="images/lx.jpg"/></a>
								</td>
							</tr>
						</c:forEach>
					</table>

				</div>
				<div class="neirong_lanmu" style="float: right;height:170px;">
					<div class="neirong_lanmu_top">
						<span class="px13">不满意评价信息</span>
						<span style="padding-left: 180px;"><a href="queryResult.action"> 详细>></a></span>
					</div>
					<table width="100%"  cellpadding="0" cellspacing="0" style="border-right: 1px solid #bad6ec;">
						<tr>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								时间
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								卡号
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								姓名
							</td>
							<td align="center" valign="middle"
								background="images/table_bg_03.jpg" class="px13_1">
								评价
							</td>
							<td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
								录像
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
								<td align="center" valign="middle"  >
								  <a href="http://10.95.10.86/playback.jsp?IP=${fn:replace(p.dept_camera_url,',','&Channel=')}&StartTime=${p.serviceDate}|${p.appriesTime}&StopTime=${p.serviceDate}|${p.CustorTime}&Port=8000&UserName=a&Password=a" target="_blank"><img src="images/lx.jpg"/></a>
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
						<span class="px13">满意率曲线图</span>
						<span style="padding-left: 180px;"><a href="analyseTotal.action">详细>></a></span>
					</div>
					<div style="height: 175px;" class="kuang">
					    <div id="LineDiv" style="position: relative; width:300px;height:130px;float: left; "></div>
					</div>
				</div>
				<div class="neirong_lanmu" style="float: right;">
					<div class="neirong_lanmu_top">
						<span class="px13">满意饼图</span>
						<span style="padding-left: 200px;"><a href="queryZh.action">详细>></a></span>
					</div>
					<div style="height: 175px;" class="kuang">
 					   <div id="PieDiv" style="position: relative; height:100%; width:100%"></div>
 					</div>
				</div>
			</div>
		</div>
 		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="6" border="0" /></div>
 	</body>
</html>
 <script language="javascript">
 	var xxColor=new Array("#ccc","#b5cc88","#6B8E23","#3CB371","#f59d56","yellow","#d8d8d8","#708090","#4682B4","red","#ffc20e");	
	var pie=new Pie3D('PieDiv',355,170,'');
		pie.Cakes[0]=new Array('非常满意',${totalMap.key0},xxColor[0]);
		pie.Cakes[1]=new Array('基本满意',${totalMap.key1},xxColor[1]);
		pie.Cakes[2]=new Array('态度不好',${totalMap.key2},xxColor[2]);
		pie.Cakes[3]=new Array('时间太长',${totalMap.key3},xxColor[3]);
		pie.Cakes[4]=new Array('业务不熟',${totalMap.key4},xxColor[4]);
		pie.Cakes[5]=new Array('其它',${totalMap.key5},xxColor[5]);
		pie.Cakes[6]=new Array('未评价',${totalMap.key6},xxColor[6]);
	pie.draw();
	//曲线图
	analyseContentRateIndexAjaxDay(7);
 </script>