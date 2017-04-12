<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
  <html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
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
             table {
				font-size: 12px;
				font-weight: 10px;
				text-align: center;
			 }
		 </STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		 <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div class="fengge">&nbsp;</div>
			<div class="sundyn_row">以下是汇总${startDate}到${endDate}的评价信息	</div>
			<div><img src="images/03_01_03.jpg" /></div>
 			<div style="width:732px;margin:0 auto;">
				<table width="100%"  cellpadding="0" cellspacing="0"  >
				  <tr>
				    
				    <td width="20%"   align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">一级指标</td>
				    <td width="20%"   align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">二级指标</td>
				    <td width="20%"   align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">三级指标</td>
				    <td width="20%"   align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">四级指标</td>
				    <td width="20%"   align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">满意度</td>
				  </tr>
				  <c:forEach items="${list}" var="d">
					  <tr>
					 	 <td>${d.oneName}</td>
					 	 <td>${d.twoName}</td>
					 	 <td>${d.threeName}</td>
						 <td>${d.businessName}</td>
						 <td> <fmt:formatNumber value="${d.d}" pattern="#00.000#" /></td> 
						
						 
					  </tr>
				  </c:forEach>
   				</table>
  			</div> 
  			 <div class="fengge"></div>
  			 <div><a href="totalDExcel.action?startDate=${startDate}&endDate=${endDate}&ids=${ids}" target="_blank"><img src="images/03_03_11.jpg" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="totalDPrint.action?startDate=${startDate}&endDate=${endDate}&ids=${ids}" target="_blank"><img src="images/03_04_11.jpg" /></a></div>
		    <!-- 统计信息结束-->
		    <div class="fengge"></div>
		</div>
		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
 	</body>
</html>