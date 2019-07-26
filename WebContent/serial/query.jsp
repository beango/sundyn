<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>排队业务管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
</head>
<body>
<script type="text/javascript">
    function serialQuery(){
        var hallid = $("#hallsele").val();
        window.location.href = "serialQuery.action?hallid="+hallid + "&bizname=" + $("#bizname").val();
    }

    function serialAdd(id){
        var action = id==undefined?"<s:text name="main.add" />":"<s:text name="main.edit" />";
        new dialog().iframe("serialAdd.action?id=" + id + "&halldef=" + $("#hallsele").val(), {title: action, resize:false, w:"500px", h:"400px"});
    }

    function serialDel(id){
        lconfirm('<s:text name="main.delete.confirm" />', function(index){
            $.post("serialDel.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    succ('<s:text name="main.delete.succ" />', function(){
                        closeDialog();
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
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-inline">
        <label class="layui-form-label" style="width:60px;"><s:text name="hall.field.hallname" /></label>
        <div class="layui-input-inline">
            <select id="hallsele" class="select2">
                <option <c:if test="${''==hallid || hallid==null}"> selected="selected"</c:if> value=""><s:text name="main.all" /></option>
                <c:forEach items="${hallList}" var="hall" varStatus="index">
                    <option <c:if test="${hall.id==hallid}"> selected="selected"</c:if> value="${hall.id}">${hall.hallname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label"><s:text name="serial.field.serialname" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="bizname" class="scinput" type="text" value="<%=request.getParameter("bizname")==null?"":request.getParameter("bizname")%>" style="width:120px;" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="serialQuery('')" value="<s:text name="main.query" />" />
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="serialAdd()" value="<s:text name="main.add" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq" /></th>
                <th>
                    <s:text name="serial.column.head.hallname" />
                </th>
                <th>
                    <s:text name="serial.column.head.serialid" />
                </th>
                <th>
                    <s:text name="serial.column.head.serialname" />
                </th>
                <%--<th>
                    业务类型
                </th>--%>
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
                                ${data.hallname}
                        </td>
                        <td>
                                ${data.bizid}
                        </td>
                        <td>
                                ${data.bizname}
                        </td>
                        <%--<td>
                                ${data.biztypename}
                        </td>--%>
                        <td>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();serialAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name="main.edit" /></a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();serialDel('${data.id}','<s:text name="main.delete" />');"><i class="layui-icon layui-icon-delete"></i><s:text name="main.delete" /></a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <div id="pp"></div>
    </div>
</div>
<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
</body>
<script type="text/javascript" src="lib/util/deptselutil.js"></script>
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });
</script>
</html>
