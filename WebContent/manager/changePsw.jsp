<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title><s:text name='sundyn.title'/></title>
        <link href="${ctx}/assets/stylesheets/application.css?14427995576" rel="stylesheet"/>
        <link href="${ctx}/assets/stylesheets/fontawesome.css?1440992355" rel="stylesheet"/>
        <!-- /### You don't need include in your project -->
        <link href="${ctx}/assets/stylesheets/highlighting.css?1442373184" rel="stylesheet"/>
        <link rel="stylesheet" href="${ctx}/assets/stylesheets/main.css?1514875963" media="screen">
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
    <body class='easyui-layout theme-default'>
    <div class='container' fit="true">
        <div class='col-12'>
            <div class='panel panel-primary' id="tablePanel" data-options="region:'center',noheader:true">
                <div class="col-12">
                    <form method="post" action="managerChangePswDeal2.action" id="f">
                        <table align="center" width="30%">

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
                            <c:if test="${not empty type}">
                                <tr>
                                    <td colspan="2">
                                        <div class='col-12'>
                                            <c:choose>
                                                <c:when test="${type=='input'}">
                                                    <c:set value="alert-warning" var="msgclass"></c:set>
                                                </c:when>
                                                <c:when test="${type=='error'}">
                                                    <c:set value="alert-danger" var="msgclass"></c:set>
                                                </c:when>
                                                <c:when test="${type=='success'}">
                                                    <c:set value="alert-success" var="msgclass"></c:set>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set value="alert-info" var="msgclass"></c:set>
                                                </c:otherwise>
                                            </c:choose>
                                            <div class='alert ${msgclass}'>
                                                <small>${msg}</small>
                                            </div>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </div>
 	</body>
    <script src="${ctx}/js/jquery-2.1.3.min.js"></script>
    <script src="${ctx}/js/easyui-1.5.3/jquery.easyui.min.js"></script>
</html>
