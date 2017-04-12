<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
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

				<br/>
				<br/>
				  <table width="100%" height="101" border="0" cellpadding="0" cellspacing="0" style="border-color: #e9f5fd;">
					  <input type="hidden" name="advice.question.id" id="aid" value="${advice.question.id }"/>
					  <tr>
						    <td width="32%" align="right" style="border-color: #e9f5fd;">
						    <s:text name='sundyn.advice.question' /><s:text name='sundyn.colon' />
	 					    </td>
	 					    
						    <td width="50%" align="left" style="border-color: #e9f5fd;">
						       <input name="notice.title" class="input_comm" id="adviceQuestion"  value="${advice.question.question }" />
						    </td>
					    </tr>

					  <tr>
					    <td colspan="2" style="border-color: #e9f5fd;"><span style="color:red;"><s:text name='sundyn.advice.question.example' />	 </span></td>
					  </tr>
					   <tr >
					    <td align="right"  style="border-color: #e9f5fd;"><s:text name='sundyn.advice.choose' /><s:text name='sundyn.colon' /></td>
					    <td align="left"  style="border-color: #e9f5fd;"> 
<%--					    	<input type="text" name="notice" id="notice"  value="${dept.notice}"  />--%>
					    	<textarea name="notice.content"  id="answerString" rows="5"  cols="30"><s:iterator value="advice.answers"  >${answer};</s:iterator></textarea>
					    </td>
					  </tr>
					 
 				 </table>
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