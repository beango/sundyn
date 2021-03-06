<%@page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="qloginmsg" value="${pageContext.request.getParameter('msg')==null?'':com.sundyn.utils.ReqUtils.format(pageContext.request.getAttribute('msg').toString())}" />
<%
    java.util.Locale locale = request.getLocale();
    out.print(locale);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><s:text name="login.title" /></title>
    <link href="css/style.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script language="JavaScript" src="js/md5.min.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script language="JavaScript" src="js/dojo.js"></script>
    <script language="JavaScript" src="js/my_zh.js"></script>
    <script language="javascript">
        $(function () {
            layui.use("layer")

            var loginmsg = '${loginmsg}';
            if(loginmsg=='')
                loginmsg = '${msg}';
            var qloginmsg = '${qloginmsg}';
            if (qloginmsg!='')
                loginmsg = qloginmsg;
            if(self!=top){
                top.document.location.href="${ctx}/login.jsp?msg=" + loginmsg;
                return;
            }
            if (loginmsg != '')
                lalert(loginmsg)
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
            if(!_$('name').value){s += "<s:text name="login.userNameNotNULL" />";}
            if(!_$('password1').value){s += "<s:text name="login.passwordNotNULL" />";}
            if(s != ""){
                error(s);
                return;
            }
            _$('password').value = _$('password1').value;
            lsubmit("managerLogin.action", {
                name: encode64(_$('name').value),
                password:md5(_$('password').value)
            }, function(resp){
                if (resp.succ){
                    succ(resp.msg, function () {
                    });
                    loginRedirec();
                }
                else
                {
                    if (resp.msg == "<s:text name="login.passwordNeedChange" />"){
                        lalert2(resp.msg, redir2Changepwd);
                    }
                    else
                        error(resp.msg);
                }
            });
        }

        function loginRedirec() {
            if(self!=top){
                document.location.href="${ctx}/queryIndex.action";
            }else{
                document.location.href="${ctx}/default.jsp";
            }
        }

        function redir2Changepwd() {
            document.location.href="${ctx}/managerChangePsw.action?type=1&name=" + _$('name').value;
        }
    </script>

</head>

<body style="background-color:#1c77ac; background-image:url(<s:text name="login.pic.loginBg" />); background-repeat:no-repeat; background-position:center top; overflow:hidden;">
${locale}
<div class="loginbody" id="loginbody">
    <span class="systemlogo"></span>
    <div class="loginbox">
        <form method="post" id="v" action="managerLogin.action" class="form-condensed">
            <ul>
                <li><label style="font-size:13pt;color:#fff;font-weight:bold;"><s:text name="login.userName" /></label><input  name="managerVo.name" id="name" type="text" class="loginuser" value="" onclick="JavaScript:this.value=''"/></li>
                <li><label style="font-size:13pt;color:#fff;font-weight:bold;"><s:text name="login.password" /></label><input name="password1" id="password1" type="password" class="loginpwd" value="" onclick="JavaScript:this.value=''"/></li>
                <li style="padding-left:75px;"><input name="" type="button" class="loginbtn" value="<s:text name="login.login" />" onclick="doclick()"/>
                    <input type="hidden" name="managerVo.password" id="password" value="" />
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
