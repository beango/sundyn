<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<c:if test="${deptType == 2}">
 		<div class="guideFont" style="margin-top:50px;"><input type="radio" name="deptType"   id="deptType2"  value="2"/><s:text name='sundyn.column.dept'/></div>	
 </c:if>
 <c:if test="${deptType == 2}">
 		<div class="guideFont" style="margin-top:10px;"><input type="radio" name="deptType" checked="checked"  id="deptType1" value="1" /><s:text name='sundyn.column.dating'/></div>	
 </c:if>
  <c:if test="${deptType == 1}">
 		<div class="guideFont" style="margin-top:10px;"><input type="radio" name="deptType" checked="checked"  id="deptType0" value="0"/><s:text name='sundyn.column.window'/></div>	
 </c:if>
 <c:if test="${deptType == 0}">
 		<div class="guideFont" style="margin-top:10px;"><input type="radio" name="deptType" checked="checked"  id="deptTypea" value="-1"/><s:text name='sundyn.column.people'/></div>	
 </c:if>