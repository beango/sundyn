<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="now" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title><c:forEach items="${navbar_menuname}" var="menu" varStatus="index">${menu.name}${index.index==0?"->":""}</c:forEach></title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">

    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
</head>
<body>

<div id="layui-form">
    <div class="place">
        <span><s:text name="main.placetitle" /></span>
        <ul class="placeul">
            <c:forEach items="${navbar_menuname}" var="menu">
                <li><a href="#">${menu.name}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div style="width:99%;">
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px;">
                <tr>
                    <td style="border-color:#FFFFFF;width:120px;" align="center">
                        <input name="keyword" id="keyword" value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>" class="input_comm"/></td>
                    <td style="border-color:#FFFFFF;width:60px;" align="left">
                        <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="lowerManagerQueryAjax()" value="<s:text name="main.query" />">
                    </td>
                    <td style="border-color:#FFFFFF;" align="left">
                        <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="lowerManagerAddDialog('<s:text name="sundyn.user.addUser"/>')" value="<s:text name="main.add" />">
                    </td>
                </tr>
            </table>
        </div>
        <table class="layui-table">
            <tr>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.userName'/></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.realName'/></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.deptName'/></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name="sundyn.column.isjy" /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name="sundyn.column.status" /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name="sundyn.column.accountexpried" /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name="sundyn.column.pwdexpried" /></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg"><s:text name='sundyn.column.operation'/></td>
            </tr>
            <c:forEach items="${pager.pageList}" var="manager">
                <tr>
                    <td style="text-align: center;">
                            ${manager.name}<c:if test="${manager.localuser}"> / <s:text name="manage.localuser.local" /></c:if><c:if test="${!manager.localuser}"> / <s:text name="manage.localuser.notlocal" /></c:if>
                    </td>
                    <td style="text-align: center;">
                            ${manager.realname}
                    </td>
                    <td style="text-align: center;">
                            ${manager.deptname}
                    </td>
                    <td style="text-align: center;">
                        <c:if test="${manager.jyflag==0}"><s:text name="manage.jy.no"/></c:if>
                        <c:if test="${manager.jyflag==1}"><s:text name="manage.jy.yes"/> / ${manager.jyno}</c:if>
                    </td>
                    <td style="text-align: center;">
                        <c:if test="${manager.status==null || manager.status==1}"><font
                                style="color:green;"><s:text name="main.radio.enable" /></font></c:if>
                        <c:if test="${manager.status==0}"><font style="color:red;"><s:text name="main.radio.disable" /></font></c:if>
                        <c:if test="${manager.checkdigited==1}"><font style="color:red;"><s:text name="main.data.noverify" /></font></c:if>
                    </td>
                    <td style="text-align: center;">
                        <fmt:formatDate value="${manager.uexpired}" type="both" pattern="yyyy-MM-dd"/>
                        <c:if test="${manager.uexpiredday<0}"><font style="color:red;">
                            (<s:text name="manage.expireddays"/>${manager.uexpiredday*-1})</font></c:if>
                        <c:if test="${manager.uexpiredday>=0 && manager.uexpiredday<=7}"><font style="color:#e8a32a;">
                            (${manager.uexpiredday}<s:text name="manage.noexpireddays"/>)</font></c:if>
                    </td>
                    <td style="text-align: center;">
                        <fmt:formatDate value="${manager.pwdexpired}" type="both" pattern="yyyy-MM-dd"/>
                        <c:if test="${manager.pwdexpiredday<0}"><font style="color:red;">
                            (<s:text name="manage.expireddays"/>${manager.pwdexpiredday*-1})</font></c:if>
                        <c:if test="${manager.pwdexpiredday>=0 && manager.pwdexpiredday<=7}"><font style="color:#e8a32a;">
                            (${manager.pwdexpiredday}<s:text name="manage.noexpireddays"/>)</font></c:if>
                    </td>
                    <td style="text-align: center;">
                        <c:if test="${manager.checkdigited==0 || manager.checkdigited == null}">
                        <c:if test="${manager.status==0}">
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                               onclick="event.stopPropagation();managerEditStatus(${manager.id}, 1);"><i
                                    class="layui-icon layui-icon-edit"></i><s:text name="main.radio.enable"/></a>
                        </c:if>
                        <c:if test="${manager.status==null || manager.status==1}">
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="edit"
                               onclick="event.stopPropagation();managerEditStatus(${manager.id}, 0);"><i
                                    class="layui-icon layui-icon-delete"></i><s:text name="main.radio.disable"/></a>
                        </c:if>
                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                           onclick="event.stopPropagation();managerEditDialog(${manager.id},'<s:text
                                   name="sundyn.user.editUser"/>');"><i class="layui-icon layui-icon-edit"></i><s:text name="main.edit"/></a>
                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"
                           onclick="event.stopPropagation();managerDel(${manager.id});"><i
                                class="layui-icon layui-icon-delete"></i><s:text name="main.delete"/></a>
                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                           onclick="event.stopPropagation();managerReset(${manager.id});"><i
                                class="layui-icon layui-icon-edit"></i><s:text
                                name='sundyn.employee.resetPassword'/></a>
                        <a href="#" onclick="managerReset(${manager.id});"></a>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div id="pp"></div>
    </div>
    <input type="hidden" name="managerId" id="managerId" value="${managerId}"/>
