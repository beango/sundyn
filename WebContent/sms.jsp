<%--
  Created by IntelliJ IDEA.
  User: beango
  Date: 19-6-11
  Time: 下午2:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="css/style.css" type="text/css"/>
    <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
    <script type="text/javascript" src="js/dojo.js"></script>
    <script type="text/javascript" src="js/dialog.js"></script>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/my_zh.js"></script>
    <script type="text/javascript" src="lib/layui/layui.js"></script>
    <script type="text/javascript">
        layui.use('layer');
    </script>
</head>
<body>
<%
    String apiCode = request.getParameter("apiCode");
    String mobile = request.getParameter("mobile");
    String content = request.getParameter("content");
    if (apiCode == null || apiCode.equalsIgnoreCase("")) {
        out.println("apiCode不能为空<br>");
    }

     else if (mobile == null || mobile.equalsIgnoreCase("")) {
        out.println("手机号不能为空<br>");
    }

    else if (content == null || content.equalsIgnoreCase("")) {
        out.println("发送内容不能为空<br>");
    }

    else {
        Object[] rst =  com.sundyn.util.SmsJobs.SendSms(mobile, content, 1, apiCode);

        out.println("发送结果：" + rst[0] + "<br>");
        out.println("发送结果：" + rst[1] + "<br>");
    }
%>
<form action="sms.jsp" method="post">
    apiCode：<input type="text" id="apiCode" name="apiCode" class="scinput"><br>
    手机号：<input type="text" id="mobile" name="mobile" class="scinput"><br>
    短信内容：<input type="text" id="content" name="content" class="scinput"><br>

    <input type="submit" class="button" value="发送">

</form>
</body>
</html>
