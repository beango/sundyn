<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	    <meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="no-cache" />
		<meta http-equiv="Expires" content="-1" />
		<meta http-equiv="Cache-Control" content="no-cache" />
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language'/>.js"></script>
	</head>
	<body>
		<div id="man_zone">
		   <div class="fengge" style="height:15px;">&nbsp;</div>
		   <div class="center_05" style="width:730px;">
           <div><img src="images/pic_set.gif" width="738" height="29" /></div>
    <div class="center_11_center kuang">
	     <table width="100%" height="90%" border="0" cellpadding="0" cellspacing="0" bgcolor="fff">
			  <tr>
			    <td align="center"   style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;">
			      <img id="img1" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/1.jpg" /><br/> 
			       广告图片一<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('1.jpg')" class="hand" /><br/>
			    </td>
			    <td style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;" align="center" valign="middle">
			      <img id="img2" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/2.jpg" /><br/> 
			       广告图片二<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('2.jpg')" class="hand" /><br/>
			    </td>
			    <td style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;" align="center" valign="middle">
			      <img id="img3" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/3.jpg" /><br/> 
			       广告图片三<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('3.jpg')" class="hand" /><br/>
			    </td>
			    <td style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;" align="center" valign="middle"> 
			      <img id="img4" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/4.jpg" /><br/> 
			       广告图片四<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('4.jpg')" class="hand" /><br/>
			     </td>
			  </tr>
                <tr>
			    <td align="center"   style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;">
			      <img id="img5" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/5.jpg" /><br/> 
			       广告图片五<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('5.jpg')" class="hand" /><br/>
			    </td>
			    <td style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;" align="center" valign="middle">
			      <img id="img6" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/6.jpg" /><br/> 
			       广告图片六<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('6.jpg')" class="hand" /><br/>
			    </td>
			    <td style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;" align="center" valign="middle">
			      <img id="img7" hspace="3"  width="176" height="120" vspace="3" border="1" name="a" src="system/upload/7.jpg" /><br/> 
			       广告图片一<br/> 
			      <img src="images/11_03_07.jpg" width="86" height="25" onclick="basePicAjax('7.jpg')" class="hand" /><br/>
			    </td>
			    <td style="background-color:#238ccd; border-color:#FFFFFF; border-width:2px;" align="center" valign="middle"> 
			      &nbsp;
			     </td>
			  </tr>
			</table>
 	</div>
</div>
 		</div>
 		<div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
		<div id="dialog" style="width: 700px; display: none;"></div>
	</body>
	<script language="javascript">
	 
	
	   for (i=1;i<=7;i++){
	       var pic=document.getElementById('img'+i);
           var img='system/upload/'+i+'.jpg?'+new Date;
           pic.src=img;
	   }
 	</script>
	
</html>
