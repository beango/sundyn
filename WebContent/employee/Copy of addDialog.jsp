<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name="sundyn.employee.addEmployee" />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			<div class="center_10_left kuang">
				<img src="images/employee_head_photo.gif" id="img123"  style="width: 140px;height: 147px;"  border="0" />
				<form id="pic" enctype="multipart/form-data" name="pic"	action="employeeAdd.action" method="post" style="margin: 0px;padding: 0px;background-color:#e9f5fd;">
					<input type="hidden" name="imgName" id="imgName" />
					<input type="file" name="img" id="img" onblur="getFileName()" style="width: 140px;" /> 
					<img src="<s:text name='sundyn.pic.upload' />" width="60" height="25" onclick="employeeUpload()" class="hand" />
				</form>
			</div>
			<div class="center_10_right">
				<table width="100%" border="0" cellpadding="0"
					cellspacing="0" style="border-color: #e9f5fd;">
					<tr>
						<td style="border-color: #e9f5fd;" width="30%" align="right" class="ff">
							<s:text name="sundyn.column.userName" /><s:text name="sundyn.colon" />						</td>
						<td style="border-color: #e9f5fd;" width="70%" align="left">
						  <input type="text" name="ext2" id="ext2" class="input_comm" style="width:100px;" onblur="employeeExsits()" /><span style="color: red; font-size: 12px;" id="tip"></span>						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" width="30%" align="right" class="ff">
							<s:text name="sundyn.column.employeeName" /><s:text name="sundyn.colon" />						</td>
						<td style="border-color: #e9f5fd;" width="70%" align="left">
							<input type="text" name="Name" id="Name" class="input_comm" />						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right" class="ff">
							<s:text name="sundyn.column.post" /><s:text name="sundyn.colon" />						</td>
						<td style="border-color: #e9f5fd;" align="left">
							<input type="text" name=job_desc id="job_desc" class="input_comm" />						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right" class="ff">
							<s:text name="sundyn.column.sex" /><s:text name="sundyn.colon" />						</td>
						<td style="border-color: #e9f5fd;" align="left">
							<input type="radio" name="Sex" id="Sex" value="1"
								checked="checked" />
							<s:text name="sundyn.male" />
							<input type="radio" name="Sex" id="Sex" value="0" />
							<s:text name="sundyn.female" />						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right" class="ff">
							<s:text name="sundyn.column.employeeCardNum" /><s:text name="sundyn.colon" />						</td>
						<td style="border-color: #e9f5fd;" align="left">
							<input type="text" name="CardNum" id="CardNum"  class="input_comm"  /><font color="#FF0000">*</font>						</td>
					</tr>
					<tr style="display:none;">
						<td style="border-color: #e9f5fd;" align="right" class="ff">
						  <s:text name="sundyn.column.post" /><s:text name="sundyn.colon" />						</td>
					  <td style="border-color: #e9f5fd;" align="left">
					  <input type="text" name="showDeptName" id="showDeptName"  class="input_comm" />						</td>
					</tr>
					<tr style="display:none;">
						<td style="border-color: #e9f5fd;" align="right" class="ff">
						  <s:text name="sundyn.employee.offices" />						</td>
						<td style="border-color: #e9f5fd;" align="left">
							<input type="text" name="showWindow" id="showWindow"  class="input_comm"  />						</td>
					</tr>
					<tr>
						<td style="border-color: #e9f5fd;" align="right" class="ff">
						    <c:if test="${star == 'true'}">
						    	<s:text name="sundyn.employee.contact" />
						    </c:if>
						    <c:if test="${star != 'true'}">
						      	 <s:text name="sundyn.column.star" /><s:text name="sundyn.colon" /> 
						    </c:if>						</td>
						<td style="border-color: #e9f5fd;" align="left">
							<input type="text" name="Phone" id="Phone"  class="input_comm" />						</td>
					</tr>
					<%--<tr>
						<td style="border-color: #e9f5fd;" align="right" class="ff">
							备注：
						</td>
						<td style="border-color: #e9f5fd;" align="left">
							 <textarea rows="2" cols="22" id="remark"></textarea>
						</td>
					</tr>
 				--%></table>
				<div style="color:red; text-align:left;"><s:text name="sundyn.employee.alertCardNum"></s:text> </div>
				
 			</div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<img src="<s:text name='sundyn.pic.ok' />" onclick="employeeAdd()"
				 class="hand" />
		</div>
		<div class="nofind">
			<img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()"
				 class="hand" />
		</div>
	</div>
</div>
