<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>blacklist</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
</head>
<body>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<script type="text/javascript">
    function query(){
        var idcard = $("#idcard").val();
        window.location.href = "blackQuery.action?idcard="+idcard + "&name=" + $("#name").val();
    }

    function vipAdd(id){
        var action = id==undefined?"<s:text name="main.add" />":"<s:text name="main.edit" />";
        new dialog().iframe("blackAdd.action?id=" + id, {title: action + "", resize:false, w:"460px", h:"340px"});
    }

    function vipDel(id){
        lconfirm('<s:text name="main.delete.confirm" />', function(index){
            $.post("blackDel.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    succ('<s:text name="main.delete.succ" />', function(){
                        refreshTab();
                    });
                }
                else{
                    error(resp);
                }
            });
            layer.close(index);
        });
    }
</script>
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label"><s:text name="blacklist.field.no" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="idcard" class="scinput" type="text" value="<%=request.getParameter("idcard")==null?"":request.getParameter("idcard")%>" style="width:120px;" />
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label"><s:text name="blacklist.field.name" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="name" class="scinput" type="text" value="<%=request.getParameter("name")==null?"":request.getParameter("name")%>" style="width:120px;" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="vipAdd()" value="<s:text name="main.add" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq" /></th>
                <th>
                    <s:text name="blacklist.column.name" />
                </th>
                <th>
                    <s:text name="blacklist.column.no" />
                </th>
                <th>
                    <s:text name="blacklist.column.type" />
                </th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${queryData==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${queryData!=null}">
                <c:forEach items="${queryData.getRecords()}" var="data" varStatus="index">
                    <tr>
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.name}
                        </td>
                        <td>
                                ${data.idcard}
                        </td>
                        <td>
                                ${data.idtype}
                        </td>
                        <td>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();vipAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name="main.edit" /></a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();vipDel('${data.id}','<s:text name="main.delete" />');"><i class="layui-icon layui-icon-delete"></i><s:text name="main.delete" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div id="pp"></div>
    </div>
</div>
</body>
<script type="text/javascript" src="lib/util/deptselutil.js"></script>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
    layui.use(['form', 'element']);
</script>
</html>
