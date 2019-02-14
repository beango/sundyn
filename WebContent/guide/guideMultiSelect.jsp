<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css" />
		<title> <s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/ddtree.js"></script>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
		<script type="text/javascript" src="js/json.js"></script>
	</head>
	<body>
	<script>
	</script>
 		<div id="man_zone">
 		  <div class="fengge" style="height:15px;">&nbsp;</div>
 		  <img src="images/guide_top3.gif" id="topimg" />
 		  <div class="guide_start2"></div>
 		  <div class="fengge" style="height:10px;">&nbsp;</div>
 		  <div class="guide_simpleThree">
 		    <div class="left">
 		        <div class="top"><div><s:text name='sundyn.guide.m.please'/><select id="id" onchange="guideDeptTypeAjax(this.value)">
 		        							  <c:forEach items="${ls}" var="dept">
 		        								<option value="${dept.id}"> ${dept.name }</option>
 		        							  </c:forEach>
 		        							</select> <s:text name='sundyn.guide.m.add'/>
 		        							<input type="hidden" id="fatherId" name="fatherId" value="${fatherId}" />
 		        							<input type="hidden" id="deptType" name="deptTypeOld" value="${deptType}" />
 		        				 </div>
 		        </div>
 		        <div class="guide_simpleThree_l_m"  id="type" >
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
 		        </div>
 		    </div>
 		    <div class="right">
 		         <s:text name="sundyn.guide.m.selectInfo1"/>
 		    	 <c:if test="${deptType == 2}">
 		           	 <s:text name="sundyn.guide.m.selectInfo2"/>
	             </c:if>
	             <c:if test="${deptType == 2}">
	           		<s:text name="sundyn.guide.m.selectInfo3"/>
	             </c:if>
		           <c:if test="${deptType == 1}">
	           		<s:text name="sundyn.guide.m.selectInfo4"/>
	             </c:if>
	             <c:if test="${deptType == 0}">
	           		<s:text name="sundyn.guide.m.selectInfo5"/>	
	             </c:if>
 		    </div>
 		  </div>
 		  <div class="fengge" style="height:20px;" ></div>
   		  <div class="guide_bottom">
 		     <img src="<s:text name='sundyn.guide.pic.previous' />"  onclick="guideBack()"/>
 		     <img src="<s:text name='sundyn.guide.pic.next' />" onclick="guideMultiDept()"/>
 		     <img src="<s:text name='sundyn.guide.pic.leave' />" onclick="guideComplete()"/>
 		  </div>
  	   </div>
 	   <div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
 	    <script  language="javascript">
  	     topImg();  
 	   </script>
	</body>
</html>
