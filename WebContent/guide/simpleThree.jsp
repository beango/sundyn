<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/dtree.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
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
 		  <img src="<s:text name='sundyn.guide.pic.top3' />" />
 		  <div class="guide_start2"></div>
 		  <div class="fengge" style="height:10px;">&nbsp;</div>
 		  <div class="guide_simpleThree">
 		    <div class="left">
 		        <div class="top"><div>${m.name}</div></div>
 		        <div class="guide_simpleThree_l_m"  >
  		           <div  class="guide_simpleThree_l_m_l">
  		             	 <div class="guide_simpleThree_l_m_l_1"><s:text name='sundyn.guide.windowList'/></div>
  		             	 <div class="guide_simpleThree_l_m_l_2" id="deptList">
  		             	   <ul>
  		             	     <c:forEach items="${ls}" var="dept">
  		             	     	<li><span>${dept.name }</span><img src="images/small_button_edit.gif" onclick="guideEdit(${dept.id})" /> &nbsp;<img src="images/small_button_del.gif" onclick="guideDel(${dept.id})" /></li>
  		             	     </c:forEach>
  		             	   </ul>
  		             	 </div>
  		           </div>
  		           <div class="guide_simpleThree_l_m_r">
  		           				<input id="fatherId" type="hidden" name="fatherId" value="${fatherId}" />
  		           				<input type="hidden" id="deptType" name="deptType" value="0" />
  		           				<input id="id" type="hidden" name="id" />
	  		          			<s:text name='sundyn.guide.windowName'/><input type="text" class="input_comm" id="name"/><br/>
 	  		              		<s:text name='sundyn.guide.deviceInfo'/><input type="text" class="input_comm" id="remark" onblur="deptExistMac(this.value)"/><br/>
 	  		              		<span id="tip" style="font-size: 9px; color: red;"></span>
	  		             		<s:text name='sundyn.guide.connectType'/><select   id="client_type" name="client_type">
									<option value="1">
										<s:text name='sundyn.guide.comServer'/>
									</option>
									<option selected="selected" value="2">
										<s:text name='sundyn.guide.clientProgram'/>
									</option>
								</select><br/><br/>
	  		              <img src="<s:text name='sundyn.guide.pic.save'/>" onclick="quideSaveOrUpdate()"/> <img src="<s:text name='sundyn.guide.pic.reset'/>" onclick="guideReset()" />
  		           </div>
 		        </div>
 		    </div>
 		    <div class="right">
 		    	 <s:text name='sundyn.guide.info4'/>
 		    </div>
 		  </div>
 		  <div class="fengge" style="height:20px;" ></div>
   		  <div class="guide_bottom">
 		     <img src="<s:text name='sundyn.guide.pic.previous'/>"  onclick="guideBack()"/>
 		     <img src="<s:text name='sundyn.guide.pic.next'/>" onclick="guideSimpleFour()" />
 		     <img src="<s:text name='sundyn.guide.pic.leave'/>" onclick="guideComplete()"/>
 		  </div>
  	   </div>
 	</body>
</html>
