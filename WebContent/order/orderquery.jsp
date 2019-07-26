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
        var start = document.getElementById("startDate").value;
        var end = document.getElementById("endDate").value;
        window.location.href = "orderquery.action?status=" + $("#status").val() + "&ispay=" + $("#ispay").val() + "&startDate=" + start + "&endDate=" + end;
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
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label">订单状态：</label>
        <div class="layui-input-inline">
            <select id="status" name="status">
                <option value="" <c:if test="${status == '' || status == null}">selected="selected"</c:if>>全部</option>
                <option value="0" <c:if test="${status == '0'}">selected="selected"</c:if>>等待支付</option>
                <option value="1" <c:if test="${status == '1'}">selected="selected"</c:if>>已完成</option>
                <option value="-1" <c:if test="${status == '-1'}">selected="selected"</c:if>>取消</option>
                <option value="-2" <c:if test="${status == '-2'}">selected="selected"</c:if>>管理员关闭</option>
            </select>
        </div>
    </div>
    <div class="layui-select-cus layui-inline">
        <label class="layui-form-label">支付状态：</label>
        <div class="layui-input-inline">
            <select id="ispay" name="ispay">
                <option value="" <c:if test="${ispay == '' || ispay == null}">selected="selected"</c:if>>全部</option>
                <option value="0" <c:if test="${ispay == '0'}">selected="selected"</c:if>>未支付</option>
                <option value="1" <c:if test="${ispay == '1'}">selected="selected"</c:if>>已支付</option>
            </select>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
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
                <th>订单编号</th>
                <th>下单时间</th>
                <th>总费用（元）</th>
                <th>支付状态/金额/时间</th>
                <th>订单状态</th>
                <th>审核人</th>
                <th>备注</th>
                <th></th>
            </tr>
            </thead>
            <c:forEach items="${orderlist.pageList}" var="entity" varStatus="s">
                <tr>
                    <td><img src="images/img/nolines_plus.gif" style="width:32px;height:32px;cursor:pointer;" onclick="toggledetail(this)"/></td>
                    <td>
                        ${entity.orgname} / ${entity.name}
                    </td>
                    <td>
                        ${entity.orderno}
                    </td>
                    <td>
                        <fmt:formatDate value="${entity.ctime}" type="both" />
                    </td>
                    <td>
                        <font style="font-weight: bold; font-size: 12pt;"><fmt:formatNumber type="number" value="${entity.totalfee * 1.0}" groupingUsed="false" pattern="0.00" /></font>
                    </td>
                    <td>
                        <c:if test="${entity.ispay==0}"><font color="red">未支付</font></c:if>
                        <c:if test="${entity.ispay==1}"><font color="blue">已支付</font> / <font style="font-weight: bold; font-size: 16pt;"><fmt:formatNumber type="number" value="${entity.payfee * 1.0}" groupingUsed="false" pattern="0.00" /></font> / <fmt:formatDate value="${entity.paytime}" type="both" /></c:if>
                    </td>
                    <td>
                            <c:if test="${entity.status==0}"> 等待支付</c:if>
                            <c:if test="${entity.status==1}"> <font color="blue">已完成</font></c:if>
                            <c:if test="${entity.status==-2}"> <font color="red">管理员关闭</font> （时间：<fmt:formatDate value="${entity.closetime}" type="both" />，原因：${entity.closedesc}）</c:if>
                            <c:if test="${entity.status==-1}"> <font color="red">取消</font> （时间：<fmt:formatDate value="${entity.canceltime}" type="both" />，原因：${entity.canceldesc}）</c:if>
                    </td>
                    <td>
                            ${entity.auditusername}<c:if test="${entity.status==1}"> （时间：<fmt:formatDate value="${entity.audittime}" type="both" />，备注：${entity.auditdesc}）</c:if>
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
                        <c:forEach items="${orderprdlist}" var="entity2" varStatus="s">
                            <c:if test="${entity.id == entity2.orderid}">
                                <div class="layui-form-item">
                                    <div class="layui-input-inline" style="width:180px;">
                                        产品名称：${entity2.productname}
                                    </div>
                                    <div class="layui-input-inline" style="width:120px;">
                                        下单数量：<font style="font-size:20px;">${entity2.num}</font>
                                    </div>
                                    <div class="layui-input-inline" style="width:120px;">
                                        下单时折扣：<font style="font-size:20px;">${entity2.rate}</font>
                                    </div>
                                    <div class="layui-input-inline" style="width:260px;">
                                        下单时原价/折后价（元）：<font style="font-size:20px;"><fmt:formatNumber type="number" value="${entity2.prdprice}" groupingUsed="false" /></font> / <font style="font-size:20px;"><fmt:formatNumber type="number" value="${entity2.realprice}" groupingUsed="false" /></font>
                                    </div>
                                    <div class="layui-input-inline" style="width:237px;">
                                        下单时总费用（元）：<font style="font-size:20px;">${entity2.num * entity2.realprice}</font>
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
    initPager(${orderlist.rowsCount}, ${param.getOrDefault("currentPage", 1)}, ${param.getOrDefault("pageSize", 20)});

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
