<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>用户管理</title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layer/layer.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>

</head>
<body>
    <div id="layui-main">
			<input type="hidden" name="managerId" id="managerId" value="${managerId}" />
			<div style="width:99%; height:100%;" class="">
			    <div>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;" align="">
				       <tr>
				         <td style="border-color:#FFFFFF;width:120px;" align="center"><input name="keyword" id="keyword" value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>" class="input_comm" /></td>
				         <td style="border-color:#FFFFFF;width:60px;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerManagerQueryAjax()" class="hand"/></td>
				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="lowerManagerAddDialog()" class="hand"/></td>
				      </tr>
				     </table>
			    </div>
				<table class="layui-table">
				  <tr>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.userName' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.realName' /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
				    </tr>
				   	<c:forEach items="${pager.pageList}" var="manager">
						<tr>

							<td style="text-align: center;">
								${manager.name}
							</td>
							<td style="text-align: center;">
								${manager.realname}
							</td>
							<td style="text-align: center;">
								<a href="javascript:managerEditDialog(${manager.id});"><s:text name='sundyn.modify' /></a>
								<a href="javascript:managerDel(${manager.id});"><s:text name='sundyn.del' /></a>
								<a href="javascript:managerReset(${manager.id});"><s:text name='sundyn.employee.resetPassword' /></a>
							</td>
						</tr>
					</c:forEach>
				</table>
                <div id="pp"></div>
		    </div>
    </div>
    <div id="dialog" style="width: 600px; display: none;"></div>
</body>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
</script>
</html>
