<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ include file="/JSClass/FusionCharts.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE"></META>
    <META HTTP-EQUIV="Expires" CONTENT="Mon， 04 Dec 1999 21：29：02 GMT"></META>
    <STYLE type="text/css">
        v\: * {
            Behavior: url(#default#VML)
        }

        o\: * {
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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="css/dialog.css" type="text/css"/>
    <title><s:text name='sundyn.title'/></title>
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/layer/layer.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script language="javascript">
        function playfile() {
            document.getElementById('MediaPlayer').Filename = document.getElementById("filepath").value;
            document.getElementById('MediaPlayer').AutoStart = 1;
            document.getElementById('MediaPlayer').play();
        }

        function nihao() {
            window.location.href = 'query/playvideo.html';
        }

        $(function(){
            deptopen('${deptId}');
        });
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
<div id="man_zone">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;height:50px;">
        <tr>
            <td align="right" style="border-color:#FFFFFF;">
                <div style="float: left;"><s:text name='sundyn.query.selectDept'/></div>
                <div id="dept" class="sundyn_content" style="float: left;">
                    <select name="deptId" style="height: 31px;"><c:forEach items="${deptList2}" var="dept" varStatus="index"><option value="${dept.id}" <c:if test="${index.index ==0 }">selected="selected"</c:if>>${dept.name}</option></c:forEach></select>
                </div>
                <div style="float: left;">
                    <s:text name='sundyn.total.startDate'/><input type="text" class="input_comm" id="startDate" value="${startDate}"
                        <s:text name="sundyn.language.calendar.setDay"/> onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                    <s:text name='sundyn.total.endDate'/>
                    <input type="text" class="input_comm" id="endDate" value="${endDate}"
                            <s:text name="sundyn.language.calendar.setDay"/>
                           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                </div>
                <div style="float: left;margin-left: 10px;">
                    <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand"
                         onclick="querydept()"/>
                </div>
                <div class="quicklyButton" style="background-image: url('<s:text name="sundyn.query.pic.exportExcel"/>')"
                     onclick="queryExcel()" align="center"></div>
            </td>
        </tr>
    </table>
    <div class="sundyn_main">
        <div class="fengge">&nbsp;</div>
        <table width="100%" class="t">
            <tr>
                <td width="10%" class="tdtitle">
                    <s:text name='sundyn.column.employeeCardNum'/>
                </td>

                <td width="8%" class="tdtitle">
                    <s:text name='sundyn.column.employeeName'/>
                </td>
                <td width="12%" class="tdtitle">
                    <s:text name='sundyn.column.atDating'/>
                </td>
                <td width="12%" class="tdtitle">
                    <s:text name='sundyn.column.appriesResult'/>
                </td>
                <td width="20%" class="tdtitle">
                    <s:text name='sundyn.column.appriesTime'/>
                </td>
                <!-- 录像 -->
                <td width="8%" class="tdtitle">
                    <%--						<s:text name="sundyn.inquiry.result.video"/>--%>
                    <s:text name="sundyn.inquiry.result.obtainEvidence"/>
                </td>

                <td width="12%" class="tdtitle"><s:text name="sundyn.inquiry.result.businessTime"></s:text></td>
                <%--						<td width="6%"  class="tdtitle">--%>
                <%--						    <s:text name='sundyn.column.demo'/>--%>
                <%--						</td> --%>
                <td width="8%" class="tdtitle">

                    客户姓名
                </td>
                <td width="8%" class="tdtitle">

                    客户电话
                </td>
                <td width="12%" class="tdtitle" style="width: 80px;display: block;height: 42px;line-height: 42px;">

                    意见反馈
                </td>

            </tr>
            <c:forEach items="${pager.pageList}" var="query">
                <tr>
                    <td width="10%" class="td">
                            ${query.CardNum}
                    </td>
                    <td width="8%" class="td">
                            ${query.employeeName}
                    </td>
                    <td width="12%" class="td">
                            ${query.fatherName}
                    </td>
                    <td width="12%" class="td">
                            ${query.keyName}
                    </td>
                    <td width="20%" class="td">
                        <fmt:formatDate value="${query.JieshouTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </td>
                    <td width="8%" class="td">
                        <c:if test="${empty query.videofile}">
                            <s:text name="sundyn.inquiry.result.noVideo"/>
                        </c:if>
                        <c:if test="${!empty query.videofile}">
                            <%-- 							    <a  href="query/videoPlay2.jsp?videofile=${query.videofile}"  target="_blank"><img src="images/lx.jpg"/></a>--%>
                            <a href="#" id="${query.id }"><img src="images/lx.jpg"
                                                               onclick="toshow('${query.videofile}','${pageContext.request.contextPath }','${query.id}');"/></a>
                            <%-- 							 <a  href="/download/${query.videofile}"  target="_blank"> <s:text name="sundyn.inquiry.result.download"></s:text></a>--%>
                            <a href="downloadVideo.action?videofile=${query.videofile}"><s:text
                                    name="sundyn.inquiry.result.download"></s:text></a>
                        </c:if>
                    </td>


                    <td width="12%" class="td">
                        <c:if test="${!empty query.businessMin}">
                            ${query.businessMin}<s:text
                                name="sundyn.inquiry.result.minuteForShort"/>${query.businessSec}<s:text
                                name="sundyn.inquiry.result.secondForShort"/>
                        </c:if>
                        <c:if test="${empty query.businessMin}">
                            <s:text name="sundyn.inquiry.result.noRecord"/>
                        </c:if>
                    </td>

                        <%--							 <td width="6%" class="td">--%>
                        <%--							  <a href="#" onclick="queryDemo(${query.id})" ><s:text name='sundyn.column.detail'/></a>--%>
                        <%--							</td>--%>

                    <td width="12%" class="td">
                            ${query.ext1}
                    </td>
                    <td width="12%" class="td">
                            ${query.ext2}
                    </td>
                    <td width="12%">
                        <a href="#" onclick="showRemark('${query.remark}')">查看</a>
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
            <div class="sundyn_rows">
                    ${pager.pageTextCn}
            </div>

            <%--					<div  class="quicklyButton" onclick="quicklyAddDialog()" align="center"></div>--%>
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
</div>
<div id="dialog" style="width: 479px; height: 328px; display: none;"></div>

<script type="text/javascript">


</script>
</body>
</html>