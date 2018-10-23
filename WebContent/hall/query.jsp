<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>服务大厅管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
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
    </style>
</head>
<body>
<script type="text/javascript">
    $(document).ready(function () {
        initTree()
    });
    function hallQuery(){
        var deptId = getCheck();
        var hallname = document.getElementById("key_hallname").value;
        window.location.href = "hallQuery.action?deptId="+deptId+"&hallname=" + encodeURIComponent(hallname) + "&deptname=" + $("#deptSel").val();
    }

    function hallAdd(id){
        new dialog().iframe("hallAdd.action?id=" + id, {title: "添加", resize:false, w:"700px", h:"650px"});
    }

    function hallSerial(id){
        parent.openTab("业务配置", "serialQuery.action?hallid=" + id);
    }

    function hallCounter(id){
        parent.openTab("窗口管理", "counterQuery.action?hallid=" + id);
    }

    function hallDel(id){
        layer.confirm('真的删除么', function(index){
            $.post("hallDel.action?id=" + id, function(resp){
                if(resp.trim()==""){
                    layer.msg('删除成功', {
                        icon: 1,
                        time: 800
                    }, function(){
                        parent.closeDialog();
                        parent.refreshTab();
                    });
                }
                else{
                    layer.msg(resp, {
                        icon: 2,
                        time: 1200
                    }, function(){
                    });
                }
            });
            layer.close(index);
        });
    }
</script>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-inline">
        <label class="layui-form-label" style="width:80px;">服务厅名称：</label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="key_hallname" name="key_hallname" value="${key_hallname}" />
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label">组织机构：</label>
        <div class="layui-form-mid layui-word-aux">
        </div>
        <div class="layui-input-inline">
            <input id="deptSel" class="scinput" type="text" readonly value="<%=request.getParameter("deptname")==null?"":request.getParameter("deptname")%>" style="width:120px;" onclick="showDeptTree();" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" class="hand" onclick="hallQuery('')"/>
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>序号 </th>
                <th>
                    服务厅编码
                </th>
                <th>
                    服务厅名称
                </th>
                <th>
                    经度
                </th>
                <th>
                    纬度
                </th>
                <th>
                    负责人
                </th>
                <th>
                    负责人电话
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
                                ${data.hallno}
                        </td>
                        <td>
                                ${data.hallname}
                        </td>
                        <td>
                                ${data.longitude}
                        </td>
                        <td>
                                ${data.dimension}
                        </td>
                        <td>
                                ${data.head}
                        </td>
                        <td>
                                ${data.headtel}
                        </td>
                        <td>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();hallAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();hallSerial('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i>业务配置</a>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();hallCounter('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i>窗口配置</a>
                            <%--<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();hallDel('${data.id}','删除');"><i class="layui-icon layui-icon-delete"></i>删除</a>--%>
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
</script>
</html>
