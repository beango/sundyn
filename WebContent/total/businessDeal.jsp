<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
		<title><s:text name='zx.title'/></title>
		 <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div class="fengge">&nbsp;</div>
			<div class="sundyn_row">以下是汇总${startDate}到${endDate}的评价信息	</div>
			<div><img src="images/03_01_03.jpg" /></div>
 			<div style="width:732px;">
				<table width="100%"  cellpadding="0" cellspacing="0"  >
				  <tr>
				    <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">业务名</td>
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">星级</td>
				    <td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_2">满意</td>
				    <td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_3">不满意</td>
				    <c:if test="${k7 == true}">
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">评价数</td>
 				    <td width="10%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">未评价数</td>
				    </c:if>
				    <td width="5%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">合计</td>
				    <c:if test="${k7 == true}">
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">评价率</td>
				    </c:if>
				    <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1">满意率</td>
				  </tr>
				  <tr>
				    <c:forEach items="${mls}" var="key">
				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>
				    </c:forEach>
				    <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1">满意合计</td>
				    <c:forEach items="${bmls}" var="key">
				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>
				    </c:forEach>   
				    <td width="12%" align="center" valign="middle" bgcolor="f5fff2" class="px13_1">不满意合计</td>
			      </tr>
	 			  <c:forEach items="${list}" var="total">
								<tr>
									<td align="center" valign="middle">
										${total.businessName}
									</td>
									<td align="center" valign="middle">
										 ${total.star }
									</td>
									<c:forEach items="${total.km}" var="k">
										<td align="center" valign="middle">
											${k}
										</td>
									</c:forEach>
									<td align="center" valign="middle">
										${total.msum}
									</td>
 									<c:forEach items="${total.kbm}" var="k">
										<td align="center" valign="middle">
											${k}
										</td>
									</c:forEach>
 									<td align="center" valign="middle">
										${total.bmsum}
									</td>
									<c:if test="${k7 ==true}">
									<td align="center" valign="middle">
										${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5}
									</td>
									<td align="center" valign="middle">
										${total.key6}
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.sum}
									</td>
									<c:if test="${k7 == true}">
									<td align="center" valign="middle">
										${total.prate}%
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.mrate}%
									</td>
								</tr>
						</c:forEach>
				</table>
			</div>
			<div class="sundyn_row">
				    ${pager.pageTextCn }
			</div>
			<div class="fengge"></div>
            <div><a href="totalBusinessExcel.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_blank"><img src="images/03_03_11.jpg" /></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="totalBusinessPrint.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_blank"><img src="images/03_04_11.jpg" /></a></div>
		    <div class="fengge" style="height:21px;"></div>
		    <!-- 统计信息开始 -->
 		     <table id="table1" width="70%" height="172" border="0" cellpadding="0" cellspacing="0" class="px12" style="border-top:1px solid #bad6ec;border-right:1px solid #bad6ec;">
			  <tr>
			    <td colspan="7" align="center" valign="middle" background="images/03_05_11.jpg" class="px12">统计信息（机构汇总查询）</td>
			    </tr>
				<c:forEach items="${mls}" var="key" varStatus="index">
				  <tr>
					    <c:if test="${index.index == 0}">
					      <td width="15%" rowspan="${fn:length(mls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2">满意</td>
					    </c:if>
					     <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
					    <td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
					    <td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
					    <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(mls)}" align="center" valign="middle">${totalMap.msum}</td>
					    </c:if>
					    <td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
					    <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(mls)}" align="center" valign="middle">${totalMap.mrate}%</td>
					    </c:if>
				   </tr>
				</c:forEach>
				<c:forEach items="${bmls}" var="key" varStatus="index">
				  <tr>
					    <c:if test="${index.index == 0}">
					      <td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2">不满意</td>
					    </c:if>
					     <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
					    <td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
					    <td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
					    <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.bmsum}</td>
					    </c:if>
					    <td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
					     <c:if test="${index.index == 0}">
					      <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.bmrate}%</td>
					    </c:if>
				   </tr>
				</c:forEach>
				<c:if test="${k7 == true}">
				  <tr>
 					    <td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2">未评价</td>
 					    <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${totalMap.k7Name}</td>
					    <td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.kr6}%; background-color:#FF0000;">&nbsp;</div></td>
					    <td width="19%" align="center" valign="middle">${totalMap.key6}</td>
					    <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.key6}</td>
					    <td width="6%" align="center" valign="middle">${totalMap.kr6}%</td>
					    <td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.kr6}%</td>
				   </tr>
				</c:if>
 			</table>
		    <!-- 统计信息结束-->
		    <div class="fengge"></div>
		</div>
		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
 	</body>
</html>
