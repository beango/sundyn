<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<% String videofile=(String)request.getParameter("videofile");
%>
<head>


<script type="text/javascript" src="swfobject.js"></script>


</head>
<body>


<h3><span style="color:red;">查看其他视频时,请关闭此页面</span></h3>


<p id="player1"><a href="http://www.macromedia.com/go/getflashplayer">Get the Flash Player</a> to see this player.</p>
<script type="text/javascript">
	var s1 = new SWFObject("flvplayer.swf","single","700","500","7");
	s1.addParam("allowfullscreen","true");
	s1.addVariable("file","../download/recorder/<%=videofile%>");
    //s1.addVariable("file","ftp://111.230.14.84/recorder/<%=videofile%>");
	s1.addVariable("image","preview.jpg");
	s1.addVariable("width","700");
	s1.addVariable("height","600");//s1.addVariable("autostart","true");
	s1.write("player1");
</script>

</body>

</html>
