<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>代理人机构管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
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
    function query(){
        var orgname = $("#orgname").val();
        window.location.href = "proxyOrgQuery.action?orgname="+orgname + "&orgcode=" + $("#orgcode").val();
    }

    function Add(id){
        var action = id==undefined?"<s:text name="main.add" />":"<s:text name="main.edit" />";
        new dialog().iframe("proxyOrgAdd.action?id=" + id, {title: action+"", resize:false, w:"460px", h:"340px"});
    }

    function Review(id){
        lconfirm('<s:text name="proxyorg.review.confirm" />', function(index){
            $.post("proxyOrgReview.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    succ('<s:text name="proxyorg.review.succ" />', function(){
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

    function Cancel(id){
        lconfirm('<s:text name="proxyorg.cancel.confirm" />', function(index){
            $.post("proxyOrgCancel.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    succ('<s:text name="proxyorg.cancel.succ" />', function(){
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

    function Pause(id){
        lconfirm('<s:text name="proxyorg.pause.confirm" />', function(index){
            $.post("proxyOrgPause.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    succ('<s:text name="proxyorg.pause.succ" />', function(){
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

    function Restore(id){
        lconfirm('<s:text name="proxyorg.resume.confirm" />', function(index){
            $.post("proxyOrgRestore.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    succ('<s:text name="proxyorg.resume.succ" />', function(){
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

    function proxyDel(id){
        lconfirm('<s:text name="main.delete.confirm" />', function(index){
            $.post("proxyOrgDel.action?id=" + id, function(resp){
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
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:100px;"><s:text name="proxyorg.field.orgno" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="orgcode" class="scinput" type="text" value="<%=request.getParameter("orgcode")==null?"":request.getParameter("orgcode")%>" style="width:150px;" />
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:100px;"><s:text name="proxyorg.field.orgname" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="orgname" class="scinput" type="text" value="<%=request.getParameter("orgname")==null?"":request.getParameter("orgname")%>" style="width:120px;" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="Add()" value="<s:text name="main.add" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq" /> </th>
                <th>
                    <s:text name="proxyorg.column.orgname" />
                </th>
                <th>
                    <s:text name="proxyorg.column.orgno" />
                </th>
                <th>
                    <s:text name="proxyorg.column.status" />
                </th>
                <th>
                    <s:text name="proxyorg.column.contact" />
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
                                ${data.orgname}
                        </td>
                        <td>
                                ${data.orgcode}
                        </td>
                        <td>
                                <c:if test="${data.status==0}"><s:text name='proxy.status.noreview' /></c:if><c:if test="${data.status==1}"><s:text name='proxy.status.review' /></c:if><c:if test="${data.status==-1}"><s:text name='proxy.status.cancel' /></c:if>
                        </td>
                        <td>
                                ${data.mainname}
                        </td>
                        <td>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();Add('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name='main.edit' /></a>
                            <c:if test="${data.status==0}">
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();Review('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name='proxy.action.review' /></a>
                            </c:if>
                            <c:if test="${data.status==1}">
                                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();Cancel('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name='proxy.action.cancel' /></a>
                            </c:if>
                            <c:if test="${data.ispause==1 && data.status==1}">
                                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();Restore('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name='proxy.action.resume' /></a>
                            </c:if>
                            <c:if test="${(data.ispause==0 || data.ispause==null) && data.status==1}">
                                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();Pause('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name='proxy.action.pause' /></a>
                            </c:if>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();proxyDel('${data.id}','<s:text name='main.delete' />');"><i class="layui-icon layui-icon-delete"></i><s:text name='main.delete' /></a>
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
<script type="text/javascript">
    initPager(${queryData.getTotal()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
    layui.use(['form', 'element']);
</script>
</html>
