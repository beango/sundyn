<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
<div class="dialog">
			<div class="title">
				<div class="text">
						<s:text name='sundyn.advice.question.add1' />
				</div>
				<div class="close">
					<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()"/>
				</div>
			</div>
			<div class="content" style="margin:0 auto;">
				<div class="content_main" >
<!--				<br/>-->
				<br/>
 				 <div style="padding-left: 50px" align="left"><s:text name='sundyn.advice.question' /><s:text name='sundyn.colon' /><input style="width:220px" name="notice.title" class="input_comm" id="adviceQuestion"  value="" /></p>
 				  <s:text name='sundyn.advice.choose' /><s:text name='sundyn.colon' />
 				 </div>
 				 <!-- 
 				 <div style="padding-left: 50px;padding-top: 10px" align="left" ></div> -->
 				 
 				 <ul id="addInput" >
				        <li style="width:400px">
				        	<input style="width:300px" type='text'  name='as' value='' /> <img src="images/tp_add.gif"  onclick="addInput()"/>
					    </li>
      			</ul>
			   </div>
			</div>
			<div class="bottom">
				<div class="close">
					<img src="<s:text name="sundyn.pic.ok" />" onclick="adviceAdd();"  style="cursor: pointer;"/>
				</div>
				<div class="nofind">
					<img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
				</div>
			</div>
		</div>  
		