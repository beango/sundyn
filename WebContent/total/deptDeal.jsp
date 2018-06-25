<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ page import="java.util.List,java.util.Map"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title><s:text name='sundyn.title' /></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="js/wz_jsgraphics.js"></script>
    <script type="text/javascript" src="js/pie.js"></script>
    <script type="text/javascript" src="js/Pie3D.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="lib/lhgdialog/lhgdialog.js"></script>
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
            <label class="layui-form-label" style="width:100px;">请选择部门：</label>
            <div class="layui-form-mid layui-word-aux">
            </div>
        </div>
        <div class="layui-inline">
            <div class="layui-input-inline">
                <a href="#" onclick="totalDeptDeal()">
                    <img src="<s:text name='sundyn.total.pic.query'/>" />
                </a>
                <a href="#" onclick="totalDeptDeal(true)">
                    <img src="<s:text name='sundyn.total.pic.excel'/>" />
                </a>
            </div>
        </div>
		<div style="width: 100%; margin: 0 auto;">
			<table width="100%" class="layui-table" id="table123">
				<tr>
					<td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.deptName" /></td>
					<td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.star" /></td>
					<td colspan="${fn:length(mls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_2"><s:text name="sundyn.column.content" /></td>
					<td colspan="${fn:length(bmls)+1}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_3"><s:text name="sundyn.column.nocontent" /></td>
					<td width="10%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.noappries" /></td><%--未评价--%>
					<c:if test="${k7 == true}">
						<td width="7%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.appriesNum" /></td><%--合计--%>
					</c:if>

					<td width="5%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.sum" /></td>
					<td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.contentRate" /></td>
					<td width="8%" rowspan="2" align="center" valign="middle" background="images/03_02_07.jpg" class="px13_1"><s:text name="sundyn.column.contentDegree" /></td>
				</tr>
				<tr>
					<c:forEach items="${mls}" var="key">
						<s:if test='getText("sundyn.language") eq "en"'>
							<td width="5%" height="42" align="center" valign="middle" bgcolor="fef9f3" class="px13_1">${key.ext2}</td>
						</s:if>
						<s:else>
							<td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1">${key.name}</td>
						</s:else>
					</c:forEach>
					<td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text name="sundyn.column.contentTotal" /></td>
					<c:forEach items="${bmls}" var="key">
						<s:if test='getText("sundyn.language") eq "en"'>
							<td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1">${key.ext2}</td>
						</s:if>
						<s:else>
							<td width="5%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1">${key.name}</td>
						</s:else>
					</c:forEach>
					<td width="7%" align="center" valign="middle" bgcolor="fef9f3" class="px13_1"><s:text name="sundyn.column.nocontentTotal" /></td>
				</tr>
				<c:forEach items="${list}" var="total">
					<tr style="height: 28px;">
						<td align="center" valign="middle">${total.name}</td>
						<td align="center" valign="middle">${total.star}</td>
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
							<!-- 工作量，利用该数据形成对比图 -->
							<td align="center" valign="middle">
								${total.key0+total.key1+total.key3+total.key4+total.key5+total.key6}
							</td>
						</c:if>
						<td align="center" valign="middle">
							${total.msum+total.bmsum+total.key6}
						</td>
						<td align="center" valign="middle">${total.mrate}%</td>
						<td align="center" valign="middle">${total.num}</td>
					</tr>
				</c:forEach>
			</table>
		</div>
        <c:if test="${list.size() == 0}">
            <div class="sundyn_rows">
                <s:text name="sundyn.system.checkM7Info.noRecord" />
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
        <c:if test="${list.size() > 0}">
			<table id="table1" class="layui-table">
				<tr>
					<td colspan="7" align="center" valign="middle" background="images/03_05_11.jpg" class="px12"><s:text name="sundyn.total.toatlInfo" /></td>
				</tr>
				<c:forEach items="${mls}" var="key" varStatus="index">
					<tr>
						<c:if test="${index.index == 0}">
							<td width="15%" rowspan="${fn:length(mls)}" align="center" valign="middle" bgcolor="fef9f3"><s:text name="sundyn.column.content" /></td>
						</c:if>
						<td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
						<td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
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
							<td width="15%" rowspan="${fn:length(bmls)}" align="center" valign="middle" bgcolor="fef9f3" class="px13_2"><s:text name="sundyn.column.nocontent" /></td>
						</c:if>
						<td width="19%" align="center" valign="middle" bgcolor="fffcf9">${key.name}</td>
						<td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.keyr[key.keyNo]}%; background-color:#FF0000;">&nbsp;</div></td>
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
						<td width="15%" rowspan="${fn:length(bmls)}" align="center"
							valign="middle" bgcolor="fef9f3" class="px13_2"><s:text name="sundyn.column.noappries" /></td>
						<td width="19%" align="center" valign="middle" bgcolor="fffcf9">${totalMap.k7Name}</td>
						<td width="28%" align="left" valign="middle"><div style="height:10px; width:${totalMap.kr6}%; background-color:#FF0000;">&nbsp;</div></td>
						<td width="19%" align="center" valign="middle">${totalMap.key6}</td>
						<td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.key6}</td>
						<td width="6%" align="center" valign="middle">${totalMap.kr6}%</td>
						<td width="13%" rowspan="${fn:length(bmls)}" align="center" valign="middle">${totalMap.kr6}%</td>
					</tr>
				</c:if>
			</table>
        </c:if>
		</div>
</body>
<script type="text/javascript">
    layui.use('form', function(){
        var form = layui.form;
        var deptpath = '<%=request.getParameter("deptpath")==null?"":request.getParameter("deptpath")%>'.split(",");
        renderchild(form, -1, -1, deptpath,"dept");
    });
</script>
</html>