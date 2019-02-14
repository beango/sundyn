<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.query.satffQuery' />
		</div>
		<div class="close">
			<img src="images/dialog_close.gif" border="0" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
		  <div style="width: 100%">
			   <div style="width:20%;float:left;"><s:text name='sundyn.query.selectDept'/></div>
			   <div id="dept" style="text-align: left;width:80%;float:left;">
					<select name="deptId">
						<c:forEach items="${temp}" var="dept" varStatus="index">
							<option value="${dept.id}"  
							<c:if test="${index.index ==0 }">
							    selected="selected"   
							</c:if> 
							     >
								${dept.name}
							</option>
						</c:forEach>
					</select>
					<img src="images/arrow.gif" style="cursor: pointer;" onclick="deptopen()" />
				</div>
			</div>  
			<ul id="employeeList">
				<c:forEach items="${deptList}" var="people">
					<a href="#" onclick="selectPeople('${people.Id}','${people.Name}')" title="${people.deptName}->${people.Name}(${people.CardNum})"><li> ${people.Name}</li></a>
				</c:forEach>
			</ul>
		</div>
	</div>
	<div class="bottom">
			<a href="javascript:queryEmployee2()"><img
					src="<s:text name='sundyn.query.pic.query' />" /> </a>
			<a href="javascript:closeDialog()"><img
					src="<s:text name='sundyn.pic.close' />" /> </a>
	</div>
</div>