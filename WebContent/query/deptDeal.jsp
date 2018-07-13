<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/JSClass/FusionCharts.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></META>
    <META HTTP-EQUIV="Expires" CONTENT="Mon， 04 Dec 1999 21：29：02 GMT"></META>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="css/dialog.css" type="text/css"/>
    <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/application.css?1442799557" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <title><s:text name='sundyn.title'/></title>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script language="javascript">
        function playfile() {
            document.getElementById('MediaPlayer').Filename = document.getElementById("filepath").value;
            document.getElementById('MediaPlayer').AutoStart = 1;
            document.getElementById('MediaPlayer').play();
        }

        function nihao() {
            window.location.href = 'query/playvideo.html';
        }
    </script>
</head>
<body>
<iframe src="piedata.xml" style="display: none;"></iframe>
<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}"/>
<%--<input type="hidden" name="startDate" id="startDate" value="${startDate}"/>
<input type="hidden" name="endDate" id="endDate" value="${endDate}"/>--%>
<input type="hidden" name="keys" id="keys" value="${keys}"/>
<input type="hidden" name="id" id="id" value="${id}"/>
<input type="hidden" name="videofile" id="videofile" value="${videofile}"/>
<input type="hidden" name="CardNum" id="CardNum" value="${CardNum}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.query.selectDept'/></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
    </div>
    <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="input_comm" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="input_comm" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand"
                 onclick="querydept()"/>
            <img src="<s:text name='sundyn.query.pic.exportExcel'/>" class="hand"
                 onclick="querydept(true)"/>
        </div>
    </div>
    <table width="100%" class="layui-table">
        <thead>
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
                <s:text name="sundyn.inquiry.result.businessTime"></s:text></td>
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
        </thead>
        <c:forEach items="${pager.pageList}" var="query">
            <tr>
                <td align="center">
                        ${query.CardNum}
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
                <td align="center">
                        ${query.JieshouTime}
                </td>
                <td align="center">
                    <c:if test="${not empty query.imgfile}">
                        <a class="layui-btn layui-btn-sm" href="${pageContext.request.contextPath }/download/recorder/${query.imgfile}" target="_blank" >截图</a>
                    </c:if>
                    <c:if test="${empty query.videofile}">
                        <s:text name="sundyn.inquiry.result.noVideo"/>
                    </c:if>
                    <c:if test="${!empty query.videofile}">
                        <a href="#" id="${query.id}"><img src="images/lx.jpg" onclick="toshow('${query.videofile}','${pageContext.request.contextPath }','${query.id}');"/></a>
                        <a href="downloadVideo.action?videofile=${query.videofile}" target="_blank"><s:text
                                name="sundyn.inquiry.result.download"></s:text></a>
                    </c:if>
                </td>
                <td align="center">
                    <c:if test="${!empty query.businessMin}">
                        ${query.businessMin}<s:text
                            name="sundyn.inquiry.result.minuteForShort"/>${query.businessSec}<s:text
                            name="sundyn.inquiry.result.secondForShort"/>
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
    <c:if test="${pager.rowsCount == 0}">
        <div class="sundyn_rows">
            <s:text name="sundyn.system.checkM7Info.noRecord"/>
        </div>
    </c:if>
    <c:if test="${pager.rowsCount > 0}">
        <div class="sundyn_rows" id="pp">
                ${pager.pageTextCn}
        </div>
    </c:if>

    <%
        String strXML1 = (String) request.getAttribute("strXML1");
        if (strXML1 != null && !"".equals(strXML1)) {
            String chartHTML1 = createChartHTML(
                    "Charts/Pie3D.swf", "", strXML1, "",
                    600, 350, false);
    %>
    <span style="z-index:0"> <%=chartHTML1%> </span>
    <%
        }
    %>
</div>
</body>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()},${pager.getPageSize()});

    layui.use('form', function(){
        var form = layui.form;
        var deptpath = '<%=request.getParameter("deptpath")==null?"":request.getParameter("deptpath")%>'.split(",");
        renderchild(form, -1, -1, deptpath,'dept');
    });

</script>
</html>