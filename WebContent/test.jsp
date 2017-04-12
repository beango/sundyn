<%@ page   pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/dojo.js"></script>
	<script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
		<script type="text/javascript" src="js/layer/layer.js"></script>
	<script type="text/javascript">
	var i=0
			function testDll(){
				dojo.xhrGet({url:"deptView.action",   load:function (resp, ioArgs) {
					  var msg=document.getElementById("msg");
					  msg.innerHTML=msg.innerHTML+(++i)+"<br/>"
					 
				}});
			}
	</script>
  </head>
  <body>
      <div id="msg">
      <a onclick="showRemark('xxx')">xxx</a>
      </div>
  </body>
  <script type="text/javascript">
  // window.setInterval("testDll()",50);
  
  
			function showRemark(content){
				
					  layer.alert(content, {
					    skin: 'layui-layer-lan'
					    ,closeBtn: 0
					    ,shift: 4 //动画类型
					  });
				
				
			}
  </script>
</html>
