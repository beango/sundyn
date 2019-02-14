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
 		  <img src="<s:text name='sundyn.guide.pic.top4'/>" />
 		  <div class="guide_start2"></div>
 		  <div class="fengge" style="height:10px;">&nbsp;</div>
 		  <div class="guide_simpleFour">
	 		    <div  id="employeeList">
		 		    <table  cellspacing="0" cellpadding="0"  >
		 		       <tr>
		 		         <td class="guidetitle"><s:text name='sundyn.column.name'/></td>
		 		         <td class="guidetitle"><s:text name='sundyn.column.sex'/></td>
		 		         <td class="guidetitle"><s:text name='sundyn.column.employeeCardNum'/></td>
		 		         <td class="guidetitle"><s:text name='sundyn.column.operation'/></td>
		 		       </tr>
		 		       <c:forEach items="${ls}" var="employee">
		 		       <tr>
		 		         <td class="text">${employee.name}</td>
		 		         <td class="text">
		 		           <c:if test="${employee.Sex==1}">
							   <s:text name='sundyn.male'/>
						   </c:if>
						   <c:if test="${employee.Sex!=1}">
							   <s:text name='sundyn.female'/>
						   </c:if>
		 		         </td>
		 		         <td class="text">${employee.cardnum}</td>
		 		         <td class="text"><a href="javascript:guideSimpleEmployeeEdit(${employee.Id})"><s:text name='sundyn.modify'/></a>&nbsp;<a href="javascript:guideSimpleEmployeeDel(${employee.Id})"><s:text name='sundyn.del'/></a> </td>
		 		       </tr>
		  		       </c:forEach>
		  		    </table>
	  		    </div>
  		    <div class="four22">
  		       
  		    </div>
  		    <div class="four32">
  		       <div class="center_10_left kuang" style="margin:9px 0px 0px 12px; ">
					<img border="0" style="width: 140px; height: 147px;" id="img123" src="images/employee_head_photo.gif" />
					<form style="margin: 0px; padding: 0px; background-color: rgb(233, 245, 253);" method="post" action="employeeAdd.action" name="pic" enctype="multipart/form-data" id="pic">
						<input type="hidden" id="imgName" name="imgName" value="images/employee_head_photo.gif" />
						<input type="file" style="width:140px;" onblur="getFileName()" id="img" name="img" /> 
						<img width="60" height="25" class="hand" onclick="employeeUpload()" src="<s:text name='sundyn.pic.upload'/>" />
					</form>
				</div>
 				<div class="four32r">
 					<input type="hidden" name="deptId" id="deptId" value="${deptId}"/>
 					<input type="hidden" name="id" id="id"/>
 					<div><s:text name="sundyn.column.userName" /><s:text name="sundyn.colon" /><input type="text" name="ext2" id="ext2" class="input_guide"/> </div>
 					<div class="fengge" style="height: 5px;"></div>
 					<div><s:text name="sundyn.column.employeeName" /><s:text name="sundyn.colon" /><input type="text" name="name" id="name" class="input_guide"/> </div>
 					<div class="fengge" style="height: 5px;"></div>
 					<div><s:text name="sundyn.column.post" /><s:text name="sundyn.colon" /> <input type="text" name="job_desc" id="job_desc" class="input_guide"/> </div>
 					 <div class="fengge" style="height:5px;"></div>
 					<div> <s:text name='sundyn.guide.employeeCardNum'/><input type="text" name="cardnum" id="cardnum" class="input_guide" onkeyup="keyIsNumber(this)" /></div>
 					<div class="fengge" style="height: 10px;"></div>
 					<div><s:text name='sundyn.guide.employeeSex'/><input type="radio" name="sex" id="sex1" checked="checked" value="1"/><s:text name='sundyn.male'/><input type="radio" name="sex" id="sex2" value="0"/><s:text name='sundyn.female'/></div>
 					<div class="fengge" style="height: 21px;"></div>
 					<div> <img src="<s:text name='sundyn.guide.pic.save'/>" onclick="guideSimpleEmployeeSaveOrUpdate()" /> &nbsp;<img src="<s:text name='sundyn.guide.pic.reset'/>"  onclick="guideSimpleEmployeeReset()" /></div>
				</div>
  		    </div>
 		  </div>
 		  <div class="fengge" style="height:20px;" ></div>
   		  <div class="guide_bottom" style="margin-bottom: 0px;">
 		     <img src="<s:text name='sundyn.guide.pic.previous' />"   onclick="guideBack()"/>
 		     <img src="<s:text name='sundyn.guide.pic.complete' />"  onclick="guideComplete()" />
 		     <img src="<s:text name='sundyn.guide.pic.height' />"  onclick="javascript:window.location.href='employeeView.action';" />
 		  </div>
  	   </div>
 	</body>
</html>
