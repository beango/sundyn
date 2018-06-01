<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/JSClass/FusionCharts.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" />

		<title><s:text name='sundyn.title' />
		</title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
        <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
        <link href="${ctx}/assets/stylesheets/application.css?1442799557" rel="stylesheet" />
        <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
        <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet" />
        <link href="${ctx}/assets/stylesheets/main.css?1514875963" rel="stylesheet" media="screen">
        <link rel="stylesheet" href="css/dialog.css" type="text/css" />

        <script src="${ctx}/assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<%--<script type="text/javascript" src="js/jquery.js"></script>--%>
		<script type="text/javascript" src="js/layer/layer.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
        <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
		<script type="text/javascript"
			src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
    <body>
		<iframe src="piedata.xml" style="display: none;"></iframe>
		<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}" />
		<input type="hidden" name="keys" id="keys" value="${keys}" />
		<input type="hidden" name="id" id="id" value="${id}" />
        <div class="container theme-default easyui-layout">
            <div class='col-12'>
                <div class='panel panel-primary easyui-panel' id="tablePanel" data-options="region:'center',noheader:true">
                        <table width="100%" height="51" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
                            <tr>
                                <td align="left" style="border-color:#FFFFFF;">
                                    <div style="float:left;"><s:text name='sundyn.query.selectEmployee'/></div>
                                    <div id="dept" class="sundyn_content" style="float: left;">
                                        <a id="deptSelectMenu" class="easyui-menubutton l-btn-primary" data-options="menu:'#mm-deptSelectMenu'" href="javascript:void(0)">-- 请选择 --</a>
                                        <div data-options='itemHeight:32' id='mm-deptSelectMenu' style="display: none;">
                                            <jsp:include page="../control/recursive.jsp">
                                                <jsp:param name="objectid" value="deptSelectMenu,id"></jsp:param>
                                                <jsp:param name="fatherId" value="-1"></jsp:param>
                                                <jsp:param name="idPath" value='-1'></jsp:param>
                                                <jsp:param name="namePath" value=""></jsp:param>
                                                <jsp:param name="selected" value="${id}"></jsp:param>
                                                <jsp:param name="showEmployee" value="true"></jsp:param>
                                            </jsp:include>
                                        </div>
                                    </div>
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
                        <table width="100%" class="t">
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
                                            ${query.JieshouTime}
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
                                    <td class="td">
                                        ${query.ext1}
                                    </td>
                                    <td class="td">
                                        ${query.ext2}
                                    </td>
                                    <td class="td">
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
            </div>
		    <div id="dialog" style="width: 479px; height: 328px; display: none;"></div>
        </div>
	</body>
    <%--<script src="${ctx}/assets/javascripts/jquery.easyui.min.js"></script>--%>
    <script src="${ctx}/js/easyui-1.5.3/jquery.easyui.min.js"></script>
    <script src="${ctx}/js/main.js"></script>
</html>