<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
	</head>
	<body>
	<div style="width:840px;height:300px" >
	<div  style="float:left;width: 420" >
		<h2>调用系统方法appriesAddSp插入</h2>
		<form  method="post" action="appriesAddSp">
			<table style="border-color: #FFFFFF;">
<%--				<tr>--%>
<%--					<td>输入服务器ip</td>--%>
<%--					<td><input type="text" id="" name="" /></td>--%>
<%--				</tr>--%>
				<tr>
					<td>评价时间</td>
					<td><input type="text" value="${date}" name="tt" /></td>
				</tr>
				<tr>
					<td>选择Mac地址</td>
					<td><select name="mac" id="mac">
							<c:forEach items="${allMac}" var="dept" varStatus="index">
								<option value="${dept.remark}">${dept.name},mac=${dept.remark}
								</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td>选择员工卡号</td>
					<td><select name="cardnum" id="cardnum">
							<c:forEach items="${employeeList}" var="employee" varStatus="index">
								<option value="${employee.cardNum}">姓名=${employee.name},卡号=${employee.cardNum}
								</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td>选择评价器按键</td>
					<td>
					<select id="pj" name="pj">
						<c:forEach items="${keyList}" var="key" varStatus="index">
							<option value="${key.keyNo}">
								${key.name}
							</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			<tr>
				<td>业务办理时间</td>
				<td><input type="text" id="tt" name="tt" value="${bussinessTime}" /></td>
			</tr>
			<tr>
				<td>输入手机号</td>
				<td><input type="text" id="tel" name="tel" value="${tel}"  /></td>
			</tr>
			<tr>
				<td>输入身份证号码</td>
				<td><input type="text" id="idCard" name="idCard" value="${idCard}"  /></td>
			</tr>
			<tr>
				<td><input type="submit" value="插入测试数据" /></td>
			</tr>
			</table>
		</form>
<%--			<input type="submit" value="查询评价数据插入方法" ><a href=""> </a></input>--%>
		</div>
	<div  style="float:left;width: 420" >
		<h2>调用系统方法appriesAddSp插入</h2>
		<form  method="post" action="appriesAddSp">
			<table style="border-color: #FFFFFF;">
<%--				<tr>--%>
<%--					<td>输入服务器ip</td>--%>
<%--					<td><input type="text" id="" name="" /></td>--%>
<%--				</tr>--%>
				<tr>
					<td>评价时间</td>
					<td><input type="text" value="${date}" name="tt" /></td>
				</tr>
				<tr>
					<td>输入Mac地址</td>
					<td>
					<c:forEach items="${allMac}" var="dept" varStatus="index">
						<c:if test="${index.index==1}">
							<input type="text "name="mac" id="mac"  value="${dept.remark}"/>
						</c:if>
					</c:forEach>
					</td>
				</tr>
				<tr>
					<td>选择员工卡号</td>
					<td>
						<c:forEach items="${employeeList}" var="employee" varStatus="index">
							<c:if test="${index.index==0}">
								<input type="text" name="cardnum" id="cardnum" value="${employee.cardNum}"/>
							</c:if>
						</c:forEach>
	  				</td>
				</tr>
				<tr>
					<td>选择评价器按键</td>
					<td>
						<c:forEach items="${keyList}" var="key" varStatus="index">
						<c:if test="${index.index==0}">
							<input type="text" name="pj" value="${key.keyNo}"/>
						</c:if>
						</c:forEach>
					</td>
				</tr>
			<tr>
				<td>业务办理时间</td>
				<td><input type="text" id="tt" name="tt" value="${bussinessTime}" /></td>
			</tr>
			<tr>
				<td>输入手机号</td>
				<td><input type="text" id="tel" name="tel" value="${tel}"  /></td>
			</tr>
			<tr>
				<td>输入身份证号码</td>
				<td><input type="text" id="idCard" name="idCard" value="${idCard}"  /></td>
			</tr>
			<tr>
				<td><input type="submit" value="插入测试数据" /></td>
			</tr>
			</table>
		</form>
<%--			<input type="submit" value="查询评价数据插入方法" ><a href=""> </a></input>--%>
		</div>
		</div>
		
		
		
		<div style="width:840px;height:300px">
			<div align="left"  >
		<h2>调用调试方法插入数据</h2>
				<form  method="post" action="appriesAddDebug">
			<table style="border-color: #FFFFFF;">
<%--				<tr>--%>
<%--					<td>输入服务器ip</td>--%>
<%--					<td><input type="text" id="" name="" /></td>--%>
<%--				</tr>--%>
				<tr>
					<td>评价时间</td>
					<td><input type="text" value="${date}" name="appriesDate" /></td>
				</tr>
				<tr>
					<td>选择Mac地址</td>
					<td><select name="mac" id="mac">
							<c:forEach items="${allMac}" var="dept" varStatus="index">
								<option value="${dept.remark}">${dept.name},mac=${dept.remark}
								</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td>选择员工卡号</td>
					<td><select name="cardNum" id="cardNum">
							<c:forEach items="${employeeList}" var="employee" varStatus="index">
								<option value="${employee.cardNum}">姓名=${employee.name},卡号=${employee.cardNum}
								</option>
							</c:forEach>
						</select></td>
				</tr>
				<tr>
					<td>选择评价器按键</td>
					<td>
					<select id="appriesButton" name="appriesButton">
						<c:forEach items="${keyList}" var="key" varStatus="index">
							<option value="${key.keyNo}">
								${key.name}
							</option>
						</c:forEach>
					</select>
					</td>
				</tr>
			<tr>
				<td>业务办理时间</td>
				<td><input type="text" id="bussinessTime" name="bussinessTime" value="${bussinessTime}" /></td>
			</tr>
			<tr>
				<td>输入手机号</td>
				<td><input type="text" id="tel" name="tel" value="${tel}"  /></td>
			</tr>
			<tr>
				<td>输入身份证号码</td>
				<td><input type="text" id="idCard" name="idCard" value="${idCard}"  /></td>
			</tr>
			<tr>
				<td><input type="submit" value="插入测试数据" /></td>
			</tr>
			</table>
		</form>
<%--			<input type="submit" value="查询评价数据插入方法" ><a href=""> </a></input>--%>
		</div>
		
		</div>
	</body>
  </html>
