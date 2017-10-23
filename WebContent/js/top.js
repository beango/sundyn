/**
 * 
 */

$(function(){
			$.ajax({
            	url :"/sundyn/getMenu.action",
            	dataType:"json",
            	success:function(data){
            		$.each(data, function (i, menu) {
	            		if(menu.parentId==0){
	            			var cls = "bg_image";
	            			if(i == 0)
	            				cls = "bg_image_onclick";
	            			$("#nav ul").append("<li id=\"man_nav_"+(i+1)+"\" onclick=\"list_sub_nav(id,'"+menu.menuName+"',"+menu.id+")\" class=\""+cls+"\">"+menu.menuName+"</li>")
	            			
	            		}
            		});
            	}
            });
		});
var preClassName = "man_nav_1";
		function list_sub_nav(Id,sortname,menuid){
		   if(preClassName != ""){
		      getObject(preClassName).className="bg_image";
		   }
		   if(getObject(Id).className == "bg_image"){
			  //addPanel(Id, sortname);
		      getObject(Id).className="bg_image_onclick";
		      preClassName = Id;
			  //showInnerText(Id);
			  //window.top.frames['leftFrame'].queryLeft(0);
			  //console.log(window.top.frames['leftFrame'].contentWindow.queryLeft(0));
			  window.top.queryLeft(menuid);
			  //$(window.top.frames['leftFrame'])[0].outlookbar.getdefaultnav(sortname);
			  
			  //window.top.frames['leftFrame'].outlookbar.getdefaultnav(sortname); //queryLeft(menuid);
			  //window.top.frames['leftFrame'].outlookbar.getbytitle(sortname);
			  //window.top.frames['leftFrame'].outlookbar.initaccordion();
		   }
		}
		function addPanel(Id, sortname){
			 var switchId = parseInt(Id.substring(8));
	            var linkUrl = "";
	            switch(switchId){
			    case 1:
			    	linkUrl = 'queryIndex.action'; 
				   showText =  "<s:text name='main.welcome' />";
		 		   break;
			    case 2:
			    	linkUrl = 'queryDept.action'; 
				   showText =  "<s:text name='left.menu.dh2' />";
				   break;
			    case 3:
			    	linkUrl = 'analyseTotal.action'; 
				   showText =  "<s:text name='left.menu.dh3' />";
				   break;		   
			    case 4:
			       window.top.managerQx();
 				   showText =  "<s:text name='left.menu.dh4' />";
				   break;	
			    case 5:
				   showText =  "<s:text name='main.help' />";
				   linkUrl = 'baseHelp.action';
				   break;	
				case 7:
	 				 showText =  "<s:text name='left.menu.dh7' />";
	 					linkUrl = 'adviceShowAnserTable.action';
	 				 break;
				case 6:
 				 showText =  "<s:text name='main.fullScreen' />"; 
				break;  	   		   
			}
	            window.top.addTab(sortname, linkUrl);
	            
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