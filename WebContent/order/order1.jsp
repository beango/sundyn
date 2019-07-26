<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="powers" value="${pageContext.session.getAttribute('powers')}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <title><s:text name='zx.title'/>登录信息查询</title>
    <link rel="stylesheet" href="css/style.css" media="all">
    <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
    <script language="javascript">
        function check(){
            if(document.getElementById("oldPsw").value==""){
                alert("<s:text name='sundyn.changePassword.pleaseOldPassword' />");
                return false;
            }
            if(document.getElementById("newPsw").value==""){
                alert("<s:text name='sundyn.changePassword.pleaseNewPassword1' />");
                return false;
            }
            if(document.getElementById("newPsw2").value==""){
                alert("<s:text name='sundyn.changePassword.pleaseNewPassword2' />");
                return false;
            }
            f.submit();
        }
    </script>
</head>
<body>
<div class="place">
    <span><s:text name="main.placetitle" /></span>
    <ul class="placeul">
        <li><a href="#"><s:text name="main.loginmsg" /></a></li>
    </ul>
</div>
<div class="layui-tab">
    <ul class="layui-tab-title">
        <li><s:text name="main.loginmsg" /></li>
        <li><s:text name="main.profile"/></li>
        <li class="layui-this">现在下单</li>
        <li>商品管理</li>
        <li>订单管理</li>
    </ul>
    <div class="layui-tab-content">
        <div class="layui-tab-item">
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
                        用户有效期:
                    </td>
                    <td align="left">
                        ${uexpired} (距离到期天数：<fmt:formatNumber type="number" value="${(uexpired.time-now.time)/1000/60/60/24}" pattern="#0" />)
                    </td>
                    <td align="right">
                        密码有效期:
                    </td>
                    <td align="left">
                        ${pwdexpired} (距离到期天数：<fmt:formatNumber type="number" value="${(pwdexpired.time-now.time)/1000/60/60/24}" pattern="#0" />)
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        当前在线用户数:
                    </td>
                    <td align="left">
                        ${activeSessions==null?0:activeSessions.size()}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        近期登录失败记录:
                    </td>
                    <td align="left" colspan="3">
                        <c:forEach items="${data}" var="item">
                            <li>
                                    ${item.ctime}
                                    ${item.logrstdesc}
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
        <div class="layui-tab-item">
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
        <div class="layui-tab-item layui-show">
            <form class="layui-fluid" id="formorder">
                <c:forEach items="${productlist}" var="entity" varStatus="s">
                    <div class="layui-form-item">
                        <div class="layui-input-inline" style="width:250px;">
                            <font style="font-size:20px;" class="productname">${entity.productname}</font>
                        </div>
                        <div class="layui-input-inline">
                            单价（元）：<fmt:formatNumber type="number" value="${entity.price}" groupingUsed="false" />
                        </div>
                        <div class="layui-input-inline" style="width:37px;">
                            数量：
                        </div>
                        <div class="layui-input-inline">
                            <input type="text" value="0" name="txtprodnum" class="layui-input productnum" style="width: 50px;" />
                            <input type="hidden" value="${entity.price}" />
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
                    <label class="layui-form-label"></label>
                    <div class="layui-input-inline" style="width:100%;">
                        <input type="button" class="layui-btn" onclick="submitorder()" value="提交订单" />
                        合计（元）：<label id="totaldeci">0.00</label>
                    </div>
                </div>
            </form>
        </div>
        <div class="layui-tab-item">内容1</div>
        <div class="layui-tab-item">内容1</div>
    </div>
</div>

<script>
    //注意：选项卡 依赖 element 模块，否则无法进行功能性操作
    layui.use(['element','layer'], function(){
        var element = layui.element;
    });

    function saveManagerExt() {
        lsubmit("saveManagerExt.action", $("#formex").serialize(),function (res) {
            console.log(res);
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
                console.log(res);
                if (res.succ){
                    succ(res.msg);
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
</script>
</body>
</html>
