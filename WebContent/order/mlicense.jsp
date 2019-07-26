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
    <header><a href="mlicense.action" class="logo">授权管理</a>
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
            <c:forEach items="${prdlicensedetails}" var="entity2" varStatus="s">
                <div class="weui-form-preview">
                    <div class="weui-form-preview__hd">
                        <label class="weui-form-preview__label"></label>
                        <em class="weui-form-preview__value">${entity2.productname}</em>
                    </div>
                    <div class="weui-form-preview__bd">
                        <div class="weui-form-preview__item">
                            <label class="weui-form-preview__label">剩余授权数</label>
                            <span class="weui-form-preview__value">${entity2.totalnum-entity2.usednum}</span>
                        </div>
                        <div class="weui-form-preview__item">
                            <label class="weui-form-preview__label">总授权数</label>
                            <span class="weui-form-preview__value">${entity2.totalnum}</span>
                        </div>
                        <div class="weui-form-preview__item">
                            <label class="weui-form-preview__label">已使用授权数</label>
                            <span class="weui-form-preview__value">${entity2.usednum}</span>
                        </div>
                    </div>
                    <div class="weui-form-preview__ft">
                        <a class="open-popup weui-form-preview__btn weui-form-preview__btn_default" href="javascript:" data-target="#licensedetail${entity2.id}" onclick="viewlicensedetail('${entity2.id}')">授权明细</a>
                        <button type="submit" class="open-popup weui-form-preview__btn weui-form-preview__btn_primary" href="javascript:" data-target="#license" onclick="openlicense('${entity2.id}','${entity2.productname}')">授权</button>
                    </div>
                </div>
            </c:forEach>
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
    <a href="mlicense.action" class="weui-tabbar__item weui-bar__item--on">
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
    <a href="mprofile.action" class="weui-tabbar__item">
        <div class="weui-tabbar__icon">
            <img src="images/icon_nav_cell.png" alt="">
        </div>
        <p class="weui-tabbar__label">我的资料</p>
    </a>
</div>


<div id="license" class='weui-popup__container popup-bottom'>
    <div class="weui-popup__overlay"></div>
    <div class="weui-popup__modal">
        <div class="toolbar">
            <div class="toolbar-inner">
                <span class="title">授权</span>
            </div>
        </div>
        <form id="formlicense">
            <input type="hidden" name="id" value=""/>
            <div class="modal-content">
                <div class="weui-cells producttotal">
                    授权MAC地址（批量授权使用英文逗号分隔）：<textarea id="licensemac" class="layui-textarea" style="width:99%;height:120px;"></textarea>
                </div>
            </div>
            <a href="javascript:;" class="picker-button weui-btn_mini weui-btn_primary" style="margin-right:10px;color:white;" onclick="saveLicense()">提交</a>
        </form>
    </div>
</div>

<c:forEach items="${prdlicensedetails}" var="entity" varStatus="s">
    <div id="licensedetail${entity.id}" class='weui-popup__container popup-bottom'>
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <a href="javascript:;" class="picker-button close-popup">关闭</a>
                    <h1 class="title">授权明细</h1>
                </div>
            </div>
            <div class="modal-content">
                <div class="weui-panel__bd" style="height:250px; scroll:auto;">
                    <c:forEach items="${licensedetails}" var="entity2" varStatus="s">
                        <c:if test="${entity.id==entity2.productid}">
                            <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                                <div class="weui-media-box__bd">
                                    <h4 class="weui-media-box__title">
                                        授权MAC地址：${entity2.devicemac}
                                    </h4>
                                    <p class="weui-media-box__desc">
                                        授权时间：<span class="weui-media-box__title-after"><fmt:formatDate value="${entity2.usetime}" type="both" /></span><br>
                                        下载次数：${entity2.downedtimes} <br>
                                        最后一次下载时间：<fmt:formatDate value="${entity2.lstdowntime}" type="both" />
                                    </p>
                                </div>
                            </a>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div style="height:48px;">
            </div>
        </div>
    </div>
</c:forEach>
<script src="js/jquery-weui.js"></script>
<script src="js/fastclick.js"></script>
<script type="text/javascript">
    $('.vision').parallax();

    function openlicense(id, name){
        $("#license .title").html(name);
        $("#formlicense input[type=hidden][name=id]").val(id);
    }

    function viewlicensedetail(id) {
    }


    function saveLicense(){
        var mac = $("#licensemac").val();
        var prdid = $("#formlicense input[type=hidden][name=id]").val();
        if (mac == ""){
            return $.alert("mac地址不能为空！");
        }
        if (!prdid || prdid == ""){
            return $.alert("选择授权的产品！");
        }
        $.confirm('确认要授权这些设备吗？', function(text, index){
            var data = "mac="+mac+"&productid="+ prdid;
            $.ajax({
                url:"orderLincese.action",
                data: data,
                success: function (resp) {
                    console.log(resp)
                    if (resp.succ)
                        $.toast(resp.msg, function () {
                            location.href = "mlicense.action";
                        });
                    else
                        $.toast(resp.msg, "forbidden");
                }
            });
        });
    }
</script>
</body>
</html>