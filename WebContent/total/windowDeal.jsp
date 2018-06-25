<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@page import="java.util.List,java.util.Map"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><s:text name='sundyn.title' /></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

    <script src="${ctx}/assets/javascripts/vendor/jquery-2.1.3.min.js?1440992355"></script>
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="lib/lhgdialog/lhgdialog.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
</head>
<body>
	<div class="layui-form">
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
        <input type="hidden" id="deptId" name="deptId" value="${deptId}"/>
        <div class="layui-select-cus layui-inline">
            <label class="layui-form-label" style="width:100px;">请选择窗口：</label>
            <div class="layui-form-mid layui-word-aux">
            </div>
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <div style="float: left; height: 25px; margin-left:10px; border-color:#FFFFFF;" onclick="totalWindowDeal()">
                    <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" />
                </div>
                <div style="float:left; margin-left:10px;">
                    <s:if test="list.size==0">
                    <a href="javascript:noData('this');">
                        </s:if>
                        <s:else>
                        <a href="#" onclick="totalWindowDeal(true)">
                            </s:else>
                            <img src="<s:text name='sundyn.total.pic.excel'/>" />
                        </a>
                </div>
            </div>
        </div>

        <table width="100%" cellpadding="0" cellspacing="0" class="layui-table">
            <tr>
                <td width="8%" rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text
                        name="sundyn.column.windowName" /></td>
                <td width="7%" rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text
                        name="sundyn.column.star" /></td>
                <td colspan="${fn:length(mls)+1}" align="center" valign="middle"
                    background="images/table_bg_03.jpg" class="px13_2"><s:text
                        name="sundyn.column.content" /></td>
                <td colspan="${fn:length(bmls)+1}" align="center" valign="middle"
                    background="images/table_bg_03.jpg" class="px13_2"><s:text
                        name="sundyn.column.nocontent" /></td>
                <td width="7%" rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text
                        name="sundyn.column.noappries" /></td>
                <c:if test="${k7 == true}">
                    <td width="7%" rowspan="2" align="center" valign="middle"
                        background="images/03_02_07.jpg" class="px13_1"><s:text
                            name="sundyn.column.appriesNum" /></td>
                </c:if>
                <td width="5%" rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text
                        name="sundyn.column.sum" /></td>
                <td width="8%" rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text
                        name="sundyn.column.contentRate" /></td>

                <td width="8%" rowspan="2" align="center" valign="middle"
                    background="images/03_02_07.jpg" class="px13_1"><s:text
                        name="sundyn.column.contentDegree" /></td>
            </tr>
            <tr>
                <c:forEach items="${mls}" var="key">
                    <%--				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>--%>
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" height="47" align="center" valign="middle"
                            bgcolor="fef9f3" class="px13_1">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" height="47" align="center" valign="middle"
                            bgcolor="fef9f3" class="px13_1">${key.name}</td>
                    </s:else>
                </c:forEach>
                <td width="5%" align="center" valign="middle" bgcolor="fef9f3"
                    class="px13_1"><s:text name="sundyn.column.contentTotal" /></td>

                <c:forEach items="${bmls}" var="key">
                    <%--				    <td width="5%" height="47" align="center" valign="middle" bgcolor="fef9f3"  class="px13_1">${key.name}</td>--%>
                    <s:if test='getText("sundyn.language") eq "en"'>
                        <td width="5%" height="47" align="center" valign="middle"
                            bgcolor="fef9f3" class="px13_1">${key.ext2}</td>
                    </s:if>
                    <s:else>
                        <td width="5%" height="47" align="center" valign="middle"
                            bgcolor="fef9f3" class="px13_1">${key.name}</td>
                    </s:else>
                </c:forEach>
                <td width="7%" align="center" valign="middle" bgcolor="fef9f3"
                    class="px13_1"><s:text name="sundyn.column.nocontentTotal" /></td>
            </tr>
            <c:forEach items="${list}" var="total">
                <tr>
                    <td align="center" valign="middle">${total.windowname}</td>
                    <td align="center" valign="middle">${total.star }</td>
                    <c:forEach items="${total.km}" var="k">
                        <td align="center" valign="middle">${k}</td>
                    </c:forEach>
                    <td align="center" valign="middle">${total.msum}</td>
                    <c:forEach items="${total.kbm}" var="k">
                        <td align="center" valign="middle">${k}</td>
                    </c:forEach>
                    <td align="center" valign="middle">${total.bmsum}</td>
                    <td align="center" valign="middle">${total.key6}</td>
                    <c:if test="${k7 ==true}">
                        <td align="center" valign="middle">
                                ${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
                        </td>
                    </c:if>
                    <td align="center" valign="middle">
                            ${total.key0+total.key1+total.key2+total.key3+total.key4+total.key5+total.key6}
                    </td>
                    <td align="center" valign="middle">${total.mrate}%</td>
                    <td align="center" valign="middle">${total.num}</td>
                </tr>
            </c:forEach>
        </table>

        <c:if test="${list.size() == 0}">
            <div class="sundyn_rows">
                <s:text name="sundyn.system.checkM7Info.noRecord" />
            </div>
        </c:if>
        <c:if test="${list.size() > 0}">
		<!-- 统计信息开始 -->
		<table id="table1" width="100%" height="172" border="0"
			cellpadding="0" cellspacing="0" class="layui-table"
			style="border-top: 1px solid #bad6ec; border-right: 1px solid #bad6ec; margin: 0 auto;">
			<tr>
				<td colspan="7" align="center" valign="middle"
					background="images/03_05_11.jpg" class="px12"><s:text
						name="sundyn.total.toatlInfo.window" /></td>
			</tr>
			<c:forEach items="${mls}" var="key" varStatus="index">
				<tr>
					<c:if test="${index.index == 0}">
						<td width="15%" rowspan="${fn:length(mls)}" align="center"
							valign="middle" bgcolor="fef9f3" class="px13_2"><s:text
								name="sundyn.column.content" /></td>
					</c:if>
					<td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
					<td width="28%" align="left" valign="middle"><div
							style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
					<td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
					<c:if test="${index.index == 0}">
						<td width="13%" rowspan="${fn:length(mls)}" align="center"
							valign="middle">${totalMap.msum}</td>
					</c:if>
					<td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
					<c:if test="${index.index == 0}">
						<td width="13%" rowspan="${fn:length(mls)}" align="center"
							valign="middle">${totalMap.mrate}%</td>
					</c:if>
				</tr>
			</c:forEach>
			<c:forEach items="${bmls}" var="key" varStatus="index">
				<tr>
					<c:if test="${index.index == 0}">
						<td width="15%" rowspan="${fn:length(bmls)}" align="center"
							valign="middle" bgcolor="fef9f3" class="px13_2"><s:text
								name="sundyn.column.nocontent" /></td>
					</c:if>
					<td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
					<td width="28%" align="left" valign="middle"><div
							style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
					<td width="19%" align="center" valign="middle">${totalMap.key[key.keyNo]}</td>
					<c:if test="${index.index == 0}">
						<td width="13%" rowspan="${fn:length(bmls)}" align="center"
							valign="middle">${totalMap.bmsum}</td>
					</c:if>
					<td width="6%" align="center" valign="middle">${totalMap.keyr[key.keyNo]}%</td>
					<c:if test="${index.index == 0}">
						<td width="13%" rowspan="${fn:length(bmls)}" align="center"
							valign="middle">${totalMap.bmrate}%</td>
					</c:if>
				</tr>
			</c:forEach>
			<c:if test="${k7 == true}">
				<tr>
					<td width="15%" rowspan="${fn:length(bmls)}" align="center"
						valign="middle" bgcolor="fef9f3" class="px13_2"><s:text
							name="sundyn.column.noappries" /></td>
					<td width="19%" align="center" valign="middle" bgcolor="fffcf9">${totalMap.k7Name}</td>
					<td width="28%" align="left" valign="middle"><div
							style="height:10px; width:${totalMap.kr6}%; background-color:#FF0000;">&nbsp;</div></td>
					<td width="19%" align="center" valign="middle">${totalMap.key6}</td>
					<td width="13%" rowspan="${fn:length(bmls)}" align="center"
						valign="middle">${totalMap.key6}</td>
					<td width="6%" align="center" valign="middle">${totalMap.kr6}%</td>
					<td width="13%" rowspan="${fn:length(bmls)}" align="center"
						valign="middle">${totalMap.kr6}%</td>
				</tr>
			</c:if>
		</table>
        </c:if>
		<!-- 统计信息结束-->
	</div>
</body>
<script type="text/javascript">
    layui.use('form', function(){
        var form = layui.form;
        var deptpath = '<%=request.getParameter("deptpath")==null?"":request.getParameter("deptpath")%>'.split(",");
        renderchild(form, -1, -1, deptpath,'window');
    });
</script>
</html>