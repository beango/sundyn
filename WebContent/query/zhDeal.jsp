<%@ page import="java.text.*,java.io.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/JSClass/FusionCharts.jsp"%>
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
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/layer/layer.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
 		<link href="css/clearbox.css" rel="stylesheet" type="text/css" />
		<script src="js/clearbox.js" type="text/javascript"></script>
 		<script>
 		  function toshow(videofile,path1,id){
 	 		  var a = document.getElementById(id);
 	 		  var file1 = videofile;
 	 		  videofile = videofile.toLowerCase();
 	 		  videofile = videofile.substring(videofile.lastIndexOf('.')+1);
 	 		  if(videofile=="jpg"){
					a.href="showPic.jsp?pic="+file1;
	 	 	 		a.target="_bank";
 	 	 		}else{
 	 	 			a.href="query/videoPlay2.jsp?videofile="+file1;
 	 	 	 		a.target="_bank";
 	 	 	 	}
 	 		  }
 		</script>
 	</head>
	<body>
	<iframe src="piedata.xml" style="display: none;"></iframe>
	<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}"  />
	<input type="hidden" name="startDate" id="startDate" value="${startDate}" />
	<input type="hidden" name="endDate" id="endDate" value="${endDate}" />
	<input type="hidden" name="keys" id="keys" value="${keys}" />
	<input type="hidden" name="id" id="id" value="${id}" />
	<input type="hidden" name="id" id="id" value="${id}" />
		<div id="man_zone">
			<div class="sundyn_main">
			<div class="fengge">&nbsp;</div>
				<table width="1200px" class="t" >
					<tr>
						<td width="8%" class="tdtitle">
							<s:text name='sundyn.column.employeeCardNum'/>
						</td>

						<td width="8%" class="tdtitle">
							<s:text name='sundyn.column.employeeName'/>
						</td>
						<td width="10%" class="tdtitle">
							<s:text name='sundyn.column.atDating'/>
 						</td>
						<td width="10%" class="tdtitle">
							<s:text name='sundyn.column.appriesResult'/>
						</td>
						<td width="18%" class="tdtitle">
							<s:text name='sundyn.column.appriesTime'/>
						</td>
						<td width="10%" class="tdtitle">
							<s:text name="sundyn.inquiry.result.obtainEvidence"/>
						</td>
						<td width="12%" class="tdtitle">
							<s:text name="sundyn.inquiry.result.businessTime"></s:text>
						</td>
<%--						<td width="8%"  class="tdtitle">--%>
<%--						    <s:text name='sundyn.column.demo'/>--%>
<%--						</td> --%>
						<td width="8%" class="tdtitle">

						客户姓名
						</td>
						<td width="8%" class="tdtitle">

						客户电话
						</td>
						<td width="8%" class="tdtitle" >

						意见反馈
						</td>
					</tr>
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
						   
							<td width="8%" class="td">
								${query.cardNum}
							</td>
							<td width="8%" class="td">
								${query.employeeName}
							</td>
							<td width="10%" class="td">
								${query.fatherName}
							</td>
							<td width="10%" class="td">
								${query.keyName}
							</td>
							<td width="18%" class="td">
								<fmt:formatDate value="${query.JieshouTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
							</td>
							<td width="10%" class="td">
								<c:if test="${empty query.videofile}">
<%--								<s:text name="sundyn.inquiry.result.noVideo" />--%>
									<s:text name="sundyn.inquiry.result.noRecord" />
								
 							 	</c:if>
 							 <c:if test="${!empty query.videofile}">
<%-- 							    <a  href="query/videoPlay2.jsp?videofile=${query.videofile}"  target="_blank"><img src="images/lx.jpg"/></a>--%>
 							   <a  href="#"  id="${query.id }" ><img src="images/lx.jpg" onclick="toshow('${query.videofile}','${pageContext.request.contextPath }','${query.id}');"/></a>
<%-- 							    <a  href="/download/${query.videofile}"  target="_blank"> <s:text name="sundyn.inquiry.result.download"></s:text></a>--%>
 							    <a href="downloadVideo.action?videofile=${query.videofile}"><s:text name="sundyn.inquiry.result.download"></s:text></a>
 							 </c:if>  
							</td>
							<td width="12%" class="td">
 							  <c:if test="${!empty query.businessMin}">
								${query.businessMin}<s:text name="sundyn.inquiry.result.minuteForShort"/>${query.businessSec}<s:text name="sundyn.inquiry.result.secondForShort"/>
							  </c:if>
							  <c:if test="${empty query.businessMin}">
							  <s:text name="sundyn.inquiry.result.noRecord"/>
							  </c:if>
							</td>
<%--							<td width="8%" class="td">--%>
<%--							  <a href="#" onclick="queryDemo(${query.id})" ><s:text name='sundyn.column.detail'/></a>--%>
<%--							</td>--%>
							<td width="12%" class="td">
								${query.ext1}
							</td>
							<td width="12%" class="td">
								${query.ext2}
							</td>
							<td width="12%" class="td" style="width: 80px;display: block;height: 42px;line-height: 42px;">
								<a href="#" onclick="showRemark('${query.remark}')">查看</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${pager.rowsCount == 0 }">
					<div class="sundyn_rows">
							<s:text name="sundyn.system.checkM7Info.noRecord" />
					</div>
				</c:if>
 				<c:if test="${pager.rowsCount >0 }">
					<div class="sundyn_rows">
						${pager.pageTextCn}
					</div>
					<div class="quicklyButton" style="background-image: url('<s:text name="sundyn.query.pic.exportExcel"/>')" onclick="queryExcel()"></div>
<%--					<div class="quicklyButton" onclick="quicklyAddDialog()"></div>--%>
				 </c:if>
  			</div>
  			
  				
					<%
						String strXML1 = (String) request.getAttribute("strXML1");
						if (strXML1 != null && !"".equals(strXML1)) {
							String chartHTML1 = createChartHTML(
									"/pingjia2/Charts/Pie3D.swf", "", strXML1, "",
									600, 350, false);
					%>
					<span> <%=chartHTML1%> </span>    
					<%
						}
					%>
		</div>
		<div id="dialog" style="width: 400px; height: 328px; display: none;"></div>
 	</body>
</html>