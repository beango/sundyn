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
            <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border: 0px;">
                <tr>
                    <td style="border-color:#FFFFFF;width:120px;" align="center"><input name="keyword" id="keyword"
                                                                                        value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>"
                                                                                        class="input_comm"/></td>
                    <td style="border-color:#FFFFFF;width:60px;" align="left"><img
                            src="<s:text name='sundyn.pic.query' />" width="55" height="25"
                            onclick="lowerManagerQueryAjax()" class="hand"/></td>
                    <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />"
                                                                        width="63" height="25"
                                                                        onclick="lowerManagerAddDialog('<s:text
                                                                                name="sundyn.user.addUser"/>')"
                                                                        class="hand"/></td>
                </tr>
            </table>
        </div>
        <table class="layui-table">
            <tr>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text
                        name='sundyn.column.userName'/></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text
                        name='sundyn.column.realName'/></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text
                        name='sundyn.column.deptName'/></td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">是否警员</td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">状态</td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">账号过期</td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">密码过期</td>
                <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text
                        name='sundyn.column.operation'/></td>
            </tr>
            <c:forEach items="${pager.pageList}" var="manager">
                <tr>
                    <td style="text-align: center;">
                            ${manager.name} / ${manager.localuser?"本地用户":"异地用户"}
                    </td>
                    <td style="text-align: center;">
                            ${manager.realname}
                    </td>
                    <td style="text-align: center;">
                            ${manager.deptname}
                    </td>
                    <td style="text-align: center;">
                            ${manager.jyflag==0?"非警员":("警员")} ${manager.jyno}
                    </td>
                    <td style="text-align: center;">
                        <c:if test="${manager.status==null || manager.status==1}"><font
                                style="color:green;">启用</font></c:if>
                        <c:if test="${manager.status==0}"><font style="color:red;">禁用</font></c:if>
                        <font style="color:red;">${manager.checkdigited==1?"(数据被篡改)":""}</font>
                    </td>
                    <td style="text-align: center;">
                        <fmt:formatDate value="${manager.uexpired}" type="both" pattern="yyyy-MM-dd"/>
                        <c:if test="${manager.uexpiredday<0}"><font style="color:red;">
                            (过期${manager.uexpiredday*-1}天)</font></c:if>
                        <c:if test="${manager.uexpiredday>=0 && manager.uexpiredday<=7}"><font style="color:#e8a32a;">
                            (还剩${manager.uexpiredday}天过期)</font></c:if>
                    </td>
                    <td style="text-align: center;">
                        <fmt:formatDate value="${manager.pwdexpired}" type="both" pattern="yyyy-MM-dd"/>
                        <c:if test="${manager.pwdexpiredday<0}"><font style="color:red;">
                            (过期${manager.pwdexpiredday*-1}天)</font></c:if>
                        <c:if test="${manager.pwdexpiredday>=0 && manager.pwdexpiredday<=7}"><font style="color:#e8a32a;">
                            (还剩${manager.pwdexpiredday}天过期)</font></c:if>
                    </td>
                    <td style="text-align: center;">
                        <c:if test="${manager.checkdigited==0 || manager.checkdigited == null}">
                        <c:if test="${manager.status==0}">
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                               onclick="event.stopPropagation();managerEditStatus(${manager.id},'启用');"><i
                                    class="layui-icon layui-icon-edit"></i>启用</a>
                        </c:if>

                        <c:if test="${manager.status==null || manager.status==1}">
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="edit"
                               onclick="event.stopPropagation();managerEditStatus(${manager.id},'禁用');"><i
                                    class="layui-icon layui-icon-delete"></i>禁用</a>
                        </c:if>
                        <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                           onclick="event.stopPropagation();managerEditDialog(${manager.id},'<s:text
                                   name="sundyn.user.editUser"/>');"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"
                           onclick="event.stopPropagation();managerDel(${manager.id});"><i
                                class="layui-icon layui-icon-delete"></i>删除</a>
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
    layui.use('layer', function() {
    });
</script>
</html>
