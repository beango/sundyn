<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.List,java.util.Map" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <title><s:text name='zx.title'/></title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script language="javascript" type="text/javascript" src="lib/util/TableSorterV2.js"></script>
    <script language="javascript">
        $(document).ready(function () {
            initTree("?depttype=2", '<%=request.getParameter("deptId")%>');
            new TableSorter("table123", tableSortCallback, '<%=request.getParameter("sort")%>');
        });

        function tableSortCallback(sort){
            totalDeptDeal(false, sort);
        }
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
<div class="layui-form">
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}"
                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}"
                   onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:100px;"><s:text name='sundyn.query.selectDept'/></label>
        <input id="deptSel" class="scinput" type="text" readonly
               value="${param.getOrDefault("deptname", main_all)}"
               style="width:120px;cursor: pointer;" onclick="showDeptTree(this,null);"/>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="totalDeptDeal(false)" value="<s:text name="main.query" />" />
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="totalDeptDeal(true, '<%=request.getParameter("sort")%>')" value="<s:text name="main.export" />" />
        </div>
    </div>
    <div style="width: 100%; margin: 0 auto;">
        <table width="100%" class="tablelist" id="table123">
            <thead>
            <tr>
                <td rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text name="main.column.rank" /></td>
                <td rowspan="2" align="center" background="../images/03_02_07.jpg">
                    <s:text name="sundyn.column.deptName"/></td>
                <td rowspan="2" align="center" valign="middle"
                    background="../images/03_02_07.jpg" sort="ticketcount"><s:text name="queuereport.column.ticketcount" /> <img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle"
                    background="../images/03_02_07.jpg" sort="servercount"><s:text name="queuereport.column.serialcount" /> <img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle"
                    background="../images/03_02_07.jpg" sort="cancelcount"><s:text name="queuereport.column.cancelcount" /> <img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle"
                    background="../images/03_02_07.jpg" sort="waittime"><s:text name="queuereport.column.waittimeavg" /> <img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle"
                    background="../images/03_02_07.jpg" sort="servicetime"><s:text name="queuereport.column.processtimeavg" /> <img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle"
                    background="../images/03_02_07.jpg" sort="pausetime"><s:text name="queuereport.column.pausetime" /> <img src="../images/nor.png" /></td>
                <td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_2"><s:text name="sundyn.column.content"/></td>
                <td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_3"><s:text name="sundyn.column.nocontent"/></td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="unkey"><s:text name="sundyn.column.noappries"/><img src="../images/nor.png" /></td>
                <%--未评价--%>
                <c:if test="${k7 == true}">
                    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                        class="px13_1"><s:text name="sundyn.column.appriesNum"/><img src="../images/nor.png" /></td><%--合计--%>
                </c:if>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="totalkey"><s:text name="sundyn.column.appries"/><s:text name="sundyn.column.sum"/><img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="mrate"><s:text name="sundyn.column.contentRate"/><img src="../images/nor.png" /><span id="lblcontentRate" class="layui-badge layui-bg-orange" style="text-indent:0px;"> ? </span>
                </td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="num"><s:text name="sundyn.column.contentDegree"/><img src="../images/nor.png" /><span id="lblContentDegree" class="layui-badge layui-bg-orange" style="text-indent:0px;"> ? </span>
                </td>
            </tr>
            <tr>
                <c:forEach items="${mls}" var="key">
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" height="42" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1" style="line-height: 30px;">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1" style="line-height: 30px;" sort="key${key.keyNo}">${key.name}<img src="../images/nor.png" /></td>
                    </s:else>
                </c:forEach>
                <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1" style="line-height: 30px;" sort="msum"><s:text
                        name="sundyn.column.contentTotal"/><img src="../images/nor.png" /></td>
                <c:forEach items="${bmls}" var="key">
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1" style="line-height: 30px;">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1" style="line-height: 30px;" sort="key${key.keyNo}">${key.name}<img src="../images/nor.png" /></td>
                    </s:else>
                </c:forEach>
                <td width="7%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1" style="line-height: 30px;" sort="bmsum"><s:text
                        name="sundyn.column.nocontentTotal"/><img src="../images/nor.png" /></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${list}" var="total" varStatus="status">
                <tr style="height: 28px;">
                    <td align="center" valign="middle">${status.index+1}</td>
                    <td align="center" valign="middle">${total.name}</td>
                    <%--<td align="center" valign="middle">${total.star}</td>--%>
                    <td align="center" valign="middle">${total.ticketcount}</td>
                    <td align="center" valign="middle">${total.servercount}</td>
                    <td align="center" valign="middle">${total.cancelcount}</td>
                    <td align="center" valign="middle">${total.waittimeavg}</td>
                    <td align="center" valign="middle">${total.servicetimeavg}</td>
                    <td align="center" valign="middle">${total.pausetime == null ? "" : total.pausetime.replace("null","")}</td>
                    <c:forEach items="${total.km}" var="k">
                        <td align="center" valign="middle">${k}</td>
                    </c:forEach>
                    <td align="center" valign="middle">${total.msum}</td>
                    <c:forEach items="${total.kbm}" var="k">
                        <td align="center" valign="middle">${k}</td>
                    </c:forEach>
                    <td align="center" valign="middle">${total.bmsum}</td>
                    <td align="center" valign="middle">${total.totalunkey}</td>
                    <td align="center" valign="middle">${total.totalkey}</td>
                    <td align="center" valign="middle">${total.mrate}%</td>
                    <td align="center" valign="middle">${total.num}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <c:if test="${list.size() == 0}">
        <div class="sundyn_rows">
            <s:text name="sundyn.system.checkM7Info.noRecord"/>
        </div>
    </c:if>
    <div id="bt">
        <%
            String str = "";
        %>
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
					<span style="color: red;">
						<%
                            //out.print(str);
                        %>
					</span>
        </s:if>
    </div>
    <!-- 统计信息开始 -->
    <c:if test="${list.size() > 0 && 1==2}">
        <table id="table1" class="layui-table">
            <tr>
                <td colspan="7" align="center" valign="middle" background="images/03_05_11.jpg" class="px12"><s:text
                        name="sundyn.total.toatlInfo"/></td>
            </tr>
            <c:forEach items="${mls}" var="key" varStatus="index">
                <tr>
                    <c:if test="${index.index == 0}">
                        <td width="15%" rowspan="${fn:length(mls)}" align="center" valign="middle" bgcolor="fef9f3">
                            <s:text name="sundyn.column.content"/></td>
                    </c:if>
                    <td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
                    <td width="28%" align="left" valign="middle">
                        <div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;
                        </div>
                    </td>
                    <td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
                    <c:if test="${index.index == 0}">
                        <td width="13%" rowspan="${fn:length(mls)}" align="center" valign="middle">${totalMap.msum}</td>
                    </c:if>
                    <td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
                    <c:if test="${index.index == 0}">
                        <td width="13%" rowspan="${fn:length(mls)}" align="center" valign="middle">${totalMap.mrate}%
                        </td>
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
                        <div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;
                        </div>
                    </td>
                    <td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
                    <c:if test="${index.index == 0}">
                        <td width="13%" rowspan="${fn:length(bmls)}" align="center"
                            valign="middle">${totalMap.bmsum}</td>
                    </c:if>
                    <td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
                    <c:if test="${index.index == 0}">
                        <td width="13%" rowspan="${fn:length(bmls)}" align="center"
                            valign="middle">${totalMap.bmrate}%
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            <c:if test="${k7 == true}">
                <tr>
                    <td width="15%" rowspan="${fn:length(bmls)}" align="center"
                        valign="middle" bgcolor="fef9f3" class="px13_2"><s:text name="sundyn.column.noappries"/></td>
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
    </c:if>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
