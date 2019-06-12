<%@ page import="com.sundyn.util.MyRequest" %>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title><s:text name='zx.title'/></title>
        <link rel="stylesheet" href="css/style.css" media="all">
        <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script language="JavaScript" src="js/dojo.js"></script>
        <script type="text/javascript" src="lib/layui/layui.all.js"></script>
        <script language="JavaScript" src="js/md5.min.js"></script>
        <script language="JavaScript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script>
            // base64加密开始
            var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"
                + "wxyz0123456789+/" + "=";

            function encode64(input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;
                do {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);
                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;
                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }
                    output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)
                        + keyStr.charAt(enc3) + keyStr.charAt(enc4);
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
                } while (i < input.length);

                return output;
            }

            function _$(id){return document.getElementById(id);}
        </script>
		<script language="javascript">
		function check(){
			if(document.getElementById("oldPsw").value==""){
					lalert("旧密码不能为空！");
					return false;
			}
			if(document.getElementById("newPsw").value==""){
					lalert("新密码不能为空！");
					return false;
			}
			if(document.getElementById("newPsw2").value==""){
					lalert("确认新密码不能为空！");
					return false;
			}
			submit();
		}

        function submit(){
            var s = "";
            if(!_$('oldPsw').value){s += "旧密码不能为空!";}
            if(!_$('newPsw').value){s += "新密码不能为空!";}
            if(!_$('newPsw2').value){s += "确认新密码不能为空!";}
            if(s != ""){
                layer.msg(s, {icon: 2});
                return;
            }
            if(_$('newPsw').value!=_$('newPsw2').value){
                layer.msg("确认新密码错误！", {icon: 2});
                return;
            }
            lsubmit("managerChangePswDeal2.action", {
                type: '${type}',
                name: encode64('${name}'),
                oldPsw:md5(_$('oldPsw').value),
                newPsw:md5(_$('newPsw').value),
                newPsw2:md5(_$('newPsw2').value)
            }, function(resp){
                console.log(resp)
                if (resp.succ){
                    succ(resp.msg, function () {
                        if (resp.home)
                            location.href="/login.jsp";
                    });
                }
                else
                {
                    if (resp.msg == "信息被变更,请重新修改密码!"){
                        lalert2(resp.msg, redir2Changepwd);
                    }
                    else
                        error(resp.msg);
                }
            });
        }
		</script>
 	</head>
    <body>
    <div class="place">
        <span><s:text name="main.placetitle" /></span>
        <ul class="placeul">
            <li><a href="#">修改密码</a></li>
        </ul>
    </div>
    <div class="layui-main">
        <form method="post" class="layui-form" action="managerChangePswDeal2.action" id="f">
            <table align="center" width="100%" borde="0" cellpadding="0" cellspacing="0" style="border:0px;">
                <tr>
                    <td width="120" align="right">
                        <s:text name="sundyn.changePassword.oldPassword" />
                    </td>
                    <td align="left">
                        <input type="password" name="oldPsw" id="oldPsw" class="input_comm" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <s:text name="sundyn.changePassword.newPassword1" />
                    </td>

                    <td align="left">
                        <input type="password" name="newPsw" id="newPsw" class="input_comm" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <s:text name="sundyn.changePassword.newPassword2" />
                    </td>
                    <td align="left">
                        <input type="password" name="newPsw2" id="newPsw2" class="input_comm" />
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        <input name="" type="button" class="scbtn" onclick="return check()" value="提交">
                    </td>
                    <td align="left">
                        <input name="" type="button" class="scbtnn" onclick="return f.reset()" value="重置">
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
        </form>
    </div>
 	</body>
</html>
