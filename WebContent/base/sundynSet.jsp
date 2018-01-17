<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <script type="text/javascript" src="js/jquery-1.4.3.js"></script>
  		<script type="text/javascript" src="js/dojo.js"></script>
  		<script type="text/javascript" src="js/dialog.js"></script>
   	    <script type="text/javascript" src="js/json.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js?1"></script>
 		<title><s:text name='sundyn.title'/></title>
		<script language="javascript">
		  function check() {
              var patrn=/^[0-9][0-9]:[0-9][0-9]$/;
              var sam=document.getElementById("sam").value;
              var eam=document.getElementById("eam").value;
              var spm=document.getElementById("spm").value;
              var epm=document.getElementById("epm").value;
              if(!patrn.test(sam)){
                alert("<s:text name='sundyn.softSetup.timeerror1' />");
                 return false;
               }
             if(!patrn.test(eam)){
                alert("<s:text name='sundyn.softSetup.timeerror2' />");
                return false;
              }
              if(!patrn.test(spm)){
                alert("<s:text name='sundyn.softSetup.timeerror3' />");
                return false;
              }
              if(!patrn.test(epm)){
                alert("<s:text name='sundyn.softSetup.timeerror4' />");
                return false;
              }
              document.getElementById("f").submit();
           }
          function checkStar(){
				if(document.getElementById('star').checked){
						document.getElementById('est66').style.display='';
					}
				if(!document.getElementById('star').checked){
					document.getElementById('est66').style.display='none';
                    //document.getElementById('est6').click();
					document.getElementById('est6').checked='';
				}
              }
		</script>
	</head>
	<body>
		<div id="man_zone">
			<div style="width: 728px;margin: 0 auto;height: 380px;overflow: hidden;" class="kuang">
				<div class="top718">
				    <div>
					   <b id="top1" onclick="showN(1)"><s:text name='sundyn.softSetup.systemSetup' /></b>
	 				   <img src="images/fgx.gif" />
	 				   <b id="top2" onclick="showN(2)"><s:text name='sundyn.softSetup.infoSetup' /></b>
					   <img src="images/fgx.gif" />
					   <b id="top3" onclick="showN(3)"><s:text name='sundyn.softSetup.timeSetup' /></b>
