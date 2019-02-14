<%@ page pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<html>
	<head></head>
	<body>
<div class="dialog">
	<div class="title">
		<div class="text">
			<s:text name='sundyn.query.seeDemo' />
		</div>
		<div class="close">
			<img border="0" src="images/dialog_close.gif" class="hand" onclick="closeDialog()" />
		</div>
	</div>
	<div class="content">
		<div class="content_main" >
 				 <table  border="0" class="querydemo">
					 <tr>
						  <td class="ftitle" width="25%">
						   ${ls[0]}
						  </td>
						  <td class="ftext" width="25%">
						  ${info[0]}
						  </td>
						  <td class="ftitle" width="25%">
						  ${ls[1]}
						  </td>
						  <td class="ftext" width="25%">
						  ${info[1]}
						  </td>
					 </tr>
					 <tr>
						  <td class="ftitle">
						   ${ls[2]}
						  </td>
						  <td class="ftext">
						  ${info[2]}
						  </td>
						  <td class="ftitle">
						  ${ls[3]}
						  </td>
						  <td class="ftext">
						  ${info[3]}
						  </td>
					 </tr>
					 <tr>
						  <td class="ftitle">
						   ${ls[4]}
						  </td>
						  <td colspan="3">
						     <div class="ftext" style="border: 1px solid #c4deee;width: 300px;height: 150px;text-align:left;">
						  ${info[4]}
						     </div>
						  </td>
 					 </tr>
 				 </table>
 		</div>
	</div>
	<div class="bottom">
			<img src="<s:text name='sundyn.pic.close' />" onclick="closeDialog()"  class="hand" />
	</div>
</div>
</body></html>