<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    if(com.sundyn.util.CommonUtil.isMobileAgent()){
        response.sendRedirect(basePath+"morder.action");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>广州市众鑫电子设备授权管理系统</title>
    <link rel="stylesheet" href="css/style.css" media="all">
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
</head>
<body class="layui-layout-body">

<div id="LAY_app">
    <div class="layui-layout layui-layout-admin">
        <!-- 主体内容 -->
        <div class="layui-form" id="LAY_app_body">
                <div class="layui-tab" lay-filter="mytab">
                    <ul class="layui-tab-title">
                        <li <c:if test="${param.t==0 || param.t==null}"> class="layui-this"</c:if>><s:text name="main.loginmsg" /></li>
                        <li <c:if test="${param.t==1}"> class="layui-this"</c:if>><s:text name="main.profile"/></li>
                        <li <c:if test="${param.t==2}"> class="layui-this"</c:if>>现在下单</li>
                        <li <c:if test="${param.t==3}"> class="layui-this"</c:if>>订单管理</li>
                        <li <c:if test="${param.t==4}"> class="layui-this"</c:if>>授权管理</li>
                        <li <c:if test="${param.t==5}"> class="layui-this"</c:if> style="color:blue;">退出</li>
                    </ul>
                    <div class="layui-tab-content">
                        <div class="layui-tab-item<c:if test="${param.t==0 || param.t==null}"> layui-show</c:if>">
                            <table align="center" width="100%" borde="0" cellpadding="0" cellspacing="0" style="border:0px;">
                                <tr>
                                    <td width="120" align="right">
                                        当前登录时间:
                                    </td>
                                    <td align="left">
                                        ${curlogintime}
                                    </td>
                                    <td align="right">
                                        上次登录时间:
                                    </td>
                                    <td align="left">
                                        ${data2.ctime}
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        当前登录IP:
                                    </td>
                                    <td align="left">
                                        ${curloginadd}
                                    </td>
                                    <td align="right">
                                        上次登录IP:
                                    </td>
                                    <td align="left">
                                        ${data2.ipadd}
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        近期登录失败记录:
                                    </td>
                                    <td align="left" colspan="3">
                                        <c:forEach items="${data}" var="item">
                                            <li>${item.ctime}${item.logrstdesc}
                                            </li>
                                        </c:forEach>
                                    </td>
                                </tr>
                                <c:if test="${not empty type}">
                                    <tr>
                                        <td colspan="2">
                                            <div class='col-12'>
                                                <c:choose>
                                                    <c:when test="${type=='input'}">
                                                        <c:set value="alert-warning" var="msgclass"></c:set>
                                                    </c:when>
                                                    <c:when test="${type=='error'}">
                                                        <c:set value="alert-danger" var="msgclass"></c:set>
                                                    </c:when>
                                                    <c:when test="${type=='success'}">
                                                        <c:set value="alert-success" var="msgclass"></c:set>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set value="alert-info" var="msgclass"></c:set>
                                                    </c:otherwise>
                                                </c:choose>
                                                <div class='alert ${msgclass}'>
                                                    <small>${msg}</small>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                        </div>
                        <div class="layui-tab-item<c:if test="${param.t==1}"> layui-show</c:if>">
                            <form class="layui-fluid" id="formex">
                                <div class="layui-form-item">
                                    <label class="layui-form-label">我的角色：</label>
                                    <div class="layui-input-inline">
                                        ${powers}
                                    </div>
                                    <div class="layui-form-mid layui-word-aux"></div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">用户名：</label>
                                    <div class="layui-input-inline">
                                        ${sessionScope.get("manager").name}
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">真实姓名：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="realname" value="${managerentity.realname}" placeholder="真实姓名" class="layui-input" >
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">公司名称：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="orgname" value="${managerextentity.orgname}" placeholder="公司名称" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">联系人：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="contact" value="${managerentity.contact}" placeholder="联系人" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">手机：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="telphone" value="${managerentity.telphone}" placeholder="手机" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">邮箱：</label>
                                    <div class="layui-input-inline">
                                        <input type="text" name="email" value="${managerentity.email}" placeholder="邮箱" class="layui-input">
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label"></label>
                                    <div class="layui-input-inline" style="width:100%;">
                                        <input type="button" class="layui-btn" onclick="saveManagerExt()" value="<s:text name="main.save"/>" />
                                        <input type="button" class="layui-btn layui-btn-primary" onclick="refreshTab()" value="<s:text name="main.reset"/>" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="layui-tab-item<c:if test="${param.t==2}"> layui-show</c:if>">
                            <form class="layui-fluid" id="formorder">
                                <c:forEach items="${productlist}" var="entity" varStatus="s">
                                    <div class="layui-form-item">
                                        <div class="layui-input-inline" style="width:250px;">
                                            <font style="font-size:20px;" class="productname">${entity.productname}</font>
                                        </div>
                                        <div class="layui-input-inline">
                                            单价（元）：<fmt:formatNumber type="number" value="${entity.rateprice}" groupingUsed="false" />
                                        </div>
                                        <div class="layui-input-inline" style="width:37px;">
                                            数量：
                                        </div>
                                        <div class="layui-input-inline">
                                            <input type="text" value="0" name="txtprodnum" class="layui-input productnum" style="width: 50px;" />
                                            <input type="hidden" value="${entity.rateprice}" />
                                        </div>
                                        <div class="layui-input-inline">
                                            总价（元）：<label class="producttotal">0.00</label>
                                            <input name="id" type="hidden" value="${entity.id}" />
                                        </div>
                                        <div class="layui-form-mid layui-word-aux">

                                        </div>
                                    </div>
                                </c:forEach>
                                <div class="layui-form-item">
                                    <label class="layui-form-label">备注：</label>
                                    <div class="layui-input-inline" style="width:100%;">
                                       <textarea name="comment" class="layui-textarea" style="width:800px;"></textarea>
                                    </div>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label"></label>
                                    <div class="layui-input-inline" style="width:100%;">
                                        <input type="button" class="layui-btn" onclick="submitorder()" value="提交订单" />
                                        合计（元）：<label id="totaldeci">0.00</label>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="layui-tab-item<c:if test="${param.t==3}"> layui-show</c:if>">
                            <table style="width:100%;border: 0px;" border="0" cellpadding="0" cellspacing="0">
                                <thead>
                                <tr>
                                    <th style="width:20px;"></th>
                                    <th style="width:250px;">订单编号</th>
                                    <th style="width:300px;">下单时间</th>
                                    <th style="width:150px;">总价（元）</th>
                                    <th style="width:280px;">支付状态/时间</th>
                                    <th style="width:420px;">订单状态</th>
                                    <th>备注</th>
                                    <th></th>
                                </tr>
                                </thead>
                            <c:forEach items="${orderlist}" var="entity" varStatus="s">
                                <tr>
                                    <td><img src="images/img/nolines_plus.gif" style="width:32px;height:32px;cursor:pointer;" onclick="toggledetail(this)"/></td>
                                    <td>
                                        <font style="font-size:20px;">${entity.orderno}</font>
                                    </td>
                                    <td>
                                        <font style="font-size:20px;"><fmt:formatDate value="${entity.ctime}" type="both" /></font>
                                    </td>
                                    <td style="font-size:20px;">
                                        <fmt:formatNumber type="number" value="${entity.totalfee * 1.0}" groupingUsed="false" pattern="0.00" />
                                    </td>
                                    <td>
                                        <c:if test="${entity.ispay==0}"><font color="red" style="font-size:20px;">未支付</font></c:if>
                                        <c:if test="${entity.ispay==1}"><font color="blue" style="font-size:20px;">已支付</font> / <fmt:formatDate value="${entity.paytime}" type="both" /></c:if>
                                    </td>
                                    <td>
                                            <c:if test="${entity.status==0}"><font style="font-size:20px;">等待支付</font> </c:if>
                                            <c:if test="${entity.status==1}"><font color="blue" style="font-size:20px;">已完成</font> </c:if>
                                            <c:if test="${entity.status==-2}"><font color="red" style="font-size:20px;">管理员关闭</font> （时间：<fmt:formatDate value="${entity.closetime}" type="both" />，原因：${entity.closedesc}）</c:if>
                                            <c:if test="${entity.status==-1}"><font color="red" style="font-size:20px;">取消</font> （时间：<fmt:formatDate value="${entity.canceltime}" type="both" />，原因：${entity.canceldesc}）</c:if>
                                    </td>
                                    <td>
                                        <font style="font-size:20px;">${entity.comment}</font>
                                    </td>
                                    <td>
                                        <c:if test="${entity.status==0}">
                                        <a class="layui-btn layui-btn-danger layui-btn-xs"
                                           onclick="event.stopPropagation();cancelorder(${entity.id});"><i
                                                class="layui-icon layui-icon-delete"></i>取消订单</a>
                                        </c:if>
                                    </td>
                                </tr>
                                <tr style="display: none;">
                                    <td colspan="7" style="padding-left:42px;">
                                        <c:forEach items="${orderprdlist}" var="entity2" varStatus="s">
                                            <c:if test="${entity.id == entity2.orderid}">
                                                <div class="layui-form-item">
                                                    <div class="layui-input-inline" style="width:120px;">
                                                        下单数量：<font style="font-size:20px;">${entity2.num}</font>
                                                    </div>
                                                    <div class="layui-input-inline" style="width:220px;">
                                                        下单时单价（元）：<font style="font-size:20px;"><fmt:formatNumber type="number" value="${entity2.realprice}" groupingUsed="false" /></font>
                                                    </div>
                                                    <div class="layui-input-inline" style="width:237px;">
                                                        下单时总价（元）：<font style="font-size:20px;">${entity2.num * entity2.realprice}</font>
                                                    </div>
                                                </div>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                            </table>
                        </div>
                        <div class="layui-tab-item<c:if test="${param.t==4}"> layui-show</c:if>">
                            <c:forEach items="${prdlicensedetails}" var="entity2" varStatus="s">
                                <div class="layui-form-item">
                                    <div class="layui-input-inline" style="width:220px;">
                                        产品名称：${entity2.productname}
                                    </div>
                                    <div class="layui-input-inline" style="width:137px;">
                                        总授权数：<font>${entity2.totalnum}</font>
                                    </div>
                                     <div class="layui-input-inline" style="width:137px;">
                                         剩余授权数：<font>${entity2.totalnum-entity2.usednum}</font>
                                    </div>
                                    <div class="layui-input-inline" style="width:137px;">
                                        已使用授权数：<c:if test="${entity2.usednum==0}"><font>${entity2.usednum}</font></c:if>
                                        <c:if test="${entity2.usednum>0}">
                                            <a href="#" style="color:blue; text-decoration: underline;" onclick="dialoglicensedetail('${entity2.id}')"><font>${entity2.usednum}</font></a>
                                        </c:if>
                                    </div>
                                    <div class="layui-input-inline" style="width:137px;">
                                        <a href="#" onclick="dialogdown('${entity2.id}')">附件下载</a>
                                    </div>
                                </div>
                            </c:forEach>
                            <fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
                                <legend>授权</legend>
                            </fieldset>
                            <div class="layui-form-item">
                                <div class="layui-input-inline" style="width:100%;">
                                    授权MAC地址（批量授权使用英文逗号分隔）：<textarea id="licensemac" class="layui-textarea"></textarea>
                                </div>
                            </div>
                            <div class="layui-form-item">
                                <div class="layui-input-inline" style="width:100%;">
                                    <c:forEach items="${productlist}" var="entity" varStatus="s">
                                        <input type="radio" name="rdlicenseproduct" value="${entity.id}" />${entity.productname}
                                    </c:forEach>
                                </div>
                                <div class="layui-form-item">
                                    <label class="layui-form-label"></label>
                                    <div class="layui-input-inline" style="width:100%;">
                                        <input type="button" class="layui-btn" onclick="saveLicense()" value="提交授权" />
                                        <a href="#" id="importmac" style="color:blue;">导入</a> <a style="color:#bbb;">只能导入文本文件，每个MAC地址一行，不需要加逗号</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        <!-- 辅助元素，一般用于移动设备下遮罩 -->
        <div class="layadmin-body-shade" layadmin-event="shade"></div>
    </div>
</div>

<script type="text/javascript" src="lib/layui/layui.js"></script>

<script>
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use(['element','layer', 'form', "upload"],function(){
        var element = layui.element;
        element.on('tab(mytab)', function(data){
            if (data.index == 5){
                location.href="managerLogout.action";
            }
        });

        var $ = layui.jquery, upload = layui.upload;
        $("#licensemac").val('');
        upload.render({
            elem: '#importmac'
            ,url: 'macUpload.action'
            ,accept: 'file' //普通文件
            ,auto: false
            ,choose: function(obj){
                obj.preview(function(index, file, result){
                    console.log(file, index, result);
                    $("#licensemac").val(file.filecontent);
                    obj.upload(index, file);
                });
            }
            ,done: function(res){
                if (res.succ){
                    $("#licensemac").val(res.filecontent);
                    $("#filepreview").attr("href", res.filepath);
                    $("#filepreview").html(res.filename);
                }
                else
                    error("文件上传失败");
            }
        });
    });

    function saveManagerExt() {
        lsubmit("saveManagerExt.action", $("#formex").serialize(), function (res) {
            if (res.succ){
                succ(res.msg);
            }
            else
                error(res.msg);
        });
    }

    $(function () {
        $("input[name=txtprodnum]").change(function () {
            var num = parseInt($(this).val());
            if(!isNumber(num) || parseInt(num)<0){
                num = 0;
                $(this).val(0);
            }

            var price = parseFloat($(this).next().val());
            $(this).parent().parent().find("label").html(Math.round(num * price * 100)/100);

            var allprod = $(".producttotal");
            var totalprice = 0;
            for (var i=0; i<allprod.length; i++){
                var v = $(allprod[i]).html();
                if(isNumber(v)){
                    totalprice += parseFloat(v);
                }
            }
            totalprice = Math.round(totalprice*100)/100;
            $("#totaldeci").html(totalprice)
            //console.log("数量：" + num + ",单价：" + price);
        });
    });

    function submitorder() {
        var allprod = $(".producttotal");
        var totalprice = 0;
        var confirmmsg = "";
        var poststr = "";
        for (var i=0; i<allprod.length; i++){
            var v = $(allprod[i]).html();
            if(isNumber(v)){
                if(confirmmsg!='')
                    confirmmsg += "<br>";
                if (parseFloat(v)<=0)
                    continue;
                totalprice += parseFloat(v);
                var productname = $(allprod[i]).parent().parent().find(".productname").html();
                var productid = $(allprod[i]).next().val();
                var productnum = $(allprod[i]).parent().prev().find("input:eq(0)").val();
                confirmmsg += "\"" + productname + "\"   数量：" + productnum + "   总价：" + parseFloat(v);
                poststr += "," + productid + ":" + productnum;
            }
        }
        if(poststr == '')
            return error("请填写下单的数量！");
        layer.confirm(confirmmsg, {title:"订单确认"},function () {
            lsubmit("saveOrder.action", $("#formorder").serialize(), function (res) {
                if (res.succ){
                    lalert3(res.msg + "，请联系管理员支付后，在\"授权管理\"中进行设备授权！", function(){location.href="order.action?t=3";});
                }
                else
                    error(res.msg);
            });
        })
    }

    function isNumber(val){
        var regPos = /^\d+(\.\d+)?$/; //非负浮点数
        var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
        if(regPos.test(val) || regNeg.test(val)){
            return true;
        }else{
            return false;
        }
    }

    function toggledetail(obj){
        var o =$(obj);
        o.parents('tr').next().toggle('normal', function(){
            if($(o).attr('src')==='images/img/nolines_plus.gif'){
                $(o).attr('src', 'images/img/nolines_minus.gif');
            }else {
                $(o).attr('src', 'images/img/nolines_plus.gif');
            }
        });
    }

    function cancelorder(id){
        layer.prompt({title: '确认要取消该订单吗？', formType: 2}, function(text, index){
            lsubmit("orderCancel.action", {id:id, canceldesc: text}, function (resp, ioArgs) {
                if(resp.succ)
                    succ(resp.msg, function(){
                        location.href="order.action?t=3";
                    });
                else
                    error(resp.msg);
            });
        });
    }

    function saveLicense(){
        var mac = $("#licensemac").val();
        var prdid = $("input[type=radio][name=rdlicenseproduct]:checked").val();
        if (mac == ""){
            return lalert("mac地址不能为空！");
        }
        if (!prdid || prdid == ""){
            return lalert("选择授权的产品！");
        }
        layer.confirm('确认要授权这些设备吗？', function(text, index){
            var data = "mac="+mac+"&productid="+ prdid;
            lsubmit("orderLincese.action", data, function (resp) {
                console.log(resp)
                if(resp.succ)
                    succ(resp.msg, function(){
                        location.href="order.action?t=4";
                    });
                else
                    error(resp.msg);
            });
        });
    }

    function dialoglicensedetail(productid){
        new dialog().iframe("dialoglicensedetail.action?productid=" + productid, {type:1, title: false, resize:false, w:"1060px", h:"840px"});
    }

    function dialogdown(productid){
        new dialog().iframe("dialogdown.action?productid=" + productid, {type:1, title: false, resize:false, w:"1060px", h:"840px"});
    }
</script>
</body>
</html>


