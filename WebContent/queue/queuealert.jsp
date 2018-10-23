<%@ page import="org.json.JSONArray" %>
<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>排队历史数据查询</title>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/admin.css" type="text/css"/>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/jquery-2.1.3.min.js"></script>
    <script type="text/javascript" src="js/json2.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/FusionCharts.js"></script>
    <style type="text/css">
        .layui-card-body{padding:3px;}
    </style>
</head>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
        <c:if test="${deptObj!=null}"><li>${deptObj["name"]}</li></c:if>
    </ul>
</div>
<body>
<div class="layui-fluid">
    <div class="layui-row layui-col-space15">

        <div class="layui-col-sm6 layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    当日人流量
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">当日</span>
                </div>
                <div class="layui-card-body" style="height:235px">
                    <p class="layuiadmin-big-font" id="divservicecount">
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    当日等候人数
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">当日</span>
                </div>
                <div class="layui-card-body" style="height:235px">
                    <p class="layuiadmin-big-font" id="divwaitcount">
                    </p>
                </div>
            </div>
        </div>
        <div class="layui-col-sm6 layui-col-md4">
            <div class="layui-card">
                <div class="layui-card-header">
                    最近评价
                    <span class="layui-badge layui-bg-blue layuiadmin-badge">当日</span>
                </div>
                <div class="layui-card-body" style="height:235px">
                    <table lay-skin="line" id="divnewestappries">
                    </table>
                </div>
            </div>
        </div>

        <div class="layui-col-sm4">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">差评预警<span class="layui-badge layui-bg-green layuiadmin-badge">当月</span></div>
                        <div class="layui-card-body" style="height:315px;">
                            <table lay-skin="line" id="TableMonthDeptBMY">
                            </table>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">工作量较低预警<span class="layui-badge layui-bg-green layuiadmin-badge">当月</span></div>
                        <div class="layui-card-body" style="height:315px;">
                            <table lay-skin="line" id="layoutservicecount">
                            </table>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">满意度较低预警<span class="layui-badge layui-bg-green layuiadmin-badge">当月</span></div>
                        <div class="layui-card-body" style="height:315px;">
                            <table lay-skin="line" id="layoutmyd">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-sm4">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">服务大厅平均办理时间预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body">
                            <p class="layuiadmin-big-font" id="ChartTodayDeptServiceTime">
                            </p>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">服务大厅平均等待时间预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body">
                            <p class="layuiadmin-big-font" id="ChartTodayDeptWaitTime">
                            </p>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">服务大厅差评数预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body">
                            <p class="layuiadmin-big-font" id="ChartTodayDeptBMY">
                            </p>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">服务大厅暂停时长预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body">
                            <p class="layuiadmin-big-font" id="ChartTodayDeptPauseTime">
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="layui-col-sm4">
            <div class="layui-row layui-col-space15">
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">员工平均办理时间预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body" style="height:315px;">
                            <table lay-skin="line" id="layoutemployeeservicetime">
                            </table>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">员工差评率预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body" style="height:315px;">
                            <table lay-skin="line" id="layoutemployeekeybmy">
                            </table>
                        </div>
                    </div>
                </div>
                <div class="layui-col-sm12">
                    <div class="layui-card">
                        <div class="layui-card-header">员工暂停时长预警<span class="layui-badge layui-bg-orange layuiadmin-badge">当日</span></div>
                        <div class="layui-card-body" style="height:315px;">
                            <table lay-skin="line" id="layoutemployeepausetime">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        //$("#lstappries").load("getlstappries.action");
        function loaddata(){
            $.post("getQueueDeptAnysleAjax.action?deptid=<%=request.getParameter("deptid")%>", function (data) {
                //当日人流量
                var myChart1 = new FusionCharts("Charts/Column3D.swf", "chartid", "100%", "220","0","1");
                myChart1.setDataXML(data.DeptServiceCount);// data.DeptServiceCount
                myChart1.render("divservicecount");
                //当日等候人数
                var myChart2 = new FusionCharts("Charts/Column3D.swf", "chartid", "100%", "220","0","1");
                myChart2.setDataXML(data.DeptWaitCount);// data.DeptServiceCount
                myChart2.render("divwaitcount");

                layui.use(['table','laypage'], function() {
                    var table = layui.table;
                    //最近评价
                    var newestappries = data.lst5Appries;
                    table.render({
                        elem: '#divnewestappries'
                        , height: 230
                        , data: newestappries //数据接口
                        , page: newestappries.length > 6 ? {limit: 6, layout: ['prev', 'page', 'next']} : false //开启分页
                        , cols: [[ //表头
                            {field: 'staffname', title: '员工姓名', width: '33%'}
                            , {field: 'appriesname', title: '评价结果', width: '28%'}
                            , {field: 'apprisetime', title: '评价时间'}
                        ]]
                        , skin: 'nob'
                    });
                });
                //服务厅平均办理时间预警
                var myChart3 = new FusionCharts("Charts/Column3D.swf", "chartid", "100%", "220","0","1");
                myChart3.setDataXML(data.ChartTodayDeptServiceTime);
                myChart3.render("ChartTodayDeptServiceTime");

                //服务厅平均等待时间预警
                var myChart4 = new FusionCharts("Charts/Column3D.swf", "chartid", "100%", "220","0","1");
                myChart4.setDataXML(data.ChartTodayDeptWaitTime);
                myChart4.render("ChartTodayDeptWaitTime");

                //服务厅差评数预警
                var myChart4 = new FusionCharts("Charts/Column3D.swf", "chartid", "100%", "220","0","1");
                myChart4.setDataXML(data.ChartTodayDeptBMY);
                myChart4.render("ChartTodayDeptBMY");

                //服务厅暂停时长预警
                var myChart4 = new FusionCharts("Charts/Column3D.swf", "chartid", "100%", "220","0","1");
                myChart4.setDataXML(data.ChartTodayDeptPauseTime);
                myChart4.render("ChartTodayDeptPauseTime");
            });
            //业务监管预警
            $.post("getQueueTotal.action?deptid=<%=request.getParameter("deptid")%>", function (data) {

                layui.use(['table','laypage'], function(){
                    var laypage = layui.laypage;
                    var table = layui.table;
                    //员工平均办理时间预警
                    var dataservicetime = data.dataservicetime123;
                    table.render({
                        elem: '#layoutemployeeservicetime'
                        ,height: 310
                        ,data: dataservicetime //数据接口
                        ,page: dataservicetime.length>6?{limit:6,layout:['prev','page','next']}:false //开启分页
                        ,cols: [[ //表头
                            {field: 'deptname', title: '服务大厅', width:'33%'}
                            ,{field: 'ename', title: '员工姓名', width:'28%'}
                            ,{field: 'totalservicetimename', title: '平均办理时间（'+dataservicetime[0].avgservicetimename+"）", sort: true}
                        ]]
                        ,skin:'nob'
                    });
                    //员工差评率预警
                    var datakeybmy = data.datakeybmy;
                    table.render({
                        elem: '#layoutemployeekeybmy'
                        ,height: 310
                        ,data: datakeybmy //数据接口
                        ,page: datakeybmy.length>6?{limit:6,layout:['prev','page','next']}:false //开启分页
                        ,cols: [[ //表头
                            {field: 'deptname', title: '服务大厅', width:'33%'}
                            ,{field: 'ename', title: '员工姓名', width:'28%'}
                            ,{field: 'keybmyrate', title: '差评率（'+dataservicetime[0].avgtotalkeybmy+"%）", sort: true}
                        ]]
                        ,skin:'nob'
                    });
                    //暂停时长
                    var totalpausetime = data.totalpausetime;
                    table.render({
                        elem: '#layoutemployeepausetime'
                        ,height: 310
                        ,data: totalpausetime //数据接口
                        ,page: totalpausetime.length>6?{limit:6,layout:['prev','page','next']}:false //开启分页
                        ,cols: [[ //表头
                            {field: 'deptname', title: '服务大厅', width:'33%'}
                            ,{field: 'ename', title: '员工姓名', width:'28%'}
                            ,{field: 'totalpausetimename', title: '暂停时长（'+totalpausetime[0].avgpausetimename+"）", sort: true}
                        ]]
                        ,skin:'nob'
                    });
                    $("#layoutemployeepausetime tbody").html("");
                    if(totalpausetime.length>0)
                        $("#divemployeepausetime").html(totalpausetime[0].avgpausetimename);
                    for (var i=0; i<totalpausetime.length && i<15; i++){
                        var o = totalpausetime[i];
                        //$("#layoutemployeepausetime tbody").append("<tr><td>"+o.deptname+"</td><td>"+o.ename+"</td><td>" + o.totalpausetimename+"</td></tr>");
                    }
                });
            });
        }

        function loadonce(){
            $.post("getQueueTotalOnce.action?deptid=<%=request.getParameter("deptid")%>", function (data) {
                layui.use(['table','laypage'], function(){
                    var laypage = layui.laypage;
                    var table = layui.table;
                    //差评预警
                    var TableMonthDeptBMY = data.TableMonthDeptBMY;
                    $("#TableMonthDeptBMY tbody").html("");
                    for (var i = 0; i < TableMonthDeptBMY.length && i < 15; i++) {
                        var o = TableMonthDeptBMY[i];
                        $("#TableMonthDeptBMY tbody").append("<tr><td>"+o.deptname+"</td><td>" + o.ename + "</td><td>" + o.totalkeybmy + "</td></tr>");
                    }
                    table.render({
                        elem: '#TableMonthDeptBMY'
                        ,height: 310
                        ,data: TableMonthDeptBMY //数据接口
                        ,page: TableMonthDeptBMY.length>6?{limit:6,layout:['prev','page','next']}:false //开启分页
                        ,cols: [[ //表头
                            {field: 'deptname', title: '服务大厅', width:'33%'}
                            ,{field: 'ename', title: '员工姓名', width:'33%'}
                            ,{field: 'totalkeybmy', title: '差评数', sort: true}
                        ]]
                        ,skin:'nob'
                    });

                    //工作量较低预警
                    var employeeservicecount = data.employeeservicecount;
                    table.render({
                        elem: '#layoutservicecount'
                        ,height: 310
                        ,data: employeeservicecount //数据接口
                        ,page: employeeservicecount.length>6?{limit:6,layout:['prev','page','next']}:false //开启分页
                        ,cols: [[ //表头
                            {field: 'deptname', title: '服务大厅', width:'33%'}
                            ,{field: 'ename', title: '员工姓名', width:'33%'}
                            ,{field: 'servicecount', title: '工作量', sort: true}
                        ]]
                        ,skin:'nob'
                    });

                    //满意度较低
                    var myd = data.myd;
                    table.render({
                        elem: '#layoutmyd'
                        ,height: 310
                        ,data: myd //数据接口
                        ,page: myd.length>6?{limit:6,layout:['prev','page','next']}:false //开启分页
                        ,cols: [[ //表头
                            {field: 'deptname', title: '服务大厅', width:'33%'}
                            ,{field: 'ename', title: '员工姓名', width:'33%'}
                            ,{field: 'totalmyd', title: '满意度', sort: true}
                        ]]
                        ,skin:'nob'
                    });
                });
            });
        }
        loadonce();
        loaddata();
        window.setInterval(loaddata, 30 * 1000)
    });
</script>
</body>
</html>
