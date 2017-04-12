<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s"  uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<style>
			a:link {color:#464646;text-decoration:none;}
			a:visited {color:#464646;text-decoration:none;}
			a:hover{color:#ed145b;text-decoration:underline;}
			a:active{color:#ed145b;text-decoration:underline;}
			td{font-size:12px}
			
			/*想要改输入日历控件的样子就改下面的CSS样式就可以了*/
			/*Date*/
			.header {font: 12px Arial, Tahoma !important;font-weight: bold !important;font: 11px Arial, Tahoma;font-weight: bold;color: #154BA0;background:#C2DEED;height: 25px;padding-left: 10px;
			}
			.header td {padding-left: 10px;}
			.header a {color: #154BA0;}
			.header input {background:none;vertical-align: middle;height: 16px;}
			.category {font: 12px Arial, Tahoma !important;font: 11px Arial, Tahoma;color: #92A05A;height:20px;background-color: #FFFFD9;}
			.category td {border-bottom: 1px solid #DEDEB8;}
			.expire, .expire a:link, .expire a:visited {color: #999999;}
			.default, .default a:link, .default a:visited {color: #000000;}
			.checked, .checked a:link, .checked a:visited {color: #FF0000;}
			.today, .today a:link, .today a:visited {color: #00BB00;}
			#calendar_year {display: none;line-height: 130%;background: #FFFFFF;position: absolute;z-index: 10;}
			#calendar_year .col {float: left;background: #FFFFFF;margin-left: 1px;border: 1px solid #86B9D6;padding: 4px;}
			#calendar_month {display: none;background: #FFFFFF;line-height: 130%;border: 1px solid #86B9D6;padding: 4px;position: absolute;z-index: 11;}
			.tableborder {background: white;border: 1px solid #86B9D6;}
			#year,#month{padding-right:10px;background:url(onbottom.gif) no-repeat center right;}/*图片路径可以改成自己的*/
			/*Date*/
			table {
				border:0px;
			}
			table td{
				border:0px;
			}
			img{
			    cursor: pointer;
			}
		</style>
		<script>
			//这段脚本如果你的页面里有，就可以去掉它们了
			//欢迎访问我的网站queyang.com
			var ie =navigator.appName=="Microsoft Internet Explorer"?true:false;
			function $(objID){
				return document.getElementById(objID);
			}
		</script>
	<script type="text/javascript" src="js/dojo.js"></script>
	<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
 	</head>
	<body>
	<script type="text/javascript" src="js/calendar_<s:text name='sundyn.language.calendar' />.js"></script>
 		<div id="man_zone">
 		  <div id="three">
 		  	<div id="three_dh" >
 		   		<img id="img1" src="images/three_dh1.gif"  border="0" style="border:0px;" onmouseover="threeSwap(1)"  />
 		   		<img id="img2" src="images/three_dh2_.gif" border="0" style="border:0px;" onmouseover="threeSwap(2)" />
 		   		<img id="img3" src="images/three_dh3_.gif" border="0" style="border:0px;" onmouseover="threeSwap(3)" />
	   	    </div>
 	   	    <div id="three_content">
 	   	      <div id="three1" >
 	   	    	<div id="three11">
 	   	    	    <div style="margin-top: 60px;">
 	   	    	       <form id="pic" enctype="multipart/form-data" name="pic"	action="employeeAdd.action" method="post" >
 	   	    	    	文件：  <input type="file" id="img" name="img" class="input_comm" style="height:20px;width:200px;margin:0px;padding:0px;"/>  <img src="images/button_three_dr.gif" onclick="threeLoad();"/>
 	   	    	       </form>
 	   	    	    </div> 
 	   	    	    <div id="msg"></div>
 	   	    	</div>
 	   	    	<img src="images/three_hr.gif" />
 	   	    	<div style="text-align: left;">
 	   	    	   &nbsp;&nbsp;&nbsp;&nbsp;先从业务数据里导出txt文件，然后导入到评价系统，每次导入数据前，自动清空以前的数据。
  	   	    	</div>
  	   	      </div>
  	   	      <div id="three2" style="display: none;">
	  	   	        <div id="three21">
	  	   	          <div style="margin-top:50px;height:25px;overflow:hidden;" id="three211">
		  	   	         <table  border="0" cellspacing="0" cellpadding="0">
							  <tr>
							    <td>最大时间：</td>
							    <td   align="left"> 
							        <input type="text" name="big" id="big" class="input_comm" value="300"  onkeyup="  isNumber(this.value);"/>
							    </td>
							    <td><img src="images/three_suit.gif"   onclick="threeSuit()"  /></td>
							    <td> <img src="images/three_heigh.gif" onclick="hidden()"     /></td>
							  </tr>
							  <tr>
							    <td>最小时间：</td>
							    <td><input type="text" name="small" id="small" class="input_comm" value="0" onkeydown="  isNumber(this.value);" /></td>
							    <td>步长：</td>
							    <td><input type="text" name="step" id="step" class="input_comm" value="5"   onkeydown="  isNumber(this.value);"/></td>
		 					  </tr>
						 </table>
					  </div>
					  <div id="msg2"></div>
 	   	   	        </div> 
 	   	   	        <img src="images/three_hr.gif" />
	 	   	    	<div style="text-align: left;">
	 	   	    	   	&nbsp;&nbsp;&nbsp;&nbsp;单位:所有单位都是秒，300秒=5分钟 <br/>
	 	   	    	   	&nbsp;&nbsp;&nbsp;&nbsp;最大时间：指业务系统受理时间上下浮动的最大范围。<br/>
	 	   	    	   	&nbsp;&nbsp;&nbsp;&nbsp;最小时间：只从比这个时间大的数据里找，小于这个时间的不考虑。<br/>
	 	   	    	   	&nbsp;&nbsp;&nbsp;&nbsp;步长：从最小时间开始，逐次扫描，查找时间在这个范围的数据开始匹配。然后再次增加步长，再次配置。直到所有数据到匹配完成为止。
	   	   	    	</div>
  	   	      </div>
  	   	      <div id="three3" style="display:none;">
	  	   	        <div id="three31">
	  	   	        	  <img  src="images/button_three_query.gif" style="margin-top:80px;" onclick="javascript:window.location.href='threeQuery.action'" />
 	   	   	        </div>
 	   	   	        <img src="images/three_hr.gif" />
	 	   	    	<div style="text-align: left;">
	 	   	    		&nbsp;&nbsp;&nbsp;&nbsp;单击“查询”按钮，进入查询界面，可以根据部门，日期，结果，等查询， 查询，然后导出到Excel。
	   	   	    	</div> 
  	   	      </div>
  	   	   </div>
  		  </div>
  		</div>
 		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
	</body>
</html>
