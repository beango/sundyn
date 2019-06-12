<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>绩效数据配置</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<script type="text/javascript">
    function query(){
        var key = $("#key").val();
        window.location.href = "jxDataQuery.action?key="+key + "&servicedate=" + $("#servicedate").val();
    }

    function proxyAdd(id){
        var action = id==undefined?"<s:text name="main.add" />":"<s:text name="main.edit" />";
        new dialog().iframe("jxDataAdd.action?id=" + id, {title: action+"", resize:false, w:"40%", h:"710px"});
    }

    function del(id){
        layer.confirm('<s:text name="main.delete.confirm" />', function(index){
            $.post("jxDataDel.action?id=" + id, function(resp){
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
        <label class="layui-form-label" style="width:100px;"><s:text name="jx.query.search.name" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="key" class="scinput" type="text" value="<%=request.getParameter("key")==null?"":request.getParameter("key")%>" style="width:150px;" />
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label" style="width:60px;"><s:text name="jx.query.search.month" /></label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="servicedate" name="servicedate" value="<%=request.getParameter("servicedate")==null?"":request.getParameter("servicedate")%>" onClick="WdatePicker({dateFmt:'yyyy-MM',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="proxyAdd()" value="<s:text name="main.add" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq" /></th>
                <th>
                    <s:text name="jx.column.name" />
                </th>
                <th>
                    <s:text name="jx.column.deptname" />
                </th>
                <th>
                    <s:text name="jx.column.month" />
                </th>
                <th>
                    <s:text name="jx.column.monthkq" />
                </th>
                <th>
                    <s:text name="jx.column.qzby" />
                </th>
                <th>
                    <s:text name="jx.column.rcxc" />
                </th>
                <th>
                    <s:text name="jx.column.ypfj" />
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
                                ${data.ename} / ${data.eno}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>
                                ${data.servicedate}
                        </td>
                        <td>
                                ${data.ykq}
                        </td>
                        <td>
                                ${data.qzby}
                        </td>
                        <td>
                                ${data.rcxc}
                        </td>
                        <td>
                            <c:if test="${data.ypfj==null}"><button class="layui-btn layui-btn-xs"><s:text name="jx.td.ypfj2" /></button></c:if>
                            <c:if test="${data.ypfj!=null}"><button class="layui-btn layui-btn-danger layui-btn-xs"><s:text name="jx.td.ypfj1" /></button>
                                ${data.ypfj==999 && data.fjdesc!=null && data.fjdesc!=''? data.fjdesc : data.note}
                            </c:if>
                        </td>
                        <td>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();proxyAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i><s:text name="main.edit" /></a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();del('${data.id}','<s:text name="main.delete" />');"><i class="layui-icon layui-icon-delete"></i><s:text name="main.delete" /></a>
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
    layui.use(['form', 'element'], function() {
        var form = layui.form;
    });
</script>
</html>
