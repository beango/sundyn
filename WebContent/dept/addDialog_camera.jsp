<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="dialog">
			<div class="title">
				<div class="text">
					<c:if test="${deptType==2}">添加机构</c:if>
					<c:if test="${deptType==1}">添加大厅</c:if>
					<c:if test="${deptType==0}">添加窗口</c:if>
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
						    <c:if test="${deptType==2}">机构名称：</c:if>
							<c:if test="${deptType==1}">大厅名称：</c:if>
							<c:if test="${deptType==0}">窗口名称：</c:if>
 					    </td>
					    <td width="50%" align="left" style="border-color:#FFFFFF;">
					        <input type="hidden" name="deptType" id="deptType" value="${deptType}" />
							<input type="text" name="deptName" id="deptName" class="input_comm" />
							<c:if test="${deptType==2}">xx机构</c:if>
							<c:if test="${deptType==1}">xx大厅：</c:if>
							<c:if test="${deptType==0}">xx窗口：</c:if>
					    </td>
					    </tr>
					  <tr <c:if test="${deptType==2 || deptType==1 }"> style="display: none;"</c:if> >
					    <td align="right" style="border-color:#FFFFFF;">客户端连接类型：</td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					       <select name="client_type" id="client_type">
								<option value="1">
									串口服务器
								</option>
								<option selected="selected" value="2">
									客户端程序
								</option>
							</select>
					    </td>
					  </tr>
					  <tr <c:if test="${deptType==2   }"> style="display: none;" </c:if>>
					    <td align="right" style="border-color:#FFFFFF;">设备信息：</td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					    	<input type="text" name="reMark" id="reMark" />
							<span style="font-size: 9px; color: red;">(填写设备号或mac地址)</span>
					    </td>
					  </tr>
					   <tr <c:if test="${deptType==2 || deptType==1 }">style="display: none;"</c:if>>
					    <td align="right" style="border-color:#FFFFFF;">摄像头参数：</td>
					    <td align="left" style="border-color:#FFFFFF;"> 
					        <input type="text" name="dept_camera_url" id="dept_camera_url"/>
					        <span style="font-size: 9px; color: red;">(http://192.168.100?channel=100)</span>
					    </td>
					  </tr>
 				 </table>
			   </div>
			</div>
			<div class="bottom">
				<div class="close">
					<img src="<s:text name='sundyn.pic.ok' />" onclick="addChildItem()"  style="cursor: pointer;"/>
				</div>
				<div class="nofind">
					<img src="<s:text name='sundyn.pic.close' />"  onclick="closeDialog()"  style="cursor: pointer;" />
				</div>
			</div>
		</div>  