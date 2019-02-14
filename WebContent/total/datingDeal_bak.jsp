<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
		 </STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<style type="text/css">
table {
	font-size: 12px;
	font-weight: 10px;
	text-align: center;
}
</style>
		 <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<div id="man_zone">
			<div class="sundyn_main">
				<div class="sundyn_row">
					以下是汇总${startDate}到${endDate}的评价信息
				</div>
				<div class="sundyn_row">
					<table width="800" border="1" align="center" cellpadding="0"
						cellspacing="0">
						<tr>
							<td width="100" height="54" rowspan="2">
								大厅名称
							</td>
							<td width="70" rowspan="2">
								星级
							</td>
							<td width="200" colspan="3">
								满意
							</td>
							<td width="300" colspan="5">
								不满意
							</td>
							<td width="50" rowspan="2">
								评价数
							</td>
							<td width="50" rowspan="2">
								未评价数
							</td>
							<td width="50" rowspan="2">
								合计
							</td>
							<td width="50" rowspan="2">
								评价率
							</td>
							<td width="50" rowspan="2">
								满意率
							</td>
						</tr>
						<tr>
							<td>
								非常满意
							</td>
							<td>
								基本满意
							</td>
							<td>
								合计
							</td>
							<td>
								态度不好
							</td>
							<td>
								时间太长
							</td>
							<td>
								业务不熟
							</td>
							<td>
								其他
							</td>
							<td>
								不满意合计
							</td>
						</tr>
						<c:forEach items="${pager.pageList}" var="total">
							<tr>
								<td>
									${total.datingname}
								</td>
								<td>
									<c:choose>
										<c:when test="${total.mrate>=90}">
											<font color="red"> ★★★★★</font>
										</c:when>
										<c:when test="${total.mrate>=80}">
											<font color="red"> ★★★★</font>
											<font color="blue"> ★</font>
										</c:when>
										<c:when test="${total.mrate>=70}">
											<font color="red"> ★★★★</font>
										</c:when>
										<c:when test="${total.mrate>=60}">
											<font color="red"> ★★★</font>
											<font color="blue"> ★</font>
										</c:when>
										<c:when test="${total.mrate>=50}">
											<font color="red"> ★★★</font>
										</c:when>
										<c:when test="${total.mrate>=40}">
											<font color="red"> ★★ </font>
											<font color="blue"> ★</font>
										</c:when>

										<c:when test="${total.mrate>=30}">
											<font color="red"> ★★</font>
										</c:when>
										<c:when test="${total.mrate>=20}">
											<font color="red">★</font>
											<font color="blue">★</font>
										</c:when>
										<c:when test="${total.mrate>=10}">
											<font color="red"> ★</font>
										</c:when>
										<c:otherwise>
											<font color="blue"> ★</font>
										</c:otherwise>
									</c:choose>
								</td>
								<td width="32">
									${total.key0}
								</td>
								<td width="30">
									${total.key1}
								</td>
								<td width="21">
									${total.key0+total.key1}
								</td>
								<td width="32">
									${total.key2}
								</td>
								<td width="32">
									${total.key3}
								</td>
								<td width="32">
									${total.key4}
								</td>
								<td width="20">
									${total.key5}
								</td>
								<td width="37">
									${total.key2+total.key3+total.key4+total.key5}
								</td>
								<td>
									${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5}
								</td>
								<td>
									${total.key6}
								</td>
								<td>
									${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
								</td>
								<td>
									${total.prate}%
								</td>
								<td>
									${total.mrate}%
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
				<div class="sundyn_row">
					<a href="totalDatingExcel.action?startDate=${startDate}&endDate=${endDate}">导出excel</a>
					<a href="totalDatingPrint.action?startDate=${startDate}&endDate=${endDate}" target="_blank">打印</a>	
				</div>
				<div class="sundyn_row">
				    ${pager.pageTextCn }
				</div>
				
					<div class="sundyn_row">
					<br />
					<br />
					<table width="800" border="1" cellspacing="0" cellpadding="0">
						<tr>
							<td colspan="6">
								统计信息(机构汇总查询)
							</td>
						</tr>
						<tr>
							<td width="134" rowspan="2">
								满意
							</td>
							<td width="93">
								非常满意
							</td>
							<td width="130" align="left">
								<div style="background-color: red; width:${totalMap.kr0}%; height: 100%;">
									&nbsp;
								</div>
							</td>
							<td width="137">
								${totalMap.key0}
							</td>
							<td width="138" rowspan="2">
								${totalMap.key0+totalMap.key1}
							</td>
							<td width="99">
								${totalMap.kr0}%
							</td>
						</tr>
						<tr>
							<td>
								基本满意
							</td>
							<td align="left">
								<div style="background-color: red; width:${totalMap.kr1}%; height: 100%;">
									&nbsp;
								</div>
							</td>
							<td>
								${totalMap.key1}
							</td>
							<td>
								${totalMap.kr1}%
							</td>
						</tr>
						<tr>
							<td rowspan="4">
								不满意
							</td>
							<td>
								态度不好
							</td>
							<td align="left">
							<div style="background-color: red; width:${totalMap.kr2}%; height: 100%;">
									&nbsp;
								</div>
							</td>
							<td>
								${totalMap.key2}
							</td>
							<td rowspan="4">
								${ totalMap.key2+totalMap.key3+totalMap.key4+totalMap.key5}
							</td>
							<td>
								${totalMap.kr2}%
							</td>
						</tr>
						<tr>
							<td>
								时间太长
							</td>
							<td  align="left">
								<div style="background-color: red; width:${totalMap.kr3}%; height: 100%;">
									&nbsp;
								</div>
							</td>
							<td>
								${totalMap.key3}
							</td>
							<td>
								${totalMap.kr3}%
							</td>
						</tr>
						<tr>
							<td>
								业务不熟
							</td>
							<td align="left">
								<div style="background-color: red; width:${totalMap.kr4}%; height: 100%;">
									&nbsp;
								</div>
							</td>
							<td>
								${totalMap.key4}
							</td>
							<td>
								${totalMap.kr4}%
							</td>
						</tr>
						<tr>
							<td>
								其它
							</td>
							<td align="left">
								<div style="background-color: red; width:${totalMap.kr5}%; height: 100%;">
									&nbsp;
								</div>
							</td>
							<td>
								${totalMap.key5}
							</td>
							<td>
								${totalMap.kr5}%
							</td>
						</tr>
					</table>
				</div>
				<div class="sundyn_row">
				满意率：${totalMap.krm}% 不满意率:${totalMap.krbm}%
				 </div>
				<div class="sundyn_rows">
					<div id="PieDiv"
						style="position: relative; height:300px; width:300px;"></div>
				</div>
  			</div>
		</div>
	</body>
<script language="javascript">
 	if(isIe()){
			xxColor=new Array("#ccc","#b5cc88","#6B8E23","#3CB371","#f59d56","yellow","#d8d8d8","#708090","#4682B4","red","#ffc20e");	
			var pie=new Pie3D('PieDiv',320,192,'大厅统计');
 				pie.Cakes[0]=new Array('非常满意',${totalMap.key0},xxColor[0]);
				pie.Cakes[1]=new Array('基本满意',${totalMap.key1},xxColor[1]);
				pie.Cakes[2]=new Array('态度不好',${totalMap.key2},xxColor[2]);
				pie.Cakes[3]=new Array('时间太长',${totalMap.key3},xxColor[3]);
				pie.Cakes[4]=new Array('业务不熟',${totalMap.key4},xxColor[4]);
				pie.Cakes[5]=new Array('其它',${totalMap.key5},xxColor[5]);
				pie.Cakes[6]=new Array('未评价',${totalMap.key6},xxColor[6]);
  			pie.draw();
		}else{
			var y= new Array ();
		    <c:forEach items="${chatList}" var="chat" varStatus="index">
			 y[${index.index}] = ${chat.num};
			</c:forEach>
				var x = new Array ();
			<c:forEach items="${chatList}" var="chat" varStatus="index">
			 x[${index.index}] = '${chat.name}';
			</c:forEach>	
				var mypie=  new Pie("PieDiv",300,300);
			mypie.drawPie(y,x);
	}
</script>
</html>

