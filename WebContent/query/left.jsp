<%@ page   pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<link rel="stylesheet"
	href="style/lib/ligerUI/skins/Aqua/css/ligerui-all.css" type="text/css" />
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script src="style/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerTree.js"></script>
<script type="text/javascript"
	src="style/lib/ligerUI/js/plugins/ligerAccordion.js"></script>
<%-- <link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css" />
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script> --%>
<title></title>
</head>
<script type="text/javascript">
var preClassName = ""; 
function list_sub_detail(Id, item) 
{ 
	return;
	if(preClassName != "") 
	{ 
		getObject(preClassName).className = "left_back" 
	} 
	if(getObject(Id).className == "left_back") 
	{ 
		getObject(Id).className = "left_back_onclick"; 
		outlookbar.getbyitem(item); 
		preClassName = Id 
	} 
} 
function getObject(objectId) 
{ 
	if(document.getElementById && document.getElementById(objectId)) 
	{ 
		return document.getElementById(objectId) 
	} 
	else if(document.all && document.all(objectId)) 
	{ 
		return document.all(objectId) 
	} 
	else if(document.layers && document.layers[objectId]) 
	{ 
		return document.layers[objectId] 
	} 
	else 
	{ 
		return false 
	} 
} 
function outlook() 
{ 
	this.titlelist = new Array(); 
	this.itemlist = new Array(); 
	this.addtitle = addtitle; 
	this.additem = additem; 
	this.getbytitle = getbytitle; 
	this.getbyitem = getbyitem; 
	this.getdefaultnav = getdefaultnav;
	//this.queryLeft = queryLeft;
	this.initaccordion = initaccordion;
} 
function theitem(intitle, insort, inkey, inisdefault) 
{ 
	this.sortname = insort; 
	this.key = inkey; 
	this.title = intitle; 
	this.isdefault = inisdefault 
} 
function addtitle(intitle, sortname, inisdefault) 
{ 
	outlookbar.itemlist[outlookbar.titlelist.length] = new Array(); 
	outlookbar.titlelist[outlookbar.titlelist.length] = new theitem(intitle, sortname, 0, inisdefault); 
	return(outlookbar.titlelist.length - 1) 
} 
function additem(intitle, parentid, inkey) 
{ 
	if(parentid >= 0 && parentid <= outlookbar.titlelist.length) 
	{ 
		insort = "item_" + parentid; 
		outlookbar.itemlist[parentid][outlookbar.itemlist[parentid].length] = new theitem(intitle, insort, inkey, 0); 
		return(outlookbar.itemlist[parentid].length - 1) 
	} 
	else additem = - 1 
} 
function getdefaultnav(sortname) 
{ 
	var output = ""; 
	for(i = 0; i < outlookbar.titlelist.length; i ++ ) 
	{ 
		if(outlookbar.titlelist[i].isdefault == 1 && outlookbar.titlelist[i].sortname == sortname) 
		{ 
			//output += "<div class=list_tilte id=sub_sort_" + i + " onclick=\"hideorshow('sub_detail_"+i+"')\">"; 
			//output += "<span>" + outlookbar.titlelist[i].title + "</span>"; 
			//output += "</div>"; 
			output += "<div class=list_detail id=sub_detail_" + i + "><ul>"; 
			for(j = 0; j < outlookbar.itemlist[i].length; j ++ ) 
			{ 
				output += "<li id=" + outlookbar.itemlist[i][j].sortname + j + " onclick=\"return changeframe('"+outlookbar.itemlist[i][j].title+"', '"+outlookbar.titlelist[i].title+"', '"+outlookbar.itemlist[i][j].key+"')\"><a href=#>" + outlookbar.itemlist[i][j].title + "</a></li>" 
			} 
			output += "</ul></div>";
		} 
	} 
	getObject('right_main_nav').innerHTML = output;
} 
function getbytitle(sortname) 
{ 
	var output = "<div><ul>"; 
	for(i = 0; i < outlookbar.titlelist.length; i ++ ) 
	{ 
		if(outlookbar.titlelist[i].sortname == sortname) 
		{ 
			output += "<li id=left_nav_" + i + " onclick=\"list_sub_detail(id, '"+outlookbar.titlelist[i].title+"')\" class=left_back> <div>" + outlookbar.titlelist[i].title + "</div></li>" 
		} 
	} 
	output += "</ul></div>"; 
	getObject('left_main_nav').innerHTML = output 
} 
function getbyitem(item) 
{ 
	var output = ""; 
	for(i = 0; i < outlookbar.titlelist.length; i ++ ) 
	{ 
		if(outlookbar.titlelist[i].title == item) 
		{ 
			//output = "<div class=list_tilte id=sub_sort_" + i + " onclick=\"hideorshow('sub_detail_"+i+"')\">"; 
			//output += "<span>" + outlookbar.titlelist[i].title + "</span>"; 
			//output += "</div>"; 
			output += "<div class=list_detail id=sub_detail_" + i + " style='display:block;'><ul>"; 
			for(j = 0; j < outlookbar.itemlist[i].length; j++) 
			{ 
				//output += "<li id=" + outlookbar.itemlist[i][j].sortname + "_" + j + " onclick=\"changeframe('"+outlookbar.itemlist[i][j].title+"', '"+outlookbar.titlelist[i].title+"', '"+outlookbar.itemlist[i][j].key+"');return false;\">" + outlookbar.itemlist[i][j].title + "</li>" 
			} 
			output += "</ul></div>";
		} 
	} 
	getObject('right_main_nav').innerHTML = output 
} 
function changeframe(item, sortname, src) 
{ 
	if(src != "") 
	{ 
		window.top.addTab(item, src);
	} 
	return false;
} 
function hideorshow(divid) 
{ 
	subsortid = "sub_sort_" + divid.substring(11); 
	if(getObject(divid).style.display == "none") 
	{ 
		getObject(divid).style.display = "block"; 
		getObject(subsortid).className = "list_tilte" 
	} 
	else 
	{ 
		getObject(divid).style.display = "none"; 
		getObject(subsortid).className = "list_tilte_onclick" 
	} 
} 
function initinav(sortname) 
{ 
	outlookbar.getdefaultnav(sortname); 
	outlookbar.getbytitle(sortname); 
	//window.top.frames['manFrame'].location = "manFrame.html" 
}
function initaccordion(){
	$("#right_main_nav").ligerAccordion({
		//height : 300
	});
}
/**
 * 菜单项鼠标Hover
 */
