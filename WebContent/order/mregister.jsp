<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    if(!com.sundyn.util.CommonUtil.isMobileAgent()){
        response.sendRedirect(basePath+"order.jsp?");
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
    <link rel="stylesheet" href="css/demos.css">
    <!-- 上传图片css -->
    <script src="js/jquery-2.1.4.js"></script>
    <script src="js/fastclick.js"></script>
    <script src="js/parallax.min.js"></script>
    <script src="js/swiper.min.js"></script>
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

<link rel="stylesheet" type="text/css" href="css/ty.css"/>
<div class="wrapper">
    <header><a href="mprofile.action" class="logo">账号注册</a>
        <span>
            <a href="mlogin.jsp">登录</a>
        </span>
    </header>

    <div class="mpart">
        <div class="vision">
            <div class="s_mide layer" data-depth=".35">
            </div>
            <div class="s_botm layer" data-depth-x=".1" data-depth-y=".08">
            </div>
            <div class="s_botm layer" data-depth-x=".18" data-depth-y=".15">
            </div>
        </div>
    </div>
    <div class="tool_index">
        <div class="weui-cells">
            <div class="detail_content">
                <form class="layui-fluid" id="formex">
                    <div class="weui-cells weui-cells_form">

                        <div class="weui-cell">
                            <div class="weui-cell__hd">
                                <label class="weui-label">账号</label>
                            </div>
                            <div class="weui-cell__bd">
                                <input type="text" name="name" id="name" placeholder="账号" class="weui-input">
                            </div>
                        </div>
                        <div class="weui-cell">
                            <div class="weui-cell__hd">
                                <label class="weui-label">密码</label>
                            </div>
                            <div class="weui-cell__bd">
                                <input type="password" name="password" id="password" placeholder="密码" class="weui-input">
                            </div>
                        </div>
                        <div class="weui-cell">
                            <div class="weui-cell__hd">
                                <label class="weui-label">确认密码</label>
                            </div>
                            <div class="weui-cell__bd">
                                <input type="password" name="password2" id="password2" placeholder="确认密码" class="weui-input">
                            </div>
                        </div>
                        <div class="weui-cell">
                            <div class="weui-cell__hd">
                                <label class="weui-label">手机</label>
                            </div>
                            <div class="weui-cell__bd">
                                <input type="text" name="cellphone" id="cellphone" placeholder="手机" class="weui-input">
                            </div>
                        </div>
                        <div class="weui-cells" >
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <a href="javascript:;" class="weui-btn weui-btn_primary" style="width:100%;" onclick="reg()">注册</a>
                                </div>
                            </div>
                        </div>
                        <div style="height:50px;"></div>
                    </div>
                </form>
            </div>

        </div>
    </div>
<!--banner 开始-->
<!--<div class="swiper-container" data-space-between='10' data-pagination='.swiper-pagination' data-autoplay="1000">
<div class="swiper-wrapper">
    </div>
<div class="swiper-pagination"></div>
</div>-->
</div>
<script type="text/javascript">
    //banner
    $(".swiper-container").swiper({
        loop: true,
        autoplay: 3000
    });

    function reg() {
        var name = $("#name").val();
        var password = $("#password").val();
        var password2 = $("#password2").val();
        var cellphone = $("#cellphone").val();
        if ("" == name){
            return $.toast("账号不能为空！");
        }
        if ("" == password){
            return $.toast("密码不能为空！");
        }
        if ("" == password2){
            return $.toast("确认密码不能为空！");
        }
        if(password !== password2){
            return $.toast('两次密码输入不一致！');
        }
        if(cellphone == ""){
            return $.toast('手机不能为空！');
        }
        //请求接口
        $.ajax({
            url: 'registerPost.action' //实际使用请改成服务端真实接口
            ,type: "POST"
            ,data: $("form").serialize()
            ,success: function(res){
                if(res.succ){
                    $.toast(res.msg, function(){
                        location.href = 'login.jsp'; //跳转到登入页
                    });
                }
                if (res.succ === false)
                    $.toast(res.msg);
            }
        });
    }
</script>



<div class="weui-footer">
    <div class="weui-footer__text"><p>Copyright © 2019 众鑫v1.0</p></div>
</div>
<div class="sstmb"></div>
<script src="js/jquery-weui.js"></script>
<script src="js/fastclick.js"></script>
<script type="text/javascript">
    $('.vision').parallax();

    function saveManagerExt() {
        $.ajax({
            url:"saveManagerExt.action",
            data: $("#formex").serialize(),
            success: function (resp) {
                if (resp.succ){
                    $.toast(resp.msg);
                }
                else
                    $.toast(resp.msg, "forbidden");
            }
        });
    }
</script>
<!--底部固定导航 结束-->

</body>
</html>