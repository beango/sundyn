<%@page pageEncoding="UTF-8" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.sundyn.util.SundynSet" %>
<%
    String path = request.getRealPath("/");
    SundynSet set = SundynSet.getInstance(path);
    String title = set.getM_content().get("title").toString();
    String logo = set.getM_content().get("logo").toString();
    String url = set.getM_content().get("requestAddress").toString();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title><s:text name="sundyn.title"/></title>
    <link rel="shortcut icon" href="/logo.ico" type="image/x-icon"/>
    <link rel="stylesheet" href="css/login.css" type="text/css" />
    <link rel='stylesheet' href='http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css'>
    <script type="text/javascript">
        function submitlogin() {
            var name = document.getElementById("name").value;
            if (name === "") {
                alert("<s:text name='sundyn.notNullUserName'  /> ");
                document.getElementById("name").focus();
                return false;
            }
            var password = document.getElementById("password").value;
            if (password == "") {
                alert("<s:text name='sundyn.notNullPassword'  />");
                document.getElementById("password").focus();
                return false;
            }
            var rand = document.getElementById("rand").value;
            if (rand == "") {
                alert("<s:text name='sundyn.pleaseEnterIdentifyingCode'  />");
                document.getElementById("rand").focus();
                return false;
            }
            document.getElementById("login").submit();
        }

        if (self != top) {
            parent.parent.location.href = "login.jsp";
        }

        //回车键
        function KeyDown() {
            if (event.keyCode == 13) {
                submitlogin();
            }
        }

        function deal(data) {
            data = data.replace(/["^\u4E00-\u9FA5]/g, "");
            return data;
        }

        function login() {
            window.location.href = "<%=url%>"
        }

        function employeeLogin() {
            var name = document.getElementById("name").value;
            if (name == "") {
                alert("<s:text name='sundyn.notNullUserName'  /> ");
                document.getElementById("name").focus();
                return false;
            }
            var password = document.getElementById("password").value;
            if (password == "") {
                alert("<s:text name='sundyn.notNullPassword'  />");
                document.getElementById("password").focus();
                return false;
            }
            var rand = document.getElementById("rand").value;
            if (rand == "") {
                alert("<s:text name='sundyn.pleaseEnterIdentifyingCode'  />");
                document.getElementById("rand").focus();
                return false;
            }
            var login = document.getElementById("login")
            login.action = "employeeLogin4.action";
            login.submit();
        }

        function getIp() {
            var serverIp = location.hostname;
            alert(serverIp);
        }

        function downloadFile(videoName) {
            var serverIp = location.hostname;
            try {
                var elemIF = document.createElement("iframe");
                elemIF.src = "http://" + serverIp + "/download/" + videoName;
                elemIF.style.display = "none";
                document.body.appendChild(elemIF);
            } catch (e) {
            }
        }
    </script>
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/my_zh.js"></script>
</head>
<body>
<form action="managerLogin.action" method="post" id="login"
      onsubmit="check();" method="post">
    <div class="mycenter">
        <div class="mysign">
            <div class="col-lg-12 text-center text-info">
                <h2><s:text name="sundyn.title"/></h2>
            </div>
            <div class="col-lg-10">
                <input type="text" class="form-control" name="managerVo.name"
                       id="name" placeholder="账 号" required autofocus/>
            </div>
            <div class="col-lg-10"></div>
            <div class="col-lg-10">
                <input type="password" class="form-control" name="managerVo.password" id="password" placeholder="密 码" required autofocus/>
            </div>
            <div class="col-lg-10"></div>
            <div class="col-lg-10">
                <input type="text" class="form-control" name="rand" id="rand" onkeydown="KeyDown()" style="width:130px;float:left;"/>
                <img src="image.jsp" width="67" height="20" align="absmiddle" style="margin-top:10px;"/>
            </div>
            <div class="col-lg-10"></div>
            <div class="col-lg-10">
                <input type="button" class="btn btn-success col-lg-12" style="padding-left:30px;" onclick="submitlogin()" value="登录"></input>
            </div>
            <div class="col-lg-10"></div>
            <div class="col-lg-10">
                <div class="msg" style="height:20px;">${msg}</div>
            </div>

        </div>
    </div>
</form>


<%--<div class="login">
    <div class="main">
        <div style="width: 100%;height: 20px;"></div>
        <div class="text">
            111</div>
        <div class="content">
            <form action="managerLogin.action" method="post" id="login"
                  onsubmit="check();">
                <table height="75" width="400" cellspacing="0" cellpadding="0"
                       border="0" style="border-color: rgb(255, 255, 255);">
                    <tbody>
                    <tr>
                        <td width="49" style="border-color: rgb(255, 255, 255);">
                            <s:text name="login.userName"/>
                        </td>
                        <td width="151" style="border-color: rgb(255, 255, 255);">
                            <input type="text" class="input_user" name="managerVo.name"
                                   id="name"/>
                        </td>
                        <td width="199" valign="bottom" align="mid"
                            style="border-color: rgb(255, 255, 255);" rowspan="3">
                            <img alt="<s:text name='login.login' />" src="<s:text name="login.pic.login"/>" border="0"
                                 style="cursor: pointer;" onclick="submitlogin()"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-color: rgb(255, 255, 255);">
                            <s:text name="login.password"/>
                        </td>
                        <td style="border-color: rgb(255, 255, 255);">
                            <input type="password" class="input_user"
                                   name="managerVo.password" id="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td style="border-color: rgb(255, 255, 255);">
                            <s:text name="login.identifyingCode"/>
                        </td>
                        <td style="border-color: rgb(255, 255, 255);">
                            <input type="text" class="input_yzm" name="rand" id="rand" onkeydown="KeyDown()"/>
                            <img src="image.jsp" width="67" height="20" align="absmiddle"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
        </div>
        <div class="msg" style="height:20px;">${msg}</div>
    </div>
    <div class="msg" style="height:20px;font-size:14pt;line-height:25px">

        <s:text name="login.info"></s:text><br/>
        <a href="update/MClientSetup.exe"><s:text name="login.m8Client.android"/></a>
        <td><s:text name="login.m8Clinet.android.description"/></td>
        <br/>
        <a href="update/USB-Driver.exe"><s:text name="login.usbDriver.android"/></a>
        <td><s:text name="login.usbDriver.android.descriptionForM8"/></td>
        <br/>
        <td><s:text name="login.version"/>2.1.1</td>

    </div>
</div>--%>
</body>
<script language="javascript">
    document.getElementById("name").focus();
</script>
</html>
