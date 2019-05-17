<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
    String errmsg = "";

    if(request.getParameter("msg") != null)
        errmsg = com.sundyn.utils.ReqUtils.format(request.getParameter("msg").toString());
    if(request.getAttribute("msg") != null)
        errmsg = com.sundyn.utils.ReqUtils.format(request.getAttribute("msg").toString());
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>您访问的页面不存在</title>
    <style type="text/css">
        /*reset及 clear*/
        body, div, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, pre, form, fieldset, input, p, blockquote, table, th, td, embed, object, button {
            margin:0;
            padding:0;
            font-size:12px;
            color:#343434;
            font-family:"宋体", Arial, Helvetica;
            word-wrap:break-word;
        }
        fieldset, img, abbr {
            border:0;
        }
        address, caption, cite, code, dfn, em, strong, th, var, i, b {
            font-weight:normal;
            font-style:normal;
        }
        ul, ol {
            list-style:none;
        }
        a, a:link, a:visited, a:active {
            text-decoration:none;
        }
        a:hover {
            text-decoration:underline;
        }
        a:focus, input[type="text"]:focus, input[type="password"]:focus, textarea:focus {
            outline:none;
        }
        .cl0, .cl5, .cl10, .cl15, .cl20 {
            clear:both;
            overflow:hidden;
            font-size:0;
        }
        .cl0 {
            height:0px;
        }
        .cl5 {
            height:5px;
        }
        .cl10 {
            height:10px;
        }
        .cl15 {
            height:15px;
        }
        .cl20 {
            height:20px;
        }
        .nav {
            width:100%;
            height:39px;
        }
        .wrap {
            width:770px;
            margin:0 auto;
        }
        .nav .wrap em {
            color:#f5e2c2;
            padding-left:40px;
        }
        .tl {
            text-align:left;
        }
        .tc {
            text-align:center;
        }
        .tr {
            text-align:right;
        }
        .box {
            border:1px solid #dedddd;
            border-radius:3px;
        }
        /*颜色*/
        .purity_red {
            color:#f00;
        }
        .fred {
            color:#b01f2e;
        }
        .fyellow {
            color:#ead6b3;
        }
        /*字体大小*/
        .fs12 {
            font-size:12px;
        }
        .fs14 {
            font-size:14px;
        }
        /*正文*/
        .w7 {
            width:980px;
            margin:0 auto;
        }
        .w7 span {
            display:inline-block;
            height:170px;
        }
        .w7 h1 {
            color:#a21e2c;
            font-size:26px;
            font-family:"微软雅黑";
        }
        .w7 h2 {
            color:#666;
            font: bold 16px "宋体";
            margin:10px 0;
        }
        .w7 h3 {
            color:#666;
            font: bold 14px "宋体";
            margin:10px 0;
        }
        .w7 h3 em {
            font-size:22px;
            color:#aa2230;
            padding:0 5px;
            font-weight:bold;
        }
        .w7 span p a {
            display:inline-block;
            text-align:center;
            width:106px;
            height:32px;
            margin-left:20px;
        }
    </style>
</head>

<body>
<!-- 头部背景 -->
<div class="yelangsembg">
    <!-- 头部导航 --><!-- 头部导航 结束 --><!--通栏广告1-->
    <div class="nav">
        <div class="wrap"><!--  <em>访问页面出错</em>--></div>
    </div>
    <!--通栏广告1 结束-->
    <div class="wrap" style="margin-top:85px;">
        <div class="w7">
            <table>
                <tr><td><img src="${ctx}/images/exception.png"></td>
                <td><h2 id="errplace">不允许访问<%=errmsg!=null?("，" + errmsg) : ""%></h2>
                    <p><a href="${ctx}/login.jsp"><h2>重新登录</h2></a></p>
                </td>
                </tr>
            </table>
      </div>
    </div>
</div>
</body>
</html>


<script type="text/javascript">
    if(self!=top){
        top.document.location.href="${ctx}/noauth.jsp?msg=<%=errmsg%>";
    }
    else
        document.getElementById("errplace").style.display = "block";
</script>