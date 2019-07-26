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
<link rel="stylesheet" type="text/css" href="css/ty.css"/>
<div class="wrapper">
    <header><a href="/morder.action" class="logo">我的订单</a>
        <span>
            <a href="javascript:;" class="open-popup" data-target="#addorder">下单</a>
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
            <c:forEach items="${orderlist}" var="entity" varStatus="s">
                <div class="weui-cell weui-cell_swiped">
                    <div class="weui-cell__bd">
                        <a href="javascript:void(0);" class="weui-media-box weui-media-box_appmsg">
                            <div class="weui-media-box__hd sharp color1" style="width:70px; height:70px;background:#d4d4d4;border-radius: 5px;-moz-border-radius: 5px;-webkit-border-radius: 5px;border-radius: 5px 4px 3px 2px;">
                                <span style="display: block;line-height:43px;"><fmt:formatNumber type="currency" pattern="￥.00" value="${entity.totalfee}" /></span>
                                <span style="display: block;line-height:13px;">
                                        <c:if test="${entity.status==0}"><font>未支付</font> </c:if>
                                        <c:if test="${entity.status==1}"><font color="blue">已完成</font> </c:if>
                                        <c:if test="${entity.status==-2}"><font color="red">已关闭</font></c:if>
                                        <c:if test="${entity.status==-1}"><font color="red">已取消</font></c:if>
                                    </span>
                            </div>
                            <div class="weui-media-box__bd">
                                <h4 class="weui-media-box__title">
                                        ${entity.orderno}
                                </h4>
                                <h5>
                                    <span class="weui-media-box__title-after" style="color:#d5d5d5"><fmt:formatDate value="${entity.ctime}" pattern="yyyy-MM-dd" /></span>
                                </h5>
                                <p class="weui-media-box__desc">
                                    <c:forEach items="${orderprdlist}" var="entity2" varStatus="s">
                                        <c:if test="${entity.id == entity2.orderid}">
                                            ${entity2.productname}：${entity2.num} 套 单价：<fmt:formatNumber type="currency" pattern="￥.00" value="${entity2.realprice}" /><br>
                                        </c:if>
                                    </c:forEach>
                                    <br>${entity.comment}
                                </p>
                            </div>

                        </a>
                    </div>
                    <c:if test="${entity.status == 0}">
                    <div class="weui-cell__ft">
                        <a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:" onclick="event.stopPropagation();cancelorder(${entity.id});">取消</a>
                    </div>
                    </c:if>
                </div>
            </c:forEach>
            <div style="height:50px;"></div>
        </div>
    </div>


    <div id="addorder" class='weui-popup__container popup-bottom'>
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <span class="title" style="font-size:15px;">选择授权数量</span>
                </div>
            </div>
            <form id="formorder">
                <div class="modal-content">
                    <div class="weui-cells producttotal">
                        <c:forEach items="${productlist}" var="entity" varStatus="s">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <div class="weui-cell">
                                        <div class="weui-cell__hd"></div>
                                        <div class="weui-cell__bd">
                                            <p>${entity.productname}</p>
                                        </div>
                                        <div class="weui-cell__ft">${entity.rate}
                                            <span class="price"><input type="hidden" value="${entity.rateprice}" /><fmt:formatNumber type="currency" pattern="￥.00" value="${entity.rateprice}" /></span>
                                            <div class="weui-count">
                                                <a class="weui-count__btn weui-count__decrease"></a>
                                                <input class="weui-count__number" type="number" value="0" style="width:40px;" name="txtprodnum"/>
                                                <input type="hidden" name="id" value="${entity.id}"  />
                                                <a class="weui-count__btn weui-count__increase"></a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <p class="summary" style="float:right;">
                    共计 <strong class="totalfee">￥0.00</strong>
                </p>
                <div style="height:110px;">
                    <textarea name="comment" placeholder="备注" class="layui-textarea" style="width:99%;height:100px;"></textarea>
                </div>
                <a href="javascript:;" class="weui-btn weui-btn_primary" onclick="submitorder()">提交</a>

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
    <a href="morder.action" class="weui-tabbar__item weui-bar__item--on">
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
    <a href="mprofile.action" class="weui-tabbar__item">
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

    function isNumber(val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }
    }

    var MAX = 999, MIN = 1;
    $('.weui-count__decrease').click(function (e) {
        var $input = $(e.currentTarget).parent().find('.weui-count__number');
        var $price = $(e.currentTarget).parent().prev().find("input[type=hidden]")
        var number = parseInt($input.val() || "0") - 1
        if (number < MIN) number = MIN;
        $input.val(number);
        caltotalfee();
    })
    $('.weui-count__increase').click(function (e) {
        var $input = $(e.currentTarget).parent().find('.weui-count__number');
        var $price = $(e.currentTarget).parent().prev().find("input[type=hidden]")
        var number = parseInt($input.val() || "0") + 1
        if (number > MAX) number = MAX;
        $input.val(number)
        caltotalfee();
    })

    function caltotalfee() {
        var allprod = $("input[name=txtprodnum]");
        var totalprice = 0;
        for (var i=0; i<allprod.length; i++){
            var num = $(allprod[i]).val();
            var price = $(allprod[i]).val();
            var $price = $(allprod[i]).parent().prev().find("input[type=hidden]");
            var price = parseFloat($($price).val());
            if(isNumber(num)){
                totalprice += parseFloat(num * price);
            }
        }
        totalprice = Math.round(totalprice*100)/100;
        $(".totalfee").html("￥" + totalprice)
        //console.log("数量：" + num + ",单价：" + price);
    }

    $(function(){
        $("input[name=txtprodnum]").change(caltotalfee);
    });

    function submitorder() {
        var allprod = $("input[name=txtprodnum]");
        var totalprice = 0;
        var poststr = "";
        var confirmmsg = "";
        for (var i=0; i<allprod.length; i++){
            var productnum = parseInt($(allprod[i]).val());
            var $price = $(allprod[i]).parent().prev().find("input[type=hidden]");
            var price = parseFloat($($price).val());
            //var productnum = parseInt()
            totalprice += parseFloat(productnum * price);
            var productid = $(allprod[i]).next().val();
            var productname = $(allprod[i]).parent().parent().prev().html();
            poststr += "," + productid + ":" + productnum;
            confirmmsg += productname + "， 数量：" + productnum + "<br>"
        }
        totalprice = Math.round(totalprice*100)/100;
        confirmmsg += "总价：" + totalprice;

        if(totalprice == 0)
            return $.alert("请填写下单的数量！");
        $.confirm(confirmmsg, function () {
            $.ajax({
                url: "saveOrder.action",
                data: $("#formorder").serialize(),
                success: function (res) {
                    if (res.succ){
                        //$.toast(res.msg + "，请联系管理员支付后，在\"授权管理\"中进行设备授权！", function(){location.href="morder.action?t=3";});
                        $.alert({
                            title:res.msg + "，请联系管理员支付后，在\"授权管理\"中进行设备授权！",
                            onOK: function(){
                                location.href="morder.action?t=3";
                            }
                        });
                    }
                    else
                        $.toast(res.msg);
                }
            });
        })
    }

    function cancelorder(id){
        $.alert({
            title: '确认要取消该订单吗？',
            onOK: function(text, index) {
                $.ajax({
                    url: "orderCancel.action",
                    data: {id: id, canceldesc: text},
                    success: function (resp, ioArgs) {
                        if (resp.succ)
                            $.toast(resp.msg, function () {
                                location.href = "morder.action?t=3";
                            });
                        else
                            $.toas(resp.msg);
                    }
                });
            }
        });
    }
</script>
<!--底部固定导航 结束-->

</body>
</html>