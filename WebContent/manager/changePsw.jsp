<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title><s:text name='sundyn.title'/></title>
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css"></link>
		<script language="javascript">
		function check(){
			if(document.getElementById("oldPsw").value==""){
					alert("<s:text name='sundyn.changePassword.pleaseOldPassword' />");
					return false;
			}
			if(document.getElementById("newPsw").value==""){
					alert("<s:text name='sundyn.changePassword.pleaseNewPassword1' />");
					return false;
			}
			if(document.getElementById("newPsw2").value==""){
					alert("<s:text name='sundyn.changePassword.pleaseNewPassword2' />");
					return false;
			}
			f.submit();
		}
		</script>
 	</head>
	<body>
		<div id="man_zone">
		<div class="fengge">&nbsp;</div>
			 <form method="post" action="managerChangePswDeal2.action" id="f">
				<table align="center" width="30%" style="border-top: 1px solid #8ec3e2;border-right: 1px solid #8ec3e2;">
					<tr>
						<td width="120" align="right">
						   <s:text name="sundyn.changePassword.oldPassword" />
 						</td>
						<td align="left">
							<input type="password" name="oldPsw" id="oldPsw" class="input_comm" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<s:text name="sundyn.changePassword.newPassword1" />
 						</td>

						<td align="left">
							<input type="password" name="newPsw" id="newPsw" class="input_comm" />
						</td>
					</tr>
					<tr>
						<td align="right">
							<s:text name="sundyn.changePassword.newPassword2" />
						</td>
						<td align="left">
							<input type="password" name="newPsw2" id="newPsw2" class="input_comm" />
						</td>
					</tr>
					<tr>
						<td align="right">
						    <div class="button" onclick="return check()"><s:text name="sundyn.changePassword.submit" /></div>
						</td>
						<td align="left">
						    <div class="button" onclick="f.reset()"><s:text name="sundyn.changePassword.reset" /></div>
						</td>
					</tr>
					
				</table>
			</form>
			<div class="msg">${msg}</div>
		</div>
 	</body>
</html>
 