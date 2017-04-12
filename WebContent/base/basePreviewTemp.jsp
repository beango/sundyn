<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="dialog">
	<div class="title">
		<div class="text">
			选择模板
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" />
		</div>
	</div>
	<div class="content">
		<div class="content_main">
			 <img id="picTemp" src="m7temp/${tempUrl}/k3/k.jpg" style="width:300px ;height: 170px;"><br/>
 			  请选择播放列表： <select id="playListId">
			        <c:forEach var="platList" items="${playListList}">
				   	 	<option value="${platList.playListId}">${platList.playListName}</option>
				    </c:forEach>
			   </select>
			 <div>
			   <input type="hidden" id="tempUrl" name="tempUrl" value="${tempUrl}" />
			   <input type="hidden" id="k" name="k" value="k3"  />
			   <input type="hidden" id="index" name="index" value="${index}"  />
			   <img src="images/3j.gif" onclick="basePreviewTempK('k3')"  />
			   <img src="images/4j.gif" onclick="basePreviewTempK('k4')"  />
			   <img src="images/5j.gif" onclick="basePreviewTempK('k5')"  />
			   <img src="images/6j.gif" onclick="basePreviewTempK('k6')"  />
			   
 			 </div>
		</div>
	</div>
	<div class="bottom">
		<div class="close">
			<a href="#"><img src="<s:text name='sundyn.pic.ok' />"
					onclick="baseSelectTemp()" class="hand" /> </a>
		</div>
		<div class="nofind">
			<a href="#"><img src="<s:text name='sundyn.pic.close' />"
					onclick="closeDialog()" class="hand" /> </a>
		</div>
	</div>
</div>
