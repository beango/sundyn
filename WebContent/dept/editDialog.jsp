<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.advice.question.list' /></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/jquery-1.4.3.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript">
		   
		</script>
	</head>
	
	<body>
	
<div class="dialog">
			<div class="title">
				<div class="text">
					<c:if test="${dept.deptType==2}"><s:text name="sundyn.dept.modifyDept" /> </c:if>
					<c:if test="${dept.deptType==1}"><s:text name="sundyn.dept.modifyDating" /></c:if>
					<c:if test="${dept.deptType==0}"><s:text name="sundyn.dept.modifyWindow" /></c:if>
				</div>
				<div class="close">${dept.deptPause }
					<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()"/>
				</div>
			</div>
			<div class="content">
				<div class="content_main">
				  <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
					  <tr>
					    <td width="32%" align="right" style="border-color:#FFFFFF;">
						    <c:if test="${dept.deptType==2}"><s:text name="sundyn.dept.deptName" /> </c:if>
							<c:if test="${dept.deptType==1}"><s:text name="sundyn.guide.datingName" /> </c:if>
							<c:if test="${dept.deptType==0}"><s:text name="sundyn.guide.windowName" /></c:if>
 					    </td>
					    <td width="50%" align="left" style="border-color:#FFFFFF;">
					        <input type="hidden" name="deptType" id="deptType" value="${deptType}" />
							<input type="text" name="deptName" id="deptName" class="input_comm"  value="${dept.name}" />
							<c:if test="${deptType==2}"><s:text name="sundyn.dept.xxDept" /></c:if>
							<c:if test="${deptType==1}"><s:text name="sundyn.dept.xxDating" /></c:if>
							<c:if test="${deptType==0}"><s:text name="sundyn.dept.xxWindow" /></c:if>
					    </td>
					    </tr>
