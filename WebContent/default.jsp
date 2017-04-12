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
<script type="text/javascript" src="js/dojo.js"></script>
<script type="text/javascript"
	src="js/my_<s:text name='sundyn.language' />.js"></script>
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<link rel="stylesheet" href="style/cotide/css/bootstrap.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="style/lib/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />
<script src="style/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerLayout.js"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerPanel.js"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerTab.js"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerDrag.js"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerGrid.js"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerAccordion.js"></script>
	<link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css" />
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
<script type="text/javascript"> 
var tab = null;
$(function ()
{ 
	$("#layout1").ligerLayout({ leftWidth: 230,height: '100%',heightDiff:0,space:4, onHeightChanged: f_heightChanged }); 
	$("#tttop").ligerPanel({width:"100%",url:"topframe.action"});
	//$("#ttleft").ligerPanel({height:"100%",url:"queryLeft.action"});
	var tt = $("#tt").ligerTab({changeHeightOnResize:true,height:500});
	tt.addTabItem({ tabid: "首页", text: "首页", url: "queryIndex.action" });
	tab = liger.get("framecenter");
	var height = $(".l-layout-center").height();
	$("#layoutleft").height(height-27);
	
	jQuery("#ttleft").accordion({ //初始化accordion
        fillSpace:true,
        fit:true,
        border:false,
        animate:false  
    });
	
	queryLeft(1);
});

function f_heightChanged(options)
{  
    /* if (tab)
        tab.addHeight(options.diff);
    if (accordion && options.middleHeight - 24 > 0)
        accordion.setHeight(options.middleHeight - 24); */
}

</script>
<style type="text/css">
#layout1 {
	margin: 0;
	padding: 0;
}
</style>

<title><s:text name="sundyn.title" /></title>
</head>
<body>
	<div id="layout1">
		<div position="top">
			<div id="tttop" style="width: 100%; overflow: hidden; border: 0;top:-30px;">
		     	<!-- <iframe src="topframe.jsp"></iframe> -->
			</div>
		</div>
		<div position="left" id="layoutleft">
			<div id="ttleft" class="easyui-accordion" data-options="fit:true,border:false,nimate:true,lines:true">
			</div>
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
<script type="text/javascript">
	/** 动态添加tab     **/
	function addTab(title, href, icon) {
		var tt = liger.get("tt");
		var ttlist = tt.getTabidList();
		tt.addTabItem({ tabid: title, text: title, url: href });
	}
	function queryLeft(parentId){
		$('#ttleft').accordion({onSelect:function(){}});
		var pnls = $('#ttleft').accordion('panels'); // 得到选中panel
		while(pnls.length>0){
			$('#ttleft').accordion("remove", 0);
			pnls = $('#ttleft').accordion('panels');
		}
		$.ajax({
	    	url :"/sundyn/getMenu.action",
	    	dataType:"json",
	    	success:function(data){
	    		$.each(data, function (i, menu) {//循环创建手风琴的项
	        		if(menu.parentId==parentId){
	        	        $('#ttleft').accordion('add', {
	                        title: menu.menuName,
	                        content: '<div style="padding:6px 0 6px;"><ul name="'+menu.menuName+'"></ul></div>',
	                        selected: i == 0
	                    });
	        		}
	    		});//end each
	    		//异步加载子节点，即二级菜单  
	            $('#ttleft').accordion({  
	                onSelect : function(title, index) {
	                	var menuData = {
	                			animate : true,
	                			data:[],
	                			onClick: function(node){// 在用户点击一个子节点即二级菜单时触发addTab()方法,用于添加tabs  
	                                if(node.url){
	                                    addTab(node.text, node.url);
	                                    return false;
	                                }  
	                            }  
	                	};
	                	var id=0;
	                	$.each(data, function(j, o) {
	        	        	if(o.menuName == title){
	        	        		id = o.id;
	        	        	}
	        	        });
	                	$.each(data, function(j, o) {
	        	        	if(o.parentId == id){
	        	        		menuData.data.push({"id":j,"text":o.menuName,"state" :"open", url: o.nav});
	        	        	}
	        	        });
	                	
	                    $("ul[name='" + title + "']").tree(menuData);  
	                }  
	            });  
	            /* hoverMenuItem();
				
	    		$('#RightAccordion li').live('click', function() {
	    	        var tabTitle = $(this).children('.nav').text();
	    	        var url = $(this).attr("rel");
	    	        var menuid = $(this).attr("ref");
	    	        $('#RightAccordion li').removeClass("selected");
	    	        $(this).addClass("selected");
	    	    }); */
	    	}
	    });// end ajax
	}
</script>
</html>