</div>
</body>
<script type="text/javascript">
    initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>, <%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
    layui.use('layer');

    function lowerManagerQueryAjax() {
        var keyword = document.getElementById("keyword").value;
        var myURL = parseURL(location.href);
        var _newUrl = replaceUrlParams(myURL, { name: keyword });
        if(parent.qc && parent.qc.main.mainTabs){
            var currTab = parent.qc.main.mainTabs.tabs('getSelected');
            parent.qc.main.mainTabs.tabs('update', {
                tab : currTab,
                options : {
                    content: '<iframe class="iframe-fluid" src="'+_newUrl+'"></iframe>'
                }
            });
        }
        else{
            location.href = _newUrl;
        }
    }
    // 添加低等级用户对话框
    function lowerManagerAddDialog(title) {
        var dia = new dialog();
        dia.iframe("managerEditDialog.action",{title:title, w:'700px', h:'600px'});
    }
    // 重置密码
    function managerEditStatus(id, type) {
        var confirmstr="<s:text name="manage.action.status.disable" />"
        if (type == 1)//enable
            confirmstr = "<s:text name="manage.action.status.enable" />";
        lconfirm(confirmstr, function(){
            dojo.xhrPost({url:"managerResetStatus.action", content:{id:id}, load:function (resp) {
                    if(resp.trim()==''){
                        succ('<s:text name="main.save.succ" />', function () {
                            refreshTab();
                        });
                    }
                    else{
                        error(resp);
                    }
                }});
        });
    }
    // 修改用户对话框
    function managerEditDialog(data, title) {
        var dia = new dialog();
        dia.iframe("managerEditDialog.action?id="+data, {title: title, w:'700px', h:'600px'});
    }

    function managerDel(data) {
        var managerId=document.getElementById("managerId").value;
        if(data==1){
            lalert("<s:text name="manage.action.delete.root"/>");
        }else if(managerId==data){
            lalert("<s:text name="manage.action.delete.notdelself"/>");
        }else{
            lconfirm("<s:text name="main.delete.confirm"/>",function(){
                dojo.xhrPost({url:"managerDel.action", content:{id:data}, load:function (resp, ioArgs) {
                        succ('<s:text name="main.delete.succ"/>', function(){
                            lowerManagerQueryAjax();
                        });
                    }});
            });
        }
    }

    // 重置密码
    function managerReset(data) {
        lconfirm("<s:text name="manage.action.reset.password"/>",function(){
            dojo.xhrPost({url:"managerReset.action", content:{id:data}, load:function (resp, ioArgs) {
                    if(resp.trim()==''){
                        succ('<s:text name="manage.action.reset.password.succ"/>', function(){
                            closeDialog();
                        });
                    }
                    else{
                        error('<s:text name="manage.action.reset.password.fail"/>');
                    }
                }});
        });
    }
</script>
</html>
