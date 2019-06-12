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
        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
	</head>
	<body>
    <div class="place">
        <span><s:text name="main.placetitle" /></span>
        <ul class="placeul">
            <c:forEach items="${navbar_menuname}" var="menu">
                <li><a href="#">${menu.name}</a></li>
            </c:forEach>
            <c:if test="${deptObj!=null}"><li>${deptObj["name"]}</li></c:if>
        </ul>
    </div>
    <div class="layui-form">
        <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
            <tr>
                <td style="border-color:#FFFFFF;" align="left">
                    <input name="keyword" id="keyword" class="input_comm" value="<%=request.getParameter("keyword")==null?"":request.getParameter("keyword")%>"/>
                    <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="playQueryAjax()" value="<s:text name="main.query" />">
                    <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="playAddDialog('<s:text name="sundyn.play.addTitle" />')" value="<s:text name="main.add" />">
                </td>
            </tr>
        </table>
    </div>
    <table class="layui-table">
        <tr>
            <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.playList' /></td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.playName' /></td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.playType' /></td>
            <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.operation' /></td>
        </tr>
        <c:forEach items="${pager.pageList}" var="play">
            <tr>
                <td style="text-align: center;">${play.playIndex}
                </td>
                <td style="text-align: center;">${play.playName}
                </td>
                <td style="text-align: center;">
                    <c:if test="${play.playType=='img'}"><s:text name='sundyn.play.pic' /></c:if>
                    <c:if test="${play.playType=='text'}"><s:text name='sundyn.play.txt' /></c:if>
                    <c:if test="${play.playType=='video'}"><s:text name='sundyn.play.vio' /></c:if>
                    <c:if test="${play.playType=='doc'}"><s:text name='sundyn.play.doc' /></c:if>
                </td>
                <td style="text-align: center;">
                    <a href="javascript:playEditDialog(${play.playId},'<s:text name='sundyn.play.editTitle' />');"><s:text name='sundyn.modify' /></a>
                    <a href="javascript:playDel(${play.playId});"><s:text name='sundyn.del' /></a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div id="pp"></div>
	</body>
    <script type="text/javascript">
        layui.use('layer', function() {});
        initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
        // 播放查詢
        function playQueryAjax() {
            var keyword = document.getElementById("keyword").value;
            refreshTab({keyword:keyword});
        }
        // 播放 添加对话框
        function playAddDialog(title) {
            new dialog().iframe("playAddDialog.action",{title:title});
        }

        // 播放修改对话框
        function playEditDialog(playId, title) {
            new dialog().iframe("playEditDialog.action?playId="+playId, {title: title});
        }

        // 播放删除
        function playDel(data) {
            if (confirm('<s:text name="main.delete.confirm" />')){
                dojo.xhrPost({url:"playDel.action", content:{playId:data}, load:function (resp, ioArgs) {
                        succ('<s:text name="main.delete.succ" />', function(){
                            refreshTab();
                        });
                    }});
            }
        }
    </script>
</html>
