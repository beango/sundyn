<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
	</head>
	<body>
		<div id="man_zone">
			<div class="sundyn_main">
				<form method="post" action="managerChangePswDeal.action">
					<table align="center" width="80%">
						<tr>
							<td width="50%">
								输入旧密码：
							</td>
							<td width="50%">
								<input type="password" name="oldPsw" />
							</td>
						</tr>
						<tr>
							<td>
								输入新密码：
							</td>

							<td>
								<input type="password" name="newPsw" />
							</td>
						</tr>
						<tr>
							<td>
								确认新密码：
							</td>
							<td>
								<input type="password" name="newPsw2" />
							</td>
						</tr>
						<tr>
							<td>
								<input type="submit" value="确认修改" onclick="return check()" />
							</td>
							<td>
								<input type="reset" value="重填" />
							</td>
						</tr>
								${msg}
					</table>
				</form>
			</div>
		</div>
	</body>
</html>
