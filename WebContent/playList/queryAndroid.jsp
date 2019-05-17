<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><s:text name='zx.title' /></title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
	</head>
	<body>
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <c:forEach items="${navbar_menuname}" var="menu">
                <li><a href="#">${menu.name}</a></li>
            </c:forEach>
            <c:if test="${deptObj!=null}"><li>${deptObj["name"]}</li></c:if>
        </ul>
    </div>
		<div class="layui-form">
            <div>
                <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
                    <tr>
                        <td style="border-color:#FFFFFF;" align="left">
                            <input name="keyword" id="keyword" class="input_comm" value="<%=request.getParameter("keyword")==null?"":request.getParameter("keyword")%>" />
                            <img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="playListQueryAjaxAndroid()" class="hand"/>
                            <img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="playListAddDialogAndroid('<s:text name="sundyn.playList.addTitle" />')" class="hand"/>
                        </td>
                    </tr>
                </table>
            </div>
            <table class="layui-table">
                <tr>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.playListId' /></td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.playListName' /></td>
                    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
                </tr>
                <c:forEach items="${pager.pageList}" var="playList">
                    <tr>
                        <td style="text-align: center;">
                                ${playList.playListId}
                        </td>
                        <td style="text-align: center;">
                                ${playList.playListName}
                        </td>
                        <td style="text-align: center;">
                            <a href="javascript:playListEditDialogAndroid(${playList.playListId},'<s:text name='sundyn.playList.editTitle' />');"><s:text name='sundyn.playList.editTitle' /></a><s:text name="sundyn.separator"/>
                            <a href="#" onclick="javascript:playListDelAndroid(${playList.playListId});"><s:text name='sundyn.del' /></a><s:text name="sundyn.separator"/>
                            <%--<a href="javascript:playListUpdateDialogAndroid(${playList.playListId},'<s:text name='sudnyn.playList.updateM7' />');"><s:text name='sudnyn.playList.updateM7' /></a><s:text name="sundyn.separator"/>--%>
                            <a href="javascript:playListConfigDialogAndroid(${playList.playListId},'<s:text name='sudnyn.playList.editConfigFile' />');"><s:text name="sudnyn.playList.editConfigFile"></s:text></a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <div id="pp"></div>
		</div>
	</body>
    <script type="text/javascript">
        layui.use('layer', function() {});
        initPager(${pager.getRowsCount()}, ${pager.getCurrentPage()},${pager.getPageSize()});
    </script>
</html>
