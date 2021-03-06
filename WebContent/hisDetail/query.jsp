<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>排队历史数据查询</title>
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
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
    </style>
</head>
<body>
<script type="text/javascript">
    function counterQuery(){
        var hallid = $("#hallsele").val();
        window.location.href = "counterQuery.action?hallid="+hallid + "&countername=" + $("#cname").val();
    }

    function counterAdd(id){
        new dialog().iframe("counterAdd.action?id=" + id, {title: "添加", resize:false, w:"900px", h:"650px"});
    }

    function counterDel(id){
        layer.confirm('真的删除么', function(index){
            $.post("counterDel.action?id=" + id, function(resp){
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
<input type="hidden" id="deptId" value="${deptId}"/>
<div class="layui-form" lay-filter="f">
    <div class="layui-inline">
        <label class="layui-form-label" style="width:100px;">服务厅：</label>
        <div class="layui-input-inline">
            <select id="hallsele">
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
            <input id="cname" class="input_comm" type="text" value="<%=request.getParameter("countername")==null?"":request.getParameter("countername")%>" style="width:120px;" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <a class=" layui-btn" href="#" onclick="counterQuery('')"><i class="layui-icon layui-icon-search"></i>查询</a>
            <a class=" layui-btn" href="#" onclick="counterAdd('')"><i class="layui-icon layui-icon-add-circle"></i>增加</a>
        </div>
    </div>
    <div>
        <table class="layui-table" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>序号 </th>
                <th>
                    业务事项编码
                </th>
                <th>
                    业务名称
                </th>
                <th>
                    部门名称
                </th>
                <th>
                    排队号码
                </th>
                <th>办事人名称</th>
                <th>取号时间</th>
                <th>等候时长</th>
                <th>办理时长</th>
                <th>评价结果</th>
                <th>状态</th>
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
                                ${data.bizid}
                        </td>
                        <td>
                                ${data.bizname}
                        </td>
                        <td>
                                ${data.deptname}
                        </td>
                        <td>
                                ${data.queuenumber}
                        </td>
                        <td>
                                ${data.cardName}
                        </td>
                        <td>
                                ${data.tickettime}
                        </td>
                        <td>
                                ${data.waittime}
                        </td>
                        <td>
                                ${data.servicestime}
                        </td>
                        <td>
                                ${data.appriseresult}
                        </td>
                        <td>
                                ${data.status}
                        </td>
                        <th>
                            <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit" onclick="event.stopPropagation();counterAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><i class="layui-icon layui-icon-edit"></i>编辑</a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del" onclick="event.stopPropagation();counterDel('${data.id}','删除');"><i class="layui-icon layui-icon-delete"></i>删除</a>
                        </th>
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
