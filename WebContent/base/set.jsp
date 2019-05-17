<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='zx.title'/></title>
		<style type="text/css">
		   table tr td {
	        font-size: 12px;
           }
		</style>
		<script language=javascript>
			var ocolorPopup = window.createPopup(); 
			var ecolorPopup=null;
			
			function hide(){
			ocolorPopup.hide;  
			}
			
			function colordialogmouseout(obj){ 
			obj.style.borderColor=""; 
			obj.bgColor=""; 
			}
			
			function colordialogmouseover(obj){ 
			obj.style.borderColor="#0A66EE"; 
			obj.bgColor="#EEEEEE"; 
			}
			
			function colordialogmousedown(color){ 
			ecolorPopup.value=color;
			//ecolorPopup.fontcolor=color; 
			ocolorPopup.document.body.blur();
			ocolorPopup.hide();   
			}
			
			function colordialogmore(){ 
			var sColor=dlgHelper.ChooseColorDlg(ecolorPopup.value); 
			sColor = sColor.toString(16); 
			if (sColor.length < 6) { 
			var sTempString = "000000".substring(0,6-sColor.length); 
			sColor = sTempString.concat(sColor); 
			} 
			ecolorPopup.value="#"+sColor.toUpperCase();
			//document.body.bgColor="#"+sColor.toUpperCase(); 
			ocolorPopup.document.body.blur(); 
			}
			
			function colordialog(){ 
			var e=event.srcElement; 
			e.onkeyup=colordialog; 
			ecolorPopup=e; 
			var ocbody; 
			var oPopBody = ocolorPopup.document.body; 
			var colorlist=new Array(40); 
			oPopBody.style.backgroundColor = "#f9f8f7"; 
			oPopBody.style.border = "solid #999999 1px"; 
			oPopBody.style.fontSize = "12px";
			
			colorlist[0]="#000000"; colorlist[1]="#993300"; colorlist[2]="#333300"; colorlist[3]="#003300"; 
			colorlist[4]="#003366"; colorlist[5]="#000080"; colorlist[6]="#333399"; colorlist[7]="#333333";
			
			colorlist[8]="#800000"; colorlist[9]="#FF6600"; colorlist[10]="#808000";colorlist[11]="#008000"; 
			colorlist[12]="#008080";colorlist[13]="#0000FF";colorlist[14]="#666699";colorlist[15]="#808080";
			
			colorlist[16]="#FF0000";colorlist[17]="#FF9900";colorlist[18]="#99CC00";colorlist[19]="#339966"; 
			colorlist[20]="#33CCCC";colorlist[21]="#3366FF";colorlist[22]="#800080";colorlist[23]="#999999";
			
			colorlist[24]="#FF00FF";colorlist[25]="#FFCC00";colorlist[26]="#FFFF00";colorlist[27]="#00FF00"; 
			colorlist[28]="#00FFFF";colorlist[29]="#00CCFF";colorlist[30]="#993366";colorlist[31]="#CCCCCC";
			
			colorlist[32]="#FF99CC";colorlist[33]="#FFCC99";colorlist[34]="#FFFF99";colorlist[35]="#CCFFCC"; 
			colorlist[36]="#CCFFFF";colorlist[37]="#99CCFF";colorlist[38]="#CC99FF";colorlist[39]="#FFFFFF";
			
			ocbody = "<div id='colortable'>"; 
			ocbody += "<table CELLPADDING=0 CELLSPACING=3>"; 
			ocbody += "<tr height=\"20\" width=\"20\"><td align=\"center\"><table style=\"border:1px solid #808080;\" width=\"12\" height=\"12\" bgcolor=\""+e.value+"\"><tr><td></td></tr></table></td><td bgcolor=\"eeeeee\" colspan=\"7\" style=\"font-size:12px;\" align=\"center\">当前颜色</td></tr>"; 
			for(var i=0;i<colorlist.length;i++){ 
			if(i%8==0) 
			ocbody += "<tr>"; 
			ocbody += "<td width=\"14\" height=\"16\" style=\"border:1px solid;\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" onMouseDown=\"parent.colordialogmousedown('"+colorlist[i]+"')\" align=\"center\" valign=\"middle\"><table style=\"border:1px solid #808080;\" width=\"12\" height=\"12\" bgcolor=\""+colorlist[i]+"\"><tr><td></td></tr></table></td>"; 
			if(i%8==7) 
			ocbody += "</tr>"; 
			} 
			ocbody += "<tr><td align=\"center\" height=\"22\" colspan=\"8\" onMouseOut=\"parent.colordialogmouseout(this);\" onMouseOver=\"parent.colordialogmouseover(this);\" style=\"border:1px solid;font-size:12px;cursor:default;\" onMouseDown=\"parent.colordialogmore()\">其它颜色...</td></tr>"; 
			ocbody += "</table></div>";
			
			oPopBody.innerHTML=ocbody; 
			
			ocolorPopup.show(event.clientX , event.clientY +e.offsetHeight, 158, 147, document.body);
			//相对容器的坐标
			//ocolorPopup.show(e.offsetLeft, e.offsetTop+e.offsetHeight, 108, 147, document.body); 
			//获取对象相对于版面或由 offsetParent 属性指定的父坐标的计算位置 
			} 
			
			//获取屏幕中心位置
			var x = (screen.width-420)/2;   
			var y = (screen.height-220)/2;  
			var url
			//评价界面预览
			function Positionshow(){
			var p1 =  document.getElementById("key1").value;
			var p2 =  document.getElementById("key2").value;
			var p3 =  document.getElementById("key3").value;
			var p4 =  document.getElementById("key4").value;
			var p5 =  document.getElementById("key5").value;
			var p6 =  document.getElementById("key6").value;
			var bordercolor = document.getElementById("buttonBorderColor").value;
			//颜色值传递去掉前边的#
			bordercolor = bordercolor.substr(1);
			url = 'base/Positionshow.htm?p1='+p1+'&p2='+p2+'&p3='+p3+'&p4='+p4+'&p5='+p5+'&p6='+p6+'&bordercolor='+bordercolor;
			window.open (url,'newwindow','height=270,width=340,top='+x+',left='+y+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no'); 
			}
			
			//游屏信息预览
			function Anilabeshow(){
			var Content = document.getElementById("content").value;
			var Time = document.getElementById("speed").value;
			var FontColor = document.getElementById("textColor").value;
			var FontBackColor = document.getElementById("textBackColor").value;
			//颜色值传递去掉前边的#
			FontColor = FontColor.substr(1);
			FontBackColor = FontBackColor.substr(1);
			url = 'base/Anilabeshow.htm?Content='+Content+'&Time='+Time+'&FontColor='+FontColor+'&FontBackColor='+FontBackColor;
 			window.open (url,'newwindow','height=270,width=340,top='+x+',left='+y+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
			}
 			//欢迎界面预览
			function Welcomeshow(){
			var p1 = document.getElementById("employeePicture").value;
			var i1 = document.getElementById("employeeInfo").value;
			var s1 = document.getElementById("employeeStart").value;
			var scolor = document.getElementById("starColor").value;
			scolor = scolor.substr(1);
			url = 'base/Welcomeshow.htm?p1='+p1+'&i1='+i1+'&s1='+s1+'&scolor='+scolor;
			window.open (url,'newwindow','height=270,width=340,top='+x+',left='+y+',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
			}
			</script>
	</head>
	<body>
		<div id="man_zone">
		<form action="baseSave.action" method="get">
		   <div class="fengge">&nbsp;</div>
           <div align="center"><img src="images/12_01.jpg" /></div>
		   <div style="width:728px;" class="kuang">
			     <div class="fengge"></div>
				 <div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 200px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">游屏设置</div> </div>
				 <div style="width:708px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7; padding-top:10px;" >
			       <table width="100%" height="63" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
			            <tr>
			              <td style="border-color:#FFFFFF;" width="16%" align="right"  >游屏文字：</td>
			              <td style="border-color:#FFFFFF;" colspan="5"><label>
			                	<input type="text" value="${saveTextVo.content}" size="50"	name="saveTextVo.content" id="content"  />
			              </label></td>
			            </tr>
			            <tr>
			              <td style="border-color:#FFFFFF;" align="right"  >滚动速度（秒）：</td>
			              <td   align="left" style="border-color:#FFFFFF;">	<input type="text" value="${saveTextVo.speed}"	name="saveTextVo.speed" id="speed" class="input_comm" style="width: 80px;" /></td>
			              <td   align="right" style="border-color:#FFFFFF;">游屏字体颜色：</td>
			              <td   align="left" style="border-color:#FFFFFF;"><input type="text" onclick="colordialog()"	value="${saveTextVo.textColor}" name="saveTextVo.textColor"	id="textColor" class="input_comm" style="width: 80px;" /></td>
			              <td  align="right" style="border-color:#FFFFFF;">游屏背景颜色：</td>
			              <td  align="left" style="border-color:#FFFFFF;"><input type="text" onclick="colordialog()"	value="${saveTextVo.textBackColor}"	name="saveTextVo.textBackColor" id="textBackColor" class="input_comm" style="width: 80px;" /></td>
			            </tr>
			       </table>   
	             </div>
				<div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 500px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">评价按钮位置设置（顺序是左、上、右、下）</div> </div>
				<div style="width:708px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7; padding-top:10px;" >
			       <table width="90%" height="63" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
			            <tr>
			              <td align="right" style="border-color:#FFFFFF;">按键一：</td>
			              <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.key1}" name="saveTextVo.key1" id="key1" /></td>
			              <td align="right" style="border-color:#FFFFFF;">按键二：</td>
			              <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.key2}" name="saveTextVo.key2" id="key2" /></td>
			              <td align="right" style="border-color:#FFFFFF;">按键三：</td>
			              <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.key3}" name="saveTextVo.key3" id="key3" /></td>
			            </tr>
			            <tr>
			              <td align="right" style="border-color:#FFFFFF;">按键四：</td>
			              <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.key4}" name="saveTextVo.key4" id="key4" /></td>
			              <td align="right" style="border-color:#FFFFFF;">按键五：</td>
			              <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.key5}" name="saveTextVo.key5" id="key5" /></td>
			              <td align="right" style="border-color:#FFFFFF;">按键六：</td>
			              <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.key6}" name="saveTextVo.key6" id="key6" /></td>
			            </tr>
			          </table>     
				 </div>
				 <div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 500px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">员工部分位置设置（顺序是左、上）</div> </div>
				 <div style="width:708px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7; padding-top:10px;padding-bottom: 5px;" >
				   <table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
			         <tr>
			           <td align="right" style="border-color:#FFFFFF;">员工照片：</td>
			           <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.employeePicture}" name="saveTextVo.employeePicture" id="employeePicture" class="input_comm" style="width: 80px" /></td>
			           <td align="right" style="border-color:#FFFFFF;">员工信息：</td>
			           <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.employeeInfo}" name="saveTextVo.employeeInfo" id="employeeInfo" class="input_comm" style="width: 80px" /></td>
			           <td align="right" style="border-color:#FFFFFF;">员工星级显示：</td>
			           <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.employeeStart}"	name="saveTextVo.employeeStart" id="employeeStart" class="input_comm" style="width: 80px" /></td>
			         </tr>
			       </table>
				 </div>
				 <div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 500px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">时间设置</div> </div>
				 <div style="width:708px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7; padding-top:10px;padding-bottom: 5px;" >
				   <table width="95%" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
			         <tr>
			           <td align="right" style="border-color:#FFFFFF;">	评价超时(秒)：</td>
			           <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.arrpaise}" name="saveTextVo.arrpaise" id="arrpaise" class="input_comm" style="width: 80px" /></td>
			           <td align="right" style="border-color:#FFFFFF;">员工卡显示(秒)：</td>
			           <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.card}"	name="saveTextVo.card" id="card" class="input_comm" style="width: 80px" /></td>
			           <td align="right" style="border-color:#FFFFFF;">广告显示(秒)：</td>
			           <td style="border-color:#FFFFFF;"><input type="text" value="${saveTextVo.picture}"	name="saveTextVo.picture" id="picture" class="input_comm" style="width: 80px" /></td>
			         </tr>
			       </table>
				 </div>
				 <div style="background-image: url('images/title2_bg.gif');background-repeat: no-repeat;width: 708px;height: 20px;"><div style="float: left;width: 500px;text-align: left;margin-left:20px;font-size: 12px;color: #000;font-weight: bold;">其他颜色设置</div> </div>
				 <div style="width:708px;border-left:1px solid #95c3e7;border-right:1px solid #95c3e7;border-bottom:1px solid #95c3e7; padding-top:10px;padding-bottom: 5px;" >
				    <table>
							<tr>
								<td>评价按钮边框：	</td>
								<td><input type="text" onclick="colordialog()"	value="${saveTextVo.buttonBorderColor}"	name="saveTextVo.buttonBorderColor" id="buttonBorderColor" class="input_comm" style="width: 80px" /></td>
								<td>评价按钮背景：</td>
								<td><input type="text" onclick="colordialog()" value="${saveTextVo.buttonBackColor}"	name="saveTextVo.buttonBackColor" id="buttonBackColor" class="input_comm" style="width: 80px" /></td>
								<td>员工星级：</td>
								<td><input type="text" onclick="colordialog()" value="${saveTextVo.starColor}" name="saveTextVo.starColor"	id="starColor" class="input_comm" style="width: 80px" /></td>
								<td>员工星级背景：	</td>
								<td ><input type="text" onclick="colordialog()" value="${saveTextVo.starBackColor}" name="saveTextVo.starBackColor" id="starBackColor" class="input_comm" style="width: 80px" /></td>							
							</tr>
							 
						</table>
				 </div>
				 <div class="center_04_right_03" style="width:708px;">
				      <img src="images/12_05_07.jpg"  onclick="Anilabeshow()" class="hand"/>&nbsp;
					  <img src="images/12_06_09.jpg" onclick="Positionshow()" class="hand"/>&nbsp;
					  <img src="images/12_07_11.jpg" onclick="Welcomeshow()"  class="hand"/>&nbsp;
					  <img src="images/12_08_13.jpg" onclick="submit()" class="hand" />&nbsp;
					  <img src="images/12_09_15.jpg" onclick="reset()" class="hand" />
				 </div>
		</div>
		</form>	 
	</div>
	<div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
	<script language="javascript">
		document.write("<OBJECT id=\"dlgHelper\" CLASSID=\"clsid:3050f819-98b5-11cf-bb82-00aa00bdce0b\" width=\"0px\" height=\"0px\"></OBJECT>"); 
	</script>
	</body>
</html>
