<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="now" class="java.util.Date"/>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
		<title><s:text name='zx.title'/>登录信息查询</title>
        <link rel="stylesheet" href="css/style.css" media="all">
        <link rel="stylesheet" href="lib/layui/css/layui.css" media="all">
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
    <div class="place">
        <span>位置：</span>
        <ul class="placeul">
            <li><a href="#">登录信息</a></li>
        </ul>
    </div>
    <div class="layui-main">
        <form method="post" class="layui-form" action="managerChangePswDeal2.action" id="f">
            <table align="center" width="100%" borde="0" cellpadding="0" cellspacing="0" style="border:0px;">
                <tr>
                    <td width="120" align="right">
                        当前登录时间:
                    </td>
                    <td align="left">
                        ${curlogintime}
                    </td>
                    <td align="right">
                        上次登录时间:
                    </td>

                    <td align="left">
                        ${data2.ctime}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        当前登录IP:
                    </td>
                    <td align="left">
                        ${curloginadd}
                    </td>
                    <td align="right">
                        上次登录IP:
                    </td>
                    <td align="left">
                        ${data2.ipadd}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        用户有效期:
                    </td>
                    <td align="left">
                        ${uexpired} (距离到期天数：<fmt:formatNumber type="number" value="${(uexpired.time-now.time)/1000/60/60/24}" pattern="#0" />)
                    </td>
                    <td align="right">
                        密码有效期:
                    </td>
                    <td align="left">
                        ${pwdexpired} (距离到期天数：<fmt:formatNumber type="number" value="${(pwdexpired.time-now.time)/1000/60/60/24}" pattern="#0" />)
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        当前在线用户数:
                    </td>
                    <td align="left">
                        ${activeSessions==null?0:activeSessions.size()}
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        近期登录失败记录:
                    </td>
                    <td align="left" colspan="3">
                        <c:forEach items="${data}" var="item">
                        <li>
                                ${item.ctime}
                                ${item.logrstdesc}
                        </li>
                        </c:forEach>
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
 	</body>
</html>
