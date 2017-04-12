<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@taglib prefix="s" uri="/struts-tags" %>
<div class="fengge">&nbsp;</div>
<div class="title640"> <h3><s:text name='sundyn.playList.title' /></h3> </div>
<div style="width:638px; height:290px;" class="kuang">
    <div class="fengge" style="height:25px;">&nbsp;</div>
    <div>
	    <table width="50%" border="0" cellspacing="0" cellpadding="0" style="border-color:#FFFFFF;">
	       <tr>
	         <td style="border-color:#FFFFFF;" align="center">	<input name="keyword" id="keyword" class="input_comm" /></td>
	         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.query' />" width="55" height="25" onclick="playListQueryAjax()" class="hand"/></td>
	         <td style="border-color:#FFFFFF;" align="left"><img src="<s:text name='sundyn.pic.add' />" width="63" height="25" onclick="playListAddDialog()" class="hand"/></td>
	      </tr>
	     </table>
    </div>
    <div class="fengge" style="height:25px;">&nbsp;</div>
	<table width="90%"   cellpadding="0" cellspacing="0" style="border-top: 1px solid #bad6ec; border-right: 1px solid #bad6ec;">
	  <tr>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.playListId' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.playListName' /></td>
	    <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
	  </tr>
	   <c:forEach items="${pager.pageList}" var="playList">
			<tr>
				<td style="text-align: center;">
					${playList.playListId}
				</td>
				<td style="text-align: center;">
				<div style="width:70px;height:20px;text-align: center;text-overflow:ellipsis;overflow:hidden;" >
					 ${playList.playListName}
					 </div>
				</td>
				<td style="text-align: center;">
					<a href="javascript:playListEditDialog(${playList.playListId});"><s:text name='sundyn.playList.editTitle' /></a>
					<a href="javascript:playListDel(${playList.playListId});"><s:text name='sundyn.del' /></a>
					<a href="javascript:playListUpdateDialog(${playList.playListId});"><s:text name='sudnyn.playList.updateM7' /></a>
					<a href="javascript:playListConfigDialog(${playList.playListId});"><s:text name='sudnyn.playList.editConfigFile' /></a>
				</td>
			</tr>
		</c:forEach>
	</table>
    <div class="fengge" style="height:15px;">&nbsp;</div>
	<div>${pager.pageTextAjax}</div>
</div> 