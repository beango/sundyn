<%@ page pageEncoding="UTF-8"%>
<%@page import="com.sundyn.util.SundynSet"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getRealPath("/");
	SundynSet sundynSet = SundynSet.getInstance(path);
	String url = sundynSet.getM_content().get("requestAddress").toString();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="shortcut icon" href="/logo.ico" type="image/x-icon" />
<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="css/wu.css" />
<script type="text/javascript" src="js/dojo.js"></script>
<script type="text/javascript"
	src="js/my_<s:text name='sundyn.language' />.js"></script>
<script type="text/javascript" src="js/easyui-1.5.3/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/easyui-1.5.3/themes/bootstrap/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/easyui-1.5.3/themes/icon.css" />
<script type="text/javascript" src="js/easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
<script type="text/javascript"> 
var tab = null;
$(function ()
{ 
	$.ajax({
		url: "topframejson.action",
		dataType: 'json',
		success:function(data){
			$("#realname").html(data.realname)
		}
	});
	loadMenu();
});

function loadMenu(){
	$.ajax({
    	url :"/sundyn/getMenu.action",
    	dataType:"json",
    	success:function(data){
    		$.each(data, function (i, menu) {
        		if(menu.parentId==0){
        			var cls = "btn";
        			if(i == 0)
        				cls = "btn active";
        			$('#leftmenu').accordion('add',{
                        title: menu.menuName,
                        selected: i===0,
                        content: getSubMenu(data, menu.id),
                    });
        		}
    		});
    		$('.wu-side-tree li').bind("click",function(){
    			var link = $(this).find("a");
    			var title = $(link).text();
    			var url = $(link).attr('data-link');
    			console.log(url)
    			var iconCls = $(link).attr('data-icon');
    			var iframe = $(link).attr('iframe')==1?true:false;
    			addTabMenu(title,url,iconCls,iframe);
    		});	
    	}
    });
}

function getSubMenu(data, parentid){
	var h = "<ul class=\"easyui-tree wu-side-tree\">";
	for(var i=0; i<data.length; i++){
		if(parentid == data[i].parentId){
			h += "<li><a href=\"javascript:void(0)\" data-link=\""
			+data[i].nav+"\" iframe=\"1\">"+data[i].menuName+"</a></li>"
		}
	}
	h += "</ul>";
	return h;
}
/**
* Name 添加菜单选项
* Param title 名称
* Param href 链接
* Param iconCls 图标样式
* Param iframe 链接跳转方式（true为iframe，false为href）
*/	
function addTabMenu(title, href, iconCls, iframe){
	var tabPanel = $('#wu-tabs');
	if(!tabPanel.tabs('exists',title)){
		var content = '<iframe scrolling="auto" frameborder="0"  src="'+ href +'" style="width:100%;height:100%;"></iframe>';
		if(iframe){
			tabPanel.tabs('add',{
				title:title,
				content:content,
				iconCls:iconCls,
				fit:true,
				cls:'pd3',
				closable:true
			});
		}
		else{
			tabPanel.tabs('add',{
				title:title,
				href:href,
				iconCls:iconCls,
				fit:true,
				cls:'pd3',
				closable:true
			});
		}
	}
	else
	{
		tabPanel.tabs('select',title);
	}
}

</script>
<style type="text/css">

</style>

<title><s:text name="sundyn.title" /></title>
</head>
<body class="easyui-layout">
	<!-- begin of header -->
	<div class="wu-header" data-options="region:'north',border:false,split:true" style="height:60px;">
			<div class="wu-header-left">
				<h1><s:text name='sundyn.title'/></h1>
			</div>
			<div class="wu-header-right" style="padding-right:150px;">
				<ul id="topnav"></ul>
			</div>
			<div class="wu-header-right">
				<p><strong class="easyui-tooltip" title="2条未读消息" id="realname">${name}</strong>，欢迎您！</p>
				<p><!-- <a href="#">网站首页</a>|<a href="#">支持论坛</a>|<a href="#">帮助中心</a>| --><a href="managerLogout.action"><s:text name="left.logout"/></a></p>
			</div>
	</div>
	<!-- end of header -->
	<!-- begin of sidebar -->
	<div class="wu-sidebar" data-options="region:'west',split:true,border:true"> 
    	<div id="leftmenu" class="easyui-accordion" data-options="border:false,fit:true">
        </div>
    </div>	
    <!-- end of sidebar -->  
	<!-- begin of main -->
    <div class="wu-main" data-options="region:'center'">
        <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">  
            <div title="首页" data-options="href:'queryIndex.action',closable:false,iconCls:'icon-tip',cls:'pd3'"></div>
        </div>
    </div>
    <!-- end of main --> 
    
	<div id="layout1">
		<div position="top">
			<div id="tttop" style="width: 100%; overflow: hidden; border: 0;top:-30px;">
		     	<!-- <iframe src="topframe.jsp"></iframe> -->
			</div>
		</div>
		<div position="left" id="layoutleft">
			
			<!-- <div id="ttleft" style="width: 100%; overflow: hidden; border: 0;top:-30px;">
				<iframe id="leftFrame" src="queryLeft.action?a=1" border="0" onload="this.style.height =(document.documentElement.clientHeight-80)+'px'; "></iframe>
	     	</div> -->
		</div>
		<div position="center" id="framecenter" title="" style="border:0;padding:0;">
	  		<div id="tt" style="width: 100%; overflow: hidden; border: 0;" class="liger-tab">
	          
	     	</div>
		</div>
		<!-- <div position="bottom"></div> -->
	</div>
</body>
</html>
