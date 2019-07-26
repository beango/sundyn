<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ include file="/JSClass/FusionCharts.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <title><s:text name='zx.title'/></title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script language="javascript">
        function playfile() {
            document.getElementById('MediaPlayer').Filename = document.getElementById("filepath").value;
            document.getElementById('MediaPlayer').AutoStart = 1;
            document.getElementById('MediaPlayer').play();
        }

        function nihao() {
            window.location.href = 'query/playvideo.html';
        }
        $(document).ready(function () {
            initTree("?depttype=1", '<%=request.getParameter("deptId")%>');
        });
    </script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
    </style>
</head>
<body>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
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
        <input id="deptSel" class="scinput" type="text" readonly value="${param.getOrDefault("deptname", main_all)}" style="width:120px;" onclick="showDeptTree(this,null);" />
    </div>
    <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="querydept(false)" value="<s:text name="main.query" />" />
        </div>
    </div>
    <table width="100%" class="tablelist">
        <thead>
        <tr>
            <th style="text-align: center;">
                <s:text name='sundyn.column.employeeName'/> / <s:text name='sundyn.column.atWindow'/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.atDating'/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.atSerial'/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.atQueue'/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.tickettype'/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.tickettime'/>／<s:text name='sundyn.column.calltime'/>／<s:text name='sundyn.column.waittime'/>
            </th>
            <th style="text-align: center;"><s:text name='sundyn.column.processtime'/>／<s:text name='sundyn.column.endtime'/></th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.appriesResult'/>
            </th>
            <th style="text-align: center;">
                <s:text name="sundyn.inquiry.result.obtainEvidence"/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.status'/>
            </th>
        </tr>
        </thead>
        <c:forEach items="${pager.pageList}" var="query">
            <tr>
                <td align="center">
                        ${query.staffname}<c:if test="${query.hjcountername!=null}">　／　${query.hjcountername}</c:if>
                </td>
                <td align="center">
                        ${query.deptname}
                </td>
                <td align="center"><div style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;width:180px;" title="${query.bizname}">
                        ${query.bizname}</div>
                </td>
                <td align="center">
                        ${query.queuenum}
                </td>
                <td align="center">
                    <c:if test="${query.queuetype==0}"><s:text name="queuedetail.queuetype.scene"/></c:if>
                    <c:if test="${query.queuetype==1}"><s:text name="queuedetail.queuetype.reservation"/></c:if>
                    <c:if test="${query.queuetype==2}"><s:text name="queuedetail.queuetype.online"/></c:if>
                </td>
                <td align="left">
                    <label style="height:20px;"><fmt:formatDate value="${query.tickettime}" type="both" /></label><c:if test="${query.hjtime!=null}">　／　<label style="height:20px;"><fmt:formatDate value="${query.hjtime}" type="both" /></label></c:if>
                    <c:if test="${query.hjtime==null}">　／　-- </c:if>
                    <c:if test="${query.waittimename!=null}">　／ ${query.waittimename}</c:if><c:if test="${query.waitout}"><font style="color:red;">(<s:text name="queuedetail.waittimeout"/>)</font></c:if>
                    <c:if test="${query.waittimename==null}">　／　-- </c:if>
                </td>
                <td>
                    <c:if test="${query.starttime==null}">--</c:if>
                    <label style="height:20px;"><fmt:formatDate value="${query.starttime}" type="both" /></label><c:if test="${query.endtime!=null}">　／　<label style="height:20px;"><fmt:formatDate value="${query.endtime}" type="both" /></label></c:if>
                    <c:if test="${query.endtime==null}">　／　-- </c:if>
                    <c:if test="${query.servicetimename!=null}">　／　${query.servicetimename}</c:if><c:if test="${query.serviceout}"><font style="color:red;">(<s:text name="queuedetail.processout"/>)</font></c:if>
                    <c:if test="${query.servicetimename==null}">　／　-- </c:if>
                </td>
                <td align="center">
                        ${query.appriseresultname}
                </td>
                <td align="center">
                    <c:if test="${not empty query.imgfile}">
                        <a class="layui-btn layui-btn-sm" href="${pageContext.request.contextPath }/download/recorder/${query.imgfile}" target="_blank" ><s:text name="queuedetail.cutimg"/></a>
                    </c:if>
                    <c:if test="${empty query.videofile}">
                        <s:text name="sundyn.inquiry.result.noVideo"/>
                    </c:if>
                    <c:if test="${!empty query.videofile}">
                        <a href="#" id="${query.id}"><img src="images/lx.jpg" onclick="toshow('${query.videofile}','${pageContext.request.contextPath }','${query.id}');"/></a>
                        <a href="downloadVideo.action?videofile=${query.videofile}" target="_blank"><s:text name="sundyn.inquiry.result.download"></s:text></a>
                    </c:if>
                </td>
                <td align="center">
                    ${QueueDetailBean.getStatusname(query.status)}
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
            String chartHTML1 = createChartHTML("Charts/Pie3D.swf", "", strXML1, "", 600, 350, false);
    %>
    <span style="z-index:0;display:none;"> <%=chartHTML1%> </span>
    <%
        }
    %>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
</body>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()},${pager.getPageSize()});
    layui.use('form');
</script>
</html>
