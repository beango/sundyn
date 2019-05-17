<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<%= basePath %>
<script type="text/javascript">
 if(self!=top){
    document.location.href="<%= basePath %>/queryIndex.action";
    //如果不是最顶层，调用queryIndex.action会跳转到query/index.jsp,显示内容为首页内容不带导航部分。
	}else{
	document.location.href="<%= basePath %>/default.jsp";
	//如果是最顶层，则进入到default.jsp即正式首页，带导航的。	  
  }
</script>