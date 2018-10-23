<%@ page pageEncoding="UTF-8"%>
<%@page import="com.sundyn.util.SundynSet"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getRealPath("/");
	SundynSet sundynSet = SundynSet.getInstance(path);
	String url = sundynSet.getM_content().get("requestAddress").toString();
%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang='en'>
<head>
    <meta charset='utf-8'>
    <meta content='ie=edge' http-equiv='x-ua-compatible'>
    <title>
        <s:text name="sundyn.title"/>
    </title>
    <meta content='' name='description'>
    <meta content='width=device-width, initial-scale=1' name='viewport'>

    <link href="${ctx}/assets/stylesheets/application.css?14427995576" rel="stylesheet"/>
    <link href="${ctx}/assets/stylesheets/fontawesome.css?1440992355" rel="stylesheet"/>
    <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctx}/assets/stylesheets/main.css?1514875963" media="screen">
    <%--<link rel="stylesheet" href="${ctx}/js/easyui-1.5.3/themes/metro/easyui.css" media="screen">--%>
</head>
<body class='easyui-layout theme-default'>
<div class='header' style="display: block;" data-options="region:'north', split:false, border:false, height:45">
    <div class='logo'>
        <span class='logo-text' style="font-size: 20px;"><s:text name="sundyn.title"/></span>
    </div>
    <div class='navbar-header pull-right'>
        <ul class='list-inline nav'>
            <li>
                <a href="managerLogout.action" style="color:white;"><i class='fa fa-power-off' style="margin-right:6px;"></i>退出
                </a>
            </li>
        </ul>
    </div>
    <div class='user-nav pull-right'>
        <ul class='list-inline list-user-info'>
            <li>
                <i class='fa fa-user-md' style="margin-right:6px;"></i>
                <strong id="realname">
                </strong>
            </li>
        </ul>
    </div>
    <%--<div class='user-nav pull-right' style="background:#008080;padding:0 10px 0 10px;">
        <ul class='list-inline list-user-info'>
            <li>
                <i class='fa fa-medkit' style="margin-right:6px;"></i>查询统计
            </li>
        </ul>
    </div>--%>
</div>
<div class='sidebar' data-options="region:'west', split:false, border:false, headerCls:'sidebar-header'">
    <ul class='sidebar-nav' id='mainSlideMenu'></ul>
</div>
<div data-options="region:'center', border:false">
    <div class='easyui-tabs' id='mainTabs'>
        <%--<div title="首页" style="overflow:hidden;" closable="false">
            <div style="overflow:hidden;width:100%;height:100%;"><iframe id="rightFrame" name="rightFrame" style="width:100%;height:100%;" scrolling="no" frameborder="0" src="queryIndex.action"></iframe></div>
        </div>--%>
    </div>
</div>
<div  id='open'>

</div>
<div class='easyui-menu' data-options='itemHeight:28' id='mainTabMenu' style="display: none;">
    <div data-options="iconCls:'fa fa-refresh'" id='refresh'>
        <small>刷新</small>
    </div>
    <div data-options="iconCls:'fa fa-times'" id='close'>
        <small>关闭</small>
    </div>
    <div data-options="iconCls:'fa fa-ban'" id='closeall'>
        <small>全部关闭</small>
    </div>
    <div data-options="iconCls:'fa fa-ellipsis-h'" id='closeother'>
        <small>除此之外全部关闭</small>
    </div>
    <div data-options="iconCls:'fa fa-caret-square-o-right'" id='closeright'>
        <small>当前页右侧全部关闭</small>
    </div>
    <div data-options="iconCls:'fa fa-caret-square-o-left'" id='closeleft'>
        <small>当前页左侧全部关闭</small>
    </div>
</div>

<script src="${ctx}/js/jquery-2.1.3.min.js"></script>
<script src="${ctx}/js/easyui-1.5.3/jquery.easyui.min.js"></script>
<script>
    var ctx0 = "/zxweb";
</script>
<script src="${ctx}/js/main.js"></script>
<script>

    function CreatNodeAll()
    {
        return;
        console.log("创建层");
        var NewMask=window.document.createElement("div");
        NewMask.id="Mask";
        NewMask.style.position="absolute";
        NewMask.style.top="0";
        NewMask.style.left="0";
        NewMask.style.zIndex="1";
        NewMask.style.backgroundColor="#ccc";
        NewMask.style.filter="alpha(opacity=30)";
        //NewMask.style.opacity="0.7";
        NewMask.style.width="201";
        NewMask.style.height=(window.document.body.scrollHeight+50)+"px";


        var TopMask=window.document.createElement("div");
        TopMask.id="TopMask";
        TopMask.style.position="absolute";
        TopMask.style.top="0";
        TopMask.style.left="201";
        TopMask.style.zIndex="1";
        TopMask.style.backgroundColor="#ccc";
        TopMask.style.filter="alpha(opacity=30)";
        //TopMask.style.opacity="0.7";
        TopMask.style.width="100%";
        TopMask.style.height="20%";
        window.document.body.appendChild(TopMask);

        window.document.body.appendChild(NewMask);
    }


    //移除遮罩层
    function CancelAll()
    {
        return;
        window.parent.document.body.removeChild(window.parent.document.getElementById("TopMask"));
        window.parent.document.body.removeChild(window.parent.document.getElementById("Mask"));
    }

    qc.main.ctx = "/zxweb";
    function openTab(title, url)
    {
        parent.qc.main.mainTabs.tabs('add', {
            title : title,
            content : '<iframe class="iframe-fluid" src="'+url+'"></iframe>',
            closable : true,
            selected : true
        });

        var tabsSelected = parent.$('.tabs-selected');
        var mainTabMenu = parent.$('#mainTabMenu');
        tabsSelected.bind('contextmenu', function(event) {
            mainTabMenu.menu('show', {
                left : event.pageX,
                top : event.pageY
            });
            var subtitle = $(this).children().first().text();
            mainTabMenu.data("currtab", subtitle);
            parent.qc.main.mainTabs.tabs('select', subtitle);
            return false;
        });

        var e = window.event || e;
        if (document.all) {
            e.cancelBubble = true;
        } else {
            e.stopPropagation();
        }
    }

    $(function(){
        loadmenu();

        $.ajax({
            url: "topframejson.action",
            dataType: 'json',
            success:function(data){
                $("#realname").html(data.realname + " - " + data.powername + " - " + data.deptname);
            }
        });

        qc.main.addTab("首页", "queryIndex.action", 'fa fa-home', true);
    })

    function opendialog(title, substant){
        $.messager.alert(title, substant);
    }
</script>
<script src="${ctx}/js/application.js"></script>

</body>
</html>
