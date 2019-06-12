<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title><s:text name="sundyn.query.weburl"></s:text></title>

    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
    <link rel="stylesheet" href="css/dialog.css" type="text/css" />
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript">global_language='${locale}'</script>
    <script type="text/javascript" src="js/jquery.js" ></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script src="${ctx}/js/application.js"></script>
</head>
<body style="width:100%;">
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
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="text" name="txtTitle" id="txtTitle" value="<%=request.getParameter("key_title")==null?"":request.getParameter("key_title")%>" class="input_comm" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query()" value="<s:text name="main.query" />">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="weburToAdd(null,'<s:text name="sundyn.add.weburl" />');" value="<s:text name="main.add" />">
        </div>
    </div>
    <input type="hidden" name="managerId" id="managerId" value="${managerId}" />
    <div style="width: 100%;">
        <table id="tbl" width="100%" class="layui-table">
            <thead>
            <tr>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.weburl.name' /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${weburls}" var="webUrl" varStatus="index">
                <tr>
                    <td style="text-align: center;">${index.index+1}</td>
                    <td style="text-align: center;">
                        <div style="width: 170px; height: 20px; text-align: center; text-overflow: ellipsis; overflow: hidden;">${webUrl.name}</div>
                    </td>
                    <td style="text-align: center;">
                        <a href="javascript:weburlToUpate('${webUrl.id}','<s:text name="sundyn.weburl.update" />');"><s:text name='sundyn.modifyOrupdate' /></a>
                        <a href="javascript:weburlDelete('${webUrl.id}');"><s:text name='sundyn.del' /></a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div id="pp">
        </div>
    </div>
</div>
<div id="dialog-window" style="display: none;"></div>
<script type="text/javascript">
    layui.use('layer', function() {});

    initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    function query(){
        refreshTab({key_title: $("#txtTitle").val()});
    }

    // weburl 信息查询添加框
    function weburToAdd(data, title) {
        new dialog().iframe("weburlToAdd.action", {title:title, full:true,w:'100%',h:'100%'});
    }

    // weburl 信息查询更新框
    function weburlToUpate(data, title) {
        new dialog().iframe("weburlToUpdate.action?id=" + data, {title:title, full:true,w:'100%',h:'100%'});
    }

    // 删除 信息查询
    function weburlDelete(data){
        if (confirm('<s:text name="main.delete.confirm" />')){
            dojo.xhrPost({url:"weburlDelete.action", content:{id:data}, load:function (resp, ioArgs) {
                    succ('<s:text name="main.delete.succ" />', function(){
                        refreshTab();
                    });
                }});
        }
    }
</script>
</body>
</html>
