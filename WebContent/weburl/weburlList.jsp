<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet"
	href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
<link rel="stylesheet" href="css/dialog.css" type="text/css" />
<link rel="stylesheet" href="js/easyui-1.5.3/themes/bootstrap/easyui.css" type="text/css" />
<link rel="stylesheet" href="js/easyui-1.5.3/themes/icon.css" type="text/css" />
<title><s:text name="sundyn.query.weburl"></s:text></title>
<script type="text/javascript" src="js/easyui-1.5.3/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui-1.5.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/dojo.js"></script>
<script type="text/javascript" src="js/dialog.js?v1"></script>
<script type="text/javascript"
	src="js/my_<s:text name='sundyn.language' />.js?1"></script>
<!-- 配置文件 -->
<script type="text/javascript" src="js/ueditor/ueditor.config.js"></script>
<!-- 编辑器源码文件 -->
<script type="text/javascript" src="js/ueditor/ueditor.all.min.js"></script>
<LINK rel=stylesheet href="js/ueditor/themes/default/css/ueditor.css"></LINK>
<!-- 实例化编辑器 -->
<script type="text/javascript">
$(function(){
	loadPage();
	
	
});

function loadPage(pageIndex, pageSize){
	if(!pageIndex) pageIndex = 1;
	if(!pageSize) pageSize = 20;
	$.ajax({
		url:"weburlListAjax.action?currentPage=" + pageIndex + "&pageSize="+pageSize,
		success:function(data){
			$("#tabContent>tbody").html(data);
			$('#pp').pagination({
			    total:'${pager.rowsCount }',
			    pageSize:20,
			    pageList: [10,20,50],//可以设置每页记录条数的列表 
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		        onChangePageSize:function(pageSize){
		        	//loadPage(pageSize);
		        },
		        onSelectPage: function(pageNumber, pageSize){
		        	loadPage(pageNumber, pageSize);
		        }
			});
		}
    });
}
</script>
</head>
<body>
<div id="cc" class="easyui-layout" style="width: 100%; height: 95%; overflow: auto; margin: 0; margin-bottom: 0;">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
					style="border-color: #FFFFFF;">
			<tr>
				<td style="border-color: #FFFFFF;" align="left"><img
								src="<s:text name='sundyn.pic.add' />" width="55" height="25"
								onclick="weburToAdd();" class="hand" />
					<input type="hidden" name="managerId" id="managerId" value="${managerId}" />
				</td>
			</tr>
		  </table>
		  <table id="tabContent" width="100%" cellpadding="0" cellspacing="0"
				style="border-top: 1px solid #bad6ec; border-right: 1px solid #bad6ec;">
				<thead>
					<tr style="height:30px">
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1"><s:text
							name='sundyn.column.index' /></td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1"><s:text
							name='sundyn.weburl.name' /></td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1"><s:text
							name='sundyn.weburl.url' /></td>
					<td align="center" valign="middle"
						background="images/table_bg_03.jpg" class="px13_1"><s:text
							name='sundyn.column.operation' /></td>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
</div>
<div id="pp" class="easyui-pagination" style="background:#efefef;border:1px solid #ccc;"></div>
<div id="dialog-window">
</div>
</body>
</html>
