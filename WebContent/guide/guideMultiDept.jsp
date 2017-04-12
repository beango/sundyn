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
 		<div id="man_zone">
 		  <div class="fengge" style="height:15px;">&nbsp;</div>
 		  <img src="images/guide_top3.gif" id="topimg" />
 		  <div class="guide_start2"></div>
 		  <div class="fengge" style="height:10px;">&nbsp;</div>
 		  <div class="guide_simpleThree">
 		    <div class="left">
 		        <div class="top"><div>${m.name}</div></div>
 		        <div class="guide_simpleThree_l_m"  >
  		           <div  class="guide_simpleThree_l_m_l">
  		             	 <div class="guide_simpleThree_l_m_l_1">
  		             	 	<c:if test="${deptType==2}">
  		             	 		 <s:text name='sundyn.guide.deptList'/>
  		             	 	</c:if>
  		             	 	<c:if test="${deptType==1}">
  		             	 		 <s:text name='sundyn.guide.datingList'/>
  		             	 	</c:if>
  		             	 	<c:if test="${deptType==0}">
  		             	 		 <s:text name='sundyn.guide.windowList'/>
  		             	 	</c:if>
  		             	 </div>
  		             	 <div class="guide_simpleThree_l_m_l_2" id="deptList">
  		             	   <ul>
  		             	     <c:forEach items="${ls}" var="dept">
  		             	     	<li><span>${dept.name }</span><img src="images/small_button_edit.gif" onclick="guideEdit(${dept.id})" /> &nbsp;<img src="images/small_button_del.gif" onclick="guideDel(${dept.id})" /></li>
  		             	     </c:forEach>
  		             	   </ul>
  		             	 </div>
  		           </div>
  		           <div class="guide_simpleThree_l_m_r">
  		           				<input id="fatherId" type="hidden" name="fatherId" value="${id}"/>
  		           				<input id="deptType" type="hidden" name="deptType" value="${deptType}"/>
  		           				<input id="id" type="hidden" name="id" />
  		           				<c:if test="${deptType==2}">
	  		             	 		<s:text name='sundyn.column.deptName'/><s:text name='sundyn.colon'/>
	  		             	 	</c:if>
	  		             	 	<c:if test="${deptType==1}">
	  		             	 		<s:text name='sundyn.column.datingName'/><s:text name='sundyn.colon'/>
	  		             	 	</c:if>
	  		             	 	<c:if test="${deptType==0}">
	  		             	 		<s:text name='sundyn.column.windowName'/><s:text name='sundyn.colon'/>	
	  		             	 	</c:if>
	  		              <input type="text" class="input_comm" id="name"/><br/>
	  		              <c:if test="${deptType == 1 || deptType == 0}">
 	  		                 <s:text name='sundyn.guide.deviceInfo'/><input type="text" class="input_comm" id="remark" onblur="deptExistMac(this.value)"/><br/>
 	  		              </c:if>
 	  		              <c:if test="${deptType == 0}">
 	  		              <span id="tip" style="font-size: 9px; color: red;"></span>
	  		                 <s:text name='sundyn.guide.connectType'/><select   id="client_type" name="client_type">
									<option value="1">
										<s:text name='sundyn.guide.comServer'/>
									</option>
									<option selected="selected" value="2">
										<s:text name='sundyn.guide.clientProgram'/>
									</option>
								</select>
						 </c:if><br/><br/>
	  		              <img src="<s:text name='sundyn.guide.pic.save'/>" onclick="guideMultiDeptAdd()"/> <img src="<s:text name='sundyn.guide.pic.reset'/>" onclick="guideReset()" />
  		           </div>
 		        </div>
 		    </div>
 		    <div class="right">
 		    				<c:if test="${deptType==2}">
  		             	 		 <s:text name='sundyn.column.deptName'/>
  		             	 	</c:if>
  		             	 	<c:if test="${deptType==1}">
  		             	 		 <s:text name='sundyn.column.datingName'/>
  		             	 	</c:if>
  		             	 	<c:if test="${deptType==0}">
  		             	 		 <s:text name='sundyn.column.windowName'/>
  		             	 	</c:if>
  		             	 	<s:text name='sundyn.colon'/><s:text name='sundyn.guide.m.deptInfo1'/>
  		             	 	<c:if test="${deptType == 1}">
  		             	 			<s:text name='sundyn.guide.m.deptInfo2'/>
  		             	 	</c:if>
 		    	 			<c:if test="${deptType == 0}">
  		             	 			<s:text name='sundyn.guide.m.deptInfo3'/>
  		             	 	</c:if>
 		    	<c:if test="${deptType==2}">
           	 		<s:text name='sundyn.column.deptName'/>
           	 	</c:if>
           	 	<c:if test="${deptType==1}">
           	 		<s:text name='sundyn.column.datingName'/>
           	 	</c:if>
           	 	<c:if test="${deptType==0}">
           	 		 <s:text name='sundyn.column.windowName'/>
           	 	</c:if><s:text name='sundyn.colon'/><s:text name='sundyn.guide.m.deptInfo4'/>
  		    </div>
 		  </div>
 		  <div class="fengge" style="height:20px;" ></div>
   		  <div class="guide_bottom">
 		     <img src="<s:text name='sundyn.guide.pic.previous' />"  onclick="guideBack()"/>
 		     <img src="<s:text name='sundyn.guide.pic.next' />" onclick="guideMultiSelect()" />
 		     <img src="<s:text name='sundyn.guide.pic.leave' />" onclick="guideComplete()"/>
 		  </div>
  	   </div>
 	   <div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif"/></div>
 	   <script  language="javascript">
  	     topImg();
 	   </script>
	</body>
</html>