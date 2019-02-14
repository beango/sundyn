<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
      xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:text name='sundyn.title'/></title>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <script src="assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" name="deptIds" id="deptIds" value="${deptIds}"/>
<input type="hidden" name="keys" id="keys" value="${keys}"/>
<input type="hidden" name="id" id="id" value="${id}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-inline">
        <label class="layui-form-label" style="width: 90px;"><s:text name='sundyn.query.selectResult'/></label>
        <div class="layui-input-inline" style="width:90px;">
            <select id="result">
                <option value="" <c:if test="${keys == \"\"}">selected="selected"</c:if>>--全部--</option>
                <c:forEach items="${deptList}" var="key" varStatus="index">
                    <option value="${key.keyNo}" <c:if test="${keys == key.keyNo.toString()}">selected="selected"</c:if>>${key.name}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}" <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" style="vertical-align: middle;"  width="80" height="25" onclick="queryResultDeal()" class="hand" />
            <%--<img src="<s:text name="sundyn.query.pic.exportExcel"/>"  style="vertical-align: middle;" width="120" height="25" onclick="queryResultDeal(true)" class="hand" />--%>
        </div>
    </div>
    <table width="100%" class="tablelist">
        <thead>
        <tr>
            <th style="text-align: center;">
                <s:text name='sundyn.column.employeeName'/>
            </th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.atDating'/>
            </th>
            <th style="text-align: center;">
                业务名称
            </th>
            <th style="text-align: center;">
                排队号码
            </th>
            <th style="text-align: center;">
                取号类型
            </th>
            <th style="text-align: center;">
                取号／叫号时间／等待时长
            </th>
            <th style="text-align: center;">办理／办结时间</th>
            <th style="text-align: center;">
                <s:text name='sundyn.column.appriesResult'/>
            </th>
            <th style="text-align: center;">
                <s:text name="sundyn.inquiry.result.obtainEvidence"/>
            </th>
            <th style="text-align: center;">
                状态
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
                <td align="center">
                    <div style="overflow: hidden; white-space: nowrap; text-overflow: ellipsis;width:180px;" title="${query.bizname}">
                            ${query.bizname}</div>
                </td>
                <td align="center">
                        ${query.queuenum}
                </td>
                <td align="center">
                    <c:if test="${query.queuetype==0}">现场号</c:if><c:if test="${query.queuetype==1}">预约号</c:if><c:if test="${query.queuetype==2}">线上取号</c:if>
                </td>
                <td align="left">
                    <label style="height:20px;"><fmt:formatDate value="${query.tickettime}" type="both" /></label><c:if test="${query.hjtime!=null}">　／　<label style="height:20px;"><fmt:formatDate value="${query.hjtime}" type="both" /></label></c:if>
                    <c:if test="${query.hjtime==null}">　／　-- </c:if>
                    <c:if test="${query.waittimename!=null}">　／　${query.waittimename.replace("0天00时00分","").replace("0天00时","").replace("0天","")}</c:if><c:if test="${query.waitout}"><font style="color:red;">(等候超时)</font></c:if>
                    <c:if test="${query.waittimename==null}">　／　-- </c:if>
                </td>
                <td>
                    <c:if test="${query.starttime==null}">--</c:if>
                    <label style="height:20px;"><fmt:formatDate value="${query.starttime}" type="both" /></label><c:if test="${query.endtime!=null}">　／　<label style="height:20px;"><fmt:formatDate value="${query.endtime}" type="both" /></label></c:if>
                    <c:if test="${query.endtime==null}">　／　-- </c:if>
                    <c:if test="${query.servicetimename!=null}">　／　${query.servicetimename.replace("0天00时00分","").replace("0天00时","").replace("0天","")}</c:if><c:if test="${query.serviceout}"><font style="color:red;">(办理超时)</font></c:if>
                    <c:if test="${query.servicetimename==null}">　／　-- </c:if>
                </td>
                <td align="center">
                        ${query.appriseresultname}<%--<c:if test="${query.apprisetime!=null}">　／　</c:if>${query.apprisetime}--%>
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
        </div>
        <%
            String strXML1 = (String) request.getAttribute("strXML1");
            if (strXML1 != null && !"".equals(strXML1)) {
                String chartHTML1 = createChartHTML("Charts/Pie3D.swf", "", strXML1, "", 600, 350, false);
        %>
        <div style="display: none;">
            <%=chartHTML1%>
        </div>
        <%
            }
        %>
    </c:if>
</div>
</body>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()}, ${pager.getPageSize()});

    layui.use('form', function(){
        var form = layui.form;
    });
</script>
</html>