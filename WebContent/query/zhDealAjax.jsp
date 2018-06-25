<%@ page import="java.text.*,java.io.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
      xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"/>
    <title><s:text name='sundyn.title'/></title>

    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
</head>
<body>
<div class="layui-form" lay-filter="f">
    <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:120px;"><s:text name='sundyn.query.selectEmployee'/></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="input_comm" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="input_comm" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label" style="width: 100px;"><s:text name='sundyn.query.selectResult'/></label>
        <div class="layui-input-inline" style="width:120px;">
            <select id="result">
                <option value="" <c:if test="${keys == \"\"}">selected="selected"</c:if>>--全部--</option>
                <c:forEach items="${keyList}" var="key" varStatus="index">
                    <option value="${key.keyNo}" <c:if test="${keys == key.keyNo.toString()}">selected="selected"</c:if>>${key.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="81" height="25" style="cursor: pointer;" onclick="queryZhDeal1()" class="hand" />
        </div>
    </div>
    <table width="100%" class="layui-table">
        <tr>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name='sundyn.column.employeeCardNum'/>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name='sundyn.column.employeeName'/>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name='sundyn.column.atDating'/>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name='sundyn.column.appriesResult'/>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name='sundyn.column.appriesTime'/>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.inquiry.result.obtainEvidence"/>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                <s:text name="sundyn.inquiry.result.businessTime"></s:text>
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                客户姓名
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                客户电话
            </td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">
                意见反馈
            </td>
        </tr>
        <c:forEach items="${pager.pageList}" var="query">
            <tr>
                <td align="center">
                        ${query.cardNum}
                </td>
                <td align="center">
                        ${query.employeeName}
                </td>
                <td align="center">
                        ${query.fatherName}
                </td>
                <td align="center">
                        ${query.keyName}
                </td>
                <td align="center">${query.JieshouTime}
                </td>
                <td align="center">
                    <c:if test="${not empty query.imgfile}">
                        <a class="layui-btn layui-btn-sm" href="${pageContext.request.contextPath }/download/recorder/${query.imgfile}" target="_blank" >截图</a>
                    </c:if>
                    <c:if test="${empty query.videofile}">
                        <s:text name="sundyn.inquiry.result.noRecord"/>
                    </c:if>
                    <c:if test="${!empty query.videofile}">
                        <a href="#" id="${query.id }"><img src="images/lx.jpg" onclick="toshow('${query.videofile}','${pageContext.request.contextPath }','${query.id}');"/></a>
                        <a href="downloadVideo.action?videofile=${query.videofile}" target="_blank"><s:text name="sundyn.inquiry.result.download"></s:text></a>
                    </c:if>
                </td>
                <td align="center">
                    <c:if test="${!empty query.businessMin}">
                        ${query.businessMin}<s:text name="sundyn.inquiry.result.minuteForShort"/>${query.businessSec}<s:text name="sundyn.inquiry.result.secondForShort"/>
                    </c:if>
                    <c:if test="${empty query.businessMin}">
                        <s:text name="sundyn.inquiry.result.noRecord"/>
                    </c:if>
                </td>
                <td align="center">
                        ${query.ext1}
                </td>
                <td align="center">
                        ${query.ext2}
                </td>
                <td align="center">
                    <c:if test="${query.remark != null && query.remark != ''}"><a href="#" onclick="showRemark('${query.remark}')">查看</a></c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
    <c:if test="${pager.rowsCount == 0 }">
        <div class="sundyn_rows">
            <s:text name="sundyn.system.checkM7Info.noRecord"/>
        </div>
    </c:if>
    <c:if test="${pager.rowsCount >0 }">
        <div class="sundyn_rows" id="pp"></div>
    </c:if>
</div>
<c:if test="${pager.rowsCount >0 }">
<%
    String strXML1 = (String) request.getAttribute("strXML1");
    if (strXML1 != null && !"".equals(strXML1)) {
        String chartHTML1 = createChartHTML("Charts/Pie3D.swf", "", strXML1, "", 600, 350, false);
%>
<span> <%=chartHTML1%> </span>
<%
    }
%>
</c:if>
</body>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()}, ${pager.getPageSize()});

    layui.use('form', function(){
        var form = layui.form;
        var deptpath = '<%=request.getParameter("deptpath")==null?"":request.getParameter("deptpath")%>'.split(",");
        renderchild(form, -1, -1, deptpath,'employee');
    });
</script>
</html>