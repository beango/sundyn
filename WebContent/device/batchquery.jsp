<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><s:text name='sundyn.notice.list' /></title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layer/layer.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
	</head>
	<body>
		<div class="layui-main">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
                <tr>
                    <td style="border-color:#FFFFFF;" align="left">
                        <a class=" layui-btn" href="#" onclick="batchToAdd('','添加')">添加</a>
                    </td>
                </tr>
            </table>
            <table class="layui-table">
                <tr>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">批次号</td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">批次名</td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">设备数</td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">日期</td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"></td>
                </tr>
                <c:if test="${pager==null}"><s:text name='sundyn.nodate' /> </c:if>
                <c:if test="${pager!=null}">
                    <c:forEach items="${pager.pageList}" var="data" varStatus="index">
                        <tr>
                            <td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
                                    ${index.index+1}
                            </td>
                            <td style="text-align: left;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.batchid}
                            </td>
                            <td style="text-align: left;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.batchname}
                            </td>
                            <td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.devices}
                            </td>
                            <td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.batchdate}
                            </td>
                            <td style="text-align: center;">
                                <a href="javascript:batchToAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><s:text name='sundyn.modifyOrupdate' /></a>
                                <a href="javascript:batchDelete('${data.id}');"><s:text name='sundyn.del' /></a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </table>
            <div id="pp"></div>
		</div>
	</body>
    <script type="text/javascript">
        initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
    </script>
</html>
