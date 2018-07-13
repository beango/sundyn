<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.sundyn.util.SundynSet"%>
	<%
	String path= request.getRealPath("/");
  	SundynSet set=SundynSet.getInstance(path);
	String title=set.getM_content().get("title").toString();
	String logo=set.getM_content().get("logo").toString();
 	String url = set.getM_content().get("requestAddress").toString();
 	%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title> <s:text name="sundyn.title" /> </title>
		<link rel="stylesheet" href="style/login.css" type="text/css"></link>
        <link rel="stylesheet" href="${ctx}/css/zh-cn.default.css" type="text/css" media="screen">
        <!--<link rel="stylesheet" href="./用户登录 - 禅道_files/zh-cn.default.css" type="text/css" media="screen">-->
        <style>#featurebar ul.nav li .chosen-container a.chosen-single{background:#F8FAFE; border:none; -webkit-box-shadow:none;box-shadow:none; padding-top:5px;}
        #featurebar ul.nav li .chosen-container .chosen-drop {min-width: 200px;!important}
        #dept_chosen.chosen-container .chosen-drop {min-width: 400px;!important}
        #login-panel{margin: 0 auto;width: 540px;min-height: 280px;background-color: #fff;border: 1px solid #dfdfdf;-moz-border-radius:3px; -webkit-border-radius:3px; border-radius:3px;-moz-box-shadow:0px 0px 30px rgba(0,0,0,0.75); -webkit-box-shadow:0px 0px 30px rgba(0,0,0,0.75); box-shadow:0px 0px 30px rgba(0,0,0,0.75)}
        #login-panel .panel-head{min-height: 70px;background-color: #edf3fe;border-bottom: 1px solid #dfdfdf;position: relative}
        #login-panel .panel-head h4{margin: 0 0 0 20px;padding: 0;line-height: 70px; font-size: 14px}
        #login-panel .panel-actions{float: right;position: absolute;right: 15px;top: 18px;padding: 0}
        #login-panel .panel-actions .dropdown {display: inline-block; margin-right: 2px}
        #mobile {font-size: 28px; padding: 1px 12px; line-height: 28px}
        #mobile i {font-size: 28px;}
        #container{padding-top: 24%;}
        #login-panel .panel-content{padding-left: 150px; min-height: 161px;background-size:120px 120px;}
        #login-panel .panel-content table{border: none;width: 300px;margin: 20px auto}
        #login-panel .panel-content .button-s{width: 80px}
        #login-panel .panel-content .button-c{width: 88px;margin-right: 0}
        #login-panel .panel-foot{text-align: center;padding: 15px;line-height: 2em;background-color: #e5e5e5;border-top: 1px solid #dfdfdf}
        #poweredby{float: none; color: #eee;text-align: center;margin: 10px auto}
        #poweredby a{color: #fff}
        #keeplogin label {font-weight: normal}
        .popover {max-width: 500px}
        .popover-content {padding: 0; width: 297px}
        .btn-submit {min-width: 70px}
        .body-modal #container {margin: 0;}
        .body-modal {padding: 40px 0 25px;}
        .msg{color:red;}
        </style>
        <script laguage="Javascript">
            function _$(id){return document.getElementById(id);}
            function setCookie(k,v,d){var x=new Date();x.setDate(x.getDate()+d);document.cookie=k+"="+escape(v)+((d==null)?"":";expires="+x.toGMTString());}
            function getCookie(k){if(document.cookie.length>0){var x1=document.cookie.indexOf(k+"=");if(x1!=-1){x1=x1+k.length+1;x2=document.cookie.indexOf(";",x1);if(x2==-1){x2=document.cookie.length;}return unescape(document.cookie.substring(x1,x2));} }return "";}
            if(top.location != this.location){top.location = this.location;}
            function doclick(){
                var s = "";
                if(!_$('name').value){s += "账号不能为空\n";}
                if(!_$('password1').value){s += "密码不能为空\n";}
                if(s != ""){alert(s);return;}
                if(_$("savename").checked){setCookie('savename',_$('name').value,365);}else{setCookie('savename','',0);}
                try{_$('password').value = _$('password1').value//$jskey.md5($jskey.md5(_$('password1').value));
                }catch(e){}
                _$('v').submit();
            }
        </script>
	</head>
    <body>

    <div class="newLogin">
        <div class="nLContent">
            <ul\>
                <li>
                    <img src="images/managementLogin.jpg" style="width: 100%;height: 632px;" />
                </li>
            </ul>
                <div class="nLpostion">
                <div class="wid980">
                    <div style="height: 800px;">
                        <div class="guang">
                        </div>
                        <div class="logo">
                        </div>
                        <div style="width: 400px; position: relative; top: 245px; left: 240px;">
                            <div class="logo_txt"></div>
                            <div class="login_box">
                                <div style="height: 10px;"></div>
                                <form method="post" id="v" action="managerLogin.action" class="form-condensed">
                                    <table class="table table-form">
                                        <tbody><tr>
                                            <th>用户名</th>
                                            <td><input class="form-control" type="text" name="managerVo.name" id="name" value="admin"></td>
                                        </tr>
                                        <tr>
                                            <th>密码</th>
                                            <td><input class="form-control" type="password" name="password1" id="password1" value="a">
                                                <input type="hidden" title="密码" name="managerVo.password" id="password" value="" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th></th>
                                            <td id="keeplogin"><label class="checkbox-inline"><input type="checkbox"  value="on" id="savename"> 记住用户名</label></td>
                                        </tr>
                                        <tr>
                                            <th></th>
                                            <td>
                                                <input type="button" class="btn btn-primary" data-loading="稍候..." value="登 录" onclick="doclick()" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <th></th>
                                            <td>
                                                <div class="msg" style="height:20px;">${msg}</div>
                                            </td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </form>
                                <div>
                                </div>
                            </div>
                            <div class="login_footer">

                            </div>
                        </div>
                    </div>
                </div>
            </div>
                <input type="submit" value="提交" />
            </div>
    </div>
    </body>
  </html>
