<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html style="height: 100%">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>登录日志统计</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/jquery.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:420px;height:360px;overflow-y:scroll;overflow-x:auto;}
        .layui-btn-xs{text-indent:0px;}
    </style>
</head>
<body>
<script type="text/javascript">
    function query(){
        window.location.href = "securitylogrpt.action?startDate="+$("#startDate").val() + "&endDate=" + $("#endDate").val();
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
        <label class="layui-form-label"><s:text name='sundyn.total.startDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <label class="layui-form-label"><s:text name='sundyn.total.endDate'/></label>
        <div class="layui-input-inline">
            <input type="text" class="scinput" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',lang:'${locale}'})"/>
        </div>
    </div>
    <div class="layui-inline">
        <div class="layui-input-inline">
            <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="query('')" value="<s:text name="main.query" />" />
        </div>
    </div>
    <div>
        <div id="container" style="height: 50%"></div>
        <table class="tablelist" lay-filter="tbl" id="demo">
            <thead>
            <tr>
                <th><s:text name="main.column.seq"/></th>
                <th>
                    <s:text name="securitylogrpt.column.name"/>
                </th>
                <th>
                    <s:text name="securitylogrpt.column.succtimes"/>
                </th>
                <th>
                    <s:text name="securitylogrpt.column.failtimes"/>
                </th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${data==null}"><s:text name='sundyn.nodate' /> </c:if>
            <c:if test="${data!=null}">
                <c:forEach items="${data}" var="data" varStatus="index">
                    <tr>
                        <td>
                                ${index.index+1}
                        </td>
                        <td>
                                ${data.realname} / ${data.name}
                        </td>

                        <td>${data.succtimes}</td>
                        <td>
                                ${data.errortimes}
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
<script type="text/javascript">
   var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    option = null;
    option = {
        calculable: true,
        grid: {
            y: 80,
            y2: 40,
            x2: 40
        },
        tooltip: {},
        legend: {
            data:['登录次数']
        },
        xAxis: {
            type: 'category',
            data: ${xAxisData}
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: '登录成功次数', itemStyle: '#ffffff',
            data: ${seriesData1},
            type: 'bar'
        },{
            name: '登录失败次数', itemStyle: '#000000',
            data: ${seriesData2},
            type: 'bar'
        }]
    };

    if (option && typeof option === "object") {
        myChart.setOption(option);
    }
</script>
</body>

<style type="text/css">
    .autocut {
        width:300px;
        overflow:hidden;
        white-space:nowrap;
        text-overflow:ellipsis;
        -o-text-overflow:ellipsis;
        -icab-text-overflow: ellipsis;
        -khtml-text-overflow: ellipsis;
        -moz-text-overflow: ellipsis;
        -webkit-text-overflow: ellipsis;
    }
</style>
</html>
