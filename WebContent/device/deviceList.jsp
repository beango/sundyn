<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>设备列表</title>
        <link rel="stylesheet" href="css/common_<s:text name='sundyn.language' />.css" type="text/css" />
        <link rel="stylesheet" href="lib/layui/css/layui.css"  media="all">

        <script type="text/javascript" src="js/dojo.js"></script>
        <script type="text/javascript" src="js/dialog.js"></script>
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript" src="js/my_<s:text name='sundyn.language' />.js"></script>
        <script type="text/javascript" src="lib/layer/layer.js"></script>
        <script type="text/javascript" src="lib/layui/layui.js"></script>
        <script type="text/javascript" src="js/myAjax.js"></script>
        <script type="text/javascript" src="js/application.js?1"></script>
        <script language="javascript" type="text/javascript" src="My97DatePicker/WdatePicker.js"></script>
	</head>
	<body>

		<div class="layui-form">
            <div>
                <div class="layui-inline">
                    <label class="layui-form-label">MAC地址：</label>
                    <div class="layui-input-inline">
                        <input type="text" class="input_comm" id="mac" name="mac" value="${mac}" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">批次：</label>
                    <div class="layui-input-inline">
                        <input type="text" class="input_comm" id="batchno" name="batchno" value="${batchno}" />
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">开始时间：</label>
                    <div class="layui-input-inline">
                        <input type="text" class="input_comm" id="startDate1" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                        至
                        <input type="text" class="input_comm" id="endDate1" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" />
                    </div>
                </div>
                <div class="layui-inline">
                    <div class="layui-input-inline">
                        <a class=" layui-btn" href="#" onclick="deviceQuery()">查询</a>
                        <a class=" layui-btn" href="#" onclick="deviceToAdd('','添加')">添加</a>
                        <a class=" layui-btn" href="#" onclick="batchCert()">批量授权</a>
                        <a class=" layui-btn" href="#" onclick="toBatch()">批次管理</a>
                    </div>
                </div>
            </div>
			<div>
				<table class="layui-table" lay-filter="demo" id="demo">
                    <thead>
                    <tr>
                          <th lay-data="{checkbox:true}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"><s:text name='sundyn.column.index' /></th>
                          <td lay-data="{field:'a',width:'20%'}" align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">批次号</td>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">批次名</td>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">MAC地址</td>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">添加时间</td>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">最后在线时间</td>
                        <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1">授权</td>
                          <td align="center" valign="middle" background="images/table_bg_03.jpg" class="px13_1"></td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${pager==null}"><s:text name='sundyn.nodate' /> </c:if>
				    <c:if test="${pager!=null}">
                        <c:forEach items="${pager.pageList}" var="data" varStatus="index">
						<tr check="false" mac="${data.mac}" batchname="${data.batchname}">
							<th style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
							    ${index.index+1}
							</th>
							<td style="text-align: left;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.deviceno}
							</td>
                            <td style="text-align: left;text-overflow:ellipsis;overflow:hidden;">
                                <c:if test="${data.batchid2!=null}">${data.batchname}(${data.batchid2})</c:if>
                            </td>
                            <td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.mac}
                            </td>
                            <td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.ctime}
                            </td>
                            <td style="text-align: center;text-overflow:ellipsis;overflow:hidden;">
                                    ${data.lastonlinetime}
                            </td>
                            <td style="text-align: center;">
                                <c:if test="${data.cerfile!=''}"><a style="color:dodgerblue;" href="cer/${data.mac}.cer" onclick="event.stopPropagation();">已授权</a></c:if>
                                <a href="#" class="layui-btn layui-btn-sm layui-btn-warm" onclick="deptGenCer('${data.mac}','${data.batchname}')">授权</a>
                            </td>
							<td style="text-align: center;">
								<a href="#" onclick="event.stopPropagation();deviceToAdd('${data.id}','<s:text name='sundyn.modifyOrupdate' />');"><s:text name='sundyn.modifyOrupdate' /></a>
							</td>
						</tr>
				        </c:forEach>
				 </c:if>
                    </tbody>
				</table>
				<div id="pp"></div>
		    </div> 
		</div>
        <div id="dialog" style="width: 600px; display: none;"></div>
	</body>
    <script type="text/javascript">
        initPager(${pager.getRowsCount()}, <%=request.getParameter("currentPage")==null?1:request.getParameter("currentPage")%>,<%=request.getParameter("pageSize")==null?20:request.getParameter("pageSize")%>);
        $("#demo").find("tbody>tr").bind("click",function(){
            var check = $(this).attr("check");
            if(check==="true"){
                $(this).prop("style", "background-color:#fff");//.siblings().prop("style", "background-color:#ffffff");
                $(this).attr("check", false);
            }
            else{
                $(this).prop("style", "background-color:#ececec");//.siblings().prop("style", "background-color:#ffffff");
                $(this).attr("check", true);
            }
        });

        //批量授权
        function batchCert(){
            $("#demo").find("tbody>tr").each(function(){
                var check = $(this).attr("check");
                var c = 0;
                if(check==="true"){
                    deptGenCer($(this).attr("mac"), $(this).attr("batchname"));
                    c++;
                }
                if (c==0){
                    layer.msg('没有选中任何记录!', {
                        icon: 2,
                        time: 800
                    }, function(){
                    });
                }
            });
        }

        function toBatch(){
            parent.openTab("批次管理","/batchquery.action");
        }
    </script>
</html>
