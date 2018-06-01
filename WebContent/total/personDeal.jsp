<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page import="java.util.List,java.util.Map" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
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

        table {
            font-size: 12px;
            font-weight: 10px;
            text-align: center;
        }
    </STYLE>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:text name='sundyn.title'/></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/application.css?1442799557" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/vendor/fontawesome/fontawesome.css?1440992355" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet" />
    <link href="${ctx}/assets/stylesheets/main.css?1514875963" rel="stylesheet" media="screen">
    <script src="${ctx}/assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="lib/lhgdialog/lhgdialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?<%=new java.util.Date().getTime()%>"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body class="theme-default easyui-layout">
<div id="man_zone">
    <form id="form1" name="form1" method="post" action="">
        <table width="100%" height="50" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
            <tr>
                <td align="left" style="border-color:#FFFFFF;width:100%;">
                    <div style="float: left; margin-right:10px;">
                        <div style="float: left; margin-right: 10px;">
                        <s:text name='sundyn.total.startDate'/>
                        <input type="text" id="startDate" class="input_comm" value="${startDate}"  <s:text
                                name="sundyn.language.calendar.setDay"/>
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        <s:text name='sundyn.total.endDate'/>
                        <input type="text" id="endDate" class="input_comm" value="${endDate}" <s:text
                                name="sundyn.language.calendar.setDay"/>
                               onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        </div>
                        <div style="float:left;">选择大厅：</div>
                        <div id="dept" class="sundyn_content" style="float: left;">
                            <a id="deptSelectMenu" class="easyui-menubutton l-btn-primary" data-options="menu:'#mm-deptSelectMenu'" href="javascript:void(0)">-- 请选择 --</a>
                            <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
                            <div data-options='itemHeight:32' id='mm-deptSelectMenu' style="display: none;">
                                <jsp:include page="../control/recursive.jsp">
                                    <jsp:param name="objectid" value="deptSelectMenu,deptId"></jsp:param>
                                    <jsp:param name="fatherId" value="-1"></jsp:param>
                                    <jsp:param name="idPath" value='-1'></jsp:param>
                                    <jsp:param name="namePath" value=""></jsp:param>
                                    <jsp:param name="selected" value="${deptId}"></jsp:param>
                                </jsp:include>
                            </div>
                        </div>
                    </div>
                    <div style="float:left; height:25px;background:url('<s:text
                            name='sundyn.total.pic.query'/>') center no-repeat" onclick="totalPersonDeal()"><img
                            src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25"/>
                    </div>

                    <div style="float:left;"><s:if test="list.size==0"><a href="javascript:noData('this');"></s:if><s:else><a href="totalPersonExcel.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_blank"></s:else>
                        <img src="<s:text name='sundyn.total.pic.excel'/>" /></a>
                        <a href="totalPersonPrint.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}" target="_blank">
                            <img src="<s:text name='sundyn.total.pic.print'/>" /></a>
                        <!-- 人员满意度对比 -->
                        <a href="#" onclick="$.dialog({title:'数据分析',content:'url:personDuiBiAction.action?startDate=${startDate}&endDate=${endDate}&deptId=${deptId}'});">
                            <img src="<s:text name='sundyn.total.jigou.duibi'/>" /></a>
                    </div>
                </td>
            </tr>
        </table>
    </form>
    <div style="width:100%;margin:0 auto;">
        <table width="100%" cellpadding="0" cellspacing="0">
            <tr>
                <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1">
                    <s:text name="sundyn.column.name"/></td>
                <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1">
                    <s:text name="sundyn.column.star"/></td>
                <td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg"
                    class="px13_2"><s:text name="sundyn.column.content"/></td>
                <td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg"
                    class="px13_3"><s:text name="sundyn.column.nocontent"/></td>
                <td width="10%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1">
                    <s:text name="sundyn.column.noappries"/></td>
                <c:if test="${k7 == true}">
                    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                        class="px13_1"><s:text name="sundyn.column.appriesNum"/></td>
                </c:if>
                <td width="5%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1">
                    <s:text name="sundyn.column.sum"/></td>
                <!--
				    <c:if test="${k7 == true}">
				    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.appriesRate"/></td>
				    </c:if>

				     <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.workEffective"/></td>
				    -->
                <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1">
                    <s:text name="sundyn.column.contentRate"/></td>
                <td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1">
                    <s:text name="sundyn.column.contentDegree"/></td>
            </tr>
            <tr>
                <c:forEach items="${mls}" var="key">
                    <%--				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>--%>
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1">${key.name}</td>
                    </s:else>
                </c:forEach>
                <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text
                        name="sundyn.column.contentTotal"/></td>
                <c:forEach items="${bmls}" var="key">
                    <%--				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>--%>
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1">${key.name}</td>
                    </s:else>
                </c:forEach>
                <td width="7%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text
                        name="sundyn.column.nocontentTotal"/></td>
            </tr>
            <c:forEach items="${list}" var="total">
                <tr>
                    <td align="center" valign="middle">
                            ${total.employeeName}
                    </td>
                    <td align="center" valign="middle">
                            ${total.star}
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
                    <td align="center" valign="middle">
                            ${total.key6}
                    </td>
                    <c:if test="${k7 ==true}">
                        <td align="center" valign="middle">
                                ${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
                        </td>
                    </c:if>
                    <td align="center" valign="middle">
                            ${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
                    </td>
                    <!--
									<c:if test="${k7 == true}">
									<td align="center" valign="middle">
										${total.prate}%
									</td>
									</c:if>
									<td align="center" valign="middle">
										${total.erate}%
									</td>
									-->
                    <td align="center" valign="middle">
                            ${total.mrate}%
                    </td>
                    <td align="center" valign="middle">
                            ${total.num}
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="sundyn_row">
        ${pager.pageTextCn }
    </div>
    <div class="fengge"">
        <%String str = ""; %>
        <s:if test='getText("sundyn.language") eq "en"'>
            <%
                List mls = (List) request.getAttribute("mls");
                int n = 0;
                for (int i = 0; i < mls.size(); i++) {
                    Map m = (Map) mls.get(i);
                    String s = m.get("ext2") + "=" + m.get("name");
                    if (n % 3 == 0) {
                        str = str + s + " ";
                    } else {
                        if (n % 3 == 2) {
                            str = str + " , " + s + "</br></br>";
                        } else {
                            str = str + "," + s;
                        }
                    }
                    n++;
                }
                List bmls = (List) request.getAttribute("bmls");
                for (int i = 0; i < bmls.size(); i++) {
                    Map m = (Map) bmls.get(i);
                    String s = m.get("ext2") + "=" + m.get("name");
                    if (n % 3 == 0) {
                        str = str + s + " ";
                    } else {
                        if (n % 3 == 2) {
                            str = str + " , " + s + "</br></br>";
                        } else {
                            str = str + ", " + s;
                        }
                    }
                    n++;
                }
            %>
        </s:if>
        <s:if test='getText("sundyn.language") eq "en"'>
            <span style="color:red;"><% out.print(str); %></span>
        </s:if></div>
    </br></br>
    <!-- 统计信息开始 -->
    <table id="table1" width="100%" height="172" border="0" cellpadding="0" cellspacing="0" class="px12"
           style="border-top:1px solid #bad6ec;border-right:1px solid #bad6ec;margin:0 auto;">
        <tr>
            <td colspan="7" align="center" valign="middle" background="images/03_05_11.jpg" class="px12"><s:text
                    name="sundyn.total.toatlInfo"/></td>
        </tr>
        <c:forEach items="${mls}" var="key" varStatus="index">
            <tr>
                <c:if test="${index.index == 0}">
                    <td width="15%" rowspan="${fn:length(mls)}" align="center" valign="middle" bgcolor="fef9f3"
                        class="px13_2"><s:text name="sundyn.column.content"/></td>
                </c:if>
                <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
                <td width="28%" align="left" valign="middle">
                    <div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div>
                </td>
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
                    <td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3"
                        class="px13_2"><s:text name="sundyn.column.nocontent"/></td>
                </c:if>
                <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
                <td width="28%" align="left" valign="middle">
                    <div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div>
                </td>
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
                <td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3"
                    class="px13_2">
                    <s:text name="sundyn.column.noappries"/></td>
                <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${totalMap.k7Name}</td>
                <td width="28%" align="left" valign="middle">
                    <div style="height:10px; width:${totalMap.kr6}%; background-color:#FF0000;">&nbsp;</div>
                </td>
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
</body>
<script src="${ctx}/assets/javascripts/jquery.easyui.min.js"></script>
<%--<script src="${ctx}/js/easyui-1.5.3/jquery.easyui.min.js"></script>--%>
<script src="${ctx}/js/main.js"></script>
</html>