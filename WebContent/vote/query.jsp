<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
  		<script type="text/javascript" src="js/dojo.js"></script>
  		<script type="text/javascript" src="js/dialog.js"></script>
   	    <script type="text/javascript" src="js/json.js"></script>
		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
 		<title><s:text name='sundyn.title'/></title>
 	</head>
	<body>
		<div id="man_zone">
			<div class="fengge"></div>
			<div class="title730none">
				<h3><s:text name='sundyn.vote.title' /></h3>
			</div>
			<div style="width: 728px;margin: 0 auto;height: 380px; overflow:hidden;" class="kuang">
 				<div class="fengge"></div>
 				<div id="vote">
	 				<c:forEach items="${ls}" var="vote" varStatus="index">
	 				    <c:if test="${index.index % 2 ==0}">
	                          <div class="a718"><div     <c:if test="${vote.voteIsUse!=1}"> class="tplefta"   </c:if> <c:if test="${vote.voteIsUse==1}"> class="tpleftb"   </c:if>   id="vote${vote.voteId}">${vote.voteTitle}</div><div class="tpright"> <a href="#" onclick="voteResult(${vote.voteId})"><s:text name='sundyn.vote.voteResult' /></a> <a href="#" onclick="voteUse(${vote.voteId})"><s:text name='sundyn.vote.use' /></a> <a href="#" onclick="voteDel(${vote.voteId})"><s:text name='sundyn.del' /></a></div></div> 				    
	 				    </c:if>
	 					<c:if test="${index.index % 2 !=0}">
	                          <div class="b718"><div   <c:if test="${vote.voteIsUse!=1}"> class="tplefta"   </c:if> <c:if test="${vote.voteIsUse==1}"> class="tpleftb"   </c:if> id="vote${vote.voteId}">${vote.voteTitle}</div><div class="tpright"><a href="#" onclick="voteResult(${vote.voteId})"> <s:text name='sundyn.vote.voteResult' /></a><a href="#" onclick="voteUse(${vote.voteId})"><s:text name='sundyn.vote.use' /></a>	<a href="#" onclick="voteDel(${vote.voteId})"><s:text name='sundyn.del' /></a></div></div> 				    
	 				    </c:if>
	 				</c:forEach>
 				</div>
  				<div class="c718"> 
  				    <div class="tpleftc"><s:text name='sundyn.vote.voteTitle' /><input type="text" name="voteTitle" id="voteTitle" class="input_comm" style="width:400px;" /> <span>1/80</span>  </div>
  				</div>
 				 <ul class="tplefte" id="voteSelect">
				      <li>
				       	<s:text name='sundyn.vote.selection' /><input type="text" name="voteSelect"   /><img src="images/tp_add.gif" onclick="voteSelectAdd()" /><img src="images/tp_del.gif" onclick="voteSelectDel(this)" />
 				      </li>
 				      <li>
				       	<s:text name='sundyn.vote.selection' /><input type="text" name="voteSelect"   /><img src="images/tp_add.gif" onclick="voteSelectAdd()" /><img src="images/tp_del.gif" onclick="voteSelectDel(this)" />
 				      </li>
				 </ul>
				 <div><s:text name='sundyn.vote.isMutli' /><input type="checkbox" name="voteNum" id="voteNum" /></div>
  				<div class="d718">  <img src="<s:text name='sundyn.vote.pic.createVote' />" onclick="voteAdd()" /> <img src="<s:text name='sundyn.pic.cancel' />" onclick="voteCancel()" /> </div>
 			</div>
 		</div>
		<div style="float: left;"><img height="7" width="763" border="0" src="images/man_zone_bottom.gif" /></div>
		<div id="dialog" style="width: 700px; display: none;"></div>
	</body>
</html>
