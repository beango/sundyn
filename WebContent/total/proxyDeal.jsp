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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><s:text name='sundyn.title'/></title>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
    <script src="${ctx}/assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script language="javascript" type="text/javascript" src="lib/util/TableSorterV2.js"></script>
    <script language="javascript">
        $(document).ready(function () {
            //initTree("?depttype=3", '<%=request.getParameter("deptId")%>');
            new TableSorter("table123", tableSortCallback, '<%=request.getParameter("sort")%>');
        });
        function tableSortCallback(sort){
            totalProxyDeal(false, sort);
        }
    </script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
    </style>
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
<div class="layui-form">
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
        </div>
    </div>
    <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:90px;">代理人姓名：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <input type="text" class="scinput" id="cardname" name="cardname" value="<%=request.getParameter("cardname")==null?"":request.getParameter("cardname")%>" />
    </div>

    <div class="layui-inline">
        <div class="layui-input-inline">
            <div style="float:left; height:25px;background:url('<s:text
                    name='sundyn.total.pic.query'/>') center no-repeat;margin-left:10px;" onclick="totalProxyDeal()">
                <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25"/>
            </div>
            <div style="float:left;margin-left:10px;"><s:if test="list.size==0"><a href="javascript:noData('this');"></s:if><s:else>
                <a href="#" onclick="totalProxyDeal(true, '<%=request.getParameter("sort")%>')"></s:else>
                <img src="<s:text name='sundyn.total.pic.excel'/>" /></a>
            </div>
        </div>
    </div>

    <div style="width:100%;margin:0 auto;">
        <table width="100%" cellpadding="0" cellspacing="0" class="tablelist" id="table123">
            <tr>
                <td rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1">代理人 / 识别码</td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="ticketcount">取号量<img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="servicecount">业务量<img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="cancelcount">弃号量<img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="waittime">平均等待时长<img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                    class="px13_1" sort="servicetime">平均办理时长<img src="../images/nor.png" /></td>
                <td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg"
                    class="px13_2"><s:text name="sundyn.column.content"/></td>
                <td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg"
                    class="px13_3"><s:text name="sundyn.column.nocontent"/></td>
                <td rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1" sort="totalunkey"><s:text
                        name="sundyn.column.noappries"/><img src="../images/nor.png" /></td>
                <c:if test="${k7 == true}">
                    <td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg"
                        class="px13_1"><s:text name="sundyn.column.appriesNum"/></td>
                </c:if>
                <td rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1" sort="totalkey">评价<s:text
                        name="sundyn.column.sum"/><img src="../images/nor.png" /></td>
                <td rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1" sort="keymyl"><s:text
                        name="sundyn.column.contentRate"/><img src="../images/nor.png" />
                    <span id="lblcontentRate" class="layui-badge layui-bg-orange" style="text-indent:0px;"> ? </span></td>
                <td rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1" sort="myd"><s:text
                        name="sundyn.column.contentDegree"/><img src="../images/nor.png" /><span id="lblContentDegree" class="layui-badge layui-bg-orange" style="text-indent:0px;"> ? </span>
                </td>
            </tr>
            <tr>
                <c:forEach items="${mls}" var="key">
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1" style="line-height: 30px;">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1" style="line-height: 30px;" sort="key${key.keyNo}">${key.name}<img src="../images/nor.png" /></td>
                    </s:else>
                </c:forEach>
                <td width="5%" align="center" valign="middle" bgcolor="fef9f3"
                    class="px13_1" style="line-height: 30px;" sort="keymy"><s:text name="sundyn.column.contentTotal"/><img src="../images/nor.png" /></td>
                <c:forEach items="${bmls}" var="key">
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1" style="line-height: 30px;">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" align="center" valign="middle" bgcolor="fef9f3"
                            class="px13_1" style="line-height: 30px;" sort="key${key.keyNo}">${key.name}<img src="../images/nor.png" /></td>
                    </s:else>
                </c:forEach>
                <td width="7%" align="center" valign="middle" bgcolor="fef9f3"
                    class="px13_1" style="line-height: 30px;" sort="keybmy"><s:text name="sundyn.column.nocontentTotal"/><img src="../images/nor.png" /></td>
            </tr>
            <c:forEach items="${list}" var="total" varStatus="status">
                <tr>
                    <td align="center" valign="middle">
                            ${total.cardname} / ${total.cardid}
                    </td>
                    <td align="center" valign="middle">${total.ticketcount}</td>
                    <td align="center" valign="middle">${total.servercount}</td>
                    <td align="center" valign="middle">${total.cancelcount}</td>
                    <td align="center" valign="middle">${total.waittimeavg.replace("0天00时00分","").replace("0天00时","").replace("0天","")}</td>
                    <td align="center" valign="middle">${total.servicetimeavg.replace("0天00时00分","").replace("0天00时","").replace("0天","")}</td>
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
                    <td align="center" valign="middle">${total.totalunkey}</td>
                    <td align="center" valign="middle">${total.totalkey}</td>
                    <td align="center" valign="middle">${total.mrate}%</td>
                    <td align="center" valign="middle">${total.num}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <c:if test="${pager.rowsCount == 0}">
        <div class="sundyn_rows">
            <s:text name="sundyn.system.checkM7Info.noRecord" />
        </div>
    </c:if>
    <c:if test="${pager.rowsCount > 0}">
        <div class="sundyn_rows" id="pp">
        </div>
    </c:if>
    <!-- 统计信息开始 -->
    <c:if test="${pager.rowsCount>0 && 1==2}">
    <table id="table1" width="100%" height="172" border="0" cellpadding="0" cellspacing="0" class="px12"
           style="border-top:1px solid #bad6ec;border-right:1px solid #bad6ec;margin:0 auto;">
        <tr>
            <td colspan="7" align="center" valign="middle" background="images/03_05_11.jpg" class="px12"><s:text
                    name="sundyn.total.toatlInfo.person"/></td>
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
    </c:if>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
