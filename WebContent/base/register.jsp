<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <link rel="stylesheet" href="lib/layui/css/layui.2.4.5.css" media="all">
        <link rel="stylesheet" href="css/admin2.css" media="all">
        <link rel="stylesheet" href="css/login2.css" media="all">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/mmain.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<title><s:text name='zx.title'/></title>
	</head>
	<body style="background-image:image('imagesm/bg.jpg') repeat">
    <div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
        <div class="layadmin-user-login-main">
            <div class="layadmin-user-login-box layadmin-user-login-header">
                <h2>账号注册</h2>
                <p></p>
            </div>
            <form>
                <div class="layadmin-user-login-box layadmin-user-login-body layui-form">

                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-nickname"></label>
                        <input type="text" name="name" id="name" placeholder="账号" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
                        <input type="password" name="password" id="password" placeholder="密码" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-repass"></label>
                        <input type="password" name="password2" id="password2" placeholder="确认密码" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
                        <input type="text" name="cellphone" id="cellphone" placeholder="手机" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
                        <input type="text" name="contact" value="" placeholder="联系人" class="layui-input">
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
                        <input type="text" name="realname" value="" placeholder="真实姓名" class="layui-input" >
                    </div>
                    <div class="layui-form-item">
                        <label class="layadmin-user-login-icon layui-icon layui-icon-cellphone" for="LAY-user-login-cellphone"></label>
                        <input type="text" name="orgname" value="" placeholder="公司名称" class="layui-input">
                    </div>
                    <%--<div class="layui-form-item">
                        <input type="checkbox" name="agreement" lay-skin="primary" title="同意用户协议" checked>
                    </div>--%>
                    <div class="layui-form-item">
                        <input type="button" class="layui-btn layui-btn-fluid" onclick="reg()" value="注 册"></input>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script>
        layui.use("layer");
        if (browserRedirect()){
            location.href="mregister.action";
        }
        function reg() {
            var name = $("#name").val();
            var password = $("#password").val();
            var password2 = $("#password2").val();
            var cellphone = $("#cellphone").val();
            var contact = $("#contact").val();
            var realname = $("#realname").val();
            var orgname = $("#orgname").val();
            if ("" == name){
                return error("账号不能为空！");
            }
            if ("" == password){
                return error("密码不能为空！");
            }
            if ("" == password2){
                return error("确认密码不能为空！");
            }
            if(password !== password2){
                return error('两次密码输入不一致！');
            }
            if(cellphone == ""){
                return error('手机不能为空！');
            }
            if(contact == ""){
                return error('联系人不能为空！');
            }
            if(realname == ""){
                return error('真实姓名不能为空！');
            }
            if(orgname == ""){
                return error('公司名称不能为空！');
            }
            //请求接口
            $.ajax({
                url: 'registerPost.action' //实际使用请改成服务端真实接口
                ,type: "POST"
                ,data: $("form").serialize()
                ,success: function(res){
                    console.log(res)
                    if(res.succ){
                        succ(res.msg, function(){
                            location.href = 'login.jsp'; //跳转到登入页
                        });
                    }
                    if (res.succ === false)
                        error(res.msg);
                }
            });
        }
    </script>




<%--

 	   <div id="man_zone">
 	   	  <div class="register_top"></div>
 	   	  <div class="register_2">
 	   	    <table width="463" border="0" cellspacing="0" cellpadding="0" style="border:0px; "   >
			  <tr>
			    <td width="120">&nbsp;</td>
			    <td>&nbsp;</td>
			  </tr>
			  <tr>
			    <td><div align="right"><s:text name='sundyn.register.applyNum'/></div></td>
			    <td align="left"><input  type="text" name="ser" id="ser" style="width: 300px;height: 19px;border: 1px solid #a8acad;" value="${ser}"/>    </td>
			  </tr>
			  <tr>
			    <td><div align="right"><s:text name='sundyn.register.serialNum'/></div></td>
			    <td align="left"> <input  type="text" name="serTxt" id="serTxt" style="width: 123px;height: 18px;border: 1px solid #70a9d4;"/> </td>
			  </tr>
			  <tr>
			    <td  > &nbsp;</td>
			    <td align="left">
                    <input type="button" value="注册" onclick="registerReg()"
                           class="layui-btn"/></td>
			  </tr>
 			</table>
 			<div>${msg}</div>
 	   	  </div>
 	   </div>
		<div id="dialog" style="width: 600px; display: none;">
		</div>--%>
	</body>
</html>
