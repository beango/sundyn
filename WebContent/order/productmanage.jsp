<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>商品管理</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
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
<input type="hidden" id="deptId" value="${deptId}"/>

<div class="layui-form" lay-filter="f">
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="edit('')" value="<s:text name="main.add" />" />
        </div>
    </div>
    <div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th>名称</th>
                <th>型号</th>
                <th>价格（元）</th>
                <th>总授权数</th>
                <th>已用授权数</th>
                <th>状态</th>
                <th>最新版本</th>
            </tr>
            </thead>
            <c:forEach items="${list}" var="entity" varStatus="s">
                <tr>
                    <td>
                        ${entity.productname}
                    </td>
                    <td>
                        ${entity.productcode}
                    </td>
                    <td>
                        <fmt:formatNumber type="number" value="${entity.price * 1.0}" groupingUsed="false" pattern="0.00" />
                    </td>
                    <td>
                            ${entity.totalnum}
                    </td>
                    <td>
                            ${entity.usednum}
                    </td>
                    <td>
                            ${entity.canbuy==1?"可购买":"不支持购买"}
                    </td>
                     <td>
                         <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                            onclick="event.stopPropagation();edit('${entity.id}');"><i
                                 class="layui-icon layui-icon-edit"></i>修改</a>
                         <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"
                            onclick="event.stopPropagation();del(${entity.id});"><i
                                 class="layui-icon layui-icon-delete"></i><s:text name="main.delete"/></a>
                         <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"
                            onclick="event.stopPropagation();dialogupload('${entity.id}');"><i
                                 class="layui-icon layui-icon-edit"></i>附件管理</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <div id="pp"></div>
    </div>
</div>

</body>
<script type="text/javascript">
    //initPager(${orderlist.rowsCount}, ${param.getOrDefault("currentPage", 1)}, ${param.getOrDefault("pageSize", 20)});

    function dialogupload(prdid){
        new dialog().iframe("productversion.action?productid=" + prdid, {title: "附件管理", resize:true, w:"700px", h:"650px"});
    }

    function edit(prdid){
        new dialog().iframe("productform.action?productid=" + prdid, {title: prdid==0?"新增":"修改", resize:true, w:"700px", h:"650px"});
    }

    function del(prdid){
        lconfirm("<s:text name="main.delete.confirm"/>",function(){
            $.ajax({
                url:"productdel.action",
                data:{id:prdid},
                type:"post",
                success: function (resp, ioArgs) {
                    if(resp.succ){
                        succ('<s:text name="main.delete.succ"/>', function(){
                            refreshTab();
                        });
                    }
                    else
                        error(resp.msg);
                }});
        });
    }

    layui.use(['form', 'element']);
</script>

</html>
