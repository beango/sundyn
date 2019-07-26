<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    if(!com.sundyn.util.CommonUtil.isMobileAgent()){
        response.sendRedirect(basePath+"order.action");
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
<!--头部导航 开始-->



<!--头部导航 结束-->


<link rel="stylesheet" type="text/css" href="css/ty.css"/>
<div class="wrapper">
    <header><a href="mprofile.action" class="logo">基本信息</a>
        <span>
            <a href="managerLogout.action" class="open-popup" data-target="#addorder">退出</a>
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
                        <div class="weui-cells__title">账号</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    ${sessionScope.get("manager").name}
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">密码</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="password" class="weui-input" name="password" value="" placeholder="密码，如不修改请留空！" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">真实姓名</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" class="weui-input" name="realname" value="${managerentity.realname}" placeholder="真实姓名" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">公司名称</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="orgname" class="weui-input" value="${managerextentity.orgname}" placeholder="公司名称" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">联系人</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="contact" class="weui-input" value="${managerentity.contact}" placeholder="联系人" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">手机</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="telphone" class="weui-input" value="${managerentity.telphone}" placeholder="手机" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">邮箱</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="email" class="weui-input" value="${managerentity.email}" placeholder="邮箱" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells" >
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <a href="javascript:;" class="weui-btn weui-btn_primary" style="width:100%;" onclick="saveManagerExt()">保存</a>
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
</script>



<div class="weui-footer">
    <div class="weui-footer__text"><p>Copyright © 2019 众鑫v1.0</p></div>
</div>
<div class="sstmb"></div>
<!--底部固定导航 开始-->
<div class="weui-tabbar">
    <a href="morder.action" class="weui-tabbar__item">
        <div class="weui-tabbar__icon">
            <img src="images/icon_nav_article.png" alt="">
        </div>
        <p class="weui-tabbar__label">我的订单</p>
    </a>
    <a href="mlicense.action" class="weui-tabbar__item">
        <div class="weui-tabbar__icon">
            <img src="images/icon_nav_msg.png" alt="">
        </div>
        <p class="weui-tabbar__label">授权管理</p>
    </a>
    <a href="mlogin.action" class="weui-tabbar__item">
        <div class="weui-tabbar__icon">
            <img src="images/icon_nav_button.png" alt="">
        </div>
        <p class="weui-tabbar__label">安全信息</p>
    </a>
    <a href="mprofile.action" class="weui-tabbar__item weui-bar__item--on">
        <div class="weui-tabbar__icon">
            <img src="images/icon_nav_cell.png" alt="">
        </div>
        <p class="weui-tabbar__label">我的资料</p>
    </a>
</div>
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