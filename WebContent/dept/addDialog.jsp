<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="dialog">
			<div class="title">
				<div class="text">
					<c:if test="${deptType==2}"><s:text name="sundyn.dept.addDept2" /></c:if>
					<c:if test="${deptType==1}"><s:text name="sundyn.dept.addDating" /></c:if>
					<c:if test="${deptType==0}"><s:text name="sundyn.dept.addWindow" /></c:if>
				</div>
				<div class="close">
					<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()"/>
				</div>
			</div>
			<div class="content">
				<div class="content_main">
				  <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color:#FFFFFF;">
					  <tr>
					    <td width="32%" align="right" style="border-color:#FFFFFF;">
						    <c:if test="${deptType==2}"><s:text name="sundyn.column.deptName" /><s:text name="sundyn.colon" /> </c:if>
							<c:if test="${deptType==1}"><s:text name="sundyn.column.datingName" /><s:text name="sundyn.colon" /> </c:if>
							<c:if test="${deptType==0}"><s:text name="sundyn.column.windowName" /><s:text name="sundyn.colon" /> </c:if>
 					    </td>
					    <td width="50%" align="left" style="border-color:#FFFFFF;">
					        <input type="hidden" name="deptType" id="deptType" value="${deptType}" />
							<input type="text" name="deptName" id="deptName" class="input_comm" />
							<c:if test="${deptType==2}"><s:text name="sundyn.dept.xxDept" /></c:if>
							<c:if test="${deptType==1}"><s:text name="sundyn.dept.xxDating" /></c:if>
							<c:if test="${deptType==0}"><s:text name="sundyn.dept.xxWindow" /></c:if>
					    </td>
					    </tr>
<%--					  <tr <c:if test="${deptType==2 || deptType==1 }"> style="display: none;"</c:if> >--%>
<%--					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.clientConnectType" /></td>--%>
<%--					    <td align="left" style="border-color:#FFFFFF;"> --%>
<%--					       <select name="client_type" id="client_type"  onchange="clientTypeChange(this.value)">--%>
<%--								<option value="1">--%>
<%--									<s:text name="sundyn.guide.comServer" />--%>
<%--								</option>--%>
<%--								<option selected="selected" value="2">--%>
<%--									<s:text name="sundyn.guide.clientProgram" />--%>
<%--								</option>--%>
<%--							</select>--%>
<%--							<select name="product_type" id="product_type" onchange="productTypeChange(this.value)">--%>
<%--								<option value="-1"   >--%>
<%--									U<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="-2"  >--%>
<%--									D<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="-3" >--%>
<%--									T01U<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="-4"  >--%>
<%--									M7<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="1"  >--%>
<%--									S<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="2" >--%>
<%--									W<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%--								<option value="3"  >--%>
<%--									T01<s:text name="sundyn.dept.type" />--%>
<%--								</option>--%>
<%-- 							</select>--%>
<%--					    </td>--%>
<%--					  </tr>--%>
					  <tr <c:if test="${deptType==2 || deptType==1 }"> style="display: none;" </c:if>>
					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.deviceInfo" /></td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					    	<input type="text" name="reMark" id="reMark" value="${remark}" onblur="macCheck(this.value)" />
							<span style="font-size: 9px; color: red;" id="tip">(<s:text name="sundyn.dept.demoOrMac" />)</span>
					    </td>
					  </tr>
					   <c:if test="${deptType==0}">
						  <c:if test="${bind == 'true'}">
							  <tr>
							    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.guide.employeeCardNum" /></td>
							    <td align="left" style="border-color:#FFFFFF;">
						 	    	<input type="text" name="dept_camera_url" id="dept_camera_url"    onclick="this.select()" onkeyup="employeeFindByCardnumOrName()" class="input_comm" />
							    	<div class="findreslut" id="findreslut" onkeydown="employeeresultdown()">
								    </div>
							    </td>
							  </tr>
					  </c:if>
					</c:if>
					<c:if test="${deptType==0 && '0'=='1' }">
 						  <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.businessType" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <select id="dept_businessId">
						    	    <c:forEach var="business" items="${list}">
						    	      <option value="${business.businessId}">${business.businessName}</option>
						    	    </c:forEach>
						    	 </select>
  						    </td>
						  </tr>
 					  </c:if>
 					<c:if test="${deptType==0}">
 						  <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.playList" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <select id="playListId">
						    	    <c:forEach var="playList" items="${playListList}">
						    	      <option value="${playList.playListId}">${playList.playListName}</option>
						    	    </c:forEach>
						    	 </select>
  						    </td>
						  </tr>
 					  </c:if>
 					   <c:if test="${deptType==0  && '0' == '1'}"> 
 						  <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.pauseInfo" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <input type="text" name="deptPause" id="deptPause" class="input_comm" />
  						    </td>
						  </tr>
						   <tr>
						    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.dept.pausePic" /></td>
						    <td align="left" style="border-color:#FFFFFF;">
						    	 <input type="text" name="deptPausePic" id="deptPausePic" class="input_comm" />
						    	
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
						    	 <input type="text" name="deptLogoPic" id="deptLogoPic" class="input_comm" />
						    	
						    	<form id="pic2" enctype="multipart/form-data" name="pic2"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
									<input type="hidden" name="imgName2" id="imgName2" />
									<input type="file" name="img2" id="img2" onblur="getFileName()" style="width: 140px;" /> 
									<img src="<s:text name='sundyn.pic.upload'/>" width="60" height="25" onclick="DeptLogoUpload()" class="hand" />
								</form>
  						    </td>
						  </tr>
						 
 					  </c:if>
 					  <c:if test="${deptType==0}">
 					   <tr >
					    <td align="right" style="border-color:#FFFFFF;"><s:text name="sundyn.system.unit.AudioAndVideo"></s:text></td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					       <select name="useVideo" id="useVideo" >
								<option value="0" <c:if test="${dept.useVideo=='0'}"> selected="selected" </c:if>>
									<s:text name="sundyn.system.unit.bothNot"></s:text>
								</option>
								<option value="1" <c:if test="${dept.useVideo=='1'}"> selected="selected" </c:if>>
									<s:text name="sundyn.system.unit.useAudio"></s:text>
								</option>
								<option value="2" <c:if test="${dept.useVideo=='2'}"> selected="selected" </c:if>>
									<s:text name="sundyn.system.unit.useVideo"></s:text>
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
					    <td align="right"  style="border-color:#FFFFFF;"><s:text name="sundyn.system.screenInfo"></s:text></td>
					    <td align="left"  style="border-color:#FFFFFF;"> 
<%--					    	<input type="text" name="notice" id="notice"  value="${dept.notice}"  />--%>
					    	<textarea name="notice" id="notice" rows="5"  cols="30">${dept.notice}</textarea>
					    </td>
					  </tr>
					  </c:if>
 				 </table>
			   </div>
			</div>
			<div class="bottom">
				<div class="close">
					<img src="<s:text name="sundyn.pic.ok" />" onclick="addChildItem()"  style="cursor: pointer;"/>
				</div>
				<div class="nofind">
					<img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
				</div>
			</div>
		</div>  