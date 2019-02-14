<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>角色管理</title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
        <script type="text/javascript" src="js/jquery.blockUI.js?1"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layer/layer.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>

	</head>
	<body>
		<div id="man_zone">
            <div class="place">
                <span>位置：</span>
                <ul class="placeul">
                    <c:forEach items="${navbar_menuname}" var="menu">
                        <li><a href="#">${menu.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
			<div style="width:99%;" class="">
			    <div>
				    <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
				       <tr>
				         <td style="border-color:#FFFFFF;width:120px;" align="center">	<input name="keyword" id="keyword" class="input_comm"  value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>"/></td>
				         <td style="border-color:#FFFFFF;width:60px;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="lowerPowerQueryAjax()" class="hand"/></td>
				         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="powerAddDialog('<s:text name="sundyn.role.addRole" />')" class="hand"/></td>
				      </tr>
				     </table>
			    </div>
                <table class="layui-table">
				  <tr>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.roleName" /></td>
				     <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1" style="display: none;"><s:text name="sundyn.column.systemSetup" /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1" style="display: none;"><s:text name="sundyn.column.baseSetup" /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1" style="display: none;"><s:text name="sundyn.column.dept" /></td>
				    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name="sundyn.column.operation" /></td>
				  </tr>
				    <c:forEach items="${pager.pageList}" var="power">
						<tr>
							<td style="text-align: center;">
								${power.name}
							</td>
							<td style="text-align: center;display: none;">
								<c:if test="${ power.baseSet==1    }">
									<s:text name='sundyn.yes' />
							    </c:if>
								<c:if test="${ power.baseSet==0   }">
									<s:text name='sundyn.no' />
							     </c:if>
							</td>
							<td style="text-align: center;display: none;">
								<c:if test="${power.dataManage ==  1}">
								   <s:text name='sundyn.yes' />
								</c:if>
								<c:if test="${power.dataManage ==  0}">
								   <s:text name='sundyn.no' /> 
								</c:if>
							</td>
							<td style="text-align: center;display: none;">
								${power.deptname}
							</td>
								<td style="text-align: center;">
                                    <a href="javascript:powerCopy(${power.id});">复制</a>
								<a href="javascript:powerEditDialog(${power.id},'<s:text name="sundyn.role.editRole" />');"><s:text name='sundyn.modify' /></a>
								<a href="javascript:powerDel(${power.id});"><s:text name='sundyn.del' /></a>
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
        initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?10:request.getParameter("pageSize")%>);
    </script>
</html>