<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<div class="dialog">
			<div class="title">
				<div class="text">
						<s:text name='sundyn.advice.question.update1' />
				</div>
				<div class="close">
					<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()"/>
				</div>
			</div>
			<div class="content" style="margin:0 auto;">
				<div class="content_main" >
<!--				<br/>-->
<!--				<br/>-->
<input type="hidden" name="advice.question.id" id="aid" value="${advice.question.id }"/>
 				 <br/>
 				 <div style="padding-left: 50px" align="left"><s:text name='sundyn.advice.question' /><s:text name='sundyn.colon' /><input name="notice.title" style="width:220px" class="input_comm" id="adviceQuestion"  value="${advice.question.question }" /></p>
 				  <s:text name='sundyn.advice.choose' /><s:text name='sundyn.colon' />
 				 </div>
 				  <ul id="addInput">
 				   <s:iterator value="advice.answers" status="index" >
 				   <li style="width:400px">
		   					 <input style="width:300px" type='text'  name='as' value='${answer}' /> <c:if test="${index.index ==0 }"><img src="images/tp_add.gif"  onclick="addInput()"/>   </c:if><c:if test="${index.index !=0 }"><img src="images/tp_del.gif" onclick="delInput(this)" /> </c:if>
		    			</li>
		    	 </s:iterator>
		    	 </ul>
			   </div>
			   </div>
			<div class="bottom">
				<div class="close">
					<img src="<s:text name="sundyn.pic.ok" />" onclick="adviceUpate();"  style="cursor: pointer;"/>
				</div>
				<div class="nofind">
					<img src="<s:text name="sundyn.pic.close" />"  onclick="closeDialog()"   style="cursor: pointer;" />
				</div>
			</div>
		</div>  