</body>
<script type="text/javascript">
    var t = "";
    <c:forEach items="${mls}" var="key">
    t += "${key.ext2}" + "*<s:text name="sundyn.column.quanValue"/> + ";
    </c:forEach>
    <c:forEach items="${bmls}" var="key">
    t += "${key.ext2}" + "*<s:text name="sundyn.column.quanValue"/> + ";
    </c:forEach>
    t = t.substr(0, t.length - 2);
    t += " <hr /><s:text name="queuereport.column.serialcount"/> * 10";
    <%--<c:forEach items="${mls}" var="key">
    t += "${key.ext2}" + " + ";
    </c:forEach>
    <c:forEach items="${bmls}" var="key">
    t += "${key.ext2}" + " + ";
    </c:forEach>
    t = t.substr(0, t.length - 2);
    t += ")*10";--%>

    var t2 = "";
    <c:forEach items="${mls}" var="key">
    t2 += "${key.ext2}" + " + ";
    </c:forEach>
    t2 = t2.substr(0, t2.length - 2);
    t2 += " <hr />";
    <c:forEach items="${mls}" var="key">
    t2 += "${key.ext2}" + " + ";
    </c:forEach>
    <c:forEach items="${bmls}" var="key">
    t2 += "${key.ext2}" + " + ";
    </c:forEach>
    t2 = t2.substr(0, t2.length - 2);

    t2 = "<s:text name="sundyn.column.appries"/><s:text name="sundyn.column.sum"/> <hr /> <s:text name="queuereport.column.serialcount"/>"
    layui.use(['form','table'], function () {
        var form = layui.form;

        layui.use('form', function () {
            var form = layui.form;
            var tip1 = null, tip2 = null;
            $('#lblContentDegree').click(function (event) {
                event.stopPropagation();
                if (null == tip1) {
                    tip1 = layer.tips('<s:text name="sundyn.column.cal"/><br />' + t, '#lblContentDegree', {
                        tips: 3,
                        time: 0
                    });
                }
                else {
                    layer.closeAll('tips'); //关闭所有的tips层
                    tip1 = null;
                }
            });
            $('#lblcontentRate').click(function (event) {
                event.stopPropagation();
                if (null == tip2) {
                    tip2 = layer.tips('<s:text name="sundyn.column.cal"/><br />' + t2, '#lblcontentRate', {
                        tips: 3,
                        time: 0
                    });
                }
                else {
                    layer.closeAll('tips'); //关闭所有的tips层
                    tip2 = null;
                }
            });
        });
    });
</script>
</html>