<%--					  <tr <c:if test="${dept.deptType==2 || dept.deptType==1}">style="display: none;"</c:if>>--%>
<%--					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.clientConnectType" /></td>--%>
<%--					    <td align="left" style="border-color:#FFFFFF;"> --%>
<%--					       <select name="client_type" id="client_type"  onchange="clientTypeChange(this.value)">--%>
<%--								<option value="1" <c:if test="${dept.client_type==1}"> selected="selected" </c:if>>--%>
<%--									<s:text name="sundyn.guide.comServer" />--%>
<%--								</option>--%>
<%--								<option value="2" <c:if test="${dept.client_type==2}"> selected="selected" </c:if>>--%>
<%--									<s:text name="sundyn.guide.clientProgram" />--%>
<%--								</option>--%>
<%--							</select>--%>
<%--							<select name="product_type" id="product_type" onchange="productTypeChange(this.value)"  <c:if test="${dept.client_type==1}"> style="display: none;" </c:if>  >--%>
<%--								<option value="-1" <c:if test="${dept.product_type==-1}">selected="selected" </c:if>>--%>
<%--									U<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="-2" <c:if test="${dept.product_type==-2}">selected="selected" </c:if>>--%>
<%--									D<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="-3" <c:if test="${dept.product_type==-3}">selected="selected" </c:if>>--%>
<%--									T01U<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="-4" <c:if test="${dept.product_type==-4}">selected="selected" </c:if>>--%>
<%--									M7<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="1" <c:if test="${dept.product_type==1}">selected="selected" </c:if>>--%>
<%--									S<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="2" <c:if test="${dept.product_type==2}">selected="selected" </c:if>>--%>
<%--									W<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="3" <c:if test="${dept.product_type==3}">selected="selected" </c:if>>--%>
<%--									T01<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%-- 							</select>--%>
<%--					    </td>--%>
<%--					  </tr>--%>
					  <tr <c:if test="${dept.deptType==2  }">style="display: none;"</c:if>>
					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.deviceInfo" /></td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					    	<input type="text" name="reMark" id="reMark" value="${dept.remark}" onblur="macCheck(this.value)" />
							<span style="font-size: 9px; color: red;" id="tip">(<s:text name="sundyn.dept.demoOrMac" />)</span>
					    </td>
					  </tr>
					  <c:if test="${dept.deptType==0}">
						  <c:if test="${bind == 'true'}">
						  <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.employeeCardNum" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	<input type="text" name="dept_camera_url" id="dept_camera_url" value="${dept.dept_camera_url}"  onclick="this.select()" onkeyup="employeeFindByCardnumOrName()" class="input_comm" />
						    	<div class="findreslut" id="findreslut" onkeydown="employeeresultdown()">
 						    	</div>
 						    </td>
						  </tr>
						  </c:if>
					  </c:if>
					  <c:if test="${dept.deptType==0  && '0' == '1' }">
 						<tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.businessType" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <select id="dept_businessId">
						    	    <c:forEach var="business" items="${list}">
 						    	      <option value="${business.businessId}"  <c:if test="${ dept.dept_businessId == business.businessId }"> selected="selected"</c:if>  >${business.businessName}</option>
						    	    </c:forEach>
						    	 </select>
  						    </td>
						  </tr>
  						 <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.pauseInfo" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <input type="text" name="deptPause" id="deptPause" value="${dept.ext1}"  class="input_comm" />
						    	 
  						    </td>
						  </tr>
						  <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.pausePic" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <input type="text" name="deptPausePic" id="deptPausePic" value="${dept.ext2}"  class="input_comm" />
						    	 <form id="pic" enctype="multipart/form-data" name="pic"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
									<input type="hidden" name="imgName" id="imgName" />
									<input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" /> 
									<img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="pauseUpload()" class="hand" />
								</form>
  						    </td>
						  </tr>
						  <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.logo" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <input type="text" name="deptLogoPic" id="deptLogoPic" class="input_comm" value="${dept.ext3}"/>
						    	
						    	<form id="pic2" enctype="multipart/form-data" name="pic2"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
									<input type="hidden" name="imgName2" id="imgName2" />
									<input type="file" name="img2" id="img2" onblur="getFileName2()" style="width: 140px;" /> 
									<img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="DeptLogoUpload()" class="hand" />
								</form>
  						    </td>
						  </tr>
						  
 					  </c:if>
 					      <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.playList" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <select id="playListId">
						    	    <c:forEach var="playList" items="${playListList}">
						    	      <option value="${playList.playListId}"  <c:if test="${playList.playListId ==  dept.dept_playListId}"> selected="selected" </c:if>  >${playList.playListName}</option>
						    	    </c:forEach>
						    	 </select>
  						    </td>
						  </tr>
				       
					 <tr >
					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.system.unit.AudioAndVideo"></s:text></td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					       <select name="useVideo" id="useVideo" >
								<option value="0" <c:if test="${dept.useVideo=='0'}"> selected="selected" </c:if>>
									<s:text name="sundyn.system.unit.bothNot"/>
								</option>
								<option value="1" <c:if test="${dept.useVideo=='1'}"> selected="selected" </c:if>>
									<s:text name="sundyn.system.unit.useAudio"/>
								</option>
								<option value="2" <c:if test="${dept.useVideo=='2'}"> selected="selected" </c:if>>
									<s:text name="sundyn.system.unit.useVideo"/>
								</option>
							</select>
					    </td>
					  </tr>
					   <s:if test='getText("sundyn.language") eq "en"'>
					   <tr  style="display:none;">
					   </s:if>
					   <s:else>
					    <tr>
					   </s:else>
					    <td align="right" style="border-color:#FFFFFF;">所属地区</td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					       <select name="provinceid" value="${province.id}" id="provinces" onchange="showCitys()">
					           <s:iterator value="provinces">
					            	<option value="${id}" <s:if test="province.id==id">selected</s:if>  >${name }</option>
					           </s:iterator>
						   </select>
					       <select  value="${cityid}" name="cityid" id="citys" >
						   	   <s:iterator value="citys" >
					            	<option value="${id}" <s:if test="cityid==id">selected</s:if> >${name }</option>
					           </s:iterator>
						   </select>
					    </td>
					  </tr>
					   <tr >
					    <td align="right"  style="border-color:#FFFFFF;"><s:text name="sundyn.system.screenInfo"/></td>
					    <td align="left"  style="border-color:#FFFFFF;"> 
<%--					    	<input type="text" name="notice" id="notice"  value="${dept.notice}"  />--%>
					    	<textarea name="notice" id="notice" rows="8"  cols="35">${dept.notice}</textarea>
					    </td>
					  </tr>
 				 </table>
			   </div>
			</div>
			<div class="bottom">
				<div class="close">
					<img src="<s:text name="sundyn.pic.ok" />" id="ok123"  onclick="deptEditItem()"  style="cursor: pointer;"/>
				</div>
				
				<div class="nofind">
					<img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"    style="cursor: pointer;" />
				</div>
			</div>
		</div>  
	</body>
</html>