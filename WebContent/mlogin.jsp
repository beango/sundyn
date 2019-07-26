<%@ page import="com.sundyn.util.CommonUtil" %>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    if(!CommonUtil.isMobileAgent()){
        response.sendRedirect(basePath+"login.jsp");
        return;
    }
%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="applicable-device"content="mobile">
    <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <meta http-equiv="Cache-Control" content="no-transform" />
    <title>广州市众鑫电子设备授权管理系统</title>
    <link rel="stylesheet" href="css/weui.min.css"/>
    <link rel="stylesheet" href="css/jquery-weui.css"/>
    <link rel="stylesheet" href="css/mstyle.css"/>
    <link rel="stylesheet" href="css/iconfont.css"/>
    <link rel="stylesheet" href="css/mlogin.css"/>
    <script language="JavaScript" src="js/jquery-2.1.4.js"></script>
    <script language="JavaScript" src="js/fastclick.js"></script>
    <script language="JavaScript" src="js/jquery-weui.js"></script>
    <script language="JavaScript" src="js/md5.min.js"></script>
    <script language="JavaScript" src="js/my_<s:text name='sundyn.language' />.js"></script>
</head>
<body ontouchstart>
<script type="text/javascript">
    var html= $('html');
    var hW = html.outerWidth() > 640 ? 640 : html.outerWidth();
    _rem = hW/10;
    html.css('fontSize',_rem);
    $(function() {
        FastClick.attach(document.body);
    });
</script>

<div class="wrapper">
    <div class="mpart">
        <div class="vision">
            <div class="s_mide layer" data-depth=".35">
            </div>
        </div>
        <div class="login_logo">
            <h1 style="font-size: 26pt; color:white; text-align: center;">广州市众鑫电子设备授权管理系统</h1>
        </div>
        <div class="login_form" style="margin-top: 50px;">
            <form method="post" data-url="#" id="formlogin">
                <div class="login_txt login_name">
                    <input type="text" name="username" id="username" placeholder="请输入账号" value="">
                </div>
                <div class="login_txt login_pwd">
                    <input type="password" name="password" id="password" placeholder="请输入密码" value="" >
                </div>
                <div class="btn">
                    <p class="log_msg">
                    </p>
                    <input type="button" class="login_btn weui-btn_loading" id="conf" value="登 录" onclick="login(this)">
                </div>
                <%--<div class="pswinfo">
                    <a class="forget" href="#">忘记密码？</a>
                </div>--%>
            </form>
            <div class="reg_btn">
                <a href="registerView.action">没有账号，点击马上注册</a>
            </div>

        </div>
        <%--<div class="vision_bottom">
            <div class="s_mide layer" data-depth=".35"></div>
            <div class="s_botm"></div>
            <div class="s_botm"></div>
            <div class="otherway">
                <a href="/mlogin/" class="login_icon phone"></a><a href="/Oauth/login/?type=qq" class="login_icon qq"></a><a href="/Oauth/login/?type=sina"class="login_icon xina"></a>
            </div>
        </div>--%>
    </div>
    <script type="text/javascript">
        var verifyimg = $("#verifyimg").attr("src");
        $("#verifyimg").click(function () {
            if (verifyimg.indexOf('?') > 0) {
                $("#verifyimg").attr("src", verifyimg + '&random=' + Math.random());
            } else {
                $("#verifyimg").attr("src", verifyimg.replace(/\?.*$/, '') + '?' + Math.random());
            }
        });

        $('#conf').click(function () {
            var data = $("#formlogin").serialize();
            var url = $("#formlogin").attr('data-url');
            $.post(url, data, function (msg) {

                if (msg.status == 1) {
                    $.toast(msg.info);
                    setTimeout(function () {
                        location.href = msg.url;
                    }, 200);
                } else {
                    $.toast(msg.info);
                }
            }, 'json');
        })
        $(function() {
            var h = $(window).height();
            $('.mpart').css('min-height', h);
        });

        function login(obj) {
            $(obj).val("正在登录...");
            if($('#username').val()==""){$(obj).val("登 录"); return $.alert("<s:text name="login.userNameNotNULL" />");}
            if($('#password').val()==""){$(obj).val("登 录"); return $.alert("<s:text name="login.passwordNotNULL" />");}
            $.ajax({
                url: "managerLogin.action?isremote=1",
                data: {
                    name: encode64($('#username').val()),
                    password:md5($('#password').val())
                },
                success: function(resp) {
                    $(obj).val("登 录");
                    if (resp.succ) {
                        $.toast(resp.msg, function () {
                            loginRedirec();
                        });
                    } else {
                        if (resp.msg == "<s:text name="login.passwordNeedChange" />") {
                            $.toast("登录成功", redir2Changepwd);
                        } else
                            $.toast(resp.msg);
                    }
                }
            });
        }
        function loginRedirec() {
            document.location.href="default.jsp";
        }
    </script>
</div>
<script src="js/fastclick.js"></script>
<script type="text/javascript">
    //$('.vision').parallax();
</script>
<div style="display: none;"><!-- 用于加载统计代码等隐藏元素 -->
</div>
</body>
</html>