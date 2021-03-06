<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf8"/>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css"/>
    <title><s:text name='zx.title'/></title>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="lib/util/deptselutil.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="lib/ztree/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="lib/jquery.idTabs.min.js"></script>
    <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
    <script language="javascript">
        $(document).ready(function () {
            initTree("?depttype=1&isOnlyLeaf=0", '<%=request.getParameter("deptId")%>');
        });
    </script>
    <style type="text/css">
        ul.ztree {
            margin-top: 10px;
            border: 1px solid #617775;
            background: #f0f6e4;
            width: 420px;
            height: 360px;
            overflow-y: scroll;
            overflow-x: auto;
        }
        .layui-form-radio{margin:0px !important;}
    </style>
</head>
<body class="layui-main" style="width:100%;">
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <c:forEach items="${navbar_menuname}" var="menu">
            <li><a href="#">${menu.name}</a></li>
        </c:forEach>
    </ul>
</div>

<div class="layui-select-cus layui-inline">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.query.selectDept'/></label>
    <input id="deptSel" class="scinput" type="text" readonly
           value="<%=request.getParameter("deptname")==null?"全部":request.getParameter("deptname")%>"
           style="width:120px;cursor: pointer;" onclick="showDeptTree(this, null);"/>
</div>
<div class="layui-select-cus layui-inline">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.total.startDate'/></label>
    <input type="text" id="startDate" class="scinput" <s:text name="sundyn.language.calendar.setDay"/>
           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
</div>
<div class="layui-select-cus layui-inline">
    <label class="layui-form-label" style="width:90px;"><s:text name='sundyn.total.endDate'/></label>
    <input type="text" id="endDate" class="scinput" <s:text name="sundyn.language.calendar.setDay"/>
           onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',lang:'${locale}'})"/>
</div>
<div class="layui-select-cus layui-inline">
    <label class="layui-form-label" style="width:60px;"><s:text name='sundyn.analyse.by'/></label>
    <input type="radio" name="type" checked="checked" value="day" title="<s:text name='sundyn.analyse.day'/>" style="vertical-align:middle;" /><span style="margin-top:0px;display:inline-block;height: 24px; vertical-align: middle;margin-top:8px;"><s:text name='sundyn.analyse.day'/></span>
    <input type="radio" name="type" value="month" title="<s:text name='sundyn.analyse.month'/>" style="vertical-align:middle;" /><span style="margin-top:0px;display:inline-block;height: 24px; vertical-align: middle;margin-top:8px;"><s:text name='sundyn.analyse.month'/></span>
    <input type="radio" name="type" value="year" title="<s:text name='sundyn.analyse.year'/>" style="vertical-align:middle;" /><span style="margin-top:0px;display:inline-block;height: 24px; vertical-align: middle;margin-top:8px;"><s:text name='sundyn.analyse.year'/></span>
    <img src="<s:text name='sundyn.total.pic.query'/>" width="80" height="25" style="cursor:pointer;" onclick="query()"/>
</div>

<div class="formbody">
    <div id="usual1" class="usual">
        <div class="itab">
            <ul>
                <li><a href="#tab1" class="selected">业务量分析</a></li>
                <li><a href="#tab2">弃号量分析</a></li>
                <li><a href="#tab3">平均等待时间分析</a></li>
                <li><a href="#tab4">平均办理时间分析</a></li>
                <li><a href="#tab5">满意额分析</a></li>
                <li><a href="#tab6">满意度分析</a></li>
            </ul>
        </div>
    </div>
    <div id="tab1" class="tabson">
        <div style="height:100%;width:100%;" id="chartcontainer1">
        </div>
    </div>
    <div id="tab2" class="tabson">
        <div style="height:100%;width:100%;" id="chartcontainer2">
        </div>
    </div>
    <div id="tab3" class="tabson">
        <div style="height:100%;width:100%;" id="chartcontainer3">
        </div>
    </div>
    <div id="tab4" class="tabson">
        <div style="height:100%;width:100%;" id="chartcontainer4">
        </div>
    </div>
    <div id="tab5" class="tabson">
        <div style="height:100%;width:100%;" id="chartcontainer5">
        </div>
    </div>
    <div id="tab6" class="tabson">
        <div style="height:100%;width:100%;" id="chartcontainer6">
        </div>
    </div>
</div>
<script type="text/javascript">
    $('.tablelist tbody tr:odd').addClass('odd');
</script>
</div>

<div id="treeContent" class="menuContent" style="display:none; position: absolute;">
    <ul id="treeDept" class="ztree" style="margin-top:0; width:380px; height: 300px;"></ul>
</div>
<script type="text/javascript" language="javascript">
    layui.use('layer', function () {
    });

    var curTab = "";
    $(function () {
        $("#usual1 ul").idTabs(function(id){
            curTab = id;
            loadChart(id);
            return true;
        });
    });

    function loadChart(id){
        var lay = layer.msg('加载中', {
            icon: 16
            ,shade: 0.1
        });
        var w = $(".placeul").width()-100;
        if(id == '#tab1'){
            analyseTotalAjax(w, "servicecount", 1, lay);
        }
        if(id == '#tab2'){
            analyseTotalAjax(w, "cancelcount", 2, lay);
        }
        if(id == '#tab3'){
            analyseTotalAjax(w, "totalwaittime", 3, lay);
        }
        if(id == '#tab4'){
            analyseTotalAjax(w, "totalservicetime", 4, lay);
        }
        if(id == '#tab5'){
            analyseTotalAjax(w, "totalkeymy", 5, lay);
        }
        if(id == '#tab6'){
            analyseTotalAjax(w, "totalmyd", 6, lay);
        }
    }

    //刷新
    function query(){
        loadChart(curTab);
    }
</script>
</body>
</html>

