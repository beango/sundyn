<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title><s:text name='sundyn.notice.list' /></title>
        <link rel="stylesheet" href="css/style.css" type="text/css"/>
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">
        <link rel="stylesheet" href="lib/ztree/css/metroStyle/metroStyle.css" type="text/css" />
        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
	</head>
	<body>
    <div class="place">
        <span><s:text name="main.placetitle" /></span>
        <ul class="placeul">
            <c:forEach items="${navbar_menuname}" var="menu">
                <li><a href="#">${menu.name}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div class="layui-form" lay-filter="f">
        <div class="layui-inline">
            <div class="layui-input-inline">
                <input type="button" class="button" style="background: url(images/button_bg.gif)" onclick="noticToAdd('<s:text name='sundyn.notice.add' />');" value="<s:text name="main.add" />">
            </div>
        </div>
			<div>
                <table class="tablelist" lay-filter="tbl" id="demo">
                    <thead>
                      <tr>
                          <th><s:text name='sundyn.column.index' /></th>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.notice.title' /></td>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.operation' /></td>
                      </tr>
                    </thead>
                    <c:if test="${notices==null}"><s:text name='sundyn.nodate' /> </c:if>
				    <c:if test="${notices!=null}">
                        <c:forEach items="${notices}" var="notice" varStatus="index">
						<tr>
							<td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
							      ${index.index+1}
							</td>
							<td style="text-align: left;text-overflow:ellipsis;overflow:hidden;">
                                    ${notice.title}
							</td>
							<td style="text-align: center;">
								<a href="javascript:noticToUpate('${notice.id}','<s:text name='sundyn.notice.update' />');"><s:text name='sundyn.modifyOrupdate' /></a>
								<a href="javascript:noticDelete('${notice.id}');"><s:text name='sundyn.del' /></a>
							</td>
						</tr>
				        </c:forEach>
				 </c:if>
				</table>
				<div id="pp"></div>
		    </div> 
		</div>
        <div id="dialog" style="width: 600px; display: none;"></div>
	</body>
    <script type="text/javascript">
        layui.use('layer', function() {});
        initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);

        // weburl 通知公告 更新框
        function noticToUpate(data, title) {
            new dialog().iframe("noticeToUpdate.action?id="+data,{title:title,w:'100%',h:"100%",full:true});
        }

        // notice 通知公告添加框
        function noticToAdd(title) {
            new dialog().iframe("noticeToAdd.action", {title: title,w:'100%',h:"100%",full:true});
        }

        // 删除 通知公告
        function noticDelete(data){
            if (confirm('<s:text name="main.delete.confirm" />')){
                dojo.xhrPost({url:"noticeDelete.action", content:{id:data}, load:function (resp, ioArgs) {
                        succ('<s:text name="main.delete.succ" />', function(){
                            refreshTab();
                        });
                    }});
            }
        }
    </script>
</html>
