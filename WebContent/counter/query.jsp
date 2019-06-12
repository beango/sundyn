<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>窗口管理</title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <link rel="stylesheet" href="css/style.css"  media="all" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layer/layer.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/myAjax.js"></script>
    <script type="text/javascript" src="js/application.js?1"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
    <script type="text/javascript">
        function counterQuery(){
            var hallid = $("#hallsele").val();
            window.location.href = "counterQuery.action?hallid="+hallid + "&countername=" + $("#cname").val();
        }

        function counterAdd(id){
            var action = id==undefined?"增加":"编辑";
            new dialog().iframe("counterAdd.action?id=" + id + "&halldef=" + $("#hallsele").val(), {title: action+"窗口", resize:false, w:"500px", h:"400px"});
        }

        function counterDel(id){
            layer.confirm('真的删除么', function(index){
                $.post("counterDel.action?id=" + id, function(resp){
                    if(resp.trim()==""){
                        succ('删除成功', function(){
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

<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-inline">
        <label class="layui-form-label" style="width:70px;">服务厅：</label>
        <div class="layui-input-inline">
            <select id="hallsele" class="select3">
                <option <c:if test="${''==hallid || hallid==null}"> selected="selected"</c:if> value="">全部</option>
                <c:forEach items="${hallList}" var="hall" varStatus="index">
                    <option <c:if test="${hall.id==hallid}"> selected="selected"</c:if> value="${hall.id}">${hall.hallname}</option>
                </c:forEach>
            </select>
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label">窗口名称：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="cname" class="scinput" type="text" value="<%=request.getParameter("countername")==null?"":request.getParameter("countername")%>" style="width:120px;" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand" onclick="counterQuery('')"/>
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="counterAdd()" value="增    加" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>序号 </th>
                <th>
                    服务厅
                </th>
                <th>
                    窗口号
                </th>
                <th>
                    窗口名称
                </th>
                <%--<th>
                    窗口类型
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
                                ${data.counterno}
                        </td>
                        <td>
                                ${data.countername}
                        </td>
                        <%--<td>
                                ${data.countertypename}
                        </td>--%>
                        <td>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();counterAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();counterDel('${data.id}','删除');"><i class="layui-icon layui-icon-delete"></i>删除</a>
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
