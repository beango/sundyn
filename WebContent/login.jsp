<%@ page import="com.sundyn.util.CommonUtil" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="qloginmsg" value="${pageContext.request.getParameter('msg')==null?'':com.sundyn.utils.ReqUtils.format(pageContext.request.getAttribute('msg').toString())}" />
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    if(CommonUtil.isMobileAgent()){
        response.sendRedirect(basePath+"mlogin.jsp");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><s:text name="login.title" /></title>
    <link rel="stylesheet" href="lib/layui/css/layui.2.4.5.css" media="all">
    <link rel="stylesheet" href="css/login_admin.css" media="all">
    <link rel="stylesheet" href="css/login_login.css" media="all">
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script language="JavaScript" src="js/md5.min.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script language="JavaScript" src="js/dojo.js"></script>
    <script type="text/javascript">
        layui.use("layer")
    </script>
    <script language="JavaScript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/mmain.js"></script>
    <script language="javascript">
        $(function () {
            layui.use("layer")
            var loginmsg = '${loginmsg}';
            if(loginmsg=='')
                loginmsg = '${msg}';
            var qloginmsg = '${qloginmsg}';
            if (qloginmsg!='')
                loginmsg = qloginmsg;
            if(self!=top){
                top.document.location.href="${ctx}/login.jsp?msg=" + loginmsg;
                return;
            }
            if (loginmsg != '')
                lalert(loginmsg)
            $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            $(window).resize(function () {
                $('.loginbox').css({'position': 'absolute', 'left': ($(window).width() - 692) / 2});
            });

            var root = document.getElementById('loginbody');
            document.body.addEventListener('keyup', function (e) {
                if (e.keyCode == '13') {
                    doclick();
                }
            });
        });

        function _$(id){return document.getElementById(id);}

        function doclick(){
            var s = "";
            if(!_$('name').value){s += "<s:text name="login.userNameNotNULL" />";}
            if(!_$('password1').value){s += "<s:text name="login.passwordNotNULL" />";}
            if(s != ""){
                error(s);
                return;
            }
            _$('password').value = _$('password1').value;
            lsubmit("managerLogin.action", {
                name: encode64(_$('name').value),
                password:md5(_$('password').value)
            }, function(resp){
                if (resp.succ){
                    succ(resp.msg, function () {
                    });
                    loginRedirec();
                }
                else
                {
                    if (resp.msg == "<s:text name="login.passwordNeedChange" />"){
                        lalert2(resp.msg, redir2Changepwd);
                    }
                    else
                        error(resp.msg);
                }
            });
        }

        function loginRedirec() {
            if(self!=top){
                document.location.href="${ctx}/queryIndex.action";
            }else{
                document.location.href="${ctx}/default.jsp";
            }
        }

        function redir2Changepwd() {
            document.location.href="${ctx}/managerChangePsw.action?type=1&name=" + _$('name').value;
        }
    </script>

</head>
<body>

<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login">

    <div class="layadmin-user-login-main">
        <div class="layadmin-user-login-box layadmin-user-login-header">
            <h2><s:text name="login.title" /></h2>
        </div>
        <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
                <input type="text" name="managerVo.name" id="name" lay-verify="required" placeholder="用户名" class="layui-input">
            </div>
            <div class="layui-form-item">
                <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
                <input type="password" name="password1" id="password1" lay-verify="required" placeholder="密码" class="layui-input">
            </div>
            <div class="layui-form-item">
                <button class="layui-btn layui-btn-fluid" lay-submit lay-filter="LAY-user-login-submit" onclick="doclick()">登 入</button>
            </div>
            <div class="layui-trans layui-form-item layadmin-user-login-other">
                <input type="hidden" name="managerVo.password" id="password" value="" />
                <label class="msg" style="height:20px;color:red;">${msg}</label>
                <a href="registerView.action" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
            </div>
        </div>
    </div>

    <div class="layui-trans layadmin-user-login-footer">
        <p>© 2019 众鑫</p>
    </div>
</div>

</body>
</html>
