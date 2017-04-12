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
		<link rel="shortcut icon" href="/logo.ico" type="image/x-icon" />
		<link rel="stylesheet" href="css/login.css" type="text/css"></link>
		<script type="text/javascript">
		  function submitlogin(){
			  var name=document.getElementById("name").value;
			  if(name==""){
				  alert("<s:text name='sundyn.notNullUserName'  /> ");
				  document.getElementById("name").focus();
				  return false;
			  }
			  var password=document.getElementById("password").value;
			  if(password==""){
				  alert("<s:text name='sundyn.notNullPassword'  />");
				  document.getElementById("password").focus();
				  return false;
			  }
			  var rand=document.getElementById("rand").value;
			  if(rand==""){
				  alert("<s:text name='sundyn.pleaseEnterIdentifyingCode'  />");
				  document.getElementById("rand").focus();
				  return false;
			  }
			  document.getElementById("login").submit();
		  }
		  
			 if(self!=top){
			    parent.parent.location.href="login.jsp";
			  }
			  //回车键
			  function KeyDown(){
		     if (event.keyCode == 13)
		     {
 		         submitlogin();
		     }}
 		     function deal(data){
		      data =data.replace(/["^\u4E00-\u9FA5]/g,"");
 		      return data;
		     }
		     function login(){
		   	  window.location.href="<%=url%>"
		     }
		     function employeeLogin(){
		   	      var name=document.getElementById("name").value;
				  if(name==""){
					  alert("<s:text name='sundyn.notNullUserName'  /> ");
					  document.getElementById("name").focus();
					  return false;
				  }
				  var password=document.getElementById("password").value;
				  if(password==""){
					  alert("<s:text name='sundyn.notNullPassword'  />");
					  document.getElementById("password").focus();
					  return false;
				  }
				  var rand=document.getElementById("rand").value;
				  if(rand==""){
					  alert("<s:text name='sundyn.pleaseEnterIdentifyingCode'  />");
					  document.getElementById("rand").focus();
					  return false;
				  }
				  var login=document.getElementById("login")
				  login.action="employeeLogin4.action";
				  login.submit();
 		     }
 		     function getIp(){
				var serverIp=location.hostname;
				alert(serverIp);
 	 		     }

 		    function downloadFile(videoName) 
 		   {  
 		    	var serverIp=location.hostname; 
 		   try{ 
 		   var elemIF = document.createElement("iframe");   
 		   elemIF.src = "http://"+serverIp+"/download/"+videoName;   
 		   elemIF.style.display = "none";   
 		   document.body.appendChild(elemIF);   
 		   }catch(e){ 

 		   } 
 		   }  
  	    </script>
  	    <script type="text/javascript" src="js/dojo.js"> </script>
  	    <script type="text/javascript" src="js/my_zh.js"> </script>
	</head>
	<body>

		<div class="login">
			<div class="main" style="background-image: url('${pageContext.request.contextPath }/images/<s:text name="login.pic.loginBg" />')">
			<div style="width: 100%;height: 20px;"></div>
			    <div class="logo">
<%--			    <div style="width:79px;height:55px;filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(src='<%=logo%>', sizingMethod='scale');">--%>
<%--			    </div>			    --%>
 				<%if(!logo.equals("")){ %>   
			    	<img src="<%=logo%>"  />
			     <%}%>
			    </div>
			    <div class="text"> 
			    <s:if test='getText("sundyn.language") eq "en"'>Zhengzhou Sundyn</s:if>
				<s:else><%if(!title.equals("")){ }%></s:else></div>
				<div class="content">
<%--				<s:text name='sundyn.language' />--%>
<%--				<s:if test='getText("sundyn.language") eq "en"'>sundyn</s:if>--%>
<%--				<s:else></s:else>--%>
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
											id="name"   />
									</td>
									<td width="199" valign="bottom" align="mid"
										style="border-color: rgb(255, 255, 255);" rowspan="3">
										<img alt="<s:text name='login.login' />" src="<s:text name="login.pic.login"/>" border="0"
											style="cursor: pointer;" onclick="submitlogin()" />
											<!-- 
										<img   src="images/dl_10_.gif" border="0"
											style="cursor: pointer;" onclick="employeeLogin()" />
											 -->	
 									</td>
								</tr>
								<tr>
									<td style="border-color: rgb(255, 255, 255);">
										<s:text name="login.password"/>
									</td>
									<td style="border-color: rgb(255, 255, 255);">
										<input type="password" class="input_user"
											name="managerVo.password" id="password"   />
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
			<div class="msg" style="height:20px;font:14px;line-height:25px" >   
		
				 <s:text name="login.info"></s:text><br/>
<%--                 <a href="update/CP210x_VCP_Win_XP_S2K3_Vista_7.exe"><s:text name="login.usbDrive" /></a><s:text name="login.usbDrive.info" />--%>
<%--                 &nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;<br/>--%>
<%--				 <a href="update/client.exe"><s:text name="login.commClient" /></a> <s:text name="login.commClient.info" /><br/>--%>
<%--				 <a href="update/M7.exe"><s:text name="login.m7Client" /></a><s:text name="login.m7Client.info" /><br/>--%>
<%--				 <a href="update/M7-U.exe"><s:text name="login.m7Client-U" /></a><s:text name="login.m7Client.info-U" /><br/>--%>
<%--				 <a href="update/M7ForAndroid.exe"><s:text name="login.m7Client.android" /></a><td><s:text name="login.m7Clinet.android.description" /></td><br/>--%>
				 <a href="update/MClientSetup.exe"><s:text name="login.m8Client.android" /></a><td><s:text name="login.m8Clinet.android.description" /></td><br/>
				 <a href="update/I系列USB驱动.exe"><s:text name="login.usbDriver.android" /></a><td><s:text name="login.usbDriver.android.descriptionForM8" /></td><br/>
				 <td ><s:text name="login.version" />2.1.1</td>

			</div>
		</div>
	</body>
	<script  language="javascript">
	document.getElementById("name").focus();
	//getRand('rand1');
	</script>
  </html>
