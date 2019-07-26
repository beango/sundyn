<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>订单管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
</head>
<body>
<script type="text/javascript">
    function query(){
        var key = document.getElementById("key").value;
        window.location.href = "orderlicense.action?key=" + key;
    }

    function vipAdd(id){
        var action = id == undefined ? "<s:text name="main.add" />" : "<s:text name="main.edit" />";
        new dialog().iframe("vipAdd.action?id=" + id, {title: action+"VIP名单", resize:false, w:"460px", h:"340px"});
    }

    function vipDel(id){
        lconfirm('<s:text name="main.delete.confirm" />', function(index){
            $.post("vipDel.action?id=" + id, function(resp){
                if(resp.trim()=="") {
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
        <label class="layui-form-label" style="width:100px;">客户名称 / 账号：</label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="key" value="${key}" />
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th style="width:20px;"></th>
                <th>客户名称 / 账号</th>
                <th>注册时间</th>
                <th>下单数 / 已完成订单数</th>
                <th>下单总额 / 已完成总额 / 支付总额</th>
                <th>有效授权数 / 已使用授权数 / 剩余授权数</th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${list.pageList}" var="entity" varStatus="s">
                <tr>
                    <td><img src="images/img/nolines_plus.gif" style="width:32px;height:32px;cursor:pointer;" onclick="toggledetail(this)"/></td>
                    <td>
                        ${entity.orgname} / ${entity.name}
                    </td>
                    <td>
                        <fmt:formatDate value="${entity.ctime}" type="both" />
                    </td>
                    <td>
                            ${entity.ordertotal} / ${entity.ordercomplete}
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${entity.ordertotalfee * 1.0}" groupingUsed="false" pattern="0.00" /> /
                        <fmt:formatNumber type="number" value="${entity.ordercompletefee * 1.0}" groupingUsed="false" pattern="0.00" /> /
                        <fmt:formatNumber type="number" value="${entity.totalpayfee * 1.0}" groupingUsed="false" pattern="0.00" />
                    </td>
                    <td>
                            ${entity.ordertotallicense} / ${entity.ordertusedlicense} / ${entity.ordertotallicense - entity.ordertusedlicense}
                                    <c:if test="${entity.usednums!=entity.ordertusedlicense}"><font color="red" style="font-weight: bold;">（数据异常）</font></c:if>
                    </td>
                    <td>
                        ${entity.comment}
                    </td>
                    <td>
                        <c:if test="${entity.status==0}">
                            <a class="layui-btn layui-btn-add layui-btn-xs" onclick="event.stopPropagation();audit(${entity.id});"><i class="layui-icon layui-icon-delete"></i>审核</a>
                            <a class="layui-btn layui-btn-danger layui-btn-xs" onclick="event.stopPropagation();closeorder(${entity.id});"><i class="layui-icon layui-icon-delete"></i>关闭订单</a>
                        </c:if>
                    </td>
                </tr>
                <tr style="display: none;">
                    <td colspan="8" style="padding-left:42px;">
                        <c:forEach items="${details}" var="entity2" varStatus="s">
                            <c:if test="${entity.id == entity2.managerid}">
                                <div class="layui-form-item">
                                    <div class="layui-input-inline" style="width:170px;">
                                        订单号：${entity2.orderno}
                                    </div>
                                    <div class="layui-input-inline" style="width:220px;">
                                        产品名称：${entity2.productname}
                                    </div>
                                    <div class="layui-input-inline" style="width:237px;">
                                        授权时间：${entity2.ctime}
                                    </div>
                                    <div class="layui-input-inline" style="width:237px;">
                                        总授权数：<font style="font-size:20px;">${entity2.num}</font>
                                    </div>

                                    <div class="layui-input-inline" style="width:237px;">
                                        已使用：<font style="font-size:20px;">${entity2.usednum} <c:if test="${entity2.usednum!=0 && entity2.usednums!=null && entity2.usednum!=entity2.usednums}">数据异常</c:if></font>
                                    </div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div id="pp"></div>
    </div>
</div>
</body>
<script type="text/javascript" src="lib/util/deptselutil.js"></script>
<script type="text/javascript">
    initPager(${orderlist.getTotal()}, ${param.getOrDefault("currentPage", 1)}, ${param.getOrDefault("pageSize", 20)});

    layui.use(['form', 'element','layer']);
</script>

<script type="text/javascript">
    function toggledetail(obj) {
        var o =$(obj);
        o.parents('tr').next().toggle('normal', function(){
            if($(o).attr('src')==='images/img/nolines_plus.gif'){
                $(o).attr('src', 'images/img/nolines_minus.gif');
            } else {
                $(o).attr('src', 'images/img/nolines_plus.gif');
            }
        });
    }

    function closeorder(id) {
        layer.prompt({title: '关闭原因', formType: 2}, function(text, index){
            lsubmit("orderClose.action", {id:id, closedesc: text}, function (resp, ioArgs) {
                    if(resp.succ)
                        succ(resp.msg, function(){
                            refreshTab();
                        });
                    else
                        error(resp.msg);
                });
        });
    }

    function audit(id) {
        layer.prompt({title: '审核订单 -> 付款金额（元）', formType: 3}, function(pay, index){
            layer.close(index);
            layer.prompt({title: '审核订单', formType: 2}, function(text, index){
                layer.close(index);
                lsubmit("orderAudit.action", {id:id, pay:pay, auditdesc: text}, function (resp, ioArgs) {
                    if(resp.succ)
                        succ(resp.msg, function(){
                            refreshTab();
                        });
                    else
                        error(resp.msg);
                });
            });
        });
    }
</script>
</html>
