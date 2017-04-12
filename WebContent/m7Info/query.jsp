<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office">
 	<head>
 		<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE" ></META>
		<META HTTP-EQUIV="Expires" CONTENT="Mon， 04 Dec 1999 21：29：02 GMT" ></META>
	 	 <STYLE type="text/css">
			 v\:* { Behavior: url(#default#VML) }
	         o\:* { behavior: url(#default#VML) }
	         #PieDiv{
		         font-family:arial;
 		      	 line-height: normal;
  	         }
  	         #PieDiv div{
  	            font-size: 9px;
             }
		 </STYLE>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
		<link rel="stylesheet" href="css/dialog.css" type="text/css" />
		<title><s:text name='sundyn.title'/></title>
		<script type="text/javascript" src="js/dojo.js"></script>
		<script type="text/javascript" src="js/dialog.js"></script>
		<script type="text/javascript" src="js/wz_jsgraphics.js"></script>
		<script type="text/javascript" src="js/pie.js"></script>
		<script type="text/javascript" src="js/Pie3D.js"></script>
 		<script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
 	</head>
	<body>
		<div id="man_zone">
			<div class="sundyn_main">
			   <div class="fengge">&nbsp;</div>
			   <table width="98%" class="t" >
					<tr>
						<td  class="tdtitle">
							<s:text name="sundyn.system.checkM7Info.macAddress" />
						</td>
						<td   class="tdtitle">
						<s:text name="sundyn.system.checkM7Info.ipAddress"/>
						</td>
						<td  class="tdtitle">
						<s:text name="sundyn.system.checkM7Info.versionNumber"/>
 						</td>
						<td  class="tdtitle">
						<s:text name="sundyn.system.checkM7Info.windowName"/>
						</td>
						<td   class="tdtitle">
						<s:text name="sundyn.system.checkM7Info.broadcastListName"/>
						</td>
						<td   class="tdtitle">
						<s:text name="sundyn.system.checkM7Info.upgradeTime"/>
						</td> 
					</tr>
					<c:forEach items="${pager.pageList}" var="query">
						<tr>
							<td   class="td">
								${query.mac}&nbsp;
							</td>
							<td   class="td">
								${query.ip}&nbsp;
							</td>
							<td   class="td">
								${query.version}&nbsp;
							</td>
							<td   class="td">
								${query.windowName}&nbsp;
							</td>
							<td  class="td">
								${query.playListName}&nbsp;
 							</td>
							<td  class="td">
							    ${query.date}&nbsp;
							</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${pager.rowsCount == 0}">
					<div class="sundyn_rows">
						<s:text name="sundyn.system.checkM7Info.noRecord" />
					</div>
				</c:if>
				<c:if test="${pager.rowsCount > 0}">
					<div class="sundyn_rows">
						${pager.pageTextCn}
					</div>
		 		</c:if>
  			</div>
		</div>
		<div style="float: left;"><img src="images/man_zone_bottom.gif" width="763" height="7" border="0" /></div>
		<div id="dialog" style="width: 479px; height: 328px; display: none;"></div>
 	</body>
</html>