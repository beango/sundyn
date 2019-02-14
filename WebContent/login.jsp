<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>欢迎登录后台管理系统</title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script language="JavaScript" src="js/jquery.js"></script>
    <script language="javascript">
        $(function () {
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
            if(!_$('name').value){s += "账号不能为空\n";}
            if(!_$('password1').value){s += "密码不能为空\n";}
            if(s != ""){alert(s);return;}
            _$('password').value = _$('password1').value;
            _$('v').submit();
        }
    </script>
</head>

<body style="background-color:#1c77ac; background-image:url(images/managementLogin2.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">

<div id="mainBody">
    <div id="cloud1" class="cloud"></div>
    <div id="cloud2" class="cloud"></div>
</div>

<%--<div class="logintop">
    <span>欢迎登录后台管理界面平台</span>
</div>--%>

<div class="loginbody" id="loginbody">
    <span class="systemlogo"></span>
    <div class="loginbox">
        <form method="post" id="v" action="managerLogin.action" class="form-condensed">
        <ul>
            <li><label style="font-size:13pt;color:#fff;font-weight:bold;">账号：</label><input  name="managerVo.name" id="name" type="text" class="loginuser" value="" onclick="JavaScript:this.value=''"/></li>
            <li><label style="font-size:13pt;color:#fff;font-weight:bold;">密码：</label><input name="password1" id="password1" type="password" class="loginpwd" value="" onclick="JavaScript:this.value=''"/></li>
            <li style="padding-left:75px;"><input name="" type="button" class="loginbtn" value="登录" onclick="doclick()"/>
                <%--<label style="color:#fff;"><input type="checkbox" value="on" id="savename" style="color:#fff;"/>记住密码</label>--%>
                <input type="hidden" title="密码" name="managerVo.password" id="password" value="" />
                <label class="msg" style="height:20px;color:red;">${msg}</label>
            </li>
            <li></li>
        </ul>
        </form>
    </div>
</div>
<div class="loginbm"></div>
</body>
</html>
