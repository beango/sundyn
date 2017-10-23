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
<link rel="stylesheet" type="text/css" href="js/easyui/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/easyui/themes/icon.css" />
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>	
<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>

</head>
<body style="padding:0px;background:#EAEEF5;">  
<div id="pageloading"></div>  
<div id="topmenu" class="l-topmenu">
    <div class="l-topmenu-logo">jQuery ligerUI 中文官方网站</div>
    <div class="l-topmenu-welcome">
        <label> 皮肤切换：</label>
        <select id="skinSelect">
            <option value="aqua">默认</option> 
            <option value="silvery">Silvery</option>
            <option value="gray">Gray</option>
            <option value="gray2014">Gray2014</option>
        </select>
        <a href="index.htm" class="l-link2">回首页</a>
        <span class="space">|</span>
        <a href="demo.aspx" class="l-link2">服务器版本</a>
        <span class="space">|</span>
        <a href="javascript:f_addTab('pay','捐赠','pay.htm')" class="l-link2" target="_blank">捐赠</a> 
    </div> 
</div>
  <div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
        <div position="left"  title="主要菜单" id="accordion1"> 
                     <div title="功能列表" class="l-scroll">
                         <ul id="tree1" style="margin-top:3px;">
                    </div>
                    <div title="应用场景">
                    <div style=" height:7px;"></div>
                        <a class="l-link" href="http://www.ligerui.com/go.aspx?id=case" target="_blank">演示系统</a>  
                         <a class="l-link" href="javascript:f_addTab('listpage','列表页面','demos/case/listpage.htm')">列表页面</a> 
                         <a class="l-link" href="demos/dialog/win7.htm" target="_blank">模拟Window桌面</a> 
                        <a class="l-link" href="javascript:f_addTab('week','工作日志','demos/case/week.htm')">工作日志</a>  
                    </div>    
                     <div title="实验室">
                    <div style=" height:7px;"></div>
                          <a class="l-link" href="lab/generate/index.htm" target="_blank">表格表单设计器</a> 
                          <a class="l-link" href="lab/formdesign/index.htm" target="_blank">可视化表单设计</a> 
                    </div> 
        </div>
        <div position="center" id="framecenter"> 
            <div tabid="home" title="我的主页" style="height:300px" >
                <iframe frameborder="0" name="home" id="home" src="welcome.htm"></iframe>
            </div> 
        </div> 
        
    </div>
    <div  style="height:32px; line-height:32px; text-align:center;">
            Copyright © 2011-2015 www.ligerui.com
    <a href="http://www.miitbeian.gov.cn/" target="_blank">粤ICP备09046932号-2</a>
    </div>
    <div style="display:none"><script src="http://s21.cnzz.com/stat.php?id=2970137&web_id=2970137" language="JavaScript"></script></div>
</body>
</html>