</body>
<script type="text/javascript">
    var t = "";
    <c:forEach items="${mls}" var="key">
    t += "${key.ext2}"+ "*权值 + ";
    </c:forEach>
    <c:forEach items="${bmls}" var="key">
    t += "${key.ext2}"+ "*权值 + ";
    </c:forEach>
    t = t.substr(0, t.length-2);
    t += " <hr />业务量 * 10";
        <%--<c:forEach items="${mls}" var="key">
        t += "${key.ext2}"+ " + ";
        </c:forEach>
        <c:forEach items="${bmls}" var="key">
        t += "${key.ext2}"+ " + ";
        </c:forEach>
        t = t.substr(0, t.length-2);
        t += ")*10";--%>

    var t2 = "";
    <c:forEach items="${mls}" var="key">
    t2 += "${key.ext2}"+ " + ";
    </c:forEach>
    t2 = t2.substr(0, t2.length-2);
    t2 += " <hr />";
    <c:forEach items="${mls}" var="key">
    t2 += "${key.ext2}"+ " + ";
    </c:forEach>
    <c:forEach items="${bmls}" var="key">
    t2 += "${key.ext2}"+ " + ";
    </c:forEach>
    t2 = t2.substr(0, t2.length-2);
    t2 = "满意合计 <hr /> 业务量"

    initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()},${pager.getPageSize()});

    layui.use('form', function(){
        var form = layui.form;
        var tip1 = null, tip2 = null;
        $('#lblContentDegree').click(function(event) {
            event.stopPropagation();
            if(null == tip1){
                tip1 = layer.tips('计算公式：<br />' + t, '#lblContentDegree', {
                    tips: 3,
                    time: 0
                });
            }
            else
            {
                layer.closeAll('tips'); //关闭所有的tips层
                tip1 = null;
            }
        });
        $('#lblcontentRate').click(function(event) {
            event.stopPropagation();
            if(null == tip2){
                tip2 = layer.tips('计算公式：<br />' + t2, '#lblcontentRate', {
                    tips: 3,
                    time: 0
                });
            }
            else
            {
                layer.closeAll('tips'); //关闭所有的tips层
                tip2 = null;
            }
        });
    });
</script>
</html>