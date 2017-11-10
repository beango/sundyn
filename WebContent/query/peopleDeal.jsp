<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ include file="/JSClass/FusionCharts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
	<head>
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
</STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<link rel="stylesheet"
			href="css/common_<s:text name='sundyn.language' />.css"
			type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title' />
		</title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/layer/layer.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
		<script type="text/javascript"
			src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
		<iframe src="piedata.xml" style="display: none;"></iframe>
		<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}" />
		<input type="hidden" name="keys" id="keys" value="${keys}" />
		<input type="hidden" name="id" id="id" value="${id}" />
		<div id="man_zone">
			<table width="100%" height="51" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
				<tr>
					<td align="left" style="border-color:#FFFFFF;">
						<s:text name='sundyn.query.selectEmployee'/>
						<input type="text" class="input_comm" name="keyword" id="keyword" value="${employeeinfo.name}" style="vertical-align: middle;width:130px;"/>
						<img  src="<s:text name='sundyn.pic.query'/>" onclick="queryEmployee()" class="hand"  style="vertical-align: middle;"/>
						<img  src="<s:text name='sundyn.pic.clear'/>" onclick="clearEmployee()" class="hand"  style="vertical-align: middle;"/>
						<s:text name='sundyn.total.startDate'/>
						<input type="text" class="input_comm" id="startDate" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						<s:text name='sundyn.total.endDate'/>
						<input type="text" class="input_comm" id="endDate" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
						<img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" onclick="queryEmployeeDeal()" class="hand" style="vertical-align: middle;"/>
						<img src="<s:text name='sundyn.query.pic.exportExcel'/>" width="100" height="25" onclick="queryExcel()" class="hand" style="vertical-align: middle;"/>
					</td>
				</tr>
			</table>
			<div class="sundyn_main">
				<div class="fengge">
					&nbsp;
				</div>
				<table width="1200px" class="t">
					<tr>
						<td class="tdtitle">
							<s:text name='sundyn.column.employeeXuHao' />
						</td>
						<td class="tdtitle">
							<s:text name='sundyn.column.employeeCardNum' />
						</td>

						<td class="tdtitle">
							<s:text name='sundyn.column.employeeName' />
						</td>
						<td class="tdtitle">
							<s:text name='sundyn.column.atDating' />
						</td>
						<td class="tdtitle">
							<s:text name='sundyn.column.appriesResult' />
						</td>
						<td class="tdtitle">
							<s:text name='sundyn.column.appriesTime' />
						</td>
						<td class="tdtitle">
							<%--							<s:text name="sundyn.inquiry.result.video"/>--%>
							<s:text name="sundyn.inquiry.result.obtainEvidence" />
						</td>
						<td " class="tdtitle">
							<s:text name="sundyn.inquiry.result.businessTime"></s:text>
						</td>
						<td width="8%" class="tdtitle">

						客户姓名
						</td>
						<td width="8%" class="tdtitle">

						客户电话
						</td>
						<td width="8%" class="tdtitle" >

						意见反馈
						</td>
<%--						<td class="tdtitle">--%>
<%--							<s:text name='sundyn.column.demo' />--%>
<%--						</td>--%>
					</tr>
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
							<td class="td">
								${query.id}
							</td>
							<td class="td">
								${query.CardNum}
							</td>
							<td class="td">
								${query.employeeName}
							</td>
							<td class="td">
								${query.fatherName}
							</td>
							<td class="td">
								${query.keyName}
							</td>
							<td class="td">
								<fmt:formatDate value="${query.JieshouTime}" type="both"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td class="td">
								<c:if test="${empty query.videofile}">
									<s:text name="sundyn.inquiry.result.noVideo" />
								</c:if>
								<c:if test="${!empty query.videofile}">
									<%-- 							    <a  href="query/videoPlay2.jsp?videofile=${query.videofile}"  target="_blank"><img src="images/lx.jpg"/></a>--%>
									 <a  href="#"  id="${query.id }" ><img src="images/lx.jpg" onclick="toshow('${query.videofile}','${pageContext.request.contextPath }','${query.id}');"/></a>
									</a>
									<%-- 							    <a  href="/download/${query.videofile}"  target="_blank"> <s:text name="sundyn.inquiry.result.download"></s:text></a>--%>
									<a href="downloadVideo.action?videofile=${query.videofile}"><s:text
											name="sundyn.inquiry.result.download"></s:text>
									</a>
								</c:if>
							</td>
							<td class="td">
								<c:if test="${!empty query.businessMin}">
								${query.businessMin}<s:text
										name="sundyn.inquiry.result.minuteForShort" />${query.businessSec}<s:text
										name="sundyn.inquiry.result.secondForShort" />
								</c:if>
								<c:if test="${empty query.businessMin}">
									<s:text name="sundyn.inquiry.result.noRecord" />
								</c:if>
							</td>
							<td width="12%" class="td">
								${query.ext1}
							</td>
							<td width="12%" class="td">
								${query.ext2}
							</td>
							<td width="12%" class="td" style="width: 80px;display: block;height: 42px;line-height: 42px;">
								<a href="#" onclick="showRemark('${query.remark}')">查看</a>
							</td>
<%--							<td class="td">--%>
<%--								<a href="#" onclick="queryDemo(${query.id})"><s:text--%>
<%--										name='sundyn.column.detail' />--%>
<%--								</a>--%>
<%--							</td>--%>

						</tr>
					</c:forEach>
				</table>
				<c:if test="${pager.rowsCount == 0}">
					<div class="sundyn_rows">
						<s:text name="sundyn.system.checkM7Info.noRecord" />
					</div>
				</c:if>
				<c:if test="${pager.rowsCount > 0}">
					<div class="sundyn_rows">
						${pager.pageTextCn}
					</div>

					<%--					<div class="quicklyButton" onclick="quicklyAddDialog()"></div>--%>
				</c:if>
			</div>

			<%
				String strXML1 = (String) request.getAttribute("strXML1");
				if (strXML1 != null && !"".equals(strXML1)) {
					String chartHTML1 = createChartHTML(
							"Charts/Pie3D.swf", "", strXML1, "", 600,
							350, false);
			%>
			<span> <%=chartHTML1%> </span>
			<%
				}
			%>
		</div>
		<div id="dialog" style="width: 479px; height: 328px; display: none;"></div>
	</body>
</html>