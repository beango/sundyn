<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>我的</title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <meta name="description" content="Write an awesome description for your new site here. You can edit this line in _config.yml. It will appear in your document head meta (for Google search results) and in your feed.xml site description.
">
    <link rel="stylesheet" href="css/weui.min.css">
    <link rel="stylesheet" href="css/jquery-weui.css">
    <link rel="stylesheet" href="css/demos.css">
</head>

<body ontouchstart>
<div class="weui-tab">
    <div class="weui-tab__bd">
        <div id="tab1" class="weui-tab__bd-item weui-tab__bd-item--active">
            <div class="weui-search-bar" id="searchBar">
                <%--<a href="javascript:;" class="weui-btn open-popup weui-form-preview__btn weui-form-preview__btn_primary" data-target="#addorder">下单</a>--%>
            </div>
            <div class="detail_content">
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
                                                    ${entity2.productname}：${entity2.num} 套 单价：<fmt:formatNumber type="currency" pattern="￥.00" value="${entity2.price}" /><br>
                                                </c:if>
                                            </c:forEach>
                                            <br>${entity.comment}
                                        </p>
                                    </div>

                                </a>
                            </div>
                            <div class="weui-cell__ft">
                                <a class="weui-swiped-btn weui-swiped-btn_warn delete-swipeout" href="javascript:" onclick="event.stopPropagation();cancelorder(${entity.id});">取消</a>
                            </div>
                        </div>
                    </c:forEach><div style="height:70px;"></div>
                </div>
            </div>
        </div>
        <div id="tab2" class="weui-tab__bd-item">
            <div class="weui-search-bar" id="searchBar">
                授权查询
            </div>
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
        <div id="tab3" class="weui-tab__bd-item">
            <header class='demos-header'>
                <h1 class="demos-title">登录信息</h1>
            </header>
            <div class="weui-cells weui-cells_form">
                <div class="weui-cells__title">当前登录时间</div>
                <div class="weui-cells">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            ${curlogintime}
                        </div>
                    </div>
                </div>
                <div class="weui-cells__title">上次登录时间</div>
                <div class="weui-cells">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            ${data2.ctime}
                        </div>
                    </div>
                </div>
                <div class="weui-cells__title">当前登录IP</div>
                <div class="weui-cells">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            ${curloginadd}
                        </div>
                    </div>
                </div>
                <div class="weui-cells__title">上次登录IP</div>
                <div class="weui-cells">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            ${data2.ipadd}
                        </div>
                    </div>
                </div>
                <div class="weui-cells__title">近期登录失败记录</div>
                <div class="weui-cells">
                    <div class="weui-cell">
                        <div class="weui-cell__bd">
                            <c:forEach items="${data}" var="item">
                                <li>
                                        ${item.ctime}
                                        ${item.logrstdesc}
                                </li>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="tab4" class="weui-tab__bd-item">
            <header class='demos-header'>
                <h1 class="demos-title">基本资料</h1>
            </header>
            <div class="detail_content">
                <form class="layui-fluid" id="formex">
                    <div class="weui-cells weui-cells_form">
                        <div class="weui-cells__title">我的角色</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    ${powers}
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">用户名</div>
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
                                    <input type="password" name="password" value="" placeholder="密码，如不修改请留空！" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">真实姓名</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="realname" value="${managerentity.realname}" placeholder="真实姓名" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">公司名称</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="orgname" value="${managerextentity.orgname}" placeholder="公司名称" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">联系人</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="contact" value="${managerentity.contact}" placeholder="联系人" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">手机</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="telphone" value="${managerentity.telphone}" placeholder="手机" />
                                </div>
                            </div>
                        </div>
                        <div class="weui-cells__title">邮箱</div>
                        <div class="weui-cells">
                            <div class="weui-cell">
                                <div class="weui-cell__bd">
                                    <input type="text" name="email" value="${managerentity.email}" placeholder="邮箱" />
                                </div>
                            </div>
                        </div>
                        <div style="height:150px;"></div>
                    </div>
                </form>
            </div>
            <div class="weui-form-preview__ft weui-footer_fixed-bottom" style="bottom:3.2em">
                <a href="javascript:;" class="weui-btn weui-btn_primary" style="width:100%;" onclick="saveManagerExt()">保存</a>
            </div>
        </div>
    </div>


    <div id="addorder" class='weui-popup__container popup-bottom'>
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <a href="javascript:;" class="picker-button close-popup">关闭</a>
                    <a href="javascript:;" class="picker-button weui-btn_mini weui-btn_primary" style="margin-right:10px;color:white;" onclick="submitorder()">提交</a>
                    <h1 class="title">选择授权数量</h1>
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
                                        <div class="weui-cell__ft">
                                            <span class="price"><input type="hidden" value="${entity.price}" /><fmt:formatNumber type="currency" pattern="￥.00" value="${entity.price}" /></span>
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
                <p class="summary">
                    共计 <strong class="totalfee">￥0.00</strong>
                </p>
                <div style="height:150px;">
                    <textarea name="comment" placeholder="备注" class="layui-textarea" style="width:99%;height:100px;"></textarea>
                </div>
            </form>
        </div>
    </div>

    <div id="license" class='weui-popup__container popup-bottom'>
        <div class="weui-popup__overlay"></div>
        <div class="weui-popup__modal">
            <div class="toolbar">
                <div class="toolbar-inner">
                    <a href="javascript:;" class="picker-button close-popup">关闭</a>
                    <a href="javascript:;" class="picker-button weui-btn_mini weui-btn_primary" style="margin-right:10px;color:white;" onclick="saveLicense()">提交</a>
                    <h1 class="title">授权</h1>

                </div>
            </div>
            <form id="formlicense">
                <input type="hidden" name="id" value=""/>
                <div class="modal-content">
                    <div class="weui-cells producttotal">
                        授权MAC地址（多个地址使用英文逗号分隔）：<textarea id="licensemac" class="layui-textarea" style="width:99%;height:120px;"></textarea>
                    </div>
                </div>
                <div style="height:48px;">
                </div>
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

    <div class="weui-tabbar">
        <a href="#tab1" class="weui-tabbar__item weui-bar__item--on">
            <div class="weui-tabbar__icon">
                <img src="images/icon_nav_article.png" alt="">
            </div>
            <p class="weui-tabbar__label">我的订单</p>
        </a>
        <a href="#tab2" class="weui-tabbar__item">
            <div class="weui-tabbar__icon">
                <img src="images/icon_nav_msg.png" alt="">
            </div>
            <p class="weui-tabbar__label">授权管理</p>
        </a>
        <a href="#tab3" class="weui-tabbar__item">
            <div class="weui-tabbar__icon">
                <img src="images/icon_nav_button.png" alt="">
            </div>
            <p class="weui-tabbar__label">安全信息</p>
        </a>
        <a href="#tab4" class="weui-tabbar__item">
            <div class="weui-tabbar__icon">
                <img src="images/icon_nav_cell.png" alt="">
            </div>
            <p class="weui-tabbar__label">我的资料</p>
        </a>
    </div>
</div>
<style>
    .summary {
        padding: 8px;
        text-align: right;
        background-color: white;
    }
    .price {
        font-size: .9em;
        margin-right: 6px;
    }
</style>

<script src="js/jquery-2.1.4.js"></script>
<script src="js/fastclick.js"></script>
<script>
    $(function() {
        FastClick.attach(document.body);
    });
</script>
<script src="js/jquery-weui.js"></script>

<script>
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
                        $.toast(res.msg, function(){location.href="morder.action?t=3";});

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

    function openlicense(id, name){
        $("#license .title").html(name);
        $("#formlicense input[type=hidden][name=id]").val(id);
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
                            location.href = "morder.action?t=4";
                        });
                    else
                        $.toast(resp.msg,"forbidden");
                }
            });
        });
    }

    function viewlicensedetail(id) {
    }

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
</body>
</html>
