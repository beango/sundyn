<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <base href="<%=basePath%>">
    
    <title><s:text name="sundyn.pic.previewPicture" /></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="css/clearbox.css" rel="stylesheet" type="text/css" />
<script src="js/clearbox.js" type="text/javascript"></script>
 <script>
    function toclose(){
     window.opener=null;
	 window.close();
	}
 </script>
 <script language=javascript>
function hero()
{
	var herowidth=700;//改为你要的网页宽度
	var heroheight=700;//改为你要的网页高度
	window.resizeTo(herowidth,heroheight);
}
hero();
</script>
 
 <%
   String pic = request.getParameter("pic");
 %>
  </head>
  
 <body sytle="background-color:black;">
<div style=" text-align:center; font-size:12px;">
<%--<table cellspacing="0" cellpadding="0" id="CB_Window" style="margin-left: -215px; margin-top: -185px; visibility: visible; ">--%>
<%--  <tbody>--%>
<%--      <tr id="CB_Header" style="height: 12px; ">--%>
<%--            <td id="CB_TopLeft">--%>
<%--                 <div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div>--%>
<%--            </td>--%>
<%--            <td id="CB_Top"></td>--%>
<%--            <td id="CB_TopRight">--%>
<%--                <div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div>--%>
<%--            </td>--%>
<%--      </tr>--%>
<%--      <tr id="CB_Body">--%>
<%--            <td id="CB_Left" style="width: 12px; "></td>--%>
<%--            <td id="CB_Content" valign="top" align="left">--%>
<%--                <div id="CB_Padding" style="padding: 2px; ">--%>
<%--                    <div id="CB_ImgContainer" style="height: 482px; ">--%>
<%--                        <iframe frameborder="0" id="CB_iFrame" src=""></iframe>--%>
<%--                    <div id="CB_ShowTh" style="visibility: hidden; "></div>--%>
<%--                    <div id="CB_ShowEtc" style="visibility: hidden; "></div>--%>
<%--                    <div id="CB_Etc" style="display: none; width: 0px; ">--%>
<%--                         <img src="pic/max.gif" alt="maximize">--%>
<%--                    </div>--%>
<%--                    <div id="CB_Thumbs" style="display: none; width: 0px; ">--%>
<%--                       <div id="CB_Thumbs2"></div></div>--%>
<%--                       <img id="CB_LoadingImage" alt="loading" src="pic/../pic/loading.gif" style="visibility: hidden; ">--%>
<%--                       <img id="CB_Image" alt="" src="${pageContext.request.contextPath }/download/<%=pic %>" style="border: 1px solid rgb(204, 204, 204); width: 640px; height: 480px; display: block; visibility: visible; ">--%>
<%--                       <div id="CB_PrevNext" style="display: block; ">--%>
<%--                           <div id="CB_ImgHide" style="background-color: rgb(255, 255, 255); opacity: 0.75; visibility: hidden; width: 640px; height: 480px; "></div>--%>
<%--                              <img id="CB_CloseWindow" alt="x" src="pic/close.png" style="display: block; " onclick="toclose();">--%>
<%--                              <img id="CB_SlideShowBar" src="pic/white.gif" style="opacity: 0.5; ">--%>
<%--                              <img id="CB_SlideShowP" alt="Pause SlideShow" src="pic/pause.png" style="display: none; ">--%>
<%--                              <img id="CB_SlideShowS" alt="Start SlideShow" src="pic/start.png" style="display: none; ">--%>
<%--                                 <a id="CB_Prev" href="#" style="display: none; height: 300px; "></a>--%>
<%--                                 <a id="CB_Next" href="#" style="display: none; height: 300px; "></a>--%>
<%--                            </div>--%>
<%--                        </div>--%>
<%--                        <div id="CB_Text" style="height: 30px; margin-top: 10px; font-family: arial; font-size: 12px; font-weight: normal; color: rgb(101, 101, 101); visibility: visible; "><%=pic %></div>--%>
<%--                        </div>--%>
<%--            </td>--%>
<%--            <td id="CB_Right" style="width: 12px; "></td>--%>
<%--   </tr>--%>
<%--   <tr id="CB_Footer" style="height: 12px; ">--%>
<%--        <td id="CB_BtmLeft">--%>
<%--           <div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div>--%>
<%--        </td>--%>
<%--        <td id="CB_Btm"></td>--%>
<%--        <td id="CB_BtmRight">--%>
<%--             <div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div>--%>
<%--        </td>--%>
<%--   </tr>--%>
<%--</tbody>--%>
<%--</table>--%>

<table cellspacing="0" cellpadding="0" id="CB_Window" style="margin-left: -335px; margin-top: -275px; visibility: visible; "><tbody><tr id="CB_Header" style="height: 12px; "><td id="CB_TopLeft"><div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div></td><td id="CB_Top"></td><td id="CB_TopRight"><div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div></td></tr><tr id="CB_Body"><td id="CB_Left" style="width: 12px; "></td><td id="CB_Content" valign="top" align="left"><div id="CB_Padding" style="padding: 2px; "><div id="CB_ImgContainer" style="height: 482px; "><iframe frameborder="0" id="CB_iFrame" src="${pageContext.request.contextPath }/download/recorder/<%=pic %>" style="top: 1px; left: 1px; width: 640px; height: 480px; "></iframe><div id="CB_ShowTh" style="visibility: hidden; "></div><div id="CB_ShowEtc" style="visibility: hidden; "></div><div id="CB_Etc" style="display: none; width: 0px; "><img src="pic/max.gif" alt="maximize"></div><div id="CB_Thumbs" style="display: none; width: 0px; "><div id="CB_Thumbs2"></div></div><img id="CB_LoadingImage" alt="loading" src="pic/../pic/loading.gif" style="visibility: hidden; "><img id="CB_Image" alt="" src="" style="border: 1px solid rgb(204, 204, 204); width: 640px; height: 480px; display: block; visibility: visible; "><div id="CB_PrevNext" style="display: none; "><div id="CB_ImgHide" style="background-color: rgb(255, 255, 255); opacity: 0.75; visibility: hidden; width: 0px; height: 0px; "></div><img id="CB_CloseWindow" alt="x" src="pic/close.png" style="display: none; "><img id="CB_SlideShowBar" src="pic/white.gif" style="opacity: 0.5; display: none; "><img id="CB_SlideShowP" alt="Pause SlideShow" src="pic/pause.png" style="display: none; "><img id="CB_SlideShowS" alt="Start SlideShow" src="pic/start.png" style="display: none; "><a id="CB_Prev" href="#" style="display: none; height: 343px; "></a><a id="CB_Next" href="#" style="display: none; height: 343px; "></a></div></div><div id="CB_Text" style="height: 30px; margin-top: 10px; font-family: arial; font-size: 12px; font-weight: normal; color: rgb(101, 101, 101); visibility: visible; "><%=pic %> [<a class="CB_TextNav" href="javascript:void(0)" onclick="toclose();"><s:text name="sundyn.pic.toclose" /></a>]</div></div></td><td id="CB_Right" style="width: 12px; "></td></tr><tr id="CB_Footer" style="height: 12px; "><td id="CB_BtmLeft"><div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div></td><td id="CB_Btm"></td><td id="CB_BtmRight"><div class="CB_RoundPixBugFix" style="width: 12px; height: 12px;"></div></td></tr></tbody></table>
<br />
<p></p>
  </body>
</html>
