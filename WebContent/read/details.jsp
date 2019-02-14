<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
 <script type="text/javascript" src="js/jquery.js"></script>
<title>显示内容</title>
<script language="javascript">
var step=260;
function scrollit(p)
	{
	//alert(p);
	 if (p=="up"){
	 testList.scrollTop=testList.scrollTop+step;
	}
	 else {
	 testList.scrollTop=testList.scrollTop-step;
	}
 }

function test(){
//alert("<%=request.getParameter("doc_id")%>");
  $.post("readHtmlAjax.action",{doc_id:${doc_id}},function(data){
             document.getElementById("testList").innerHTML=data;
            });
            }
</script>

<style type="text/css">
<!--
a.bt:link {
	font-size: 16px;
	color: #000000;
}
a.bt:visited {
	font-size: 16px;
	color: #000000;
}
a.list:link {
	font-size: 16px;
	line-height: 37px;
	color: #FFFFFF;
	text-decoration: underline;
}
a.list:visited {
	font-size: 16px;
	line-height: 37px;
	color: #FFFFFF;
	text-decoration: underline;
}
#Layer1 {
	position:absolute;
	left:737px;
	top:34px;
	width:55px;
	height:21px;
	z-index:1;
}

-->
</style>
</head>

<body onload="test();"   leftmargin="0">
<table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr><td height="60">&nbsp;</td></tr>
</table>
<table width="800" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="355" valign="top" background="images/lzm/bg_list.jpg"><table width="730" height="348" border="0" align="center" cellpadding="0" cellspacing="0">
      
      <tr>
        <td colspan="2"> <div align="left" id="testList" style="height: 300px;overflow:auto;">
		       <img src="images/lzm/ajax.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数据正在加载中.....
			   </div>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="images/lzm/d_back2.jpg" width="800" height="48" border="0" usemap="#Map" /></td>
  </tr>
</table>

<map name="Map" id="Map">
<area shape="rect" coords="670,10,791,46" href="#" onclick="javascript:scrollit('up')" title="向下"/>
<area shape="rect" coords="20,11,132,45" href="#" onclick="javascript:scrollit('down')" title="向上"/>
<area shape="rect" coords="331,4,470,48" href="javaScript:history.go(-1);"  title="返回上级"/>
</map></body>
</html>
