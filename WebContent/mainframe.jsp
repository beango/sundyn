<%@page pageEncoding="UTF-8"%>
<%@page import="com.sundyn.util.SundynSet"%>
<%
String path=request.getRealPath("/");
SundynSet sundynSet=SundynSet.getInstance(path);
String url=sundynSet.getM_content().get("requestAddress").toString();
 %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title>管理导航区域</title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript">
		var preClassName = "man_nav_1";
		function list_sub_nav(Id,sortname){
		   if(preClassName != ""){
		      getObject(preClassName).className="bg_image";
		   }
		   if(getObject(Id).className == "bg_image"){
		      getObject(Id).className="bg_image_onclick";
		      preClassName = Id;
			  showInnerText(Id);
			  window.top.frames['leftFrame'].outlookbar.getbytitle(sortname);
			  window.top.frames['leftFrame'].outlookbar.getdefaultnav(sortname);
		   }
		}
		
		function showInnerText(Id){
		    var switchId = parseInt(Id.substring(8));
		    var tip= document.getElementById("nav_tip")
		      tip.style.backgroundImage="url(images/menu_top"+switchId+".gif)";   
		      tip.style.backgroundPositionX="0px";   
		      tip.style.backgroundPositionY="0px";   
		 	var showText = "<s:text name='main.nomsg' />";
		    	switch(switchId){
			    case 1:
			       window.top.frames['manFrame'].location = 'queryIndex.action'; 
				   showText =  "<s:text name='main.welcome' />";
		 		   break;
			    case 2:
			    window.top.frames['manFrame'].location.href = 'queryDept.action'; 
				   showText =  "<s:text name='left.menu.dh2' />";
				   break;
			    case 3:
			    window.top.frames['manFrame'].location = 'analyseTotal.action'; 
				   showText =  "<s:text name='left.menu.dh3' />";
				   break;		   
			    case 4:
			    managerQx();
 				   showText =  "<s:text name='left.menu.dh4' />";
				   break;	
			    case 5:
				   showText =  "<s:text name='main.help' />";
				   window.top.frames['manFrame'].location = 'baseHelp.action';
				   break;	
				case 7:
	 				 showText =  "<s:text name='left.menu.dh7' />";
	 				 window.top.frames['manFrame'].location = 'adviceShowAnserTable.action';
	 				 break;
				case 6:
 				 showText =  "<s:text name='main.fullScreen' />"; 
				break;  	   		   
			}
			getObject('show_text').innerHTML = showText;
		}
		 //获取对象属性兼容方法
		 function getObject(objectId) {
		    if(document.getElementById && document.getElementById(objectId)) {
			// W3C DOM
			return document.getElementById(objectId);
		    } else if (document.all && document.all(objectId)) {
			// MSIE 4 DOM
			return document.all(objectId);
		    } else if (document.layers && document.layers[objectId]) {
			// NN 4 DOM.. note: this won't find nested layers
			return document.layers[objectId];
		    } else {
			return false;
		    }
		}
		function back(){
		 parent.manFrame.history.back();
		
		}
		function login(){
		 parent.location.href="<%=url%>"
 		}
		</script>
	</head>
	<body style="overflow: hidden;">
			<div id="nav">
				<ul>
					<li id="man_nav_1" onclick="list_sub_nav(id,'<s:text name="left.menu.dh1"/>')"
						class="bg_image_onclick">
						<s:text name="main.homePage" />
					</li>
					<li id="man_nav_2" onclick="list_sub_nav(id,'<s:text name="left.menu.dh2"/>')"
						class="bg_image">
						<s:text name="left.menu.dh2" />
					</li>
					<li id="man_nav_3" onclick="list_sub_nav(id,'<s:text name="left.menu.dh3"/>')"
						class="bg_image">
						<s:text name="left.menu.dh3" />
					</li>
					<li id="man_nav_4" onclick="list_sub_nav(id,'<s:text name="left.menu.dh4"/>')"
						class="bg_image">
						<s:text name="left.menu.dh4" />
					</li>
					<li id="man_nav_7" onclick="list_sub_nav(id,'<s:text name="left.menu.dh7"/>')"
						class="bg_image">
						<s:text name="left.menu.dh7" />
					</li>
					<li id="man_nav_5" onclick="list_sub_nav(id,'<s:text name="left.menu.dh5"/>')" class="bg_image">
						<s:text name="left.menu.dh5" />
					</li>
					<!-- 
					<li id="man_nav_6" onclick="login()" class="bg_image">
						大液晶评价
					</li>
					 -->
				</ul>
			</div>
			<div id="nav_tip">
				<span id="show_text"><s:text name="main.welcome" /></span>
				<span id="back"> <!-- <a href="#" onclick="back()">返回</a> -->   </span>
			</div>
	</body>
</html>