function hoverMenuItem() {
    $("#RightAccordion").find('li').hover(function() {
        $(this).addClass("hover");
    }, function() {
        $(this).removeClass("hover");
    });
}
function queryLeft(parentId){
	var pnls = $('#RightAccordion').accordion('panels'); // 得到选中panel
	console.log(pnls.length);
	while(pnls.length>0){
		$('#RightAccordion').accordion("remove", 0);
		pnls = $('#RightAccordion').accordion('panels');
	}
	$.ajax({
    	url :"/sundyn/getMenu.action",
    	dataType:"json",
    	success:function(data){
    		$.each(data, function (i, menu) {//循环创建手风琴的项
    			console.log(menu);
        		if(menu.parentId==parentId){
        			
        			var menulist = "";
        	        menulist += '<ul>';
        	        /* $.each(data, function(j, o) {
        	        	if(o.parentId == menu.id){
        	            	menulist += '<li onclick=\"changeframe(\''+o.menuName+'\', \''+o.menuName+'\', \''+o.nav+'\');return false;\"><div><a ref="' + o.id + '" href="javascript:void(0);" rel="'
        	                    + o.url + '" ><span class="icon ' + ""
        	                    + '" >&nbsp;</span><span class="nav">' + o.menuName
        	                    + '</span></a></div></li> ';
        	        	}
        	        }); */
        	        menulist += '</ul>';
        			//console.log(menu.menuName);
        	        $('#RightAccordion').accordion('add', {
                        title: menu.menuName,
                        collapsed:true,
                        collapsible:true,
                        content: '<div style="padding:10px"><ul name="'+menu.menuName+'"></ul></div>',
                        selected: true
                    });
        		}
    		});//end each
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
function queryLeft1(parentId){
	/* var pnl = $('#RightAccordion').accordion('getSelected'); // 得到选中panel
	if(pnl){
		var title = pnl.panel("options").title;
	    $('#RightAccordion').accordion("remove",title);
		 //pp.accordion('remove',0);
	} */
	var pnls = $('#RightAccordion').accordion('panels'); // 得到选中panel
	while(pnls.length>0){
		$('#RightAccordion').accordion("remove", 0);
		pnls = $('#RightAccordion').accordion('panels');
	}
	$.ajax({
    	url :"/sundyn/getMenu.action",
    	dataType:"json",
    	success:function(data){
    		$.each(data, function (i, menu) {//循环创建手风琴的项
    			//console.log(menu);
        		if(menu.parentId==parentId){
        			
        			var menulist = "";
        	        menulist += '<ul>';
        	        $.each(data, function(j, o) {
        	        	
        	        	if(o.parentId == menu.id){
        	        		/* $('#RightAccordion').accordion('add', {
                                title: menu.menuName,
                                content: "123",
                                selected: true
                            }); */
        	            	menulist += '<li onclick=\"changeframe(\''+o.menuName+'\', \''+o.menuName+'\', \''+o.nav+'\');return false;\"><div><a ref="' + o.id + '" href="javascript:void(0);" rel="'
        	                    + o.url + '" ><span class="icon ' + ""
        	                    + '" >&nbsp;</span><span class="nav">' + o.menuName
        	                    + '</span></a></div></li> ';
        	        	}
        	        });
        	        menulist += '</ul>';
        			//console.log(menu.menuName);
        	        $('#RightAccordion').accordion('add', {
                        title: menu.menuName,
                        collapsed:true,
                        collapsible:true,
                        content: menulist,
                        selected: true
                    });
        		}
    		});//end each
    		hoverMenuItem();

    	    $('#RightAccordion li').live('click', function() {
    	        var tabTitle = $(this).children('.nav').text();
    	        var url = $(this).attr("rel");
    	        var menuid = $(this).attr("ref");
    	        $('#RightAccordion li').removeClass("selected");
    	        $(this).addClass("selected");
    	    });
    	}
    });// end ajax
}
</script>
<style>
#RightAccordion ul{list-style-type:none;margin:0px; }
#RightAccordion ul li{ }
#RightAccordion ul li a{line-height:24px;text-decoration: none;}
#RightAccordion ul li div{border-bottom:1px dashed #99BBE8;}
#RightAccordion ul li.hover{background:#E0ECFF;cursor:pointer;}
#RightAccordion ul li.hover a{color:#416AA3;}
#RightAccordion ul li.selected{color:#416AA3;background:#E0ECFF;}
</style>
<script type="text/javascript" src="js/${jsurl}"></script>
<script>
        $(function(){
            jQuery("#RightAccordion").accordion({ //初始化accordion
                fillSpace:true,
                fit:true,
                border:false,
                animate:false  
            });
            queryLeft(1);
        });
    </script>
<body style="overflow-x:hidden ">
	<div id="RightAccordion" class="easyui-accordion" >
	</div>
	<!-- <div id="left_content">
		<div id="main_nav">
			<div id="right_main_nav"></div>
		</div>
	</div> -->
</body>
</html>
 