<%--					   <img src="images/fgx.gif" /> --%>
<%--					   <b id="top4" onclick="showN(4)"><s:text name='sundyn.softSetup.tempSetup' /></b>--%>
	 				   <img src="images/fgx.gif" />
					   <b id="top5" onclick="showN(5)"><s:text name='sundyn.softSetup.starSetup' /></b>
					    <img src="images/fgx.gif" />
	 				   <b id="top6" onclick="showN(6)"><s:text name='sundyn.softSetup.employeeInfoSetup' /></b>
				   </div>
 				</div>
				<div class="sundynSet" >
					  <div id="content1">
					      <div style="display:none"><input type="checkbox" name="camera" id="camera" <c:if test="${system.camera == 'true' }"> checked="checked"</c:if>      />&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.camera' /></b>&nbsp;&nbsp;&nbsp;&nbsp; <c> <s:text name='sundyn.softSetup.cameraInfo' /></c></div>
						  <div style="display:none"><input type="checkbox" name="k7" id="k7"     <c:if test="${system.k7 == 'true' }"> checked="checked"</c:if>  />&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.k7' /></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text name='sundyn.softSetup.k7Info' /></c></div>
						  <div><input type="checkbox" name="star" onclick="checkStar()" id="star" <c:if test="${system.star == 'true' }"> checked="checked"</c:if>  />&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.starGrade' /></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text name='sundyn.softSetup.starGradeInfo' /></c></div>
						  <div><input type="checkbox" name="bind" id="bind" <c:if test="${system.bind == 'true' }"> checked="checked"</c:if>  />&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.bind' /></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text name='sundyn.softSetup.bindInfo' /></c></div>
						  <div><input type="checkbox" name="guide" id="guide" <c:if test="${system.guide == 'true' }"> checked="checked"</c:if>  />&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.guide' /></b>&nbsp;&nbsp;&nbsp;&nbsp;<c><s:text name='sundyn.softSetup.guideInfo' /></c></div>
						  <input type="hidden" name="tipLanguage" id="tipLanguage" value="${system.tipLanguage}"/>
 					  </div>
					  <div id="content2" style="display: none;">
					      <div><b><s:text name='sundyn.softSetup.danweiName'/></b><input type="text" name="title" id="title" class="input_comm" value="${content.title}" />  </div>
					      <div style="display:none;"><b><s:text name='sundyn.softSetup.m7BottomInfo'/></b><input type="text" name="buttom" id="buttom" class="input_comm" value="${content.buttom}" />  </div>
					      <div style="display: none;"><b><s:text name='sundyn.softSetup.tipServerUrl'/></b><input type="text" name="requestAddress" id="requestAddress" class="input_comm" value="${content.requestAddress}" />  </div>
                          <div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><s:text name='sundyn.softSetup.logo'/></b><input type="text" name="logo" id="logo" class="input_comm" value="${content.logo}" /><input value="<s:text name='sundyn.softSetup.upload'/>" type="button" onclick="baseUploadPic()"  /><input type="button" value="清空" onclick="$('#logo').val('');$('#picLogo').attr('src','');$('#picLogo').parent().hide();" />  </div>
					      <div class="left"><b><s:text name='sundyn.softSetup.require'/></b></div>
					      <div class="left" style="display:none;"><img src="${content.logo}" id="picLogo" style="width: 133px;height: 37px;" /></div>
					      <div style="display: none;"><b><s:text name='sundyn.softSetup.contentDegreeStandard'/></b><input type="text" name="standard" id="standard" class="input_comm" value="${content.standard}" />  </div>
 					  </div>
 					  <div id="content3" style="display: none;">
						 <s:text name='sundyn.softSetup.moningUpTime'/><input type="text" value="${work2.sam}" size="50"	name="sam" id="sam" class="input_comm" 		  /><br/>
		         		 <s:text name='sundyn.softSetup.moningDownTime'/><input type="text" value="${work2.eam}" size="50"	name="eam" id="eam" class="input_comm" /><br/>
	          		 	 <s:text name='sundyn.softSetup.afternoonUpTime'/><input type="text" value="${work2.spm}" size="50"	name="spm" id="spm" class="input_comm" /><br/>
		         		 <s:text name='sundyn.softSetup.afternoonDownTime'/><input type="text" value="${work2.epm}" size="50"	name="epm" id="epm" class="input_comm" /><br/>
  					  </div>
  					  <div id="content4" style="display: none;">
  					      <ul id="temps">
  					         <c:forEach   var="temp" items="${m7temp}" varStatus="index">
  					              <c:set var="k" value="k3" />
         		  					      <c:if test="${temp.k3== 'true'}">
	  					             <c:set  var="k" value="k3"></c:set>
	  					          </c:if>
	  					          <c:if test="${temp.k4== 'true'}">
	  					             <c:set  var="k" value="k4"></c:set>
	  					          </c:if>
	  					          <c:if test="${temp.k5== 'true'}">
	  					             <c:set  var="k" value="k5"></c:set>
	  					          </c:if>
	  					          <c:if test="${temp.k6== 'true'}">
	  					             <c:set  var="k" value="k6"></c:set>
	  					          </c:if>
		  					       		 <li <c:if test="${temp.isuse== 'true'}"> class="curentTemp" </c:if> ><img src="m7temp/${temp.tempUrl}/${k}/k.jpg" style="width: 191px; height: 129px;"/> <br/>${temp.tempName}<input type="button" onclick="basePreviewTemp(this,'${temp.tempUrl}')" value="预览"/>    <input type="button" onclick="baseDelTemp(this)" value="删除"/></li>
	  					         </c:forEach>
	   					      </ul>
   					      <div> <a href="javascript:baseUploadTemp()"><s:text name='sundyn.softSetup.add'/></a></div>
    				  </div>
   					  <div id="content5" style="display: none;">
   					       <ul id="stars">
	   					       <c:forEach var="star" items="${stars}">
		   					        <li>
		   					        	<b><s:text name='sundyn.softSetup.contentRate'/></b><input type="text" value="${star.star100}" class="input_comm" name="prate" /><b><s:text name='sundyn.softSetup.up'/></b><input type="text" value="${star.star10}"  class="input_comm" name="pgrade"/><b><s:text name='sundyn.softSetup.mark'/></b><input type="text" value="${star.star}" class="input_comm" name="pstar" /><b><s:text name='sundyn.softSetup.star'/></b>  <img src="images/tp_add.gif"  onclick="starAdd()"/> <img src="images/tp_del.gif" onclick="starDel(this)" />
		    					    </li>
		    				   </c:forEach>
      				      </ul>
   					  </div>
   					   <div id="content6" style="display: none;MARGIN-RIGHT: auto;MARGIN-LEFT: auto;" align="center">
   					     <span style="color:red;">*&nbsp;&nbsp;<s:text name="sundyn.can.write"/></span>
   					     <div align="left" style="width:200px;">

		         		 <input type="checkbox" name="employeeInfoSet1" value="1" id="est1" <c:if test="${employeeInfoSet.employeeName== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.employeeName'/><br/>
	          		 	 <input type="checkbox" name="employeeInfoSet2" value="2" id="est2" <c:if test="${employeeInfoSet.job_desc== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.post'/><br/>
		         		 <input type="checkbox" name="employeeInfoSet3" value="3" id="est3" <c:if test="${employeeInfoSet.employeeJobNum== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.employeeJobNum'/><br/>
		         		 <input type="checkbox" name="employeeInfoSet4" value="4" id="est4" <c:if test="${employeeInfoSet.employeeCardNum== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.employeeCardNum'/><br/>
		         		 <input type="checkbox" name="employeeInfoSet5" value="5" id="est5" <c:if test="${employeeInfoSet.star== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.star'/><br/>
		         		 <div id="est66">
	          		 	 <input type="checkbox" name="employeeInfoSet6" value="6" id="est6" <c:if test="${employeeInfoSet.phone== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.employee.contact'/><br/>
		         		 </div>
		         		 <input type="checkbox" name="employeeInfoSet7" value="7" id="est7" <c:if test="${employeeInfoSet.windowName== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.windowName'/><br/>
		         		 <input type="checkbox" name="employeeInfoSet8" value="8" id="est8" <c:if test="${employeeInfoSet.deptname== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.deptname'/><br/>
		         		 <input type="checkbox" name="employeeInfoSet9" value="9" id="est9" <c:if test="${employeeInfoSet.unitName== 'true'}">checked="checked"</c:if> />&nbsp;&nbsp;<s:text name='sundyn.column.unitName'/><br/>
  					  	</div>
  					  </div>
 				</div>
				<div style="clear: both;"><input type="button" value="<s:text name='sundyn.softSetup.save'/>" onclick="sundynSetSave()" /> <input type="button" value="<s:text name='sundyn.softSetup.cancel'/>"/> </div>
			</div>
 		</div>
		<div id="dialog" style="width: 700px; display: none;"></div>
	</body>
</html>
<script>
if(document.getElementById('star').checked){
	document.getElementById('est66').style.display='';
}
if(!document.getElementById('star').checked){
	document.getElementById('est66').style.display='none';
}
</script>